package logic.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordRemoval
{
    private static final String STOP_WORDS_FILE = "./extractor/stopwords.txt";
    private static Pattern defaultWordsToRemove = Pattern.compile
        (
            "[ ](?:an|a|and|about|the|are|is|not|have|has|had|of|it|in|mln|said|for|will|its|with|from|that|was|would|which|this|last|REUTER)\\b\\.?",
            Pattern.CASE_INSENSITIVE
        );
    private static final Pattern invalidWordsToRemove = Pattern.compile("([&]*[#]*[;]*[]*[]*)");
    private static final Pattern invalidOnlyCharacters = Pattern.compile("[^a-zA-Z\\s]");
    
    public static String removeDefaultWords(String text)
    {
        Matcher matcher = defaultWordsToRemove.matcher(text);
        return matcher.replaceAll("");
    }
    
    public static String removeNumericCharacters(String text)
    {
        Matcher matcher = invalidOnlyCharacters.matcher(text);
        return matcher.replaceAll(" ");
    }
    
    public static String removeEnglishStopWords(String text)
    {
        readFromFileStopWords().forEach(System.out::println);
        return null;
    }
    
    private static List<String> readFromFileStopWords()
    {
        List<String> stopWords = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get(STOP_WORDS_FILE)))
        {
            stopWords = stream
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return stopWords;
    }
}