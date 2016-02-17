package com.example.yanci.core;

/**
 * Created by yanci on 16/2/17.
 */
public interface AppActionCallBackListener<T> {
    /**
     * 成功时调用
     *
     * @param data 返回的数据
     */
    public void onSuccess(T data);

    /**
     * 失败时调用
     *
     * @param errorEvent 错误码
     * @param message    错误信息
     */
    public void onFailure(String errorEvent, String message);
}
