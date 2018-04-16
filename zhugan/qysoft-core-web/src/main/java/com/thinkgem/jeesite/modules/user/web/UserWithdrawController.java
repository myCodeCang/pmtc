/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.annotations.RecordLog;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.user.entity.UserWithdraw;
import com.thinkgem.jeesite.modules.user.service.UserWithdrawService;

import java.math.BigDecimal;

/**
 * 用户提现Controller
 * @author kevin
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userWithdraw")
public class UserWithdrawController extends BaseController {

	@Resource
	private UserWithdrawService userWithdrawService;
	@Resource
	private UserUserinfoService userUserinfoService;



	@ModelAttribute
	public UserWithdraw get(@RequestParam(required=false) String id) {
		UserWithdraw entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userWithdrawService.get(id);
		}
		if (entity == null){
			entity = new UserWithdraw();
		}
		return entity;
	}

	@RequiresPermissions("user:userWithdraw:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserWithdraw userWithdraw, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserWithdraw> page = userWithdrawService.findPage(new Page<UserWithdraw>(request, response), userWithdraw);
		model.addAttribute("page", page);
		return "modules/user/userWithdrawList";
	}
	@RequiresPermissions("user:userWithdraw:view")
	@RequestMapping(value = "form")
	public String form(UserWithdraw userWithdraw, Model model) {
		userWithdraw.setUserinfo(userUserinfoService.getByName(userWithdraw.getUserName()));
		model.addAttribute("userWithdraw", userWithdraw);
		return "modules/user/userWithdrawForm";
	}

	@RequiresPermissions("user:userWithdraw:edit")
	@RequestMapping(value = "save")
	public String save(UserWithdraw userWithdraw, Model model, RedirectAttributes redirectAttributes) {
		userWithdraw.setUserinfo(userUserinfoService.getByName(userWithdraw.getUserName()));
        if(!userWithdraw.getStatus().equals(UserWithdraw.editStatus)){
			model.addAttribute("message", "提现审核未通过,打款信息无法提交!");
			return "modules/user/userWithdrawForm";
		}
		try {
			userWithdraw.setRemittanceStatus("1");
			userWithdrawService.updateRemittanceStatus(userWithdraw);
			addMessage(redirectAttributes, "委托打款信息提交成功");
		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userWithdraw/?repage";
	}

	@RecordLog(logTitle = "财务管理-提现审核-操作-同意/驳回")
	@RequestMapping(value = "check",method = RequestMethod.POST)
	public String check(UserWithdraw userUsercenterLog,String promValue,String promMsg, Model model, RedirectAttributes redirectAttributes){
		try {
			userWithdrawService.updateStatus(userUsercenterLog,promValue,promMsg);
			addMessage(redirectAttributes, "成功");
		} catch (ValidationException e) {
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/userWithdraw?repage";
	}

	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RecordLog(logTitle = "系统设置-用户管理-导出用户数据")
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "withdrawExport", method=RequestMethod.POST)
	public String withdrawExport(UserWithdraw user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户提现数据"+ DateUtils2.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<UserWithdraw> page = userWithdrawService.findPage(new Page<UserWithdraw>(request, response, -1), user);
			for(UserWithdraw list:page.getList()){
				list.setUserinfo(userUserinfoService.getByName(list.getUserName()));
				BigDecimal poundage =new BigDecimal(Global.getOption("system_user_set", "poundage","0"));
                BigDecimal changeMoney=new BigDecimal(list.getChangeMoney());
				BigDecimal shouxuMoney=new BigDecimal(10);
				BigDecimal realMoney=null;
				BigDecimal realHandlingCharge =poundage.multiply(changeMoney).setScale(2,BigDecimal.ROUND_HALF_UP);
				if(realHandlingCharge.compareTo(shouxuMoney) > 0){
					realMoney=changeMoney.subtract(realHandlingCharge);
				}else{
					realMoney=changeMoney.subtract(shouxuMoney);
				}
				list.setRealMoney(realMoney.toString());
			}

			new ExportExcel("用户提现数据", UserWithdraw.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户提现数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/user/userWithdraw";
	}

}