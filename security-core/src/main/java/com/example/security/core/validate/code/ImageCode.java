package com.example.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCode {
    /**
     *  展示的图片
     */
    private BufferedImage image;
    /**
     *  随机数，生成图片
     */
    private String code;
    /**
     *  过期时间
     */
    private LocalDateTime expireTime;

    /**
     * @param image
     * @param code
     * @param expireIn 多少秒内过期
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
