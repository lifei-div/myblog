package com.fly.myblog.hander;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的错误就需要我们自己来拦截了
 */
//拦截有Controller注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {
    //    将异常记录到日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {
        //记录异常信息，请求的URL，异常信息
        logger.error("Request URL:{},Exception : {}",request.getUserPrincipal(),e);

        //当表示了状态码的时候就不拦截
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
                throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURI());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;

    }




}
