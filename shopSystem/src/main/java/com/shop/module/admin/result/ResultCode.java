package com.shop.module.admin.result;

/**
 * @author zhoulanzhen
 * @date 2019/7/016 13:38
 */
public enum ResultCode implements IErrorCode {
    /**
     *
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或Token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    ACCOUNTABNORMALITY(403, "账户状态异常"),
    ;
    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
