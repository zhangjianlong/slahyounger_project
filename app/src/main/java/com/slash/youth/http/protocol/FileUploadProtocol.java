package com.slash.youth.http.protocol;

import android.util.Log;

import com.core.op.lib.utils.AppToast;
import com.google.gson.Gson;
import com.slash.youth.domain.UploadFileResultBean;
import com.slash.youth.global.GlobalConstants;
import com.slash.youth.utils.LogKit;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyifeng on 2016/10/23.
 */
public class FileUploadProtocol extends BaseProtocol<UploadFileResultBean> {

    String mFilePath;

    public FileUploadProtocol(String filePath) {
        mFilePath = filePath;
    }

    @Override
    public String getUrlString() {
        return GlobalConstants.HttpUrl.IMG_UPLOAD;
    }

    @Override
    public void addRequestParams(RequestParams params) {
//        params.setAsJsonContent(false);
//        params.setMultipart(true);
//        params.addBodyParameter("filename", new File("/storage/emulated/0/360WiFi/file/4.jpg"));
        File file = new File(mFilePath);
        try {
            Log.i("FileUploadProtocol", "imgPath is  : " + getFileSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<KeyValue> multipartParams = new ArrayList<KeyValue>();
        multipartParams.add(new KeyValue("filename", file));
        MultipartBody mb = new MultipartBody(multipartParams, null);
        params.setRequestBody(mb);
    }

    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    @Override
    public UploadFileResultBean parseData(String result) {
        return mUploadFileResultBean;
    }

    UploadFileResultBean mUploadFileResultBean;

    @Override
    public boolean checkJsonResult(String result) {
        Gson gson = new Gson();
        mUploadFileResultBean = gson.fromJson(result, UploadFileResultBean.class);
        if (mUploadFileResultBean.rescode == 0) {
            return true;
        } else {
            return false;
        }
    }
}
