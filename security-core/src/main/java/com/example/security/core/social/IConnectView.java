package com.example.security.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IConnectView extends AbstractView{

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        /**
         * connections 为空解绑，不为空绑定
         */
        List<Connection<?>> connections = (List<Connection<?>>) map.get("connections");

        response.setContentType("text/html;charset=UTF-8");
        if (CollectionUtils.isEmpty(connections)) {
            response.getWriter().write("<h3>解绑成功！</h3>");
        } else {
            response.getWriter().write("<h3>绑定成功！</h3>");
        }


    }

}
