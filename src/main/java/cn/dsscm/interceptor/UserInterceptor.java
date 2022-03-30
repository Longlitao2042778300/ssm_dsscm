package cn.dsscm.interceptor;

import cn.dsscm.domain.User;
import cn.dsscm.utils.Constants;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Long
 * @create 2022-03-16 15:34
 */
public class UserInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        //没有登录 跳转到登录页面
        if (user == null){
            response.sendRedirect(request.getContextPath()+"/jsp/syserror.jsp");
            return false;
        }
        //放行
        return true;
    }
}
