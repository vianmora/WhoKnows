package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Article;
import model.LecteurFlux;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mArticlesListView = findViewById(R.id.main_articles_listview);

        /*String S = "Title";
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


        List<HashMap<String, String>> mArticleList = new ArrayList<>();

        HashMap<String, String> hashMap;


        for(int i = 0 ; i < 3 ; i++) {
            hashMap = new HashMap<>();

            hashMap.put("title", flux.get(i).getTitle());
            hashMap.put("description", flux.get(i).getDescription());
            mArticleList.add(hashMap);
        }*/

        LecteurFlux lecteurFlux = new LecteurFlux();
        List<HashMap<String, String>> mArticleList = lecteurFlux.getFluxList();

        ListAdapter adapter = new SimpleAdapter(
        this,
        mArticleList,
        android.R.layout.simple_list_item_2,
        new String[] {"title", "description"},
        new int[] {android.R.id.text1, android.R.id.text2 }
        );

        mArticlesListView.setAdapter(adapter);


        mArticlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // pour l'instant il ne se passe rien
            }
        });
    }
}
