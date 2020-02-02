package cn.wwq.rabbit.api;

/**
 * 回调函数处理
 */
public interface SendCallback {

    void onSuccess();

    void onFailure();

}
