package pers.catigeart.notice.util;

import lombok.Data;


@Data
public class Result<T> {

    private boolean success;
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    // 添加无参构造函数
    private Result() {
    }

    // 带三个参数的构造函数
    private Result(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.code = success ? CodeUtil.SUCCESS.getCode() : CodeUtil.FAILURE.getCode();
    }

    public static <T> Result<T> success() {
        return new Result<>(true, null, CodeUtil.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data, CodeUtil.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, data, message);
    }

    public static <T> Result<T> success(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.success = true;
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(CodeUtil.FAILURE.getCode());
        result.setMessage(CodeUtil.FAILURE.getMessage());
        return result;
    }

    public static <T> Result<T> fail(T data) {
        Result<T> result = fail();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String message, T data) {
        Result<T> result = fail();
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = fail();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> fail(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    // 添加新的 fail 方法重载
    public static <T> Result<T> fail(String message) {
        Result<T> result = fail();
        result.setMessage(message);
        return result;
    }
}
