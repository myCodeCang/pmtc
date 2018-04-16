/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wechat.entity.SysWxKeyword;
import com.thinkgem.jeesite.modules.wechat.service.SysWxKeywordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信关键字回复Controller
 *
 * @author kevin
 * @version 2017-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/sysWxKeyword")
public class SysWxKeywordController extends BaseController {

    @Autowired
    private SysWxKeywordService sysWxKeywordService;

    @ModelAttribute
    public SysWxKeyword get(@RequestParam(required = false) String id) {
        SysWxKeyword entity = null;
        if (StringUtils2.isNotBlank(id)) {
            entity = sysWxKeywordService.get(id);
        }
        if (entity == null) {
            entity = new SysWxKeyword();
        }
        return entity;
    }

    @RequiresPermissions("user:sysWxKeyword:view")
    @RequestMapping(value = {"list", ""})
    public String list(SysWxKeyword sysWxKeyword, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SysWxKeyword> page = sysWxKeywordService.findPage(new Page<SysWxKeyword>(request, response), sysWxKeyword);
        model.addAttribute("page", page);
        return "modules/wechat/sysWxKeywordList";
    }

    @RequiresPermissions("user:sysWxKeyword:view")
    @RequestMapping(value = "form")
    public String form(SysWxKeyword sysWxKeyword, Model model) {
        model.addAttribute("sysWxKeyword", sysWxKeyword);
        return "modules/wechat/sysWxKeywordForm";
    }

    @RequiresPermissions("user:sysWxKeyword:edit")
    @RequestMapping(value = "save")
    public String save(SysWxKeyword sysWxKeyword, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, sysWxKeyword)) {
            return form(sysWxKeyword, model);
        }
        sysWxKeywordService.save(sysWxKeyword);
        addMessage(redirectAttributes, "保存关键字回复成功");
        return "redirect:" + Global.getAdminPath() + "/wechat/sysWxKeyword/?repage";
    }

    @RequiresPermissions("user:sysWxKeyword:edit")
    @RequestMapping(value = "delete")
    public String delete(SysWxKeyword sysWxKeyword, RedirectAttributes redirectAttributes) {
        sysWxKeywordService.delete(sysWxKeyword);
        addMessage(redirectAttributes, "删除关键字回复成功");
        return "redirect:" + Global.getAdminPath() + "/wechat/sysWxKeyword/?repage";
    }

}