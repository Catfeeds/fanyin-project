package com.fanyin.configuration.security;

import com.fanyin.mapper.system.SystemMenuMapper;
import com.fanyin.model.system.SystemMenu;
import com.fanyin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 权限配置资源管理器
 * @author 二哥很猛
 * @date 2018/1/25 11:01
 */
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Map<String,Collection<ConfigAttribute>> map = new HashMap<>(256);

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    /**
     * 重新加载所有菜单权限
     */
    private void loadResource(){
        map.clear();
        Collection<ConfigAttribute> attributes;
        ConfigAttribute cfg;
        List<SystemMenu> list = systemMenuMapper.getAllList();
        for (SystemMenu menu : list){
            if(StringUtil.isNotBlank(menu.getUrl())){
                attributes = new ArrayList<>();
                cfg = new SecurityConfig(menu.getNid());
                attributes.add(cfg);
                map.put(menu.getUrl(),attributes);
            }
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(map.isEmpty()){
            loadResource();
        }
        HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        for (Map.Entry<String,Collection<ConfigAttribute>> entry : map.entrySet()){
            matcher = new AntPathRequestMatcher(entry.getKey());
            if(matcher.matches(httpRequest)){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
