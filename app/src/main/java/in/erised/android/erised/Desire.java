package in.erised.android.erised;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Desire extends ActionBarActivity {

    //int w, h;
    int widthBox, widthBox2;
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
        String[] tagItem = {"Shirts", "Suits", "dsfdfdggdgfdgg","Shoes"};
        String[] tagItem2 = {"Jeans", "Pants" ,"",""};
        LinearLayout  populatingLayout = (LinearLayout) findViewById(R.id.scroll_vertical);

        int size = tagItem.length;

        LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        int width = screenSize.x;
        int height = screenSize.y;

        for (int i = 0; i < size; i++) {
            View tempView = li.inflate(R.layout.populator, null);
            TextView textMain = (TextView) tempView.findViewById(R.id.choice1);
            TextView textChoice2 = (TextView) tempView.findViewById(R.id.choice2);
            textMain.setText(tagItem[i]);
            textChoice2.setText(tagItem2[i]);
            populatingLayout.addView(tempView);

            textMain.measure(TextView.MeasureSpec.UNSPECIFIED, TextView.MeasureSpec.UNSPECIFIED);
            textChoice2.measure(TextView.MeasureSpec.UNSPECIFIED,TextView.MeasureSpec.UNSPECIFIED);
            int boxh1= textMain.getHeight() ;
            int boxh2 = textChoice2.getHeight();
            int boxw1= textMain.getMeasuredWidth();
            int boxw2= textChoice2.getMeasuredWidth();

            int lrmargin = (int) (width-(boxw1+boxw2));
            int lmargin= ((lrmargin)/2);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(lmargin,10, 0, 10);
            textMain.setLayoutParams(params);

            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            param2.setMargins(10,10,0,10);
            textChoice2.setLayoutParams(param2);

            //textMain.setText(""+lmargin);

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
