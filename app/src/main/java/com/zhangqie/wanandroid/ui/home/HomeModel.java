package com.zhangqie.wanandroid.ui.home;

import android.util.Log;

import com.zhangqie.wanandroid.api.SubscriberCallBack;
import com.zhangqie.wanandroid.bean.HomeInfo;
import com.zhangqie.wanandroid.callback.ICallBack;
import com.zhangqie.wanandroid.callback.IModelHttp;
import com.zhangqie.wanandroid.model.BaseModel;

import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhangqie on 2019/2/18
 * Describe:
 */
public class HomeModel extends BaseModel<ICallBack> implements IModelHttp {

    public HomeModel(ICallBack orderICallBack) {
        attachModel(orderICallBack);
    }

    @Override
    public void showRequestMap(Map<String, Object> map) {
        addSubscription(jsonApiService.getBorrowOrderByBorrowCode(map), new SubscriberCallBack<HomeInfo>() {

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            protected void onSuccess(HomeInfo borrowRecordInfo) {
                Log.i("aaa", borrowRecordInfo.getErrorMsg());
                listener.onSuccess(borrowRecordInfo);

            }

            @Override
            protected void onFailure(String errorMsg) {
                listener.onFailure(errorMsg);
            }

        });
    }
}
