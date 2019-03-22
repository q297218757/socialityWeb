package czc.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //无论如何都放行。具体能不能操作还是在具体操作中逆行判断
        String header = request.getHeader("Authorization");

        if(header !=null && !"".equals(header)){
            //包含头信息Authorization就进行解析
            if(header.startsWith("Bearer ")){
                //得到token
                String token = header.substring(7);
                //对令牌进行验证
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String role = (String )claims.get("roles");
                    if(role != null || "admin".equals(role)){
                        request.setAttribute("claims_admin",token);
                    }

                    if(role != null || "user".equals(role)){
                        request.setAttribute("claims_user",token);
                    }
                }catch (Exception e){
                    throw new RuntimeException("令牌有误");
                }
            }

        }


        System.out.println("经过了拦截器");
        return true;
    }
}
