/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Exceptions;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.sys.dao.LogDao;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 字典工具类
 *
 * @author ThinkGem
 * @version 2014-11-7
 */
public class LogUtils {

    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

    private static LogDao logDao = SpringContextHolder.getBean(LogDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, String title) {
        saveLog(request, null, null, title);
    }

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title) {
        User user = UserUtils.getUser();
        if (user != null && user.getId() != null) {
            Log log = new Log();
            log.setTitle(title);
            log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
            log.setRemoteAddr(StringUtils2.getRemoteAddr(request));
            log.setUserAgent(request.getHeader("user-agent"));
            log.setRequestUri(request.getRequestURI());
            log.setParams(request.getParameterMap());
            log.setMethod(request.getMethod());
            // 异步保存日志
            new SaveLogThread(log, handler, ex).start();
        }
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread {

        private Log log;
        private Object handler;
        private Exception ex;

        public SaveLogThread(Log log, Object handler, Exception ex) {
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.ex = ex;
        }

        @Override
        public void run() {
            // 获取日志标题
            if (StringUtils2.isBlank(log.getTitle())) {
                String permission = "";
                if (handler instanceof HandlerMethod) {
                    Method m = ((HandlerMethod) handler).getMethod();
                    RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
                    permission = (rp != null ? StringUtils2.join(rp.value(), ",") : "");
                }
                log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
            }

            //BEGIN: Add by yankai 只记录请求路径以指定字符开头的请求 on 2017-05-30
            /*String uri = log.getRequestUri();
            String[] split = uri.split("/");
            if (split != null && split.length > 0) {
                String lastPath = split[split.length - 1];
                if (!uri.equals("/a") && shouldNotLog(lastPath)) {
                    return;
                }
            }*/
            //END: Add by yankai 只记录请求路径以insert、update和delete开头的请求 on 2017-05-30

            //BEGIN: Add by yankai 只记录请求方法中包含@RecodeLog注解的请求 on 2017-05-30
            if (handler instanceof HandlerMethod) {
                Method m = ((HandlerMethod) handler).getMethod();
                RecordLog rl = m.getAnnotation(RecordLog.class);
                if (null == rl) {
                    return;
                }

                if (StringUtils2.isBlank(log.getTitle())) {
                    log.setTitle(rl.logTitle());
                }
            } else {
                return;
            }
            //END: Add by yankai 只记录请求方法中包含@RecodeLog注解的请求 on 2017-05-30

            // 如果有异常，设置异常信息
            log.setException(Exceptions.getStackTraceAsString(ex));
            // 如果无标题并无异常日志，则不保存信息
            if (StringUtils2.isBlank(log.getTitle()) && StringUtils2.isBlank(log.getException())) {
                return;
            }
            // 保存日志信息
            try {
                log.preInsert();
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            logDao.insert(log);
        }

        private boolean shouldNotLog(String lastUri) {
            if (lastUri.startsWith("add") || lastUri.startsWith("create") || lastUri.startsWith("remove")
                    || lastUri.startsWith("del") || lastUri.startsWith("modify") || lastUri.startsWith("save")
                    || lastUri.startsWith("insert") || lastUri.startsWith("update") || lastUri.startsWith("delete")) {
                return false;
            }
            return true;
        }
    }

    /**
     * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
     */
    public static String getMenuNamePath(String requestUri, String permission) {
        String href = StringUtils2.substringAfter(requestUri, Global.getAdminPath());
        @SuppressWarnings("unchecked")
        Map<String, String> menuMap = (Map<String, String>) CacheUtils.get(CACHE_MENU_NAME_PATH_MAP);
        if (menuMap == null) {
            menuMap = Maps.newHashMap();
            List<Menu> menuList = menuDao.findAllList(new Menu());
            for (Menu menu : menuList) {
                // 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
                String namePath = "";
                if (menu.getParentIds() != null) {
                    List<String> namePathList = Lists.newArrayList();
                    for (String id : StringUtils2.split(menu.getParentIds(), ",")) {
                        if (Menu.getRootId().equals(id)) {
                            continue; // 过滤跟节点
                        }
                        for (Menu m : menuList) {
                            if (m.getId().equals(id)) {
                                namePathList.add(m.getName());
                                break;
                            }
                        }
                    }
                    namePathList.add(menu.getName());
                    namePath = StringUtils2.join(namePathList, "-");
                }
                // 设置菜单名称路径
                if (StringUtils2.isNotBlank(menu.getHref())) {
                    menuMap.put(menu.getHref(), namePath);
                } else if (StringUtils2.isNotBlank(menu.getPermission())) {
                    for (String p : StringUtils2.split(menu.getPermission())) {
                        menuMap.put(p, namePath);
                    }
                }

            }
            CacheUtils.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
        }

        //BEGIN: Modified by yankai 修改配置菜单路径带参数无法记录日志 on 2017-05-30
        String menuNamePath = null;
        Optional<String> menuPathKey = menuMap.keySet().stream()
                .filter(key -> key.equals(href) || key.startsWith(href + "?")).findAny();
        if (menuPathKey.isPresent()) {
            menuNamePath = menuMap.get(menuPathKey.get());
        }
        //END: Modified by yankai 修改配置菜单路径带参数无法记录日志 on 2017-05-30

        if (menuNamePath == null) {
            for (String p : StringUtils2.split(permission)) {
                menuNamePath = menuMap.get(p);
                if (StringUtils2.isNotBlank(menuNamePath)) {
                    break;
                }
            }
            if (menuNamePath == null) {
                return "";
            }
        }
        return menuNamePath;
    }
}
