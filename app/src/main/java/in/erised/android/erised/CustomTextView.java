package in.erised.android.erised;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Akshay on 30/07/15.
 */
public class CustomTextView extends TextView {

    /*
     * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
     */
    private static Typeface mTypeface;

    public CustomTextView(final Context context) {
        this(context, null);
    }

    public CustomTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/ufonts.com_maiandra-gd.ttf");
        }

        setTypeface(mTypeface);
        setTextColor(Color.BLACK);
        setBackgroundResource(R.drawable.back);
        setTextSize(20);
        setPadding(10,10,10,10);
    }

}
