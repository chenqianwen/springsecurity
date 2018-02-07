package com.example.security.core.social.weibo.api;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
public class WeiboStatus {
    /**
     * 微博创建时间
     */
    private String created_at;
    /**
     * 微博ID
     */
    private String id;
    /**
     * 微博MID
     */
    private String mid;
    /**
     * 字符串型的微博ID
     */
    private String idstr;
    private boolean can_edit;
    /**
     * 微博信息内容
     */
    private String text;
    private String textLength;
    private String source_allowclick;
    private String source_type;
    private String source;
    /**
     * 是否已收藏，true：是，false：否
     */
    private boolean favorited;
    /**
     * 是否被截断，true：是，false：否
     */
    private boolean truncated;
    /**
     * （暂未支持）回复ID
     */
    private String in_reply_to_status_id;
    /**
     * （暂未支持）回复人UID
     */
    private String in_reply_to_user_id;
    /**
     * （暂未支持）回复人昵称
     */
    private String in_reply_to_screen_name;
    /**
     * 原始图片地址，没有时不返回此字段
     */
    private WeiboGeo geo;
}
