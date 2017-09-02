package com.syntel.nexusrepo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchNGResponse
{
    private String count;

    private String totalCount;

    private String tooManyResults;

    private Data[] data;

    private String from;

    private RepoDetails[] repoDetails;

    private String collapsed;

    public String getCount ()
    {
        return count;
    }

    @XmlElement
    public void setCount (String count)
    {
        this.count = count;
    }

    public String getTotalCount ()
    {
        return totalCount;
    }

    @XmlElement
    public void setTotalCount (String totalCount)
    {
        this.totalCount = totalCount;
    }

    public String getTooManyResults ()
    {
        return tooManyResults;
    }

    @XmlElement
    public void setTooManyResults (String tooManyResults)
    {
        this.tooManyResults = tooManyResults;
    }

    public Data[] getData ()
    {
        return data;
    }

    @XmlElement
    public void setData (Data[] data)
    {
        this.data = data;
    }

    public String getFrom ()
    {
        return from;
    }

    @XmlElement
    public void setFrom (String from)
    {
        this.from = from;
    }

    public RepoDetails[] getRepoDetails ()
    {
        return repoDetails;
    }

    @XmlElement
    public void setRepoDetails (RepoDetails[] repoDetails)
    {
        this.repoDetails = repoDetails;
    }

    public String getCollapsed ()
    {
        return collapsed;
    }

    @XmlElement
    public void setCollapsed (String collapsed)
    {
        this.collapsed = collapsed;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [count = "+count+", totalCount = "+totalCount+", tooManyResults = "+tooManyResults+", data = "+data+", from = "+from+", repoDetails = "+repoDetails+", collapsed = "+collapsed+"]";
    }
}
			
			