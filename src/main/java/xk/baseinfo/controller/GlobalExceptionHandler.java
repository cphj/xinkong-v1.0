package xk.baseinfo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xk.common.exception.BusinessException;
import xk.common.result.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }
    /**
     * 校验错误拦截处理
     * 处理{@link } & {@link }
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<Void> validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for(ObjectError error : errors){
                FieldError fieldError = (FieldError) error;
                return new ApiResult<Void>(500,
                        result.getFieldError() == null ? "请求参数有误" : fieldError.getDefaultMessage(), null);
            }
            return null;
        }
        //其他错误
        return new ApiResult<Void>(500, "其他错误",null);
    }

    /**
     * 参数类型转换错误 {@link HttpMessageConversionException}
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ApiResult<Void> parameterTypeException(HttpMessageConversionException exception) {
        log.warn("parameterTypeException {}", exception.getCause().getLocalizedMessage());
        return new ApiResult<Void>(500, "请求参数类型有误",null);
    }

    /**
     * 业务层异常统一解析
     * @param req
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResult<Object> serviceExceptionErrorHandler(HttpServletRequest req,
                                                          HttpServletResponse response, BusinessException e)  {
        return new ApiResult<Object>(e.getCode(), e.getMsg(), null);
    }
}
