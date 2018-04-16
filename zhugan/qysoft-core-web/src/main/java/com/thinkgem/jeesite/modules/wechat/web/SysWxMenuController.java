/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.wechat.entity.SysWxMenu;
import com.thinkgem.jeesite.modules.wechat.service.SysWxMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 微信自定义菜单Controller
 *
 * @author kevin
 * @version 2017-07-29
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/sysWxMenu")
public class SysWxMenuController extends BaseController {

    @Autowired
    private SysWxMenuService sysWxMenuService;

    @ModelAttribute
    public SysWxMenu get(@RequestParam(required = false) String id) {
        SysWxMenu entity = null;
        if (StringUtils2.isNotBlank(id)) {
            entity = sysWxMenuService.get(id);
        }
        if (entity == null) {
            entity = new SysWxMenu();
        }
        return entity;
    }

    @RequiresPermissions("user:sysWxMenu:view")
    @RequestMapping(value = {"list", ""})
    public String list(SysWxMenu sysWxMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<SysWxMenu> list = sysWxMenuService.findList(sysWxMenu);
        model.addAttribute("list", list);
        return "modules/wechat/sysWxMenuList";
    }

    @RequiresPermissions("user:sysWxMenu:view")
    @RequestMapping(value = "form")
    public String form(SysWxMenu sysWxMenu, Model model) {
        if (sysWxMenu.getParent() != null && StringUtils2.isNotBlank(sysWxMenu.getParent().getId())) {
            sysWxMenu.setParent(sysWxMenuService.get(sysWxMenu.getParent().getId()));
            // 获取排序号，最末节点排序号+30
            if (StringUtils2.isBlank(sysWxMenu.getId())) {
                SysWxMenu sysWxMenuChild = new SysWxMenu();
                sysWxMenuChild.setParent(new SysWxMenu(sysWxMenu.getParent().getId()));
                List<SysWxMenu> list = sysWxMenuService.findList(sysWxMenu);
                if (list.size() > 0) {
                    sysWxMenu.setSort(list.get(list.size() - 1).getSort());
                    if (sysWxMenu.getSort() != null) {
                        sysWxMenu.setSort(sysWxMenu.getSort() + 30);
                    }
                }
            }
        }
        if (sysWxMenu.getSort() == null) {
            sysWxMenu.setSort(30);
        }
        model.addAttribute("sysWxMenu", sysWxMenu);
        return "modules/wechat/sysWxMenuForm";
    }

    @RequiresPermissions("user:sysWxMenu:edit")
    @RequestMapping(value = "save")
    public String save(SysWxMenu sysWxMenu, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, sysWxMenu)) {
            return form(sysWxMenu, model);
        }

        try {
            sysWxMenuService.save(sysWxMenu);
            addMessage(redirectAttributes, "保存自定义菜单成功");

        } catch (ValidationException ex) {
            addMessage(redirectAttributes, "失败:" + ex.getMessage());
            //return form(sysWxMenu, model);
        }
        return "redirect:" + Global.getAdminPath() + "/wechat/sysWxMenu/?repage";
    }

    @RequiresPermissions("user:sysWxMenu:edit")
    @RequestMapping(value = "delete")
    public String delete(SysWxMenu sysWxMenu, RedirectAttributes redirectAttributes) {
        sysWxMenuService.delete(sysWxMenu);
        addMessage(redirectAttributes, "删除微信自定义菜单成功");
        return "redirect:" + Global.getAdminPath() + "/wechat/sysWxMenu/?repage";
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<SysWxMenu> list = sysWxMenuService.findList(new SysWxMenu());
        for (int i = 0; i < list.size(); i++) {
            SysWxMenu e = list.get(i);
            if (StringUtils2.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @RequiresPermissions("user:sysWxMenu:edit")
    @RequestMapping(value = "syncMenu")
    public String syncMenu(Model model, RedirectAttributes redirectAttributes) {
        try {
            sysWxMenuService.syncMenu();
            addMessage(redirectAttributes, "同步微信自定义菜单成功");
        } catch (ValidationException ex) {
            addMessage(redirectAttributes, "同步菜单失败:" + ex.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/wechat/sysWxMenu/?repage";
    }


}