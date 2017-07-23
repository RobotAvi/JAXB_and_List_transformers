package com.britebill.interview.statistics;

import com.britebill.interview.statistics.beans.Statistics;
import com.britebill.interview.statistics.calculator.StatisticsCalculator;
import com.britebill.interview.statistics.writers.JsonStatisticsWriter;
import com.britebill.interview.statistics.writers.XmlStatisticsWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

public class StatisticsTest {


    @Test
    public void testXmlGeneration() throws IOException, JAXBException {
        //Given
        File file = File.createTempFile("test", ".xml");
        SecureRandom random = new SecureRandom();
        Statistics initialStatistic = new Statistics();
        initialStatistic.setMostRepeatedWord(UUID.randomUUID().toString());
        initialStatistic.setTotalNumberOfWords(random.ints(10, 100).findFirst().orElse(0));
        initialStatistic.setTotalNumberOfUniqueWords(random.ints(10, 100).findFirst().orElse(0));
        initialStatistic.setAverageCharactersPerWord(random.ints(10, 100).findFirst().orElse(0));
        JAXBContext jc = JAXBContext.newInstance(Statistics.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        //When
        new XmlStatisticsWriter().write(initialStatistic, file);
        Statistics statisticFromFile = (Statistics) unmarshaller.unmarshal(file);

        //Then
        assert initialStatistic.getAverageCharactersPerWord() == statisticFromFile.getAverageCharactersPerWord();
        assert initialStatistic.getMostRepeatedWord().equals(statisticFromFile.getMostRepeatedWord());
        assert initialStatistic.getTotalNumberOfUniqueWords() == statisticFromFile.getTotalNumberOfUniqueWords();
        assert initialStatistic.getTotalNumberOfWords() == statisticFromFile.getTotalNumberOfWords();
    }

    @Test
    public void testJsonGeneration() throws IOException, JAXBException {
        //Given
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        File file = File.createTempFile("test", ".json");

        SecureRandom random = new SecureRandom();
        Statistics initialStatistic = new Statistics();
        initialStatistic.setMostRepeatedWord(UUID.randomUUID().toString());
        initialStatistic.setTotalNumberOfWords(random.ints(10, 100).findFirst().orElse(0));
        initialStatistic.setTotalNumberOfUniqueWords(random.ints(10, 100).findFirst().orElse(0));
        initialStatistic.setAverageCharactersPerWord(random.ints(10, 100).findFirst().orElse(0));

        //When
        new JsonStatisticsWriter().write(initialStatistic, file);
        Statistics statisticFromFile = objectMapper.readValue(file, Statistics.class);
        //Then
        assert initialStatistic.getAverageCharactersPerWord() == statisticFromFile.getAverageCharactersPerWord();
        assert initialStatistic.getMostRepeatedWord().equals(statisticFromFile.getMostRepeatedWord());
        assert initialStatistic.getTotalNumberOfUniqueWords() == statisticFromFile.getTotalNumberOfUniqueWords();
        assert initialStatistic.getTotalNumberOfWords() == statisticFromFile.getTotalNumberOfWords();
    }

    @Test
    public void testTotalNumberOfWords() {
        //Given
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
        List<String> words = new LinkedList<>();
        SecureRandom random = new SecureRandom();
        int initialWordCount = random.ints(10, 100).findFirst().orElse(0);
        for (int i = 0; i < initialWordCount; i++) {
            words.add(UUID.randomUUID().toString());
        }
        //When
        long wordCountFromMethod = statisticsCalculator.getTotalNumberOfWords(words);
        //Then
        assert initialWordCount == wordCountFromMethod;
    }

    @Test
    public void testTotalNumberOfUniqueWords() {
        //Given
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
        SecureRandom random = new SecureRandom();
        int initialWordCount = random.ints(10, 100).findFirst().orElse(0);
        int initialUniqueWordCount = initialWordCount / 2;
        List<String> words = new ArrayList<>(initialWordCount);
        for (int i = 0; i <= initialUniqueWordCount; i++) {
            words.add(i, UUID.randomUUID().toString());
        }
        for (int i = initialUniqueWordCount; i < initialWordCount; i++) {
            words.add(i, words.get(initialUniqueWordCount));
        }

        //When
        long uniqueWordCountFromMethod = statisticsCalculator.getTotalNumberOfUniqueWords(words);
        //Then
        assert uniqueWordCountFromMethod == initialUniqueWordCount;

    }

    @Test
    public void testAverageCharactersPerWord() {
        //Given
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
        SecureRandom random = new SecureRandom();
        int initialWordCount = random.ints(10, 100).findFirst().orElse(0);
        int sumOfWordsLength = 0;
        List<String> words = new ArrayList<>(initialWordCount);
        for (int i = 0; i < initialWordCount; i++) {
            words.add(i, UUID.randomUUID().toString());
            sumOfWordsLength += words.get(i).length();
        }
        long averageWordsLength = sumOfWordsLength / initialWordCount;
        //When
        long averageWordsLengthFromMethod = statisticsCalculator.getAverageCharactersPerWord(words);
        //Then
        assert averageWordsLengthFromMethod == averageWordsLength;

    }

    @Test
    public void testMostRepeatedWord() {
        //Given
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
        SecureRandom random = new SecureRandom();
        int initialWordCount = random.ints(10, 100).findFirst().orElse(0);
        int initialUniqueWordCount = initialWordCount / 2;
        List<String> words = new LinkedList<>();
        for (int i = 0; i <= initialUniqueWordCount; i++) {
            words.add(UUID.randomUUID().toString());
        }
        String mostRepeatedWord = words.get(initialUniqueWordCount);
        for (int i = initialUniqueWordCount; i < initialWordCount; i++) {
            words.add(mostRepeatedWord);
        }

        //When
        String mostRepeatedWordFromMethod = statisticsCalculator.getMostRepeatedWord(words);
        //Then
        assert mostRepeatedWordFromMethod.equals(mostRepeatedWord);

    }

}



