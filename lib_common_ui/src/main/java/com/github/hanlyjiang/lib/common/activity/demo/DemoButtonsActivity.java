package com.github.hanlyjiang.lib.common.activity.demo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.hanlyjiang.lib.common.ui.R;

public class DemoButtonsActivity extends DemoBaseActivity {

    protected LinearLayout mRootLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootLayout = new LinearLayout(this);
        mRootLayout.setOrientation(LinearLayout.VERTICAL);
        mRootLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        mRootLayout.setDividerPadding(2);
        mRootLayout.setDividerDrawable(new ColorDrawable(Color.GRAY));
        ViewGroup.MarginLayoutParams layoutParams = getMarginLayoutParams();
        setContentView(mRootLayout, layoutParams);
    }

    @NonNull
    private ViewGroup.MarginLayoutParams getMarginLayoutParams() {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        int margin = getResources().getDimensionPixelOffset(R.dimen.demo_activity_padding);
        layoutParams.leftMargin = margin;
        layoutParams.rightMargin = margin;
        layoutParams.topMargin = margin;
        layoutParams.bottomMargin = margin;
        return layoutParams;
    }

    public View addButton(String text, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(text);
        button.setOnClickListener(listener);
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        mRootLayout.addView(button, lp);
        return button;
    }

    public void removeView(View view) {
        mRootLayout.removeView(view);
    }
}
