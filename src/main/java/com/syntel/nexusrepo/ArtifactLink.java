package com.syntel.nexusrepo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class ArtifactLink
{
    private String extension;

    private String classifier;

    public String getExtension ()
    {
        return extension;
    }

    @XmlElement
    public void setExtension (String extension)
    {
        this.extension = extension;
    }

    public String getClassifier ()
    {
        return classifier;
    }

    @XmlElement
    public void setClassifier (String classifier)
    {
        this.classifier = classifier;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [extension = "+extension+", classifier = "+classifier+"]";
    }
}