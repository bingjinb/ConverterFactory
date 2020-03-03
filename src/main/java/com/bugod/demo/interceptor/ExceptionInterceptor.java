package com.bugod.demo.interceptor;


import com.bugod.demo.common.constant.ErrorCodeEnum;
import com.bugod.demo.common.entity.ResultWrapper;
import com.bugod.demo.common.exception.ApiException;
import com.bugod.demo.common.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletException;
import java.util.List;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ExceptionInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResultWrapper handleException(Exception ex) throws ServletException {
        ResultWrapper resultWrapper = new ResultWrapper();
        Integer errorCode = ErrorCodeEnum.FAIL.getKey();
        String stack = "";

        if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<ObjectError> FieldErrors = bindingResult.getAllErrors();
            StringBuilder errorMsgTmp = new StringBuilder();
            for (ObjectError oe : FieldErrors) {
                FieldError fe = (FieldError) oe;
                String field = fe.getField();
                String defaultMessage = fe.getDefaultMessage();
                errorMsgTmp.append(field).append("|").append(defaultMessage);
            }
            errorCode = ErrorCodeEnum.ARGS_ERROR.getKey();
            stack = errorMsgTmp.toString();
            logger.error(errorMsgTmp.toString(), ex);
        } else if (ex instanceof ApiException) {
            errorCode = ((ApiException) ex).getCode();
        } else {
            logger.error(ex.getMessage(), ex);
        }
        if (ValidatorUtil.isNullOrEmpty(stack)) {
            stack = ex.getMessage() == null ? ex.toString() : ex.getMessage();
        }

        resultWrapper.setCode(errorCode);
        resultWrapper.setMessage(ErrorCodeEnum.desc(errorCode));
        resultWrapper.setStack(stack);
        resultWrapper.setSuccess(false);

        return resultWrapper;
    }
}
