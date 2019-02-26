package com.zhangqie.wanandroid.api;

import com.alibaba.fastjson.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import retrofit2.HttpException;

/**
 */
public abstract class SubscriberCallBack<T> implements Observer<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

        String msg;
        //自定义异常(即未请求到数据)
        if(e instanceof ApiException){
            ApiException apiException = (ApiException) e;
            msg = apiException.getMsg();
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            if(code == 503){
                msg = "服务器维护中";
            } else if(code == 500){
                msg = "服务器内部错误";
            } else if (code == 404) {
                msg = "请求地址错误";
            } else{
                msg = "网络不给力";
            }
        } else if (e instanceof IllegalStateException
                || e instanceof ClassCastException
                || e instanceof JSONException
                || e instanceof org.json.JSONException){
            msg = "数据处理异常";
        } else if (e instanceof SocketTimeoutException){
            msg = "服务器连接超时";
        } else if (e instanceof ConnectException){
            msg = "服务器异常";
        } else if (e instanceof UnknownHostException || e instanceof SocketException){
            msg = "联网失败，请检查后重试";
        } else{
            msg = "未知错误";
        }
        onFailure(msg);
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        if(t != null){
            onSuccess(t);
        }else{
            onFailure("服务器异常~");
        }
    }

    protected abstract void onSuccess(T result);

    protected abstract void onFailure(String errorMsg);
}
