package com.cyd.xs.config;

import com.cyd.xs.Utils.ResultUtil;
import com.cyd.xs.Utils.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultVO<Void>> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder("参数校验失败：");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsg.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append("，");
        }
        String msg = errorMsg.substring(0, errorMsg.length() - 1);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultUtil.error(400, msg));
    }

    // 处理业务异常（Service中抛出的RuntimeException）
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResultVO<Void>> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultUtil.error(400, e.getMessage()));
    }

    // 处理其他所有异常（兜底）
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultVO<Void>> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultUtil.error(500, "服务器内部错误：" + e.getMessage()));
    }
}
