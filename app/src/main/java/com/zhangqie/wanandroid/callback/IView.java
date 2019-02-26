package com.zhangqie.wanandroid.callback;

/**
 * Created by Administrator on 2017/1/4.
 */

public interface IView<L> {
    void onLoadContributorStart();

    void onLoadContribtorComplete(L topContributor);

    void onNetWork();

    void onError(String error);
}
