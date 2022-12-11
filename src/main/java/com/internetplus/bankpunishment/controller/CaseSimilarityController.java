package com.internetplus.bankpunishment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.internetplus.bankpunishment.data.ArticleMapper;
import com.internetplus.bankpunishment.entity.ApiResult;
import com.internetplus.bankpunishment.po.ArticlePO;
import com.internetplus.bankpunishment.utils.text.TFIDFExtractor;
import com.internetplus.bankpunishment.utils.text.VectorSpaceModel;
import com.internetplus.bankpunishment.vo.CaseSimilarityVO;
import com.internetplus.bankpunishment.vo.SimilarityQueryVO;

@RestController
@RequestMapping("/api/case")
public class CaseSimilarityController {
    private TFIDFExtractor tfidf;
    private List<Map<String, Double>> articleTFIDF;
    private List<ArticlePO> articles;

    @Autowired
    private ArticleMapper mapper;

    @PostMapping("/similar-articles")
    public ApiResult<List<CaseSimilarityVO>> getSimilarArticles(@RequestBody SimilarityQueryVO query) {
        if (tfidf == null) {
            articles = mapper.getArticles();
            tfidf = new TFIDFExtractor(articles.stream().map(ArticlePO::getText).collect(Collectors.toList()));
            articleTFIDF = articles.stream().map(a -> tfidf.getTFIDF(a.getText())).collect(Collectors.toList());
        }

        var caseTfidf = tfidf.getTFIDF(query.getText());
        var result = new ArrayList<CaseSimilarityVO>();
        for (var i = 0; i < articles.size(); i++) {
            var similarity = VectorSpaceModel.cosineSimilarity(articleTFIDF.get(i), caseTfidf);
            result.add(CaseSimilarityVO.fromArticleAndSimilarity(articles.get(i), similarity));
        }

        result.sort((a, b) -> Double.compare(b.getSimilarity(), a.getSimilarity()));
        return ApiResult.success(result.subList(0, Math.min(query.getResultCount(), result.size())));
    }
}
