package com.syntel.nexusrepo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArtifactHits
{
	 private ArtifactLinks[] artifactLinks;

	    private String repositoryId;

	    public ArtifactLinks[] getArtifactLinks ()
	    {
	        return artifactLinks;
	    }

	    public void setArtifactLinks (ArtifactLinks[] artifactLinks)
	    {
	        this.artifactLinks = artifactLinks;
	    }

	    public String getRepositoryId ()
	    {
	        return repositoryId;
	    }

	    public void setRepositoryId (String repositoryId)
	    {
	        this.repositoryId = repositoryId;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [artifactLinks = "+artifactLinks+", repositoryId = "+repositoryId+"]";
	    }
}