package com.cyd.xs.Utils;

import lombok.Data;

// 必须加 @Data（或手动实现无参构造和 getter）
@Data
public class ResultVO<T> {
    private int code;       // 响应码（如200成功，500失败）
    private String message; // 响应信息
    private T data;         // 响应数据

    public ResultVO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 若用 @Data，无需手动写无参构造（自动生成）
    // 若不用 Lombok，必须手动加无参构造：
    public ResultVO() {}

    // 静态工厂方法（可选，方便调用）
    public static <T> ResultVO<T> success(String message, T data) {
        ResultVO<T> result = new ResultVO<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> ResultVO<T> fail(String message) {
        ResultVO<T> result = new ResultVO<>();
        result.setCode(500);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    // 添加缺失的 error 方法
    public static <T> ResultVO<T> error(int code, String message) {
        ResultVO<T> result = new ResultVO<>();
        result.code = code;
        result.message = message;
        return result;
    }
}