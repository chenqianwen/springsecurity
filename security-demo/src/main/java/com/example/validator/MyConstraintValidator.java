package com.example.validator;

import com.example.web.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author： ygl
 * @date： 2018/2/10-16:16
 * @Description：
 * spring自动转配成bean
 */
@Slf4j
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object>{

    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        log.info("my validator init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        helloService.greet("jack");
        log.debug(value.toString());
        return false;
    }
}
