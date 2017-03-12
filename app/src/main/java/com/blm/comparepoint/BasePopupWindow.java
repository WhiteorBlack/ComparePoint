package com.blm.comparepoint;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.blm.comparepoint.interfacer.PopInterfacer;


/**
 * Created by 41508 on 2017/3/7.
 */

public class BasePopupWindow extends PopupWindow implements View.OnClickListener{
    public PopInterfacer popInterfacer;
    public int flag;
    public Context context;

    public BasePopupWindow(Context context) {
        this.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        this.getBackground().setAlpha(0);
        this.context = context;
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.audi_anim);
    }

    public void setPopInterfacer(PopInterfacer l, int flag) {
        this.popInterfacer = l;
        this.flag = flag;
    }


    @Override
    public boolean isOutsideTouchable() {
        return super.isOutsideTouchable();
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (popInterfacer != null)
            popInterfacer.OnDismiss(flag);
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
    }

    public void showPop(View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View v) {

    }
}
