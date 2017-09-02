package com.syntel.nexusrepo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RepositoryDetail
{
    private String repositoryContentClass;

    private String repositoryURL;

    private String repositoryKind;

    private String repositoryPolicy;

    private String repositoryName;

    private String repositoryId;

    public String getRepositoryContentClass ()
    {
        return repositoryContentClass;
    }

    @XmlElement
    public void setRepositoryContentClass (String repositoryContentClass)
    {
        this.repositoryContentClass = repositoryContentClass;
    }

    public String getRepositoryURL ()
    {
        return repositoryURL;
    }

    @XmlElement
    public void setRepositoryURL (String repositoryURL)
    {
        this.repositoryURL = repositoryURL;
    }

    public String getRepositoryKind ()
    {
        return repositoryKind;
    }

    @XmlElement
    public void setRepositoryKind (String repositoryKind)
    {
        this.repositoryKind = repositoryKind;
    }

    public String getRepositoryPolicy ()
    {
        return repositoryPolicy;
    }

    @XmlElement
    public void setRepositoryPolicy (String repositoryPolicy)
    {
        this.repositoryPolicy = repositoryPolicy;
    }

   
    public String getRepositoryName ()
    {
        return repositoryName;
    }

    @XmlElement
    public void setRepositoryName (String repositoryName)
    {
        this.repositoryName = repositoryName;
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
        return "ClassPojo [repositoryContentClass = "+repositoryContentClass+", repositoryURL = "+repositoryURL+", repositoryKind = "+repositoryKind+", repositoryPolicy = "+repositoryPolicy+", repositoryName = "+repositoryName+", repositoryId = "+repositoryId+"]";
    }
}