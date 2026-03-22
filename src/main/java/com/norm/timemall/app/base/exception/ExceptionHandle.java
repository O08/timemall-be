package com.norm.timemall.app.base.exception;

import com.norm.timemall.app.base.entity.ErrorVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;

/**
 * 异常处理
 *
 * @author yanpanyi
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorVO> exceptionHandler(Exception e) {
        log.error("error:", e);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorVO(CodeEnum.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ErrorVO> bindExceptionHandler(BindException e) {
        log.error("error:", e);
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.INVALID_PARAMETERS.getCode());
        vo.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(vo);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorVO> messageExceptionHandler(HttpMessageNotReadableException e) {
        log.error("http请求参数转换异常: "+ e.getMessage());
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.REQUEST_MESSAGE_NOT_READABLE.getCode());
        vo.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(vo);
    }


    @ExceptionHandler(value = QuickMessageException.class)
    public ResponseEntity<ErrorVO> quickMessageHandler(QuickMessageException e) {
        log.error("error:", e);
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.FAILED.getCode());
        vo.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(vo);
    }
    @ExceptionHandler(value = MultipartException.class)
    public ResponseEntity<ErrorVO> multipartExceptionHandler(MultipartException e) {
        log.error("error:", e);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorVO(CodeEnum.FILE_IS_EMPTY));
    }
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorVO> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("error:", e);
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.INVALID_PARAMETERS.getCode());
        vo.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(vo);
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorVO> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("error:", e);
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.INVALID_PARAMETERS.getCode());
        vo.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(vo);
    }
    @ExceptionHandler(value = MissingServletRequestPartException.class)
    public ResponseEntity<ErrorVO> missingServletRequestPartExceptionHandler(MissingServletRequestPartException e) {
        log.error("error:", e);
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.INVALID_PARAMETERS.getCode());
        vo.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(vo);
    }

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<ErrorVO> handleStreamException(ErrorCodeException e) {
        log.error("error:", e);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON) // This is the key!
                .body(new ErrorVO(e.getCode()));
    }

    @ExceptionHandler(value = org.apache.catalina.connector.ClientAbortException.class)
    public void handleClientAbortException(Exception e) {
        // 直接忽略，不需要返回 ErrorVO 给客户端，因为连接已经断了
        log.debug("Detected ClientAbortException: Peer closed connection.");
    }

    @ExceptionHandler(value = {java.io.IOException.class})
    public void handleIOException(IOException e, HttpServletRequest request) {
        String message = e.getMessage();
        // 识别常见的客户端断开异常关键字
        if (message != null && (message.contains("Broken pipe") || message.contains("Connection reset"))) {
            // 降低日志级别，只在 DEBUG 模式下可见
            log.debug("客户端主动断开连接 (User scrolled away or closed tab): {} - {}", request.getRequestURI(), message);
        } else {
            // 真正的 IO 错误才报 ERROR
            log.error("IO Exception occurred:", e);
        }
    }



}
