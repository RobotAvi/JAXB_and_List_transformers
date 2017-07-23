package com.britebill.interview.transformers;

import java.util.*;
import java.util.stream.Collectors;

public class TransformerData {
    public List<String> transformData(List<String> src) {
        return src.stream().filter(Objects::nonNull).distinct()
                .sorted((a, b)-> a.length()-b.length())
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
