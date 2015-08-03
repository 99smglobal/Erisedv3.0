package in.erised.android.erised;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import in.erised.android.erised.adapters.CategoryAdapter;
import in.erised.android.erised.pojo.ListItemPOJO;


public class Desire extends AppCompatActivity {

    //int w, h;
    private int widthBox, widthBox2;
    private Toolbar toolbar;
    private RecyclerView itemRecyclerView;
    private List<ListItemPOJO> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desire);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        itemRecyclerView = (RecyclerView) findViewById(R.id.itemListView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(this, 2);
        staggeredGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
        itemRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        populateListItem();
    }

    private void populateListItem() {
        listItems = new ArrayList<>();
        String[] tagItem = {"Shirts-Men", "Suits-Men", "Shirts-Women", "Suits-Men", "Sarees", "Kurtis",
                "OnePiece-Women", "Shoes-Kids", "Heels", "Flippers", "T-Shirts Women", "Watches", "Watches-Kids", "EthnicWear-Men",
                "DesignerWear-Men", "DesignerWear-Women", "Purse", "Wallets", "Tie & Belts", "Salvaars", "Handbags"};
        String[] tagItem2 = {"Jeans-Men", "Pants-Men", "Jeans-Women", "Pants-Women", "Nightwear-Women", "Nightwear-Kids", "Shorts-Guys",
                "CasualShoes-Men", "SportsShoes-Men", "T-Shirts Men", "Floaters", "T-Shirts Kids", "Bracelets", "EthnicWear-Women",
                "Artificial Jewellery", "Sportswear", "Bags", "Sunglasses", "Cosmetics", "Undergarments-Men", "Undergarments-Women"};
        ListItemPOJO pojo;
        for (int i = 0; i < tagItem.length; i++) {
            pojo = new ListItemPOJO(i, tagItem[i], false);
            listItems.add(pojo);
            pojo = new ListItemPOJO(i, tagItem2[i], false);
            listItems.add(pojo);
        }
        CategoryAdapter adapter = new CategoryAdapter(this, listItems);
        itemRecyclerView.setAdapter(adapter);

    }

}
