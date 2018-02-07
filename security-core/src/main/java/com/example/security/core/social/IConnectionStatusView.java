package com.example.security.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * QQ/WX 连接信息的视图
 */
@Component("connect/status")
public class IConnectionStatusView extends AbstractView{

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 渲染视图
     * @param map
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) map.get("connectionMap");

        Map<String, Boolean> result = new HashMap<>(connections.size());
        for (String key : connections.keySet()) {
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
