package com.syntel.ant2maven;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import com.syntel.ant2maven.ant.Build;
import com.syntel.ant2maven.ant.BuildReader;
import com.syntel.ant2maven.ant.CreatePomUtil;
import com.syntel.ant2maven.ant.Util;
import com.syntel.ant2maven.constants.Ant2MavenConstant;


public class Ant2Maven {
	private static Logger log = Logger.getLogger(Ant2Maven.class.getSimpleName());

	public static void main(String[] args) {

		String buildfile = Ant2MavenConstant.FILE_PATH + "\\" + Ant2MavenConstant.BUILD_XML;
		String propfile = Ant2MavenConstant.FILE_PATH + "\\" + Ant2MavenConstant.BUILD_PROPESRTIES;

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

		BuildReader reader = (BuildReader) context.getBean("buildReader");

		Util util = (Util) context.getBean("util");

		try {

			log.info("********** READING ANT BUILD **********");

			util.loadProperties(propfile);

			Build build = reader.process(buildfile);

			log.info("********** BUILD INFORMATION **********");

			log.info("PROJECT: " + build.getBasedir());
			log.info("SOURCE: " + build.getJavac().getSrcdir());
			log.info("OUTPUT: " + build.getJavac().getDestdir());

			CreatePomUtil createPomUtil = (CreatePomUtil) context.getBean("createPomUtil");
			createPomUtil.initialize(build);
			log.info("********** SUCESSFULLY DONE **********");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

	}

	
}
