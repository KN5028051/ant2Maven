package com.syntel.ant2maven.ant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Build {

	private String basedir;
	private String moduleArtifactId;
	
	
	public class Javac{
		
		private String srcdir;
		private String destdir;
	
		private List<String> classpath = new ArrayList<String>();
		
		public String getSrcdir() {
			return srcdir;
		}
		public void setSrcdir(String srcdir) {
			this.srcdir = srcdir;
		}
		public String getDestdir() {
			return destdir;
		}
		public void setDestdir(String destdir) {
			this.destdir = destdir;
		}
		public List<String> getClasspath() {
			return classpath;
		}
		public void addToClasspath(List<String> jarList) {
			this.classpath.addAll(jarList);
		}
		public void addToClasspath(String jar) {
			this.classpath.add(jar);
		}
	}
	
	private Javac javac;

	public Javac getJavac() {
		return javac;
	}
	public Javac newJavac() {
		this.javac = new Javac();
		return javac;
	}

	public class FileSet{
		
		private String todir;
		
		private List<String> files = new ArrayList<String>();
		
		
		public String getTodir() {
			return todir;
		}
		public void setTodir(String todir) {
			this.todir = todir;
		}
		public List<String> getFiles() {
			return files;
		}
		public void addFiles(List<String> files) {
			this.files.addAll(files);
		}
	}

	private List<FileSet> resources = new ArrayList<FileSet>();
	
	public List<FileSet> getResources() {
		return resources;
	}
	public FileSet addResource(String todir) {
		
		FileSet set = new FileSet();
		set.setTodir(todir);
		
		resources.add(set);
		
		return set;
	}
	public String getModuleArtifactId() {
		return moduleArtifactId;
	}
	public void setModuleArtifactId(String moduleArtifactId) {
		this.moduleArtifactId = moduleArtifactId;
	}
	public String getBasedir() {
		return basedir;
	}
	public void setBasedir(String basedir) {
		this.basedir = basedir;
	}
	
}
