package com.blm.comparepoint.widget;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ScrollTextView extends BaseScollTextView<String> {
    public ScrollTextView(Context context) {
        super(context);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getItemText(List<String> data, int postion) {
        return data.get(postion);
    }
}
