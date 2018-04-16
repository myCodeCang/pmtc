/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wechat.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.sys.utils.WxMenuUtils;
import com.thinkgem.jeesite.modules.wechat.dao.SysWxMenuDao;
import com.thinkgem.jeesite.modules.wechat.entity.SysWxMenu;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * 微信自定义菜单Service
 *
 * @author kevin
 * @version 2017-07-29
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
public class SysWxMenuService extends TreeService<SysWxMenuDao, SysWxMenu> {

    @Autowired
    private WeixinService wexinService;

    public SysWxMenu get(String id) {
        return super.get(id);
    }

    public List<SysWxMenu> findList(SysWxMenu sysWxMenu) {
        if (StringUtils2.isNotBlank(sysWxMenu.getParentIds())) {
            sysWxMenu.setParentIds("," + sysWxMenu.getParentIds() + ",");
        }
        return super.findList(sysWxMenu);
    }

    public void save(SysWxMenu sysWxMenu) {
        String parentId = sysWxMenu.getParentId();
        if (StringUtils2.isBlank(parentId)) {
            parentId = StringUtils2.ZERO;
        }

        //判断菜单数量
        if (sysWxMenu.getIsNewRecord()) {
            int subMenuNum = subMenuNum(parentId);
            if (StringUtils2.ZERO.equals(parentId)) {
                if (subMenuNum >= 3) {
                    throw new ValidationException("一级菜单不能超过3个");
                }
            } else {
                if (subMenuNum >= 5) {
                    throw new ValidationException("二级菜单不能超过5个");
                }
            }

            if (!canAddSub(sysWxMenu)) {
                throw new ValidationException("只能添加二级菜单");
            }
        }

        super.save(sysWxMenu);
    }

    public boolean canAddSub(SysWxMenu sysWxMenu) {
        SysWxMenu parent = sysWxMenu.getParent();
        return parent == null || StringUtils2.isBlank(parent.getId()) || StringUtils2.ZERO.equals(get(parent.getId()).getParentId());
    }

    public void delete(SysWxMenu sysWxMenu) {
        super.delete(sysWxMenu);
    }

    public List<SysWxMenu> findByParentId(String parentId) {
        return dao.findByParentId(parentId);
    }

    public int subMenuNum(String parentId) {
        return dao.subMenuNum(parentId);
    }

    //同步微信菜单
    public void syncMenu() {
        List<SysWxMenu> allMenus = findList(new SysWxMenu());
        if (Collections3.isEmpty(allMenus)) {
            return;
        }

        try {
            Map<String, List<SysWxMenu>> groupByMenus = allMenus.stream().collect(groupingBy(SysWxMenu::getParentId));
            List<SysWxMenu> topMenus = groupByMenus.get(StringUtils2.ZERO).stream().sorted(Comparator.comparing(SysWxMenu::getSort)).collect(Collectors.toList());

            WxMenu wexinMenu = new WxMenu();
            topMenus.forEach(menu -> {
                WxMenuButton pButton = getWxMenuButton(menu);

                List<SysWxMenu> subMenus = findByParentId(menu.getId());
                if (!Collections3.isEmpty(subMenus)) {
                    List<SysWxMenu> sortedSubMenus = subMenus.stream().sorted(Comparator.comparing(SysWxMenu::getSort)).collect(Collectors.toList());
                    sortedSubMenus.forEach(subMenu -> {
                        WxMenuButton subButton = getWxMenuButton(subMenu);
                        pButton.getSubButtons().add(subButton);
                    });
                }

                wexinMenu.getButtons().add(pButton);
            });

            wexinService.getMenuService().menuCreate(wexinMenu);
        } catch (Exception e) {
            throw new ValidationException(e.getLocalizedMessage());
        }
    }

    private WxMenuButton getWxMenuButton(SysWxMenu menu) {
        WxMenuButton wxMenuButton = new WxMenuButton();
        wxMenuButton.setName(menu.getName());
        wxMenuButton.setType(WxMenuUtils.WxMenuTypeEnum.getValueByCode(menu.getTypeId()).get());
        if (WxConsts.BUTTON_CLICK.equals(wxMenuButton.getType())) {
            wxMenuButton.setKey(menu.getEventKey());
        } else {
            String appId = Global.getOption("wechat_config", "app_id");
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + menu.getUrl() + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
            wxMenuButton.setUrl(url);
        }
        return wxMenuButton;
    }
}