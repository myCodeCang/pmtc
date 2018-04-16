/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tasks.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
import com.thinkgem.jeesite.modules.tasks.entity.UserTasks;
import com.thinkgem.jeesite.modules.tasks.service.UserTasksService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;


/**
 * 任务调度Controller
 *
 * @author yankai
 * @version 2017-06-17
 */
@Controller
@RequestMapping(value = "${adminPath}/tasks/userTasks")
public class UserTasksController extends BaseController {

    @Resource
    private UserTasksService userTasksService;

    @ModelAttribute
    public UserTasks get(@RequestParam(required = false) String id) {
        UserTasks entity = null;
        if (StringUtils2.isNotBlank(id)) {
            entity = userTasksService.get(id);
        }
        if (entity == null) {
            entity = new UserTasks();
        }
        return entity;
    }

    @RequiresPermissions("user:tasks:userTasks:view")
    @RequestMapping(value = {"list", ""})
    public String list(UserTasks userTasks, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserTasks> page = userTasksService.findPage(new Page<UserTasks>(request, response), userTasks);
        model.addAttribute("page", page);
        return "modules/tasks/userTasksList";
    }

    @RequiresPermissions("user:tasks:userTasks:view")
    @RequestMapping(value = "form")
    public String form(UserTasks userTasks, Model model) {
        model.addAttribute("userTasks", userTasks);
        if (userTasks != null) {
            model.addAttribute("isNew", userTasks.getIsNewRecord() ? "1" : "0");
        }

        return "modules/tasks/userTasksForm";
    }

    @RecordLog(logTitle = "常规配置-任务调度-添加/更新计划任务")
    @RequiresPermissions("user:tasks:userTasks:edit")
    @RequestMapping(value = "save")
    public String save(UserTasks userTasks, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, userTasks)) {
            return form(userTasks, model);
        }

        try {
            userTasksService.save(userTasks);
            addMessage(redirectAttributes, "保存计划任务成功");
            return "redirect:" + Global.getAdminPath() + "/tasks/userTasks/?repage";
        } catch (ValidationException ex) {
            addMessage(model, "失败:" + ex.getMessage());
            return form(userTasks, model);
        }
    }

    @RecordLog(logTitle = "常规配置-任务调度-删除计划任务")
    @RequiresPermissions("user:tasks:userTasks:edit")
    @RequestMapping(value = "delete")
    public String delete(UserTasks userTasks, RedirectAttributes redirectAttributes) {
        userTasksService.delete(userTasks);
        addMessage(redirectAttributes, "删除计划任务成功");
        return "redirect:" + Global.getAdminPath() + "/tasks/userTasks/?repage";
    }

    @RecordLog(logTitle = "常规配置-任务调度-暂停计划任务")
    @RequiresPermissions("user:tasks:userTasks:edit")
    @RequestMapping(value = "stop")
    public String stop(UserTasks userTasks, RedirectAttributes redirectAttributes) {
        if (userTasksService.stop(userTasks)) {
            addMessage(redirectAttributes, "停止计划任务成功");
        } else {
            addMessage(redirectAttributes, "停止计划任务失败");
        }

        return "redirect:" + Global.getAdminPath() + "/tasks/userTasks/?repage";
    }

    @RequiresPermissions("user:tasks:userTasks:edit")
    @RequestMapping(value = "resume")
    public String resume(UserTasks userTasks, RedirectAttributes redirectAttributes) {
        if (userTasksService.resume(userTasks)) {
            addMessage(redirectAttributes, "恢复计划任务成功");
        } else {
            addMessage(redirectAttributes, "恢复计划任务失败");
        }

        return "redirect:" + Global.getAdminPath() + "/tasks/userTasks/?repage";
    }

    @RecordLog(logTitle = "常规配置-任务调度-恢复计划任务")
    @RequiresPermissions("user:tasks:userTasks:edit")
    @RequestMapping(value = "start")
    public String start(UserTasks userTasks, RedirectAttributes redirectAttributes) {
        if (userTasksService.start(userTasks)) {
            addMessage(redirectAttributes, "启动计划任务成功");
        } else {
            addMessage(redirectAttributes, "启动计划任务失败");
        }

        return "redirect:" + Global.getAdminPath() + "/tasks/userTasks/?repage";
    }

    @RecordLog(logTitle = "常规配置-任务调度-恢复所有计划任务")
    @RequiresPermissions("user:tasks:userTasks:edit")
    @RequestMapping(value = "startAll")
    public String startAll(RedirectAttributes redirectAttributes) {
        if (userTasksService.startAll()) {
            addMessage(redirectAttributes, "启动计划任务成功");
        } else {
            addMessage(redirectAttributes, "启动计划任务失败");
        }

        return "redirect:" + Global.getAdminPath() + "/tasks/userTasks/?repage";
    }

    @RecordLog(logTitle = "常规配置-任务调度-暂停所有计划任务")
    @RequiresPermissions("user:tasks:userTasks:edit")
    @RequestMapping(value = "stopAll")
    public String stopAll(RedirectAttributes redirectAttributes) {
        if (userTasksService.stopAll()) {
            addMessage(redirectAttributes, "暂停计划任务成功");
        } else {
            addMessage(redirectAttributes, "暂停计划任务失败");
        }

        return "redirect:" + Global.getAdminPath() + "/tasks/userTasks/?repage";
    }

    @RequiresPermissions("user:tasks:userTasks:edit")
    @RequestMapping(value = "getLog")
    public String getLog(UserTasks userTask, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (userTask == null) {
            model.addAttribute("logInfo", "没有日志信息！");
        }

        Object bean = SpringContextHolder.getBean(userTask.getBeanName());
        String name = bean.getClass().getName();

        StringBuilder result = new StringBuilder();
        String[] nameSplit = name.split("\\$\\$");
        if (nameSplit != null && nameSplit.length > 0) {
            String path = Arrays.stream(nameSplit[0].split("\\.")).collect(joining(File.separator));

            ServletContext servletContext = request.getSession().getServletContext();
            String url = servletContext.getRealPath("/WEB-INF/logs/") + File.separator + path + ".log";

            try {
                Files.lines(Paths.get(url), StandardCharsets.UTF_8).forEach((line) -> result.append(line).append("<br/>"));
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
            }

            if (result.length() == 0) {
                result.append("<h3>暂无日志信息</h3>");
            }
        }
        model.addAttribute("logInfo", result.toString());

        return "modules/tasks/userTasksLog";
    }
}