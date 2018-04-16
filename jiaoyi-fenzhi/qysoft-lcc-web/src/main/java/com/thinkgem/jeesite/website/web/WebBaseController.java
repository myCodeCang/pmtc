package com.thinkgem.jeesite.website.web;

import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017-06-09.
 */
public abstract class WebBaseController extends BaseController {

    /**
     * 模版路径
     */
    public String themesPath;



    @ModelAttribute
    public void init(HttpServletRequest request, Model model) {


//        if(UserAgentUtils.isMobileOrTablet(request)){
//            this.themesPath = "website/themes/h5";
//        }
//        else{
//            Site site = CmsUtils.getSite(Site.defaultSiteId());
//            this.themesPath = "website/themes/" + site.getTheme();
//        }

    }


    public WebBaseController() {

        Site site = CmsUtils.getSite(Site.defaultSiteId());
        this.themesPath = "website/themes/" + site.getTheme();

    }


    public String qyview(String url, HttpServletRequest request, Model model) {

        if (StringUtils2.isBlank(request.getHeader("x-pjax"))) {
            Site site = CmsUtils.getSite(Site.defaultSiteId());
            model.addAttribute("site", site);
        }

        return themesPath + url;
    }

}
