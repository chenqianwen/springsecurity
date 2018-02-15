package com.example.security.rbac.dto;

import com.example.security.rbac.po.ResourceType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
@Data
public class ResourceInfo {

    /**
     * 资源ID
     */
    private Long id;
    /**
     *
     */
    private Long parentId;
    /**
     * 资源名
     */
    private String name;
    /**
     * 资源链接
     */
    private String link;
    /**
     * 图标
     */
    private String icon;
    /**
     * 资源类型
     */
    private ResourceType type;
    /**
     * 子节点
     */
    private List<ResourceInfo> children = new ArrayList<>();

}
