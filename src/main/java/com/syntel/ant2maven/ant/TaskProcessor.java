package com.syntel.ant2maven.ant;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.syntel.ant2maven.ant.Build.FileSet;
import com.syntel.ant2maven.ant.Build.Javac;


@Component
public class TaskProcessor {

	@Autowired
	private Util util;
	private static Logger log = Logger.getLogger( TaskProcessor.class.getSimpleName() );

	public void processResources(Build build, Document doc, Node node) {

		Element copytask = (Element) node;

		String todir = util
				.processAttributeValue(copytask.getAttribute("todir"));
		
		FileSet fset = build.addResource(todir);
		
		NodeList filesets = copytask.getElementsByTagName("fileset");

		List<String> resourceList = new ArrayList<String>();

		for (int i = 0; i < filesets.getLength(); i++) {

			Node node1 = filesets.item(i);
			Element fileset = (Element) node1;

			String dir = util
					.processAttributeValue(fileset.getAttribute("dir"));
			
			String includes = fileset.getAttribute("includes");
			String excludes = fileset.getAttribute("excludes");

			processDir(build.getBasedir(), resourceList, dir, includes, excludes);
		}
		fset.addFiles(resourceList);
	}

	public void processJavac(Build build, Document doc, Node node)
			throws XPathExpressionException {

		Element javactask = (Element) node;

		String srcdir = util
				.processAttributeValue(javactask.getAttribute("srcdir"));
		String destdir = util.processAttributeValue(javactask
				.getAttribute("destdir"));
		
		srcdir = util.getAbsolutePath(build.getBasedir(), srcdir);
		destdir = util.getAbsolutePath(build.getBasedir(), destdir);

		Javac javac = build.newJavac(); 
		javac.setSrcdir(srcdir);
		javac.setDestdir(destdir);

		String classpathrefId = javactask.getAttribute("classpathref");
		Node classPath = util.getPathById(doc, classpathrefId);
       if(null!=classpathrefId && !"".equals(classpathrefId)){
		processClassPath(build, doc, classPath);}
       else{
    	   processClassPath(build, doc, javactask);
       }
       processPathLocation(build, doc, javactask);
	}
	
	public void processPathLocation(Build build, Document doc, Node classPath) throws XPathExpressionException {

		NodeList nodes = classPath.getChildNodes();
		
		for (int i = 0; i < nodes.getLength(); i++) {

			Node tasknode = nodes.item(i);

			if (tasknode.getNodeType() == Node.ELEMENT_NODE) {

				Element task = (Element) tasknode;
				String classpathrefId = task.getAttribute("refid");
				if (null != classpathrefId && !"".equals(classpathrefId)) {
					Node pathElement = util.getPathById(doc, classpathrefId);
					processPathLocation(build, doc, pathElement);
				}
				String locationId = util.processAttributeValue(task.getAttribute("location"));
				if (null != locationId && locationId.contains(".jar")) {

					build.getJavac().getClasspath().add(locationId.replace(".jar",""));
				}

				
			}
		}
	}

	public void processClassPath(Build build, Document doc, Node classPath)  {
		if (null != classPath) {
			Element path = (Element) classPath;
			
			
			NodeList filesets = path.getElementsByTagName("fileset");

			List<String> jarList = new ArrayList<String>();

			for (int i = 0; i < filesets.getLength(); i++) {

				Node node1 = filesets.item(i);
				Element fileset = (Element) node1;

				String dir = util.processAttributeValue(fileset
						.getAttribute("dir"));

				NodeList includes = fileset.getElementsByTagName("include");
                 
				
				for (int j = 0; j < includes.getLength(); j++) {

					Node node2 = includes.item(j);
					Element include = (Element) node2;

					String filename = include.getAttribute("name");

					if (filename.contains("*")) {

						processJars(build.getBasedir(), jarList, dir);

					} else {

						String folder = util.getAbsolutePath(
								build.getBasedir(), dir);

						String jarpath = (new File(folder, filename))
								.getAbsolutePath();

						jarList.add(jarpath);
					}
				}
			
				String includeDir = util.processAttributeValue(fileset
						.getAttribute("includes"));
				
				if (includeDir.contains("*") && includes.getLength()==0) {

					processJars(build.getBasedir(), jarList, dir);

				} 
				
			}
			build.getJavac().addToClasspath(jarList);
		}
	}

	
	public void processJars(String basedir, List<String> jarList, String dir) {
		
		String abspath = util.getAbsolutePath(basedir, dir);
		
		processDirRecursively(jarList, abspath);
	}

	public void processDirRecursively(List<String> fileList, String dir) {

		File fd = new File(dir);

		System.out.println("Reading dir: " + fd.getAbsolutePath());

		File[] files = fd.listFiles();

		if (files != null) {
			for (File f : files) {

				if (f.isDirectory()) {

					processDirRecursively(fileList, f.getAbsolutePath());

				} else {
					String filepath = f.getAbsolutePath();

					fileList.add(filepath);
				}
			}
		} else {
			log.warning("No files found in " + fd.getAbsolutePath());
		}
	}

	public void processDir(String basedir, List<String> fileList, String dir, String includes, String excludes) {

		String abspath = util.getAbsolutePath(basedir, dir);

		File fd = new File(abspath);
		
		System.out.println("Reading dir: " + fd.getAbsolutePath());

		FileSystem fileSystem = FileSystems.getDefault();
		PathMatcher includematcher = null;
		PathMatcher excludematcher = null;
		if(includes != null && includes.length() > 0) {
			includematcher = fileSystem.getPathMatcher("glob:" + includes);
		}
		if(excludes != null && excludes.length() > 0) {
			excludematcher = fileSystem.getPathMatcher("glob:" + excludes);
		}
		File[] files = fd.listFiles();
		
		if (files != null) {
			
			for (File f : files) {
				
				if (f.isDirectory()) {
					
					processDir(basedir, fileList, f.getAbsolutePath(), includes, excludes);
					
				} else {

					String spath = f.getAbsolutePath();
					Path path = Paths.get(spath);
	
					boolean toadd = true;
					
					if(includematcher != null) {
						
						if(includematcher.matches(path)) {
							
							toadd = true;
						} else {
							toadd = false;
						}
						
					}
					if(excludematcher != null) {
								
						if(excludematcher.matches(path)) {
									
							toadd = false;
						} else {
							toadd = true;
						}
						
					}
					if(toadd){
						fileList.add(spath);
					}
				}
				
			}
		} else {
			log.warning("No files found in " + fd.getAbsolutePath());
		}
	}
}
