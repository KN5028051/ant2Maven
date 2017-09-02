package com.syntel.nexusrepo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArtifactLinks
{
    private String[] artifactLink;
    private String extension;
    private String classifier;
    public String[] getArtifactLink ()
    {
        return artifactLink;
    }

    @XmlElement
    public void setArtifactLink (String[] artifactLink)
    {
        this.artifactLink = artifactLink;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [artifactLink = "+artifactLink+"]";
    }

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getClassifier() {
		return classifier;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}
}
	