package ir.saa.android.mt.components;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;



public class FontAwesome extends  android.support.v7.widget.AppCompatTextView {
    public FontAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesome(Context context) {
        super(context);
        init();
    }

    private void init() {

        //Font name should not contain "/".
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fontawesome.ttf");
        setTypeface(tf);
    }



}
