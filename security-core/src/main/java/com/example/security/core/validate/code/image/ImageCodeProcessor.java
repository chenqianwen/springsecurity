package com.example.security.core.validate.code.image;

import com.example.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * 图片验证码处理器
 */
@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode>{

    /**
     * 发送图形验证码，写到响应中
     * @param request
     * @param ImageCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode ImageCode) throws Exception {
        ImageIO.write(ImageCode.getImage(),"JPEG",request.getResponse().getOutputStream());

    }
}
