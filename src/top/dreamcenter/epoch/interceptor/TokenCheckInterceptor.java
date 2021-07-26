package top.dreamcenter.epoch.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.dreamcenter.epoch.entity.TokenEntity;
import top.dreamcenter.epoch.exception.TokenParseFailException;
import top.dreamcenter.epoch.util.MyTokenCenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class TokenCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("authorization");
//        System.out.println("token:" + authorization);
        try{
            if (authorization == null)
                throw new TokenParseFailException("debug : authorization is null");
            TokenEntity entity = MyTokenCenter.getTokenEntity(authorization);
            long expireTime = entity.getStart() + entity.getExpire()*1000;
            long now = System.currentTimeMillis();
            if((now - expireTime) > 0) throw new TokenParseFailException("debug : authorization token expired");
        }catch (TokenParseFailException e) {
            System.out.println(e.getMessage());
            response.setStatus(401);
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
