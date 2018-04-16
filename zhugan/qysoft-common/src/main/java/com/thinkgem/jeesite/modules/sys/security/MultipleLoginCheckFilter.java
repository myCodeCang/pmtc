package com.thinkgem.jeesite.modules.sys.security;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kevin on 2017/9/21.
 */
@Service
public class MultipleLoginCheckFilter extends
        org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
    @Resource
    private UserUserinfoService userUserinfoService;
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) {
         String singleUserLogin= Global.getOption("system_user_set", "singleUserLogin");
        if("on".equals(singleUserLogin)) {
            String isLogin = request.getParameter("isLogin");
            if (!"1".equals(isLogin)) {
                Session session = UserInfoUtils.getSession();
                Object singleUserInfo = session.getAttribute("singleUserInfo");
                if (singleUserInfo==null){
                    return true;
                }
                if (!(singleUserInfo instanceof  UserUserinfo)) {
                    return false;
                }
                UserUserinfo userInfo = (UserUserinfo) singleUserInfo;
                UserUserinfo userUserinfo = userUserinfoService.getByName(userInfo.getUserName());

                if (userUserinfo == null) {
                    return true;
                }
                if (!userUserinfo.getLastLoginIp().equals(session.getId())) {
                    SystemService systemService = SpringContextHolder.getBean(SystemService.class);
                    systemService.getSessionDao().delete(session);
                }

            }
            }
        return true;
    }
}