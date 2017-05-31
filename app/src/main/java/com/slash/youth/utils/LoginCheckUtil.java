package com.slash.youth.utils;

import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.StrUtil;
import com.slash.youth.R;

/**
 * Created by zjl on 2017/3/24.
 */



public class LoginCheckUtil {

    private static final int PHONE_LENGTH = 11;

    public static boolean checkPhoeNumFormat(String phone){
        String tempPhone;
        if (StrUtil.isEmpty(phone)){
            AppToast.show(CommonUtils.getContext(),CommonUtils.getContext().getString(R.string.app_login_phone_error));
            return  false;
        }
        tempPhone = phone.trim();
        if (tempPhone.length()<PHONE_LENGTH){
            AppToast.show(CommonUtils.getContext(), CommonUtils.getContext().getString(R.string.app_login_phone_num_error));
            return false;
        }
     return true;
    }


    public static  boolean checkLogin(String phone,String verifyCode,boolean isAgree){
        if (!checkPhoeNumFormat(phone)){
            return false;
        }
        if (StrUtil.isEmpty(verifyCode)){
            AppToast.show(CommonUtils.getContext(),CommonUtils.getContext().getString(R.string.app_logig_verify_code_error));
            return  false;
        }
        if (!isAgree){
            AppToast.show(CommonUtils.getContext(),CommonUtils.getContext().getString(R.string.app_login_user_agreement));
            return  false;
        }
        return true;
    }


    public static  boolean checkBinding(String phone,String verifyCode){
        if (!checkPhoeNumFormat(phone)){
            return false;
        }
        if (StrUtil.isEmpty(verifyCode)){
            AppToast.show(CommonUtils.getContext(),CommonUtils.getContext().getString(R.string.app_logig_verify_code_error));
            return  false;
        }

        return true;
    }
}
