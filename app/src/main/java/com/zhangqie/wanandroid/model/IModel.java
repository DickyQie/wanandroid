package com.zhangqie.wanandroid.model;




/**
 * 接口回调
 */
public interface IModel<L> {

    void attachModel(L listener);

    void detachModel();


}
