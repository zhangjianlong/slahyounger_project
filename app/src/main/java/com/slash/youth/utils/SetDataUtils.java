package com.slash.youth.utils;

import com.core.op.lib.utils.AppToast;
import com.slash.youth.domain.SetBean;
import com.slash.youth.http.protocol.BaseProtocol;
import com.slash.youth.http.protocol.SetBaseProtocol;

import java.util.Map;

/**
 * Created by zss on 2016/11/10.
 */
public class SetDataUtils {
    //设置协议
    public static void setProtocol(String url, Map<String, String> paramsMap) {
        SetBaseProtocol setBaseProtocol = new SetBaseProtocol(url, paramsMap);
        setBaseProtocol.getDataFromServer(new BaseProtocol.IResultExecutor<SetBean>() {
            @Override
            public void execute(SetBean dataBean) {
                int rescode = dataBean.rescode;
                if (rescode == 0) {
                    AppToast.show(CommonUtils.getContext(), "举报成功！");
                    LogKit.d("SetBaseProtocol: 设置数据成功");
                } else {
                    AppToast.show(CommonUtils.getContext(), "举报失败");
                }
            }

            @Override
            public void executeResultError(String result) {
                AppToast.show(CommonUtils.getContext(), "举报失败");
                LogKit.d("result:" + result);
            }
        });
        paramsMap.clear();
    }
}
