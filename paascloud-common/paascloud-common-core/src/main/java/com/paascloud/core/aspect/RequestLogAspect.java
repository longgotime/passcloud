package com.paascloud.core.aspect;

import com.google.common.collect.Maps;
import com.paascloud.core.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * The type Request log aspect.
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018 /7/13 下午11:38
 */
@Slf4j
//@Aspect
//@Component
public class RequestLogAspect {
    /**
     * Point cut wrapper.
     */
    @Pointcut("execution(public com.paascloud.wrapper.Wrapper *(..))")
    public void pointCutWrapper() {
    }


    /**
     * Method wrapper handler object.
     *
     * @param pjp the pjp
     * @return the object
     */
    @Around("pointCutWrapper()")
    public Object methodWrapperHandler(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        HttpServletRequest request = RequestUtil.getRequest();

        Map<String, Object> param = Maps.newHashMap();

        param.put("URL", request.getRequestURI());
        param.put("HTTP_METHOD", request.getMethod());
        param.put("IP", request.getRemoteAddr());
        param.put("CLASS_METHOD", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        param.put("ARGS", Arrays.toString(pjp.getArgs()));

        log.info("request param={}",  param);

        Object result;

        try {
            result = pjp.proceed();
            log.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            log.error("异常信息：{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return result;
    }
}
