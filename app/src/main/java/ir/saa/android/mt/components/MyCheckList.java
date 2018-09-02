package ir.saa.android.mt.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


import ir.saa.android.mt.application.G;

public class MyCheckList extends LinearLayout {

	private Context _context;
	private MyCheckListMode _mode = MyCheckListMode.SingleSelection; 
	private final int DEFAULT_TEXT_SIZE = 20;
	private final int DEFAULT_TEXT_COLOR = Color.BLACK;
	private int _checkItemheight = LayoutParams.MATCH_PARENT;
	private int _checkedBackgroundColor = Color.parseColor("#238927");
	private int _uncheckedBackgroundColor = Color.parseColor("#eeeeee");
	private int _checkedTextColor = Color.parseColor("#ffffff");
	private int _uncheckedTextColor = Color.parseColor("#000000");
	private int _orientation =LinearLayout.HORIZONTAL;
	private Typeface tf = Typeface.createFromAsset(G.context.getAssets(), "fonts/byekan.ttf");
	//private ArrayList<MyCheckListItem> _checkListItemArray;
	private OnCheckListItemClickListener _onCheckListItemClickListener = null;
	private boolean _IsReadOnly = false;
	private ScrollView _scrollView;
	private LinearLayout _llMain;
	private int _verticalItemCount = 4;

	public MyCheckList(Context context,MyCheckListItem myCheckListItem1,MyCheckListItem myCheckListItem2) {
		super(context);
		_context =context;
		//_checkListItemArray = new ArrayList<MyCheckListItem>();
		//_checkListItemArray.add(myCheckListItem1);
		//_checkListItemArray.add(myCheckListItem2);
		_scrollView = new ScrollView(context);
		ScrollView.LayoutParams lp = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		_scrollView.setLayoutParams(lp);
		_llMain = new LinearLayout(context);
		LayoutParams lp2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		_llMain.setLayoutParams(lp2);

		setCheckListOrientation(_orientation);

		_llMain.addView(createItem(myCheckListItem1));
		_llMain.addView(createItem(myCheckListItem2));

		_scrollView.addView(_llMain);
		addView(_scrollView);
	}

	public MyCheckList setSelectionMode(MyCheckListMode mode){
		_mode = mode;
		return this;
	}
	
	public MyCheckList setOnCheckListItemClickListener(OnCheckListItemClickListener onCheckListItemClickListener) {
		_onCheckListItemClickListener = onCheckListItemClickListener;
		return this;
	}
	
	public MyCheckList setCheckedTextColor(int color){
		_checkedTextColor = color;
		return this;
	}
	public MyCheckList setUncheckedTextColor(int color){
		_uncheckedTextColor = color;
		return this;
	}
	public MyCheckList setCheckedBackgroundColor(int color){
		_checkedBackgroundColor = color;
		return this;
	}
	public MyCheckList setUncheckedBackgroundColor(int color){
		_uncheckedBackgroundColor = color;
		return this;
	}
	
