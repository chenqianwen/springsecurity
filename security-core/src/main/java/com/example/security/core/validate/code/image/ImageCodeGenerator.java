package com.example.security.core.validate.code.image;

import com.example.security.core.properties.ImageCodeProperties;
import com.example.security.core.properties.SecurityProperties;
import com.example.security.core.validate.code.ValidateCodeGenerator;
import com.example.security.core.validate.code.image.ImageCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 默认的图片验证码的生成器
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

    @Setter
    @Getter
    private SecurityProperties securityProperties;

    @Override
    public ImageCode generate(ServletWebRequest request) {

        ImageCodeProperties imageCodeProperties = securityProperties.getCode().getImage();

        // 图片的宽
        int codeWidth = ServletRequestUtils.getIntParameter(request.getRequest(),"width",imageCodeProperties.getWidth());
        // 图片的宽
        int codeHeight = ServletRequestUtils.getIntParameter(request.getRequest(),"height",imageCodeProperties.getHeight());
        // 图片验证码的长度
        int codeLength = ServletRequestUtils.getIntParameter(request.getRequest(),"length",imageCodeProperties.getLength());
        // 图片验证码的过期秒数
        int codeExpireIn = ServletRequestUtils.getIntParameter(request.getRequest(),"expireIn",imageCodeProperties.getExpireIn());

        // 生成图片
        BufferedImage image = new BufferedImage(codeWidth,codeHeight,BufferedImage.TYPE_INT_RGB);

        // 生成条纹 附在图片上
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200,250));
        g.fillRect(0,0,codeWidth,codeHeight);
        g.setColor(getRandColor(160,200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(codeWidth);
            int y = random.nextInt(codeHeight);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);
        }

        // 4位数字验证码 附在图片上
        String sRand = "";
        for (int i = 0; i < codeLength; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand,13*i+6,16);
        }
        g.dispose();
        // 60秒有效
        return new ImageCode(image,sRand,codeExpireIn);
    }


    /**
     * 生成随机背景条纹
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc+random.nextInt(bc-fc);
        int g = fc+random.nextInt(bc-fc);
        int b = fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}
