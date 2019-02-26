package com.zhangqie.wanandroid.api;


import com.zhangqie.wanandroid.bean.BannerInfo;
import com.zhangqie.wanandroid.bean.HomeInfo;
import com.zhangqie.wanandroid.bean.LoginData;
import com.zhangqie.wanandroid.bean.ModularInfo;
import com.zhangqie.wanandroid.bean.ModularListInfo;
import com.zhangqie.wanandroid.bean.NavigationInfo;
import com.zhangqie.wanandroid.bean.ProjectListInfo;
import com.zhangqie.wanandroid.bean.ProjectTreeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 */
public interface ApiService {


    @GET("banner/json")
    Observable<BannerInfo> getBannerInfo();


    //http://wanandroid.com/article/listproject/1/json
    @GET("article/listproject/{page}/json")
    Observable<HomeInfo> getHomeDataInfo(@Path("page") String page);


    //知识模块 "http://www.wanandroid.com/tree/json"
    @GET("tree/json")
    Observable<ModularInfo> getModularDataInfo();


    //导航模块
    @GET("navi/json")
    Observable<NavigationInfo> getNavigationDataInfo();


    @GET("project/tree/json")
    Observable<ProjectTreeBean> getProjectTreeData();


    //http://www.wanandroid.com/project/list/1/json?cid=294

    @GET("project/list/{page}/json")
    Observable<ProjectListInfo> getProjectListData(@Path("page") int page, @Query("cid") int cid);


    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page
     * @param cid
     */
    @GET("article/list/{page}/json")
    Observable<ModularListInfo> getModularListData(@Path("page") int page, @Query("cid") int cid);


    /**
     * 注册
     * http://www.wanandroid.com/user/register
     * @param username
     * @param password
     * @param repassword
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<LoginData> registerWanAndroid(@Field("username") String username,
                                             @Field("password") String password,
                                             @Field("repassword") String repassword);


    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * @param username
     * @param password
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginData> loginWanAndroid(@Field("username") String username,
                                          @Field("password") String password);



    //http://zmxy.blibao.com/interface/getOrderDetail.htm?shopperId=9356&machineId=5117&orderType=2&orderId=108
    @FormUrlEncoded
    @POST("interface/getOrderDetail.htm")
    Observable<ModularListInfo> getBorrowOrderByBorrowCode(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("list")
    Observable<String> getDetailContent(@FieldMap Map<String, Object> params);


}
