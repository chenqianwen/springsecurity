package com.example.security.core.validate.code;

import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.image.ImageCode;
import com.example.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class ValidateCodeController {

    /**
     *  收集所有的ValidateCodeProcessor 接口的实现
     *  {实现类的名字 ： 类}  ===> [{"imageCodeProcessor",ImageCodeProcessor}......]
     */
    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessors;


    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {

        // 获取对应的处理器
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(type + "CodeProcessor");
        // 创建验证码
        validateCodeProcessor.create(new ServletWebRequest(request, response));
    }

}
