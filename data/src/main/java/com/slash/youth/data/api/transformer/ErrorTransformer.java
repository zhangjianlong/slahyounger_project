package com.slash.youth.data.api.transformer;


import android.widget.Toast;

import com.slash.youth.data.api.BaseResponse;
import com.slash.youth.data.api.exception.RepositoryException;

import rx.Observable;
import rx.Subscriber;


/**
 * @author op
 * @version 1.0
 * @description 对response数据进行拦截处理
 * @createDate 2016/3/24
 */
public class ErrorTransformer<T> implements Observable.Transformer<BaseResponse<T>, T> {

    @Override
    public Observable<T> call(Observable<BaseResponse<T>> comicVineResponseObservable) {
        return comicVineResponseObservable.flatMap(response -> flatResponse(response));
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final BaseResponse<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                String msg = "";
                switch (response.getRescode()) {
                    case 0:
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(response.getData());
                            subscriber.onCompleted();
                        }
                        return;
                    case 1:
                        msg = "请求失败！";
                        break;
                    case 2:
                        msg = "参数错误！";
                        break;
                    case 3:
                        msg = "无效token！";
                        break;
                    case 4:
                        msg = "token超时！";
                        break;
                    case 5:
                        msg = "无效的用户名密码！";
                        break;
                    case 6:
                        msg = "用户存在！";
                        break;
                    case 7:
                        msg = "无效的验证码！";
                        break;
                    case 8:
                        msg = "无效的uid！";
                        break;
                    case 9:
                        msg = "需要手机号登录！";
                        break;
                    case 10:
                        msg = "未找到！";
                        break;
                    case 11:
                        msg = "新用户！";
                        break;
                    case 50:
                        msg = "标签已存在！";
                        break;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(new RepositoryException(msg));
                }
            }
        });
    }
}
