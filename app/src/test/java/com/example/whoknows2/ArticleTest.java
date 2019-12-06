package com.example.whoknows2;

import org.junit.Test;

import model.Article;

public class ArticleTest {

    @Test
    public void create(){

        Article A1 = new Article();

        String S = "titre1";

        A1.setTitle(S);

        assert A1.getTitle().equals(S);
    }
}
