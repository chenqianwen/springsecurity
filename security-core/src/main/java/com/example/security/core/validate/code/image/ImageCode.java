package com.example.security.core.validate.code.image;

import com.example.security.core.validate.code.ValidateCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
/**
 * @author： ygl
 * @date： 2018/2/7-13:07
 * @Description：
 * 图片验证码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCode extends ValidateCode {
    /**
     *  展示的图片
     */
    private BufferedImage image;

    /**
     * @param image
     * @param code
     * @param expireIn 多少秒内过期
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }


    @Override
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(getExpireTime());
    }
}
