package com.zhangqie.wanandroid.api;





import com.zhangqie.wanandroid.config.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by zhh
 */
public class ApiManager {

    /**
     * 前缀url不同则ApiService不同，尽量一个前缀
     * 一个前缀-》则就创建2个ApiService对象：json的+string的
     */


    /**
     * 这个是返回json数据(是对象）时请求所用的对象
     */
    private ApiService jsonApiService;

    /**
     * //这个是返回json是string数据时请求所用的对象
     */
    private ApiService strApiService;


    public ApiManager() {
        jsonApiService = getJsonServiceInstance();
        strApiService = getStrServiceInstance();
    }

    //在访问HttpMethods时创建单例
    private static final class SingletonHolder {
        private static final ApiManager INSTANCE = new ApiManager();
    }

    //获取单例
    public static ApiManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ApiService getJsonApiService() {
        return jsonApiService;
    }

    public ApiService getStrApiService() {
        return strApiService;
    }


    private ApiService getJsonServiceInstance() {
        if (null == jsonApiService) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain
                            .request()
                            .newBuilder()
                            .build();
                    return chain.proceed(request);
                }
            }).connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Api.Companion.getWanUrl())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            jsonApiService = retrofit.create(ApiService.class);
        }

        return jsonApiService;
    }


    private ApiService getStrServiceInstance() {
        if (null == strApiService) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain
                            .request()
                            .newBuilder()
                            .build();
                    return chain.proceed(request);
                }
            }).connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Api.Companion.getWanUrl())//这里是GTIHUB_ULR的，调试，改了
                    .addConverterFactory(StringConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            strApiService = retrofit.create(ApiService.class);
        }

        return strApiService;
    }

}
