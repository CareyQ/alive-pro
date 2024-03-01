package com.careyq.alive.satoken.interceptor;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.strategy.SaStrategy;
import cn.hutool.core.util.StrUtil;
import com.careyq.alive.core.constants.RpcConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * Sa-Token 拦截器
 *
 * @author CareyQ
 */
public class AliveSaInterceptor extends SaInterceptor {

    public AliveSaInterceptor(SaParamFunction<Object> auth) {
        super(auth);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 内部调用不鉴权
        if (StrUtil.startWithAny(request.getRequestURI(), RpcConstants.RPC_API_PREFIX)) {
            return true;
        }
        try {

            // 这里必须确保 handler 是 HandlerMethod 类型时，才能进行注解鉴权
            if (isAnnotation && handler instanceof HandlerMethod) {

                // 获取此请求对应的 Method 处理函数
                Method method = ((HandlerMethod) handler).getMethod();

                // 如果此 Method 或其所属 Class 标注了 @SaIgnore，则忽略掉鉴权
                if (SaStrategy.instance.isAnnotationPresent.apply(method, SaIgnore.class)) {
                    // 注意这里直接就退出整个鉴权了，最底部的 auth.run() 路由拦截鉴权也被跳出了
                    return true;
                }

                // 注解校验
                SaStrategy.instance.checkMethodAnnotation.accept(method);
            }

            // Auth 校验
            auth.run(handler);

        } catch (StopMatchException e) {
            // StopMatchException 异常代表：停止匹配，进入Controller

        } catch (BackResultException e) {
            if (response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }
            response.getWriter().print(e.getMessage());
            return false;
        }

        // 通过验证
        return true;
    }
}
