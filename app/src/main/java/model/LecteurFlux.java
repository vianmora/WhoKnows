package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LecteurFlux {

    private static String mUrlSources;
    private static String mUrlArticles;
    private static String mSourceID;

    public LecteurFlux() {
        mUrlSources = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
        mUrlArticles = "https://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr&sources=";
        mSourceID = "google-news-fr";
    }

    public List<HashMap<String, String>> getFluxList (){

        String S = "Title";
        String SS = "description";

        Article A1 = new Article();
        Article A2 = new Article();
        Article A3 = new Article();

        A1.setTitle(S);
        A2.setTitle(S);
        A3.setTitle(S);

        A1.setDescription(SS);
        A2.setDescription(SS);
        A3.setDescription(SS);

        ArrayList<Article> flux = new ArrayList<>();
        flux.add(A1);
        flux.add(A2);
        flux.add(A3);


        List<HashMap<String, String>> list = new ArrayList<>();

        HashMap<String, String> hashMap;


        for(int i = 0 ; i < 3 ; i++) {
            hashMap = new HashMap<>();

            hashMap.put("title", flux.get(i).getTitle());
            hashMap.put("description", flux.get(i).getDescription());
            list.add(hashMap);
        }

        return list;
    }
}
