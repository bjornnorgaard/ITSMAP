package com.leafcastlelabs.demoui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leafcastlelabs.demoui.adaptors.CardDisplayAdaptor;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView rcvList;
    RecyclerView.Adapter adaptor;
    RecyclerView.LayoutManager layoutMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        rcvList = (RecyclerView) findViewById(R.id.rcvCardList);

        layoutMan = new LinearLayoutManager(this);
        rcvList.setLayoutManager(layoutMan);

        String[] cardTitles = new String[200];
        for(int i=0; i<cardTitles.length; i++) {
            cardTitles[i] = "Card "+(i+1);
        }

        adaptor = new CardDisplayAdaptor(cardTitles);
        rcvList.setAdapter(adaptor);
    }
}
