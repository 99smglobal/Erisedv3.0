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

import static java.lang.Thread.sleep;


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
        String[] tagItem = {"Shirts-Men", "Suits-Men", "Shirts-Women","Suits-Men", "Sarees", "Kurtis",
        "OnePiece-Women","Shoes-Kids","Heels", "Flippers", "T-Shirts Women", "Watches" , "Watches-Kids","EthnicWear-Men",
        "DesignerWear-Men", "DesignerWear-Women","Purse", "Wallets", "Tie & Belts","Salvaars", "Handbags"};
        String[] tagItem2 = {"Jeans-Men", "Pants-Men" ,"Jeans-Women","Pants-Women", "Nightwear-Women" ,"Nightwear-Kids", "Shorts-Guys" ,
                "CasualShoes-Men" ,"SportsShoes-Men" ,"T-Shirts Men", "Floaters","T-Shirts Kids", "Bracelets","EthnicWear-Women",
        "Artificial Jewellery", "Sportswear","Bags", "Sunglasses", "Cosmetics","Undergarments-Men","Undergarments-Women"};

        int i, size = tagItem.length;

        LinearLayout  populatingLayout = (LinearLayout) findViewById(R.id.scroll_vertical);
        LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

              for ( i = 0; i < size; i++) {
            //textMain.setText(""+lmargin);
                  Display display = getWindowManager().getDefaultDisplay();
                  Point screenSize = new Point();
                  display.getSize(screenSize);
                  int width = screenSize.x;
                  int height = screenSize.y;

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
                  int space = width/20;
                  int lrmargin = (width-(boxw1+boxw2+space));
                  int lmargin= ((lrmargin)/2);

                  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                  params.setMargins(lmargin,space, 0,space);
                  textMain.setLayoutParams(params);

                  LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                  param2.setMargins(space,space,0,space);
                  textChoice2.setLayoutParams(param2);


              }
}
 /*   private class AppWait implements Runnable{
int i;
        String tagItem, tagItem2;

        public  AppWait(String s1, String s2, int pos)
        {
            tagItem=s1;
            tagItem2 =s2;
            i=pos;
        }
        @Override
        public void run() {

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
   */
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
