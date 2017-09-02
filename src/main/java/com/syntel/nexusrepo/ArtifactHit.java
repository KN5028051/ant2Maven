package com.syntel.nexusrepo;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArtifactHit
{
    private ArtifactLinks artifactLinks;

    private String repositoryId;

    public ArtifactLinks getArtifactLinks ()
    {
        return artifactLinks;
    }
    
    @XmlElement
    public void setArtifactLinks (ArtifactLinks artifactLinks)
    {
        this.artifactLinks = artifactLinks;
    }

    public String getRepositoryId ()
    {
        return repositoryId;
    }

    @XmlAttribute
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

