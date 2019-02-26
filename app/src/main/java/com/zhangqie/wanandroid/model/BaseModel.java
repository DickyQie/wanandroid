package com.zhangqie.wanandroid.model;



import com.zhangqie.wanandroid.api.ApiManager;
import com.zhangqie.wanandroid.api.ApiService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chengwen on 2016/8/4.
 */
public class BaseModel<L> implements IModel<L> {

    public L listener;
    public ApiService jsonApiService = ApiManager.getInstance().getJsonApiService();
    public ApiService strApiService = ApiManager.getInstance().getStrApiService();

    public CompositeDisposable compositeDisposable;

    @Override
    public void attachModel(L listener) {
        this.listener = listener;
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    public void detachModel() {
        this.listener = null;
        onUnsubscribe();
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {

      if (compositeDisposable != null){
          compositeDisposable.dispose();
      }
    }

    public void addSubscription(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
