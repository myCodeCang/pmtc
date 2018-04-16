package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.wechat.dao.SysWxMenuDao;
import com.thinkgem.jeesite.modules.wechat.entity.SysWxMenu;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by kevin on 2017/7/29.
 */
public class WxMenuUtils {
    private static SysWxMenuDao wxMenuDao = SpringContextHolder.getBean(SysWxMenuDao.class);

    public static String getParentMenuId(String menuId) {
        SysWxMenu sysWxMenu = wxMenuDao.get(menuId);
        if (null == sysWxMenu) {
            return StringUtils2.EMPTY;
        }

        return sysWxMenu.getParentId();
    }

    public static enum WxMenuTypeEnum {
        URL("0", "view", "URL跳转"),
        CLECK("1", "click", "点击事件");

        private String code;
        private String value;
        private String desc;

        WxMenuTypeEnum(String code, String value, String desc) {
            this.code = code;
            this.value = value;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("[").append(code).append("-").append(value).append("-").append(desc).append("]");

            return result.toString();
        }

        public static Optional<String> getValueByCode(String code) {
            return Arrays.stream(values())
                    .filter(menuTypeEnum -> menuTypeEnum.getCode().equals(code))
                    .map(menuTypeEnum -> menuTypeEnum.getValue()).findAny();
        }
    }
}
