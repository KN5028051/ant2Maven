package com.syntel.ant2maven.ant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class TargetProcessor {

	@Autowired
	private TaskProcessor taskProcessor;
	@Autowired
	private Util util;
	
	private static Map<String, Integer> nodeMap = new HashMap<String, Integer>();
	static{
		nodeMap.put("delete", 1);
		nodeMap.put("mkdir", 2);
		nodeMap.put("antcall", 3);
		nodeMap.put("javac", 4);
		nodeMap.put("compile", 4);
		nodeMap.put("echo", 5);
		nodeMap.put("copy", 6);
	}
	
	public void process(Build build, Document doc, String targetName) throws XPathExpressionException {
		
		Node defaultTarget = util.getTargetByName(doc, targetName);
		
		processDepends(doc, defaultTarget, build);
		
	}

	public void processDepends(Document doc, Node node, Build build)
			throws XPathExpressionException {

		if (node != null) {

			Element target = (Element) node;

			String depends = target.getAttribute("depends");

			String[] dependarr = depends.split(",");

			for (String depend : dependarr) {

				Node target1 = util.getTargetByName(doc, depend);

				processDepends(doc, target1, build);

			}
			processTarget(doc, target, build);
		}		
	}

	public void processTarget(Document doc, Node node, Build build)
			throws XPathExpressionException {

		Element target = (Element) node;
		
		NodeList nodes = target.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {

			Node tasknode = nodes.item(i);
			if (tasknode.getNodeType() == Node.ELEMENT_NODE) {

				Element task = (Element) tasknode;
				
				String nodeName = task.getNodeName();
				Integer key = nodeMap.get(nodeName);
				System.out.println("node Key : " + key);
				if(null == key){
					key = 999;
				}
				
				/*switch(key){
					case 1:	break;
					case 2:	break;		
					case 3:	String target1Name = task.getAttribute("target");
							Node callTarget = util.getTargetByName(doc, target1Name);
							processDepends(doc, callTarget, build);
							break;
					case 4: taskProcessor.processJavac(build, doc, tasknode);
							break;
					case 5: break;
					case 6: taskProcessor.processResources(build, doc, tasknode);
							break;
					default: System.out.println("Processing not defined for node name : " + nodeName);
							break;
				}*/
				if (task.getNodeName().equalsIgnoreCase("delete")) {

				} else if (task.getNodeName().equalsIgnoreCase("mkdir")) {

				} else if (task.getNodeName().equalsIgnoreCase("antcall")) {

					String target1Name = task.getAttribute("target");

					Node callTarget = util.getTargetByName(doc, target1Name);

					processDepends(doc, callTarget, build);

				} else if (task.getNodeName().equalsIgnoreCase("javac") ||task.getNodeName().equalsIgnoreCase("compile")) {

					taskProcessor.processJavac(build, doc, tasknode);

				} else if (task.getNodeName().equalsIgnoreCase("echo")) {

				} else if (task.getNodeName().equalsIgnoreCase("copy")) {

					taskProcessor.processResources(build, doc, tasknode);
				} 
				
			}
		}
	}
	
	
	

}
