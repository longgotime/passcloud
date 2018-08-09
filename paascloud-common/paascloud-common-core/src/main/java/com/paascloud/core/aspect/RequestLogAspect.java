package com.paascloud.core.aspect;

import com.google.common.collect.Maps;
import com.paascloud.core.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
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
@Aspect
@Component
@Order(Integer.MIN_VALUE + 1)
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
    public Object methodWrapperHandler(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            Map<String, Object> param = Maps.newHashMap();

            param.put("URL", request.getRequestURI());
            param.put("HTTP_METHOD", request.getMethod());
            param.put("IP", request.getRemoteAddr());
            param.put("CLASS_METHOD", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
            param.put("ARGS", Arrays.toString(pjp.getArgs()));

            log.info("request param={}",  param);
        }

        Object result;

        try {
            result = pjp.proceed();
            log.debug("{} response = {}", pjp.getSignature(), request);
        } finally {
            log.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        }

        return result;
    }
}
