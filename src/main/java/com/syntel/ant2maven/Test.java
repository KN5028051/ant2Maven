package com.syntel.ant2maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.Target;

public class Test {

	public static void main1(String...args) throws IOException{
		Properties prop = new Properties();
		InputStream inStream = new FileInputStream("D:\\Subhash\\CPG\\properties file\\build.properties");;
		prop.load(inStream);
		inStream.close();
		
		
		String dir ="${resources}/${wsdl}";
		
		String regex = "\\{(.*?)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(dir);
		
		while (matcher.find()) {
			String val1 = matcher.group(1);
			
			dir = dir.replace("${" + val1 + "}", (String)prop.get(val1));
			
			Pattern pattern2 = Pattern.compile(regex);
			Matcher matcher2 = pattern2.matcher(dir);
			
			while (matcher2.find()) {
				String val2 = matcher2.group(1);
				
				dir = dir.replace("${" + val2 + "}", (String)prop.get(val2));
			}
			
		}
		System.out.println(dir);
	}
	public static void main2(String...args) throws IOException{
		
		FileSystem fileSystem = FileSystems.getDefault();
		//PathMatcher matcher = fileSystem.getPathMatcher("glob:/**//src//**");
		//PathMatcher matcher = fileSystem.getPathMatcher("glob:**/*.java");
		PathMatcher matcher = fileSystem.getPathMatcher("glob:**/wsdl/**");
		
		Path path = Paths.get("D:\\Sameer\\CPG_workspace\\cpg_common\\src\\main\\resources\\wsdl");

		System.out.println(matcher.matches(path));	
	}
	public static void main(String...args) throws IOException{
		
		 Project project = new Project();
	        File buildFile = new File("D:\\ant2Maven\\build.xml");
	        project.init();
	        ProjectHelper.configureProject(project, buildFile);

	        Path path = (Path) project.getReference("classpath");	
	        getSourceDirectories(project);
	        getTargetName(project);
	}
	
	public static List<String>  getTargetName(Project project){
		
		Hashtable<String, Target> projectTargets = project.getTargets();
		ArrayList<String> names = new ArrayList<String>();
		for (Target target : projectTargets.values()) {
			String name = target.getName();
			if (name.length() == 0) {
				// "no name" implicit target of Ant 1.6
				continue;
			}
			names.add(name);
		}
		return names;
	}
	
	public static List<String> getSourceDirectories(Project project) {
	    Hashtable<String, Object> properties = project.getProperties();
	    String absProjectPath = project.getBaseDir().getAbsolutePath();
	    List<String> paths = new ArrayList();

	   for(String key:properties.keySet()){
		   System.out.println("Key ::"+key+":: Value=>"+properties.get(key));
	   }
	    
	    if (properties.containsKey("src.dir")) {
	        String srcPath = (String)properties.get("src.dir");
	        srcPath = srcPath.substring(absProjectPath.length());

	        if (srcPath.startsWith("/")) {
	            srcPath = srcPath.substring(1);
	        }
	        paths.add(srcPath);
	    }
	    if (properties.containsKey("test.dir")) {
	        String testPath = (String)properties.get("test.dir");
	        testPath = testPath.substring(absProjectPath.length());

	        if (testPath.startsWith("/")) {
	            testPath = testPath.substring(1);
	        }
	        paths.add(testPath);
	    }
	    if (paths.isEmpty()) {
	        paths.add("src");
	        paths.add("test");
	    }
	    return paths;
	}
}
