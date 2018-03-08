package logic.model;

import lombok.Data;

@Data
public class Similarity
{
    
    private Article article;
    
    private String label;
    
    private double distance;
    
    public Similarity(String place, Article article)
    {
        this.label = place;
        this.article = article;
    }
}

