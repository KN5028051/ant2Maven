package com.syntel.nexusrepo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement
public class NexusNGArtifact
{
    private String groupId;

    private String latestRelease;

    private ArtifactHits artifactHits;

    private String latestSnapshot;

    private String highlightedFragment;

    private String artifactId;

    private String latestSnapshotRepositoryId;

    private String latestReleaseRepositoryId;

    private String version;

    public String getGroupId ()
    {
        return groupId;
    }

    @XmlElement
    public void setGroupId (String groupId)
    {
        this.groupId = groupId;
    }

    public String getLatestRelease ()
    {
        return latestRelease;
    }

    @XmlElement
    public void setLatestRelease (String latestRelease)
    {
        this.latestRelease = latestRelease;
    }

    public ArtifactHits getArtifactHits ()
    {
        return artifactHits;
    }

    @XmlElement
    public void setArtifactHits (ArtifactHits artifactHits)
    {
        this.artifactHits = artifactHits;
    }

    public String getLatestSnapshot ()
    {
        return latestSnapshot;
    }

    @XmlElement
    public void setLatestSnapshot (String latestSnapshot)
    {
        this.latestSnapshot = latestSnapshot;
    }

    public String getHighlightedFragment ()
    {
        return highlightedFragment;
    }

    @XmlElement
    public void setHighlightedFragment (String highlightedFragment)
    {
        this.highlightedFragment = highlightedFragment;
    }

    public String getArtifactId ()
    {
        return artifactId;
    }

    @XmlAttribute
    public void setArtifactId (String artifactId)
    {
        this.artifactId = artifactId;
    }

    public String getLatestSnapshotRepositoryId ()
    {
        return latestSnapshotRepositoryId;
    }

    public void setLatestSnapshotRepositoryId (String latestSnapshotRepositoryId)
    {
        this.latestSnapshotRepositoryId = latestSnapshotRepositoryId;
    }

    @XmlAttribute
    public String getLatestReleaseRepositoryId ()
    {
        return latestReleaseRepositoryId;
    }

    public void setLatestReleaseRepositoryId (String latestReleaseRepositoryId)
    {
        this.latestReleaseRepositoryId = latestReleaseRepositoryId;
    }

    public String getVersion ()
    {
        return version;
    }

    @XmlAttribute
    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [groupId = "+groupId+", latestRelease = "+latestRelease+", artifactHits = "+artifactHits+", latestSnapshot = "+latestSnapshot+", highlightedFragment = "+highlightedFragment+", artifactId = "+artifactId+", latestSnapshotRepositoryId = "+latestSnapshotRepositoryId+", latestReleaseRepositoryId = "+latestReleaseRepositoryId+", version = "+version+"]";
    }
}
			
	