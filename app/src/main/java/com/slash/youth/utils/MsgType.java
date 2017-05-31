package com.slash.youth.utils;

/**
 * Created by lixusign on 16/11/9.
 */
public class MsgType {

    /**通用事件消息通知融云类型*/
    /*融云消息类型RC:TxtMsg*/
    public static final int RONGYUN_MSG_TYPE_TXT                = 1;
    /*融云消息类型RC:CmdNtf*/
    public static final int RONGYUN_MSG_TYPE_CMD                = 2;

    /**通用事件消息通知业务类型*/
    /*需求-未读提醒消息事件*/
    public static final int CMD_MSG_UNREAD_EVENT    = 1;
    /*需求-抢单状态小红点消息事件*/
    public static final int CMD_MSG_BIDUPDATE_EVENT = 2;

    /**通用文本消息通知业务类型*/
    /*需求-抢单事件*/
    public static final int DEMAND_MSG_BID_EVENT                = 3;
    /*需求-需求方选择淘汰*/
    public static final int DEMAND_MSG_ELIMINATE_EVENT          = 4;
    /*需求-需求方选择待服务方确认*/
    public static final int DEMAND_MSG_SELECT_EVENT             = 5;
    /*需求-服务方修改后再次提交*/
    public static final int DEMAND_MSG_MODIFY_EVENT             = 6;
    /*需求-服务方确认待需求方支付*/
    public static final int DEMAND_MSG_CONFIRM_EVENT            = 7;
    /*需求-需求方未支付过期*/
    public static final int DEMAND_MSG_UNPAY_TIMEOUT_EVENT      = 8;
    /*需求-需求方支付待服务方提交完成任务*/
    public static final int DEMAND_MSG_PAY_EVENT                = 9;
    /*需求-服务方提交完成任务待需求方确认*/
    public static final int DEMAND_MSG_COMPLETE_EVENT           = 10;
    /*需求-需求方确认服务*/
    public static final int DEMAND_MSG_COMPLETE_CONFIRM_EVENT   = 11;
    /*需求-需求方评价后*/
    public static final int DEMAND_MSG_EVALUATE_EVENT           = 12;
    /*需求-需求方点击申请退款*/
    public static final int DEMAND_MSG_REFUND_EVENT             = 13;
    /*需求-需求方点击延期支付*/
    public static final int DEMAND_MSG_ROLLBACK_EVENT           = 14;
    /*需求-服务方点击同意退款*/
    public static final int DEMAND_MSG_REFUND_AGREE_EVENT       = 15;
    /*需求-服务方点击申诉待客服处理*/
    public static final int DEMAND_MSG_INTERVENTION_EVENT       = 16;
    /*需求-服务方点击申诉客服已处理*/
    public static final int DEMAND_MSG_ENTERVENTION_DONE_EVENT  = 17;
    /*需求-需求取消*/
    public static final int DEMAND_MSG_CANCEL_EVENT             = 18;
    /*需求-需求过期*/
    public static final int DEMAND_MSG_EXPIRE_EVENT             = 19;
    /*服务-初始化订单*/
    public static final int SERVICE_MSG_INIT_EVNET              = 101;
    /*服务-服务者确认*/
    public static final int SERVICE_MSG_CONFIRM_EVENT           = 102;
    /*服务-订单已经取消*/
    public static final int SERVICE_MSG_CANCEL_EVENT            = 104;
    /*服务-订单进行中*/
    public static final int SERVICE_MSG_PROCESSING_EVENT        = 105;
    /*服务-订单完成*/
    public static final int SERVICE_MSG_COMPLETE_EVENT          = 106;
    /*服务-订单确认完成*/
    public static final int SERVICE_MSG_CONFIRM_COMPLETE_EVENT  = 107;
    /*服务-申请退款*/
    public static final int SERVICE_MSG_REFUNDING_EVENT         = 108;
    /*服务-平台申诉处理*/
    public static final int SERVICE_MSG_INTERVENTION_EVENT      = 110;
    /*服务-服务方拒绝*/
    public static final int SERVICE_MSG_REJECT_EVENT            = 111;
    /*服务-服务者确认*/
    public static final int SERVICE_MSG_CONFIRM_MODIFY_EVENT    = 112;
    /*服务-需求方评价*/
    public static final int SERVICE_MSG_EVALUTION_EVENT         = 113;
    /*服务-需求方要求延期支付*/
    public static final int SERVICE_MSG_ROLLBACK_EVENT          = 114;
    /*服务-服务方同意退款*/
    public static final int SERVICE_MSG_AGREEREFUND_EVENT       = 115;
    /*服务-平台客服申诉处理完成*/
    public static final int SERVICE_MSG_INTERVENTION_DONE_EVENT = 116;
    /*服务-取消预约*/
    public static final int SERVICE_MSG_CANCEL_SERVICE_EVENT    = 117;
    /*服务-需求方未支付过期*/
    public static final int SERVICE_MSG_UNPAY_TIMEOUT_EVENT     = 118;
    /*服务-服务方选择淘汰*/
    public static final int SERVICE_MSG_ELIMINATE_SERVICE_EVENT = 119;

    /**密码相关消息通知业务类型**/
    public static final int PASSWORD_MSG_RETRIVE_EVENT = 200;
    public static final int PASSWORD_MSG_RETRIVE_RESULT_EVENT = 201;
    public static final int PASSWORD_MSG_SET_AFTER_FIRST_PAY_EVENT = 202;
    public static final int PASSWORD_MSG_SET_EVENT = 203;
    public static final int PASSWORD_MSG_SET_RESULT_EVENT = 204;

    /**助手相关消息通知业务类型**/
    public static final int ASSISTANT_MSG_ATTENTION_EVENT = 220;
    public static final int ASSISTANT_MSG_ADD_FRIEND_EVENT = 221;
    public static final int ASSISTANT_MSG_CONTACT_REG_PUSH_EVENT = 222;
    public static final int ASSISTANT_MSG_AUTH_RESULT_EVENT = 223;
    public static final int ASSISTANT_MSG_EVALUATION_RESULT_EVENT = 224;
    public static final int ASSISTANT_MSG_APPROVAL_FRIEND_EVENT = 225;
}