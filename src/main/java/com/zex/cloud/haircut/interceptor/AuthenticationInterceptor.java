package com.zex.cloud.haircut.interceptor;

import com.zex.cloud.haircut.enums.PermissionMethodType;
import com.zex.cloud.haircut.exception.AuthenticationException;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.ISyPermissionService;
import com.zex.cloud.haircut.util.RedisKeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private RedisTemplate<String, Object> redisTemplate;

    private ISyPermissionService iSyPermissionService;

    public AuthenticationInterceptor(RedisTemplate<String, Object> redisTemplate, ISyPermissionService iSyPermissionService) {
        this.redisTemplate = redisTemplate;
        this.iSyPermissionService = iSyPermissionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authenticationHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authenticationHeader) && StringUtils.startsWith(authenticationHeader, "Bearer ")) {
            String accessToken = authenticationHeader.substring(7);
            RequestUser user = (RequestUser) redisTemplate.opsForValue().get(RedisKeys.accessTokenKey(accessToken));
            RequestHolder.put(user);
        }

        if (handler instanceof HandlerMethod) {
            String permissionUrl = "";
            Class<?> beanType = ((HandlerMethod) handler).getBeanType();
            RequestMapping classRequestMapping = beanType.getAnnotation(RequestMapping.class);
            if (classRequestMapping.value().length == 1) {
                String tempUrl = classRequestMapping.value()[0];
                tempUrl = StringUtils.removeStart(tempUrl, "/");
                tempUrl = StringUtils.removeEnd(tempUrl, "/");
                permissionUrl = tempUrl;
            }
            Method method = ((HandlerMethod) handler).getMethod();
            GetMapping methodGetMapping = method.getAnnotation(GetMapping.class);
            PermissionMethodType methodType = null;
            if (methodGetMapping != null) {
                if (methodGetMapping.value().length == 1) {
                    String tempUrl = methodGetMapping.value()[0];
                    tempUrl = StringUtils.removeStart(tempUrl, "/");
                    tempUrl = StringUtils.removeEnd(tempUrl, "/");
                    permissionUrl = permissionUrl + "/" + tempUrl;
                }
                methodType = PermissionMethodType.GET;
            }

            PostMapping methodPostMapping = method.getAnnotation(PostMapping.class);
            if (methodPostMapping != null) {
                if (methodPostMapping.value().length == 1) {
                    String tempUrl = methodPostMapping.value()[0];
                    tempUrl = StringUtils.removeStart(tempUrl, "/");
                    tempUrl = StringUtils.removeEnd(tempUrl, "/");
                    permissionUrl = permissionUrl + "/" + tempUrl;
                }
                methodType = PermissionMethodType.POST;
            }

            PutMapping methodPutMapping = method.getAnnotation(PutMapping.class);
            if (methodPutMapping != null) {
                if (methodPutMapping.value().length == 1) {
                    String tempUrl = methodPutMapping.value()[0];
                    tempUrl = StringUtils.removeStart(tempUrl, "/");
                    tempUrl = StringUtils.removeEnd(tempUrl, "/");
                    permissionUrl = permissionUrl + "/" + tempUrl;
                }
                methodType = PermissionMethodType.PUT;

            }

            DeleteMapping methodDeleteMapping = method.getAnnotation(DeleteMapping.class);
            if (methodDeleteMapping != null) {
                if (methodDeleteMapping.value().length == 1) {
                    String tempUrl = methodDeleteMapping.value()[0];
                    tempUrl = StringUtils.removeStart(tempUrl, "/");
                    tempUrl = StringUtils.removeEnd(tempUrl, "/");
                    permissionUrl = permissionUrl + "/" + tempUrl;
                }
                methodType = PermissionMethodType.DELETE;

            }

            System.out.println(permissionUrl);
            System.out.println(methodType);
            List<String> roleNames = iSyPermissionService.getRoleNamesByUrlAndMethodType(permissionUrl, methodType);
            if (roleNames.contains("ANY")) {
                return super.preHandle(request, response, handler);
            } else {
                if (RequestHolder.user() != null && CollectionUtils.containsAny(roleNames, RequestHolder.user().getRoles())) {
                    return super.preHandle(request, response, handler);
                } else {
                    throw new AuthenticationException();
                }
            }

        }
        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
        super.afterCompletion(request, response, handler, ex);
    }


}
