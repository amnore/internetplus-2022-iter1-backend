package com.internetplus.bankpunishment.utils.text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import org.apache.commons.lang3.tuple.Pair;

public class TFIDFExtractor {
    private final Map<String, Double> wordAndIDF;
    private final double defaultIDF;

    public TFIDFExtractor(Collection<String> corpus) {
        var wordAndFrequency = corpus
                .stream().flatMap(d -> HanLP.segment(d).stream().filter(CoreStopWordDictionary::shouldInclude)
                        .map(t -> t.word).distinct())
                .collect(Collectors.toMap(Function.identity(), w -> 1, Integer::sum));

        wordAndIDF = mapValue(wordAndFrequency, (k, v) -> Math.log((double) corpus.size() / v));
        defaultIDF = Math.log(corpus.size());
    }

    public Map<String, Double> getTFIDF(String text) {
        var textWordAndFrequency = HanLP.segment(text).stream().filter(CoreStopWordDictionary::shouldInclude)
                .collect(Collectors.toMap(t -> t.word, t -> 1, Integer::sum));
        var totalFrequency = (double) textWordAndFrequency.values().stream().mapToInt(i -> i).sum();

        return mapValue(textWordAndFrequency, (k, v) -> v / totalFrequency * wordAndIDF.getOrDefault(k, defaultIDF));
    }

    private <T, U, V> Map<T, V> mapValue(Map<T, U> m, BiFunction<T, U, V> func) {
        return m.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> func.apply(e.getKey(), e.getValue())));
    }
}
