package com.syntel.nexusrepo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RepoDetails
{
	
	private String repositoryContentClass;

    private String repositoryURL;

    private String repositoryKind;

    private String repositoryPolicy;

    private String repositoryName;

    private String repositoryId;
    private String[] repositoryDetail;

    public String[] getRepositoryDetail ()
    {
        return repositoryDetail;
    }

    @XmlElement
    public void setRepositoryDetail (String[] repositoryDetail)
    {
        this.repositoryDetail = repositoryDetail;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [repositoryDetail = "+repositoryDetail+"]";
    }

	public String getRepositoryContentClass() {
		return repositoryContentClass;
	}

	public void setRepositoryContentClass(String repositoryContentClass) {
		this.repositoryContentClass = repositoryContentClass;
	}

	public String getRepositoryURL() {
		return repositoryURL;
	}

	public void setRepositoryURL(String repositoryURL) {
		this.repositoryURL = repositoryURL;
	}

	public String getRepositoryKind() {
		return repositoryKind;
	}

	public void setRepositoryKind(String repositoryKind) {
		this.repositoryKind = repositoryKind;
	}

	public String getRepositoryPolicy() {
		return repositoryPolicy;
	}

	public void setRepositoryPolicy(String repositoryPolicy) {
		this.repositoryPolicy = repositoryPolicy;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}
}