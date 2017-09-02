package com.syntel.nexusrepo;

public class Blockquote
{
    private String content;

   

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", ]";
    }
}