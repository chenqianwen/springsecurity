package com.example.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@Aspect
@Component
public class TimeAspect {

    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Exception{
        return null;
    }
}
