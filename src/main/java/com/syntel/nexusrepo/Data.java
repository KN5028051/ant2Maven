package com.syntel.nexusrepo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Data
{
	private String groupId;

    private String latestRelease;

    private ArtifactHits[] artifactHits;

    private String highlightedFragment;

    private String artifactId;

    private String latestReleaseRepositoryId;

    private String version;

	private String latestSnapshot;
	private String latestSnapshotRepositoryId;
    private String[] nexusNGArtifact;

    public String[] getNexusNGArtifact ()
    {
        return nexusNGArtifact;
    }

    @XmlElement
    public void setNexusNGArtifact (String[] nexusNGArtifact)
    {
        this.nexusNGArtifact = nexusNGArtifact;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [nexusNGArtifact = "+nexusNGArtifact+"]";
    }

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getLatestRelease() {
		return latestRelease;
	}

	public void setLatestRelease(String latestRelease) {
		this.latestRelease = latestRelease;
	}

	public ArtifactHits[] getArtifactHits() {
		return artifactHits;
	}

	public void setArtifactHits(ArtifactHits[] artifactHits) {
		this.artifactHits = artifactHits;
	}

	public String getHighlightedFragment() {
		return highlightedFragment;
	}

	public void setHighlightedFragment(String highlightedFragment) {
		this.highlightedFragment = highlightedFragment;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getLatestReleaseRepositoryId() {
		return latestReleaseRepositoryId;
	}

	public void setLatestReleaseRepositoryId(String latestReleaseRepositoryId) {
		this.latestReleaseRepositoryId = latestReleaseRepositoryId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLatestSnapshot() {
		return latestSnapshot;
	}

	public void setLatestSnapshot(String latestSnapshot) {
		this.latestSnapshot = latestSnapshot;
	}

	public String getLatestSnapshotRepositoryId() {
		return latestSnapshotRepositoryId;
	}

	public void setLatestSnapshotRepositoryId(String latestSnapshotRepositoryId) {
		this.latestSnapshotRepositoryId = latestSnapshotRepositoryId;
	}
}