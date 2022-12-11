package com.internetplus.bankpunishment.vo;

import com.internetplus.bankpunishment.po.ArticlePO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaseSimilarityVO {
    String department;
    String regulation;
    String chapter;
    String article;
    String text;
    Double similarity;

    public static CaseSimilarityVO fromArticleAndSimilarity(ArticlePO article, double similarity) {
        return new CaseSimilarityVO(article.getDepartment(), article.getRegulation(), article.getChapter(),
                article.getArticle(), article.getText(), similarity);
    }
}
