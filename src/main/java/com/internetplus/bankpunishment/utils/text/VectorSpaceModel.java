package com.internetplus.bankpunishment.utils.text;

import java.util.Map;

import com.google.common.collect.Sets;

public class VectorSpaceModel {
    public static double cosineSimilarity(Map<String, Double> text1, Map<String, Double> text2) {
        var innerProduct = Sets.intersection(text1.keySet(), text2.keySet()).stream()
                .mapToDouble(w -> text1.get(w) * text2.get(w)).sum();
        var text1LengthSquare = text1.values().stream().mapToDouble(d -> d * d).sum();
        var text2LengthSquare = text2.values().stream().mapToDouble(d -> d * d).sum();

        return innerProduct / Math.sqrt(text1LengthSquare * text2LengthSquare);
    }
}
