package com.yuzhi.fine.listener;

/**
 * Created by tiansj on 2017/12/9.
 */
public class View {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void click() {
        if(onClickListener != null) {
            onClickListener.onClick();
        }
    }

}
