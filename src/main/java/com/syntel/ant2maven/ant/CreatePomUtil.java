package com.syntel.ant2maven.ant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DeploymentRepository;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Repository;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.ReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.syntel.ant2maven.constants.Ant2MavenConstant;
import com.syntel.nexusrepo.Data;
@Component
public class CreatePomUtil {

	@Autowired
	private Util util;
	com.syntel.ant2maven.ant.Build build=null;

	public void initialize(com.syntel.ant2maven.ant.Build build) {
		setBuild(build);
		MavenXpp3Reader pomReader = new MavenXpp3Reader();
		Model model;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			model = pomReader.read(
					ReaderFactory.newXmlReader(new File(classLoader.getResource(Ant2MavenConstant.PREDEFIND_POM).getFile())));
			createGroupId(model);
			writeBuild(model);
			this.util.runRepository();
			handleDependency(model);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			FileWriter fileWriter = new FileWriter(Ant2MavenConstant.FILE_PATH+"\\"+Ant2MavenConstant.POM_XML);
			new MavenXpp3Writer().write(fileWriter, model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	 
	 private void createGroupId(Model model){
		 model.setGroupId( model.getGroupId() ); 
		 model. setArtifactId( build.getModuleArtifactId()); 
		 model.setVersion( model.getVersion() ); 
		 model.setName( model.getName() ); 
		 model.setUrl( model.getUrl() ); 
		 model.setPackaging( model.getPackaging() ); 
	 	 
	 }

	private void writeBuild(Model model) {
		Build build = new Build();
		build.setFinalName(this.build.getModuleArtifactId());
		build.setDirectory(getBasedir() + "/target");
		if (null != this.build.getJavac() && null != this.build.getJavac().getSrcdir()) {
			build.setSourceDirectory(getBasedir() + this.build.getJavac().getSrcdir());
		}
		if (null != this.build.getJavac() && null != this.build.getJavac().getDestdir()) {
			build.setOutputDirectory(getBasedir() + this.build.getJavac().getDestdir());
		}
		build.setTestSourceDirectory(getBasedir() + "/src/test/java");
		build.setTestOutputDirectory(getBasedir() + "/target/test-classes");

	}
	 
	 private void handleDependency(Model model){
		 
		 if ((model.getDependencies() != null)) {
	            List<Dependency> deps = model.getDependencies();
	            
	            for(String jar: build.getJavac().getClasspath()){
	            	Dependency dep= createNewDependency(deps.get(1),jar,model);
	            	deps.add(dep);
	            	
	            	
	            }
	        }
	 }
	 
	private Dependency createNewDependency(Dependency dep, String systemPath,Model model) {
		Dependency dependency = new Dependency();
		String jarName = this.util.processSystemPathValue(systemPath);
		Data exsitingDependency = this.util.findExistingDependency(jarName);
		if (null != exsitingDependency) {
			dependency.setGroupId(exsitingDependency.getGroupId());
			dependency.setArtifactId(exsitingDependency.getArtifactId());
			dependency.setScope(dep.getScope());
			dependency.setVersion(exsitingDependency.getVersion());
			
		} else {

			dependency.setGroupId(dep.getGroupId());
			dependency.setArtifactId(dep.getArtifactId());
			dependency.setScope(dep.getScope());

			dependency.setVersion(this.util.findVersionValue(systemPath));

			dependency.setSystemPath(jarName);
		}
		return dependency;
	}
	 
	/*private void addRepository(Data exsitingDependency,Model model){
		
		List<Repository> repositories = new LinkedList<Repository>();
		Repository repository = new DeploymentRepository();
		repository.setId(exsitingDependency.getArtifactHits());
		repository.setName(NETBEANS_REPO_NAME);
		repository.setUrl(NETBEANS_REPO);
		RepositoryPolicy policy = new RepositoryPolicy();
		policy.setEnabled(false);
		repository.setSnapshots(policy);
		repositories.add(repository);
		model.setRepositories(repositories);

	}*/
	 private void writePlugin(Model model){
		  Plugin plugin=new  Plugin();
	
	   
	 }
	 
	    
	    public File getBasedir() 
	    { 
	          return new File( build.getBasedir() );
	    }

		public com.syntel.ant2maven.ant.Build getBuild() {
			return build;
		}

		public void setBuild(com.syntel.ant2maven.ant.Build build) {
			this.build = build;
		} 
}
