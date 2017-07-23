package com.britebill.interview.statistics.calculator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class StatisticsCalculator {

    public Long getTotalNumberOfWords(List<String> data) {
        return (long) data.size();
    }

    public Long getTotalNumberOfUniqueWords(List<String> data) {

        return data.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.counting());

    }

    public Long getAverageCharactersPerWord(List<String> data) {
        return (long) data.stream().mapToInt(String::length)
                .average().orElse(0);
    }

    public String getMostRepeatedWord(List<String> data) {
        return data.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max((x, y) -> Long.compare(x.getValue(), y.getValue())).orElse(null).getKey();

    }

}
