package ssm.wjx.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ssm.wjx.entity.PersonInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WuChangJian
 * @date 2020/3/27 18:43
 * 店家管理系统登录验证拦截器
 */
public class ShopInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user != null) {
            // 对用户身份进行判断
            PersonInfo user0 = (PersonInfo) user;
            if (user0.getUserId() != null && user0.getUserType() == 2 && user0.getEnableStatus() == 1) {
                // 验证成功则返回true 放行
                return true;
            }
        }
        // 若不满足登录验证，则直接跳转到账号登录页面
        response.sendRedirect(request.getContextPath() + "/local/login?userType=2");
        return false;
    }
}