	public void setSelectionByValue(Object value) {
		final int childCount = _llMain.getChildCount();
		uncheckListIfInSingleSelection();
		for (int i = 0; i < childCount; i++) {
			if(value instanceof ArrayList){
				ArrayList<Object> valueList = (ArrayList<Object>) value;
				if(valueList.contains(((MyCheckListItem)_llMain.getChildAt(i).getTag()).Value)){
					_llMain.getChildAt(i).setBackgroundColor(_checkedBackgroundColor);
					((TextView)((LinearLayout) _llMain.getChildAt(i)).getChildAt(0)).setTextColor(_checkedTextColor);
				}
			}else if(((MyCheckListItem)_llMain.getChildAt(i).getTag()).Value == value){
				_llMain.getChildAt(i).setBackgroundColor(_checkedBackgroundColor);
				((TextView)((LinearLayout) _llMain.getChildAt(i)).getChildAt(0)).setTextColor(_checkedTextColor);
			}
		}
	}
	
	
//	public void setSelectionByValue(ArrayList<Object> value) {
//		final int childCount = this.getChildCount();
//		uncheckListIfInSingleSelection();
//		for (int i = 0; i < childCount; i++) {
//			if(value instanceof ArrayList){
//				ArrayList<Object> valueList = (ArrayList<Object>) value;
//				if(valueList.contains(_checkListItemArray.get(i).Value)){
//					getChildAt(i).setBackgroundColor(_checkedBackgroundColor);
//					((TextView)((LinearLayout) getChildAt(i)).getChildAt(0)).setTextColor(_checkedTextColor);
//				}
//			}else if(_checkListItemArray.get(i).Value == value){
//				getChildAt(i).setBackgroundColor(_checkedBackgroundColor);
//				((TextView)((LinearLayout) getChildAt(i)).getChildAt(0)).setTextColor(_checkedTextColor);
//			}
//		}
//	}

//	@Override
//	public void setOrientation(int orientation) {
//		//super.setOrientation(orientation);
//
//		if(orientation == LinearLayout.HORIZONTAL){
//			int childCount = _llMain.getChildCount();
//			for (int i = 0; i < childCount; i++) {
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,_checkItemheight);
//				lp.weight = 1f;
//				lp.setMargins(0, 0, 5, 0);
//				_llMain.getChildAt(i).setLayoutParams(lp);
//			}
//		}else{
//			int childCount = _llMain.getChildCount();
//			for (int i = 0; i < childCount; i++) {
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,_checkItemheight);
//				lp.setMargins(0, 0, 0, 5);
//				_llMain.getChildAt(i).setLayoutParams(lp);
//			}
//		}
//	}

	public MyCheckList setCheckListOrientation(int orientation) {
		_llMain.setOrientation(orientation);
		if(orientation == LinearLayout.HORIZONTAL){
			int childCount = _llMain.getChildCount();
			for (int i = 0; i < childCount; i++) {
				LayoutParams lp = new LayoutParams(0,_checkItemheight);
				lp.weight = 1f;
				lp.setMargins(0, 0, 5, 0);
				_llMain.getChildAt(i).setLayoutParams(lp);
			}
		}else{
			int childCount = _llMain.getChildCount();
			for (int i = 0; i < childCount; i++) {
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,_checkItemheight);
				lp.setMargins(0, 0, 0, 5);
				_llMain.getChildAt(i).setLayoutParams(lp);
			}
		}
		return this;
	}

	public MyCheckList addCheckItem(MyCheckListItem myCheckListItem) {
		_llMain.addView(createItem(myCheckListItem));
		//_checkListItemArray.add(myCheckListItem);
		int childCount = _llMain.getChildCount();
		if(childCount>_verticalItemCount) {
			if (_llMain.getOrientation() == VERTICAL) {
				_llMain.getLayoutParams().height = (_checkItemheight * (_verticalItemCount+1));
				_scrollView.getLayoutParams().height = (_checkItemheight * (_verticalItemCount+1));
			} else {
				_llMain.getLayoutParams().height = _checkItemheight;
			}
		}
		return this;
	}
	public void removeAllCkeckItems() {
		_llMain.removeAllViews();
		//_checkListItemArray.clear();
	}
