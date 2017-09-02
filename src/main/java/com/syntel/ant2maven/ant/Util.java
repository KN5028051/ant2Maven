package com.syntel.ant2maven.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.syntel.ant2maven.constants.Ant2MavenConstant;
import com.syntel.nexusrepo.Data;
import com.syntel.nexusrepo.SearchNGResponse;



@Component
public class Util {
	private static Logger log = Logger.getLogger( Util.class.getSimpleName() );

	private Properties prop;
	private WebResource service ;
	public Properties getProp() {
		return prop;
	}
	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public void loadProperties(String propfile) throws IOException{
		
		prop = new Properties();
		InputStream inStream = new FileInputStream(propfile);;
		prop.load(inStream);
		inStream.close();
		
		log.info("Loaded properties " + propfile);
	}
	
	
	public Node getPathById(Document doc, String id)
			throws XPathExpressionException {

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath
				.compile("//path[@id=\"" + id + "\"]");
		
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);

		return node;
	}
	public Node getTargetByName(Document doc, String name)
			throws XPathExpressionException {

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath
				.compile("//target[@name=\"" + name + "\"]");
		
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);

		return node;
	}
	
	public String findVersionValue(String systemPath) {

		String regex = "(?!\\.)(\\d+(\\.\\d+)+)(?:[-.][A-Z]+)?(?![\\d.])$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(systemPath);
		String val1 =null;
		while (matcher.find()) {
			 val1 = matcher.group(1);
			}
		
		return val1;
	}
	public String processSystemPathValue(String systemPath) {

		
		 if(!"".equals(systemPath)&&systemPath.contains("\\")){
			 systemPath=		 systemPath.substring(systemPath.lastIndexOf("\\")+1,systemPath.length());
			String value=findVersionValue(systemPath);
			 if(null!=value && systemPath.contains(value)){
			 systemPath= systemPath.replace(findVersionValue(systemPath),"");
			 }
		 }
	
	
		return systemPath;
	}

	public String processAttributeValue(String dir) {

		String orig = dir;
		String regex = "\\{(.*?)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(dir);
		
		while (matcher.find()) {
			String val1 = matcher.group(1);
			
			String propval  = (String)prop.get(val1);
			
			propval = propval != null? propval : val1; 
					
			dir = dir.replace("${" + val1 + "}", propval);
			
			Pattern pattern2 = Pattern.compile(regex);
			Matcher matcher2 = pattern2.matcher(dir);
			
			while (matcher2.find()) {
				String val2 = matcher2.group(1);
				String propval2  = (String)prop.get(val1);
				
				propval2 = propval2 != null? propval2 : val2; 	
				dir = dir.replace("${" + val2 + "}", propval2);
				dir=dir.replace("../","");
			}
			
		}
		log.info(orig + " ==> " + dir);
		return dir;
	}
	public String getAbsolutePath(String parent, String file){
		
		File f = new File(parent, file);
		
		if(!f.exists()) {
			
			 f = new File(file);
		}
		if(!f.exists()){
			log.warning("File does not exist " + f.getAbsolutePath());
			
		}
		return f.getAbsolutePath();
	}
	
	public void runRepository() throws JsonParseException, JsonMappingException, IOException {
		this.service = getService();
		
		log.info("Check that Nexus is running");
		String nexusStatus = service.path("service").path("local").path("status").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class).toString();
		log.info("GET Nexus Version"+nexusStatus);
		String nexusVersion = service.path("service").path("local").path("status").accept(MediaType.APPLICATION_JSON).get(String.class).toString();
		log.info("Check that Nexus Version"+nexusVersion);
		/*RepositoryTargetResourceResponse request = new RepositoryTargetResourceResponse();
		RepositoryTargetResource data = new RepositoryTargetResource();
		data.setContentClass("maven2");
		data.setName(targetName);
		data.setPatterns(Arrays.asList(".*"));
		request.setData(data);
		service.path("service").path("local").path("repo_targets").post(request);
		*/
		
			
	}
	
	public Data findExistingDependency(String jarName) {
		String repoTargets = service.path("service").path("local").path("lucene").path("search")
				.queryParam("q", jarName).accept(MediaType.APPLICATION_JSON).get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Data data = null;
		
		Map <String,Data> dataMap=new HashMap<String,Data>();
		try {

			SearchNGResponse user = mapper.readValue(repoTargets, SearchNGResponse.class);
			for (int i = 0; i < user.getData().length; i++) {
				
				data=user.getData()[i];	
				if(!dataMap.containsKey(data.getLatestReleaseRepositoryId())){
				dataMap.put(data.getLatestReleaseRepositoryId(), data);
				}
			}
		
			
		} catch (IOException e) {
			log.info("Json object=>"+repoTargets);
			e.printStackTrace();
		}
		return handledLatestRelased(dataMap,data);
	}
	
	private Data handledLatestRelased(Map datamap,Data data) {
		
		if (!datamap.isEmpty()) {
			
			for (String pattern_Repo : Ant2MavenConstant.REPOSITERY_PATTERN) {

				if (datamap.containsKey(pattern_Repo)) {
					data=(Data) datamap.get(pattern_Repo);
					log.info("ArtifactId=>" + data.getArtifactId() + "groupId=>" + data.getGroupId() + "version=>"
								+ data.getVersion() + "latestReleaseRepositoryId::" + data.getLatestReleaseRepositoryId());
					break;
				}
			}
		}

		return data;

	}
	
	

	private WebResource getService() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		//client.addFilter(new HTTPBasicAuthFilter(Ant2MavenConstant.USER, Ant2MavenConstant.PASSWORD)); 
		return client.resource(getBaseURI());
	}

	private URI getBaseURI() {
		return UriBuilder.fromUri( Ant2MavenConstant.URL ).build();
	}
	public void setService(WebResource service) {
		this.service = service;
	}
	
}
