package com.blm.comparepoint.widget;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.blm.comparepoint.R;


public class MSGCountTimeView extends android.support.v7.widget.AppCompatTextView {
    // handler��Message
    private static final int COUNTTIME = 1;

    // �ṩĬ�ϵ�����
    private static final String INITTEXT = "";
    private static final String PREFIXRUNTEXT = "";
    private static final String SUFFIXRUNTEXT = "";
    private static final String FINISHTEXT = "";
    private static final int TOTALTIME = 60 * 1000;
    private static final int ONETIME = 1000;
    private static final int COLOR = Color.WHITE;

    // ���Բ����ļ��е���������
    private String mInittext;// ��ʼ���ı�
    private String mPrefixRuntext;// ����ʱ���ı�ǰ׺
    private String mSuffixRuntext;// ����ʱ���ı���׺
    private String mFinishtext = "0";// ��ɵ���ʱ����ı���ʾ
    private int mTotaltime;// ����ʱ����ʱ��
    private int mOnetime;// һ��ʱ��
    private int mColor;

    // ʵ��ʹ�õ���ʱ��
    private int Totaltime;

    // �ж��Ƿ��ڵ���ʱ�У���ֹ��ε��
    private boolean isRun;
    // �Ƿ�������ʱ
    private boolean isAllowRun;

    // ������ʱ�ķ���
    private Timer mTimer;
    private TimerTask mTimerTask;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COUNTTIME:
                    // ���������и�ʽ��
                    DecimalFormat df = new DecimalFormat("#00");
                    String strTotaltime = df.format(Totaltime / 1000);
                    String runtimeText = mPrefixRuntext + strTotaltime + mSuffixRuntext;

                    // ������������ɫ����
                    Spannable spannable = new SpannableString(runtimeText);
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(mColor);
                    spannable.setSpan(redSpan, mPrefixRuntext.length(), mPrefixRuntext.length() + strTotaltime.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    MSGCountTimeView.this.setText(spannable);
                    Totaltime -= mOnetime;
                    mDownTime.onDown(Totaltime / 1000);
                    if (Totaltime < 0) {
                        MSGCountTimeView.this.setText(mFinishtext);
                        isRun = false;
                        clearTimer();
                        mDownTime.onFinish();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * ����ʱ�ļ���
     */
    private onDownTime mDownTime = new onDownTime() {

        @Override
        public void onFinish() {

        }

        @Override
        public void onDown(int tiem) {

        }
    };

    public MSGCountTimeView(Context context) {
        this(context, null);
    }

    public MSGCountTimeView(Context context, AttributeSet attrs) {
        // �����дandroid.R.attr.textViewStyle�ᶪʧ�ܶ�����
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public MSGCountTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 1. �ڲ����ļ����ṩ����
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MSGCountTimeView);
        mInittext = ta.getString(R.styleable.MSGCountTimeView_inittext);
        mPrefixRuntext = ta.getString(R.styleable.MSGCountTimeView_prefixruntext);
        mSuffixRuntext = ta.getString(R.styleable.MSGCountTimeView_suffixruntext);
        mFinishtext = ta.getString(R.styleable.MSGCountTimeView_finishtext);
        mTotaltime = ta.getInteger(R.styleable.MSGCountTimeView_totaltime, TOTALTIME);
        mOnetime = ta.getInteger(R.styleable.MSGCountTimeView_onetime, ONETIME);
        mColor = ta.getColor(R.styleable.MSGCountTimeView_timecolor, COLOR);
        ta.recycle();
        // 2.��������ֵ
        // 3.������ֺʹ��붼û�����ã������Ĭ��ֵ
        initData();
        initTimer();
    }

    /**
     * ��ʼ������
     */
    private void initData() {
        // ���Ϊ�գ�������Ĭ�ϵ�ֵ

        if (TextUtils.isEmpty(mInittext)) {
            mInittext = INITTEXT;
        }
        if (TextUtils.isEmpty(mPrefixRuntext)) {
            mPrefixRuntext = PREFIXRUNTEXT;
        }
        if (TextUtils.isEmpty(mSuffixRuntext)) {
            mSuffixRuntext = SUFFIXRUNTEXT;
        }
        if (TextUtils.isEmpty(mFinishtext)) {
            mFinishtext = FINISHTEXT;
        }
        if (mTotaltime < 0) {
            mTotaltime = TOTALTIME;
        }
        if (mOnetime < 0) {
            mOnetime = ONETIME;
        }
        MSGCountTimeView.this.setText(mInittext);
    }

    /**
     * ��ʼ��ʱ��
     */
    private void initTimer() {
        Totaltime = mTotaltime;
        mTimer = new Timer();
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                mHandler.sendEmptyMessage(COUNTTIME);
            }
        };
    }


    public void startCount() {
        if (!isAllowRun) {
        } else {
            if (!isRun) {
                initTimer();
                mTimer.schedule(mTimerTask, 0, mOnetime);
                isRun = true;
            }
        }
    }

    /**
     * ���ʱ��
     */
    private void clearTimer() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * @param mInittext
     * @return
     */
    public MSGCountTimeView setInittext(String mInittext) {
        this.mInittext = mInittext;
        MSGCountTimeView.this.setText(mInittext);
        return this;
    }

    /**
     * @return
     */
    public MSGCountTimeView setPrefixRuntext(String mPrefixRuntext) {
        this.mPrefixRuntext = mPrefixRuntext;
        return this;
    }

    /**
     * @return
     */
    public MSGCountTimeView setSuffixRuntext(String mSuffixRuntext) {
        this.mSuffixRuntext = mSuffixRuntext;
        return this;
    }

    /**
     * ���ý���������
     *
     * @param mFinishtext
     * @return
     */
    public MSGCountTimeView setFinishtext(String mFinishtext) {
        this.mFinishtext = mFinishtext;
        return this;
    }

    /**
     * ���õ���ʱ����ʱ��
     *
     * @param mTotaltime
     * @return
     */
    public MSGCountTimeView setTotaltime(int mTotaltime) {
        this.mTotaltime = mTotaltime;
        return this;
    }

    /**
     * ����һ�ε���ʱ��ʱ��
     *
     * @param mOnetime
     * @return
     */
    public MSGCountTimeView setOnetime(int mOnetime) {
        this.mOnetime = mOnetime;
        return this;
    }

    /**
     * ����Ĭ�ϵ���ʱ��������ɫ
     *
     * @param mColor
     * @return
     */
    public MSGCountTimeView setTimeColor(int mColor) {
        this.mColor = mColor;
        return this;
    }

    /**
     * �����ṩ�ӿڣ���д����ʱʱ�͵���ʱ���ʱ�Ĳ���
     *
     * @author yuhao 2016��3��15��
     */
    public interface onDownTime {
        void onDown(int time);

        void onFinish();
    }

    public void onDownTime(onDownTime mDownTime) {
        this.mDownTime = mDownTime;
    }

    /**
     * ��������ʱ������ʱֹͣ
     */
    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
        clearTimer();
    }

    /**
     * �Ƿ�������ʱ
     */
    public void isAllowRun(Boolean isAllowRun) {
        this.isAllowRun = isAllowRun;
    }
}
