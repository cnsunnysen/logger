package com.mysteelsoft.logger.other;

/**
 * @author shanyesen
 */
public enum ErrorCode {

    /**
     * 用户名或密码错误
     */
    ERR_100("100", "登录失败"),

    /**
     * 登录状态失效，请重新登录
     */
    ERR_101("101", "登录状态失效"),


    /**
     * 该用户没有权限
     */
    ERR_102("102", "该用户没有权限"),

    /**
     * 操作员未设置可操作仓库
     */
    ERR_103("103", "操作员未设置可操作仓库"),

    /**
     * 请求业务参数缺失
     */
    ERR_203("203", "请求业务参数缺失"),

    /**
     * 业务参数不合法
     */
    ERR_204("204", "业务参数不合法"),

    /**
     * 签名错误
     */
    ERR_205("205", "签名错误"),

    /**
     * 两服务器时间存在5分钟以上误差
     */
    ERR_206("206", "签名时间不合法"),

    /**
     * 未找到对应派车单
     */
    ERR_210("210", "未找到对应派车单"),

    /**
     * 对应派车单不具备收发货确认条件<br>
     * 比如尚未一次称重，则不能进行收发货确认
     */
    ERR_211("211", "对应派车单不具备收发货确认条件"),

    /**
     * 对应派车单已经收发货完成
     */
    ERR_212("212", "对应派车单已经收发货完成"),


    /**
     * 已有采样卡号（一车多样时该功能要拼比）
     */
    ERR_251("251", "已采样"),

    /**
     * 对应派车单不具备采样条件<br>
     * 比如未入厂，则不能进行采样确认
     */
    ERR_252("252", "对应派车单不具备采样条件"),

    /**
     * 卡号不为空
     */
    ERR_253("253", "卡号不为空"),

    /**
     * 未找到对应采样卡
     */
    ERR_254("254", "未找到对应采样卡"),


    /**
     * 未找到对应卡
     */
    ERR_255("255", "未找到对应卡"),
    /**
     *
     */
    ERR_222("222", "未知异常"),


    ;


    private String code;

    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorCode{");
        sb.append("code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
