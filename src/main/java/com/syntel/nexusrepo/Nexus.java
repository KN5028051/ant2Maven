package com.syntel.nexusrepo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Nexus
{
    private SearchNGResponse searchNGResponse;

    public SearchNGResponse getSearchNGResponse ()
    {
        return searchNGResponse;
    }

    @XmlElement
    public void setSearchNGResponse (SearchNGResponse searchNGResponse)
    {
        this.searchNGResponse = searchNGResponse;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [searchNGResponse = "+searchNGResponse+"]";
    }
}
	