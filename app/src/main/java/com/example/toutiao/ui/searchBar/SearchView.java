package com.example.toutiao.ui.searchBar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.toutiao.R;

public class SearchView extends LinearLayout implements TextWatcher, View.OnClickListener{
    /**
     * 输入框
     */
    public final EditText mSearchEditText;
    /**
     * 输入框后面的那个清除按钮
     */
    public final Button mClearButton;

    public final LinearLayout mContainerLinearlayout;


    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /** 加载布局文件 */
        LayoutInflater.from(context).inflate(R.layout.search_bar, this, true);
        /** 找出控件 */
        mSearchEditText = findViewById(R.id.et_search);
        mClearButton = (Button) findViewById(R.id.bt_clear);
        mContainerLinearlayout = findViewById(R.id.linearLayout_container);
        mClearButton.setVisibility(GONE);
        mSearchEditText.addTextChangedListener(this);
        mClearButton.setOnClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /****
     * 对用户输入文字的监听
     *
     * @param editable
     */
    @Override
    public void afterTextChanged(Editable editable) {
        /**获取输入文字**/
        String input = mSearchEditText.getText().toString().trim();
        if (input.isEmpty()) {
            mClearButton.setVisibility(GONE);
        } else {
            mClearButton.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        mSearchEditText.setText("");
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }



    public void setEditTextClickable(Boolean clickable) {
        mSearchEditText.setClickable(clickable);
    }

    public void setEditTextFocusable(Boolean focusable) {
        mSearchEditText.setFocusable(focusable);
    }

    public void setContainerClickable(Boolean clickable) {
        mContainerLinearlayout.setClickable(clickable);
    }
}
