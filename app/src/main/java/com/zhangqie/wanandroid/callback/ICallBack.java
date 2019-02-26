package com.zhangqie.wanandroid.callback;

public interface ICallBack<R> {

    void onSuccess(R r);

    void onFailure(String errorMsg);
}
