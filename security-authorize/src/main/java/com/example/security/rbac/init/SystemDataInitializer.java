package com.example.security.rbac.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 *  系统初始化器
 */
@Component
@Slf4j
public class SystemDataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 系统中所有的{@link DataInitializer}接口实现
     */
    @Autowired(required = false)
    private List<DataInitializer> dataInitializers;

    /**
     * 循环调用系统中所有的DataInitializer
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(CollectionUtils.isNotEmpty(dataInitializers)){

            dataInitializers.sort((initor1, initor2) -> {
                return initor1.getIndex().compareTo(initor2.getIndex());
            });

            dataInitializers.stream().forEach(dataInitializer -> {
                try {
                    dataInitializer.init();
                } catch (Exception e) {
                    log.info("系统数据初始化失败("+dataInitializer.getClass().getSimpleName()+")", e);
                }
            });
        }
    }

}
