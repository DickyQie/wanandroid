package com.zhangqie.wanandroid.api;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /*
    * 转换方法
    */
    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        Log.i("zhh", "返回的数据：" + tempStr);
        if(TextUtils.isEmpty(tempStr)){
            return null;
        }
        return JSON.parseObject(tempStr, type);

    }

}