//	public MyCheckList setCheckItemWidth(int width){
//		return this;
//	}
	public MyCheckList setCheckItemsHeight(int height){
		_checkItemheight = height;
		final int childCount = _llMain.getChildCount();
		for (int i = 0; i < childCount; i++) {
			_llMain.getChildAt(i).getLayoutParams().height = height;
		}
		if(childCount>_verticalItemCount) {
			if (_llMain.getOrientation() == VERTICAL) {
				_llMain.getLayoutParams().height = (_checkItemheight * (_verticalItemCount+1));
				_scrollView.getLayoutParams().height = (_checkItemheight * (_verticalItemCount+1));
			} else {
				_llMain.getLayoutParams().height = _checkItemheight;
			}
		}
		//_llMain.getLayoutParams().height = _checkItemheight * childCount;
		return this;
	}

	public ArrayList<MyCheckListItem> getSelectedCheckListItems() {
		ArrayList<MyCheckListItem> lst = new ArrayList<MyCheckListItem>();
		final int childCount = _llMain.getChildCount();
		for (int i = 0; i < childCount; i++) {
	       if(((ColorDrawable) ((LinearLayout)_llMain.getChildAt(i)).getBackground()).getColor()==_checkedBackgroundColor){
	    	   lst.add((MyCheckListItem)_llMain.getChildAt(i).getTag());
	       }
		}
		return lst;
	}

	public ArrayList<String> getSelectedItemsText() {
		ArrayList<String> lst = new ArrayList<String>();
		final int childCount = _llMain.getChildCount();
		for (int i = 0; i < childCount; i++) {
	       if(((ColorDrawable) _llMain.getChildAt(i).getBackground()).getColor()==_checkedBackgroundColor){
	    	   lst.add(((MyCheckListItem)_llMain.getChildAt(i).getTag()).Text);
	       }
		}
		return lst;
	}

	public ArrayList<Object> getSelectedItemsValues() {
		ArrayList<Object> lst = new ArrayList<Object>();
		final int childCount = _llMain.getChildCount();
		for (int i = 0; i < childCount; i++) {
	       if(((ColorDrawable) _llMain.getChildAt(i).getBackground()).getColor()==_checkedBackgroundColor){
	    	   lst.add(((MyCheckListItem)_llMain.getChildAt(i).getTag()).Value);
	       }
		}
		return lst;
	}

	private void setBackgroundColorOnDrawable(Drawable drw,int color){
		StateListDrawable stateListDrawable= ((StateListDrawable)drw);
		DrawableContainerState drawableContainerState = (DrawableContainerState) stateListDrawable.getConstantState();
		((GradientDrawable) drawableContainerState.getChildren()[0]).setColor(_uncheckedBackgroundColor);;
	}

	private int getBackgroundColorOnDrawable(Drawable drw){
		int color = Color.YELLOW;
		if (drw instanceof ColorDrawable)
			color = ((ColorDrawable) drw).getColor();
		return color;
	}

	public MyCheckList setReadOnly(boolean isReadOnly){
		_IsReadOnly = isReadOnly;
		return this;
	}
	private View createItem(MyCheckListItem myCheckListItem) {
		LinearLayout ll = new LinearLayout(_context);
		//ll.setBackgroundResource(R.drawable.rect_round_light);
		//setBackgroundColorOnDrawable(ll.getBackground(),_uncheckedColor);
		ll.setBackgroundColor(_uncheckedBackgroundColor);
		ll.setTag(myCheckListItem);

		ll.setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,_checkItemheight);
		lp.weight = 1f;
		lp.setMargins(0, 0, 5, 0);

		TextView tv = new TextView(_context);
		tv.setTextSize(DEFAULT_TEXT_SIZE);
		tv.setTextColor(DEFAULT_TEXT_COLOR);
		tv.setText(myCheckListItem.Text);
		tv.setGravity(Gravity.CENTER);
		tv.setTypeface(tf);
		LayoutParams tvLp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(tvLp);

		OnClickListener onclicklistener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(_IsReadOnly)
					return;
				if(v instanceof LinearLayout){
					if(((ColorDrawable)v.getBackground()).getColor()==_uncheckedBackgroundColor){
						uncheckListIfInSingleSelection();
						v.setBackgroundColor(_checkedBackgroundColor);
						((TextView)((LinearLayout) v).getChildAt(0)).setTextColor(_checkedTextColor);
						if(_onCheckListItemClickListener!=null)
							_onCheckListItemClickListener.onCheckListItemClick(((MyCheckListItem)v.getTag()), true);
					}
					else{
						v.setBackgroundColor(_uncheckedBackgroundColor);
						((TextView)((LinearLayout) v).getChildAt(0)).setTextColor(_uncheckedTextColor);
						if(_onCheckListItemClickListener!=null)
							_onCheckListItemClickListener.onCheckListItemClick(((MyCheckListItem)v.getTag()), false);
					}
				}else{
					if(((ColorDrawable)((View)v.getParent()).getBackground()).getColor()==_uncheckedBackgroundColor){
						uncheckListIfInSingleSelection();
						((View)v.getParent()).setBackgroundColor(_checkedBackgroundColor);
						((TextView)v).setTextColor(_checkedTextColor);
						if(_onCheckListItemClickListener!=null)
							_onCheckListItemClickListener.onCheckListItemClick(((MyCheckListItem)((View)v.getParent()).getTag()), true);
					}
					else{
						((View)v.getParent()).setBackgroundColor(_uncheckedBackgroundColor);
						((TextView)v).setTextColor(_uncheckedTextColor);
						if(_onCheckListItemClickListener!=null)
							_onCheckListItemClickListener.onCheckListItemClick(((MyCheckListItem)((View)v.getParent()).getTag()), false);
					}
				}
			}

		};

		tv.setOnClickListener(onclicklistener);
		ll.setOnClickListener(onclicklistener);
		ll.addView(tv);
		ll.setLayoutParams(lp);

		return ll;
	}

	private void uncheckListIfInSingleSelection() {
		if(_mode == MyCheckListMode.SingleSelection){
			final int childCount = _llMain.getChildCount();
			for (int i = 0; i < childCount; i++) {
				_llMain.getChildAt(i).setBackgroundColor(_uncheckedBackgroundColor);
				((TextView)((LinearLayout) _llMain.getChildAt(i)).getChildAt(0)).setTextColor(_uncheckedTextColor);
			}
		}
	}
	 public interface OnCheckListItemClickListener {
	        void onCheckListItemClick(MyCheckListItem selectedCheckListItem, Boolean isChecked);
	 }
}

