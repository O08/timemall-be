package com.norm.timemall.app.base.exception;

import com.norm.timemall.app.base.entity.ErrorVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolationException;

/**
 * 异常处理
 *
 * @author yanpanyi
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ErrorVO exceptionHandler(Exception e) {
        log.error("error:", e);
        return new ErrorVO(CodeEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BindException.class)
    public ErrorVO bindExceptionHandler(BindException e) {
        log.error("error:", e);
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.INVALID_PARAMETERS.getCode());
        vo.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return vo;
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ErrorVO messageExceptionHandler(HttpMessageNotReadableException e) {
        log.warn("http请求参数转换异常: "+ e.getMessage());
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.REQUEST_MESSAGE_NOT_READABLE.getCode());
        vo.setMessage(CodeEnum.REQUEST_MESSAGE_NOT_READABLE.getDesc());
        return vo;
    }



    @ExceptionHandler(value = ErrorCodeException.class)
    public ErrorVO errorCodeHandler(ErrorCodeException e) {
        log.error("error:", e);
        return new ErrorVO(e.getCode());
    }
    @ExceptionHandler(value = MultipartException.class)
    public ErrorVO multipartExceptionHandler(MultipartException e) {
        log.error("error:", e);
        return new ErrorVO(CodeEnum.FILE_IS_EMPTY);
    }
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ErrorVO missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("error:", e);
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.INVALID_PARAMETERS.getCode());
        vo.setMessage(e.getMessage());
        return vo;
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ErrorVO constraintViolationExceptionHandler(Exception e) {
        log.error("error:", e);
        ErrorVO vo = new ErrorVO();
        vo.setCode(CodeEnum.INVALID_PARAMETERS.getCode());
        vo.setMessage(e.getMessage());
        return vo;
    }

}
