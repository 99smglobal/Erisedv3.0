package in.erised.android.erised;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Desire extends ActionBarActivity {

    //int w, h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desire);
        // ActionBar a = getSupportActionBar();
        LinearLayout ll = (LinearLayout) findViewById(R.id.header);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_header);
        getSupportActionBar().setIcon(R.drawable.icon);



        LayoutPopulator();
    }

    void LayoutPopulator() {
        String[] tagItem = {"Shirts", "Suits"};
        String[] tagItem2 = {"Jeans", "Pants"};
        LinearLayout populatingLayout = (LinearLayout) findViewById(R.id.scroll_vertical);

        int size = tagItem.length;

        LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < size; i++) {
            View tempView = li.inflate(R.layout.populator, null);
            TextView textMain = (TextView) tempView.findViewById(R.id.choice1);
TextView textChoice2 = (TextView) tempView.findViewById(R.id.choice2);
            textMain.setText(tagItem[i]);
            textChoice2.setText(tagItem2[i]);
            populatingLayout.addView(tempView);

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_desire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
