/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.entity.UserLevelLog;
import com.thinkgem.jeesite.modules.user.entity.SystemReport;
import com.thinkgem.jeesite.modules.user.service.UserLevelLogService;
import com.thinkgem.jeesite.modules.user.service.SystemReportService;
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
@RequestMapping(value = "${adminPath}/user/systemReport")
public class SystemReportController extends BaseController {

	@Resource
	private SystemReportService systemReportService;
	@Resource
	private UserLevelLogService userLevelLogService;
	
	@ModelAttribute
	public SystemReport get(@RequestParam(required=false) String id) {
		SystemReport entity = null;
		if (StringUtils2.isNotBlank(id)){
			entity = systemReportService.get(id);
		}
		if (entity == null){
			entity = new SystemReport();
		}
		return entity;
	}
	
	@RequiresPermissions("user:systemReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(SystemReport systemReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SystemReport> page = systemReportService.findPage(new Page<SystemReport>(request, response), systemReport);
		model.addAttribute("page", page);
		return "modules/user/systemReportList";
	}


	@RequiresPermissions("user:systemReport:view")
	@RequestMapping(value = "reportView")
	public String reportView(SystemReport systemReport,UserLevelLog userLevelLog,HttpServletRequest request, HttpServletResponse response, Model model) {

		if(systemReport.getCreateDateBegin()!=null || systemReport.getCreateDateEnd()!=null){
			SystemReport systemReports = systemReportService.sumSystemReport(systemReport);
			UserLevelLog performance = userLevelLogService.contPerformance(userLevelLog);
			if (performance!=null && systemReports!=null){
				int contPerformance = performance.getPerformance();
				int perforBase = Integer.parseInt(Global.getOption("system_user_bonus","user_goods_num","6000"));
				int totalProfits = contPerformance * perforBase;
				model.addAttribute("totalProfits", totalProfits);
//				BigDecimal totalBonus = (systemReports.getDividendThree().add(systemReports.getDividendFour()).add(systemReports.getDividendFive()).add(systemReports.getDividendSix()).add(systemReports.getDividendSeven()).add(systemReports.getDividendEight()).add(systemReports.getDividendNine())).multiply(new BigDecimal("0.68"));
				//BigDecimal totalBonus = systemReports.getDividendThree().add(systemReports.getDividendFour()).add(systemReports.getDividendFive()).add(systemReports.getDividendSix()).add(systemReports.getDividendSeven()).add(systemReports.getDividendEight()).add(systemReports.getDividendNine());

				BigDecimal totalBonus = systemReports.getDividendThirteen();

				if(new BigDecimal(totalProfits).compareTo(BigDecimal.ZERO) >= 0){
					BigDecimal bochulv = totalBonus.divide(new BigDecimal(totalProfits),4, BigDecimal.ROUND_HALF_EVEN);
					model.addAttribute("bochulv", bochulv);
				}



				model.addAttribute("systemReports", systemReports);
				model.addAttribute("totalBonus", totalBonus);

			}

		}

		model.addAttribute("createDateBegin", systemReport.getCreateDateBegin());
		model.addAttribute("createDateEnd", systemReport.getCreateDateEnd());

		return "modules/user/reportView";
	}

}