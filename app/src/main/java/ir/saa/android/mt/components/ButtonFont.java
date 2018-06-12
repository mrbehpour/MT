package ir.saa.android.mt.components;

import android.content.Context;
import android.util.AttributeSet;

public class ButtonFont extends android.support.v7.widget.AppCompatButton {

    public ButtonFont(Context context) {
        super(context);
    }

    public ButtonFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public ButtonFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }
}