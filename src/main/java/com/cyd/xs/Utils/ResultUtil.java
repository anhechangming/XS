package com.cyd.xs.Utils;

public class ResultUtil {
    public static <T> ResultVO<T> success(String message, T data) {
        return new ResultVO<>(200, message, data);
    }

    public static <T> ResultVO<T> error(int code, String message) {
        return new ResultVO<>(code, message, null);
    }
}