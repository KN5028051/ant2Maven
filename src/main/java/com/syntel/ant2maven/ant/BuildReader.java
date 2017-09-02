package com.syntel.ant2maven.ant;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

@Component
public class BuildReader {
	
	@Autowired
	private TargetProcessor targetProcessor;
	@Autowired
	private Util util;
	@Autowired
	private Build build;
	
	public Build process(String buildfile) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(buildfile);

		Node node = doc.getDocumentElement();

		Element root = (Element) node;

		String defaultTargetName = root.getAttribute("default");
		String projectName = root.getAttribute("name");
		build.setModuleArtifactId(projectName);
		
		build.setBasedir((new File(buildfile)).getParentFile().getAbsolutePath());
		
		targetProcessor.process(build, doc, defaultTargetName);
		
		return build;
	}

}
