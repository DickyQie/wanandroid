package com.zhangqie.wanandroid.tool;

import com.zhangqie.wanandroid.loading.ProgressDialogHandler;

/**
 * Created by zhangqie on 2019/2/20
 * Describe:
 */
public class ProgressDialogUtil {

    /***
     * 打开进度条
     * @param mProgressDialogHandler
     */
    public static void showProgressDialog(ProgressDialogHandler mProgressDialogHandler) {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)
                    .sendToTarget();
        }
    }

    /***
     * 关闭进度条
     * @param mProgressDialogHandler
     */
    public static void dismissProgressDialog(ProgressDialogHandler mProgressDialogHandler) {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            mProgressDialogHandler = null;
        }

    }
}
