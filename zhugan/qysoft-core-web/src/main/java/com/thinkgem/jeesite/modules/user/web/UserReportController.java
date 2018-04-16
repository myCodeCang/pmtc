/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.entity.UserLevelLog;
import com.thinkgem.jeesite.modules.user.entity.UserReport;
import com.thinkgem.jeesite.modules.user.service.UserLevelLogService;
import com.thinkgem.jeesite.modules.user.service.UserReportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * 用户奖金Controller
 * @author xueyuliang
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/user/userReport")
public class UserReportController extends BaseController {

	@Resource
	private UserReportService userReportService;
	@Resource
	private UserLevelLogService userLevelLogService;
	
	@ModelAttribute
	public UserReport get(@RequestParam(required=false) String id) {
		UserReport entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = userReportService.get(id);
		}
		if (entity == null){
			entity = new UserReport();
		}
		return entity;
	}
	
	@RequiresPermissions("user:userReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserReport userReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserReport> page = userReportService.findPage(new Page<UserReport>(request, response), userReport); 
		model.addAttribute("page", page);
		return "modules/user/userReportList";
	}

	@RequiresPermissions("user:userReport:view")
	@RequestMapping(value = "organList")
	public String organList(UserReport userReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		UserReport report = userReportService.getOrganReport(userReport);
		model.addAttribute("organ",userReportService.countBonusReport(report));
		return "modules/user/userReportOrganList";
	}

	@RequiresPermissions("user:userReport:view")
	@RequestMapping(value = "teamList")
	public String teamList(UserReport userReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		UserReport report = userReportService.getTeamReport(userReport);
		model.addAttribute("team", userReportService.countBonusReport(report));
		return "modules/user/userReportTeamList";
	}

	@RequiresPermissions("user:userReport:view")
	@RequestMapping(value = "reportView")
	public String reportView(UserReport userReport,UserLevelLog userLevelLog,HttpServletRequest request, HttpServletResponse response, Model model) {

		if(userReport.getCreateDateBegin()!=null || userReport.getCreateDateEnd()!=null){
			UserReport userReports = userReportService.sumUserReport(userReport);
			UserLevelLog performance = userLevelLogService.contPerformance(userLevelLog);
			if (performance!=null && userReports!=null){
				int contPerformance = performance.getPerformance();
				int perforBase = Integer.parseInt(Global.getOption("system_user_bonus","user_goods_num","6000"));
				int totalProfits = contPerformance * perforBase;
				model.addAttribute("totalProfits", totalProfits);
//				BigDecimal totalBonus = (userReports.getDividendThree().add(userReports.getDividendFour()).add(userReports.getDividendFive()).add(userReports.getDividendSix()).add(userReports.getDividendSeven()).add(userReports.getDividendEight()).add(userReports.getDividendNine())).multiply(new BigDecimal("0.68"));
				//BigDecimal totalBonus = userReports.getDividendThree().add(userReports.getDividendFour()).add(userReports.getDividendFive()).add(userReports.getDividendSix()).add(userReports.getDividendSeven()).add(userReports.getDividendEight()).add(userReports.getDividendNine());

				BigDecimal totalBonus = userReports.getDividendThirteen();

				if(new BigDecimal(totalProfits).compareTo(BigDecimal.ZERO) >= 0){
					BigDecimal bochulv = totalBonus.divide(new BigDecimal(totalProfits),4, BigDecimal.ROUND_HALF_EVEN);
					model.addAttribute("bochulv", bochulv);
				}



				model.addAttribute("userReports", userReports);
				model.addAttribute("totalBonus", totalBonus);

			}

		}

		model.addAttribute("createDateBegin", userReport.getCreateDateBegin());
		model.addAttribute("createDateEnd", userReport.getCreateDateEnd());

		return "modules/user/reportView";
	}

}