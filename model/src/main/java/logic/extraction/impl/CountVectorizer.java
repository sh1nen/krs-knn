package logic.extraction.impl;

import logic.extraction.Extractor;
import logic.model.entity.Article;
import logic.model.entity.WordVector;
import logic.utils.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CountVectorizer implements Extractor<Article, WordVector>
{
    
    private final Map<String, Integer> dictionary = new HashMap<>();
    
    public CountVectorizer(List<Article> entities)
    {
        fillDictionary(entities);
    }
    
    private void fillDictionary(List<Article> trainingEntities)
    {
        Integer totalWordsCount = 0;
        for(Article entity : trainingEntities)
        {
            Set<String> entityUniqueWords = TextUtils.getUniqueWords(entity);
            for(String word : entityUniqueWords)
            {
                if(!dictionary.containsKey(word))
                {
                    dictionary.put(word, totalWordsCount++);
                }
            }
        }
    }
    
    @Override
    public List<WordVector> extractFeatures(List<Article> testEntities)
    {
        return testEntities
            .stream()
            .map(this::extractFeatures)
            .collect(Collectors.toList());
    }
    
    private WordVector extractFeatures(Article textEntity)
    {
        Map<Integer, Double> features;
        List<String> allWords = TextUtils.getAllWords(textEntity);
        features = allWords
            .stream()
            .map(dictionary::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(wordId -> wordId, wordId -> 1D, Double::sum));
        
        return new WordVector(textEntity.getLabel(), features);
    }
}