package ir.saa.android.mt.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;

public class MyDialog {
	private Context _context;
	private Dialog _dialog;
	private LinearLayout _llBodyPanel;
	private LinearLayout _llButtonPanel;
	private TextView _txtDialogTitle;
	private TextView _txtDialogLeftTitle;
	private TextView _txtDialogRightTitle;
	private ImageView _imgDialogLeftTitle;
	private ImageView _imgDialogRightTitle;
	private Typeface tf = Typeface.createFromAsset(G.context.getAssets(), "fonts/byekan.ttf");
	public MyDialog(Context context) {
		_context = context;
		_dialog = new Dialog(_context, R.style.FullHeightDialogNotFloating);
		_dialog.getWindow().getAttributes().windowAnimations = R.style.FullHeightDialogNotFloating;
		_dialog.setContentView(R.layout.custom_dialog);
		_txtDialogTitle = (TextView) _dialog.findViewById(R.id.txtDialogTitle);
		_txtDialogLeftTitle = (TextView) _dialog.findViewById(R.id.txtDialogLeftTitle);
		_txtDialogRightTitle = (TextView) _dialog.findViewById(R.id.txtDialogRightTitle);
		_imgDialogLeftTitle = (ImageView) _dialog.findViewById(R.id.imgDialogLeftTitle);
		_imgDialogRightTitle = (ImageView) _dialog.findViewById(R.id.imgDialogRightTitle);
		_llBodyPanel = (LinearLayout) _dialog.findViewById(R.id.llBodyPanel);
		_llButtonPanel = (LinearLayout) _dialog.findViewById(R.id.llButtonPanel);
		_txtDialogTitle.setTypeface(tf);
		_txtDialogLeftTitle.setTypeface(tf);
		_txtDialogRightTitle.setTypeface(tf);
    }
	public MyDialog(Context context,int theme) {
		_context = context;
		_dialog = new Dialog(_context,theme); 
		_dialog.getWindow().getAttributes().windowAnimations = theme;
		_dialog.setContentView(R.layout.custom_dialog);
		_txtDialogTitle = (TextView) _dialog.findViewById(R.id.txtDialogTitle);
		_txtDialogLeftTitle = (TextView) _dialog.findViewById(R.id.txtDialogLeftTitle);
		_txtDialogRightTitle = (TextView) _dialog.findViewById(R.id.txtDialogRightTitle);
		_imgDialogLeftTitle = (ImageView) _dialog.findViewById(R.id.imgDialogLeftTitle);
		_imgDialogRightTitle = (ImageView) _dialog.findViewById(R.id.imgDialogRightTitle);
		_llBodyPanel = (LinearLayout) _dialog.findViewById(R.id.llBodyPanel);
		_llButtonPanel = (LinearLayout) _dialog.findViewById(R.id.llButtonPanel);
		_txtDialogTitle.setTypeface(tf);
		_txtDialogLeftTitle.setTypeface(tf);
		_txtDialogRightTitle.setTypeface(tf);
    }
	
	
	public Dialog getDialog(){
		return _dialog;
	}
	public MyDialog setLeftImageTitle(Drawable drw,OnClickListener onClickListener){
		_imgDialogLeftTitle.setImageDrawable(drw);
		_imgDialogLeftTitle.setVisibility(View.VISIBLE);
		_imgDialogLeftTitle.setOnClickListener(onClickListener);
		return this;
	}
	public MyDialog setRightImageTitle(Drawable drw,OnClickListener onClickListener){
		_imgDialogRightTitle.setImageDrawable(drw);
		_imgDialogRightTitle.setVisibility(View.VISIBLE);
		_imgDialogRightTitle.setOnClickListener(onClickListener);
		return this;
	}
	public MyDialog addContentView(View v) {
		_llBodyPanel.addView(v);
		return this;
	}
	public MyDialog addContentXml(int layoutId) {
	    _llBodyPanel.addView((LinearLayout) View.inflate(_context, layoutId, null));
		return this;
	}
	public MyDialog clearButtonPanel() {
		if(_llButtonPanel.getChildCount() > 0)
			_llButtonPanel.removeAllViews(); 
		return this;
	}
	public MyDialog clearContentPanel() {
		if(_llBodyPanel.getChildCount() > 0)
			_llBodyPanel.removeAllViews(); 
		return this;
	}
	public MyDialog clearAllPanel() {
		if(_llBodyPanel.getChildCount() > 0)
			_llBodyPanel.removeAllViews(); 
		if(_llButtonPanel.getChildCount() > 0)
			_llButtonPanel.removeAllViews(); 
		return this;
	}
	public MyDialog setContentSplitter() {
		LinearLayout llSplitter = new LinearLayout(_context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,1);
		llSplitter.setBackgroundColor(Color.rgb(184, 186, 188));
		lp.setMargins(5, 0, 5, 0);
		llSplitter.setLayoutParams(lp);
	    _llBodyPanel.addView(llSplitter);
		return this;
	}

	public MyDialog setVerticalMargin(int verticalMargin) {
		LinearLayout llMakeMargin = new LinearLayout(_context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,verticalMargin);
		llMakeMargin.setLayoutParams(lp);
	    _llBodyPanel.addView(llMakeMargin);
		return this;
	}
	public MyDialog setBackgroundAlpha(float alpha){
		((LinearLayout)_llBodyPanel.getParent()).setAlpha(alpha);
		return this;
	}
	public MyDialog addBodyText(String text,int textSize) {
		LinearLayout ll = new LinearLayout(_context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,40);
		ll.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		
		TextView txt= new TextView(_context);
		txt.setText(text);
		txt.setTextSize(textSize);
		txt.setTypeface(tf);
		txt.setGravity(Gravity.CENTER);
		ll.addView(txt);
		
		ll.setLayoutParams(lp);
	    _llBodyPanel.addView(ll);
		return this;
	}
	public MyDialog setBodyMargin(int left,int top,int right,int bottom) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(_llBodyPanel.getLayoutParams());
		lp.setMargins(left, top, right, bottom);	
	    _llBodyPanel.setLayoutParams(lp);
		return this;
	}
	public MyDialog setTitle(String title){
		_txtDialogTitle.setText(title);
		return this;
	}
	public MyDialog setLeftTitle(String title){
		_txtDialogLeftTitle.setText(title);
		return this;
	}
	public MyDialog setRightTitle(String title){
		_txtDialogRightTitle.setText(title);
		return this;
	}
	public MyDialog addButton(String caption , OnClickListener onClickEvent){
		Button btn = new Button(_context);
		btn.setText(caption);
		btn.setTypeface(tf);
		btn.setTextSize(20);
		btn.setTextColor(Color.WHITE);
		btn.setBackgroundDrawable(_context.getResources().getDrawable(R.drawable.login_button));
		LayoutParams btnLp = new LayoutParams();
		btnLp.width = LayoutParams.MATCH_PARENT;
		btnLp.height = LayoutParams.MATCH_PARENT;
		btn.setOnClickListener(onClickEvent);
		btn.setLayoutParams(btnLp);

		LinearLayout ll = new LinearLayout(_context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,70);
		lp.weight = 1f;
		lp.setMargins(0, 0, 5, 0);
		ll.setLayoutParams(lp);
		ll.addView(btn);
		
		_llButtonPanel.addView(ll);
		return this;
	}
	public MyDialog show() {
		try {
			_dialog.show();
		}
		catch(Exception ex){

			Log.i("err",ex.getMessage());
		}
		return this;
	}
	public MyDialog dismiss(){
		_dialog.dismiss();
		return this;
	}
}
