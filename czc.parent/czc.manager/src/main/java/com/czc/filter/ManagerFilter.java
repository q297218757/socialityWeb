package com.czc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    JwtUtil jwtUtil;
    /**
     * 表示在请求前（pre）或后（post）执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     *  表示过滤器执行顺序，越小优先级越大
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 表示过滤器是否开启,true表示开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作 return任何obj的值都是表示继续执行
     * setsendzullResponese(false)表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过过滤器了");
        //得到request上下文
        RequestContext context = RequestContext.getCurrentContext();
        //得到request域
        HttpServletRequest request = context.getRequest();
        //得到头信息

        if(request.getMethod().equals("OPIONS")){
            return null;
        }
        //请求是登录请求
        if(request.getRequestURL().indexOf("login")>0){
            return null;
        }
        String header = request.getHeader("Authorization");
        //判断是否有头信息
        if(header != null && !"".equals(header)){
            if(header.startsWith("Bearer")){
                String token = header.substring(7);
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(roles.equals("admin")){
                        context.addZuulRequestHeader("Authorization",header);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    context.setSendZuulResponse(false); //终止运行
                }
            }
        }
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(403); //终止运行
        context.setResponseBody("权限不足");
        context.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
