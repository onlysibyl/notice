package pers.catigeart.notice.util;


public enum CodeUtil {
    SUCCESS(200,"操作成功"),
    FAILURE(500,"操作失败"),
    VALIDATE_FAILED(404,"参数验证失败"),
    UNAUTHORIZED(401,"未登录或token已过期"),
    FORBIDDEN(403,"没有操作权限");

    private final Integer code;
    private final String message;

    CodeUtil(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
