package com.syntel.nexusrepo;

public class HighlightedFragment
{
    private Blockquote[] blockquote;

    public Blockquote[] getBlockquote ()
    {
        return blockquote;
    }

    public void setBlockquote (Blockquote[] blockquote)
    {
        this.blockquote = blockquote;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [blockquote = "+blockquote+"]";
    }
}
