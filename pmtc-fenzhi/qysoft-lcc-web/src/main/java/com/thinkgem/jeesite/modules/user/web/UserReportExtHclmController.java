/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.web;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.UserAccountchange;
import com.thinkgem.jeesite.modules.user.entity.UserReport;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserAccountchangeService;
import com.thinkgem.jeesite.modules.user.service.UserReportService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户奖金Controller
 * @author xueyuliang
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/user/hclmReport")
public class UserReportExtHclmController extends BaseController {

	@Resource
	private UserReportService userReportService;
	@Resource
	private UserUserinfoService userUserinfoService;
	@Resource
	private UserUserinfoDao userinfoDao;
	@Resource
	private UserAccountchangeService accountchangeService ;
	
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
	@RequestMapping(value = "hclmReportView")
	public String hclmReportView( HttpServletRequest request, HttpServletResponse response, Model model) {

		UserUserinfo userinfo = userinfoDao.sumUserEveryMoney();
		UserReport hclmReport = new UserReport();
		hclmReport.setDividendOne(userinfo.getMoney());
		hclmReport.setDividendTwo(userinfo.getMoney2());
		hclmReport.setDividendThree(userinfo.getMoney3());
		hclmReport.setDividendFour(userinfo.getMoney4());
		hclmReport.setDividendFive(userinfo.getMoney().add(userinfo.getMoney2()).add(userinfo.getMoney3()));

		BigDecimal sumMoney =  accountchangeService.sumMoneyByType(EnumUtil.MoneyChangeType.charget.toString(),EnumUtil.MoneyType.money.toString());
		BigDecimal sumMoney2 =  accountchangeService.sumMoneyByType(EnumUtil.MoneyChangeType.charget.toString(),EnumUtil.MoneyType.money2.toString());
		BigDecimal sumMoney3 =  accountchangeService.sumMoneyByType(EnumUtil.MoneyChangeType.charget.toString(),EnumUtil.MoneyType.money3.toString());

		hclmReport.setDividendSix(sumMoney == null?BigDecimal.ZERO:sumMoney);
		hclmReport.setDividendSeven(sumMoney2 == null?BigDecimal.ZERO:sumMoney2);
		hclmReport.setDividendEight(sumMoney3 == null?BigDecimal.ZERO:sumMoney3);
		model.addAttribute("hclmReport",hclmReport);
		return "modules/user/reportView";
	}

	//孵化统计
	@RequestMapping(value = "fuhuaView")
	public String fuhuaView(UserReport userReport, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<UserReport> page = userReportService.sumFuHuaMoney(new Page<UserReport>(request, response), userReport);
		model.addAttribute("page", page);
		return "modules/user/fuhuaView";
	}

	//查询团队所有手续费
	@RequestMapping(value = "sumChargeByTeam")
	public String sumChargeByTeam(UserAccountchange accountchange ,HttpServletRequest request, HttpServletResponse response, Model model){
		String leadName = request.getParameter("leadName");
		UserUserinfo leadUser = userUserinfoService.getByName(leadName);
		if(leadUser == null){
			model.addAttribute("money",BigDecimal.ZERO);
			return "modules/user/reportChargeView";
		}
		UserUserinfo userinfo = new UserUserinfo();
		userinfo.setParentListLike(leadUser.getId());
		accountchange.setUserUserinfo(userinfo);
		List<Integer> changeTypes = new ArrayList<>() ;
		changeTypes.add(205);
		changeTypes.add(203);
		accountchange.setChangeTypeArray(changeTypes);
		List<UserAccountchange> accountchangeList = accountchangeService.findList(accountchange);

		//查询自己手续费
		accountchange.setUserUserinfo(null);
		accountchange.setUserName(leadUser.getUserName());
		List<UserAccountchange> ownList = accountchangeService.findList(accountchange);

		BigDecimal ownMoney = ownList.stream().map(p -> new BigDecimal(p.getChangeMoney())).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal teamMoney = accountchangeList.stream().map(p -> new BigDecimal(p.getChangeMoney())).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal allMoney = ownMoney.add(teamMoney).multiply(new BigDecimal(-1));

		model.addAttribute("money",allMoney);
		model.addAttribute("leadName",leadName);
		model.addAttribute("createDateBegin",accountchange.getCreateDateBegin());
		model.addAttribute("createDateEnd",accountchange.getCreateDateEnd());

		return "modules/user/reportChargeView";
	}

	//团队转账计算
	@RequestMapping(value = "teamTransfer")
	public String teamTransfer(UserAccountchange accountchange , HttpServletRequest request, HttpServletResponse response, Model model) {
		String leadName = request.getParameter("leadName");
		UserUserinfo leadUser = userUserinfoService.getByName(leadName);
		if(leadUser == null){
			model.addAttribute("allMoneyToOut", BigDecimal.ZERO);
			model.addAttribute("allMoneyToIn",BigDecimal.ZERO);
			model.addAttribute("subResult",BigDecimal.ZERO);
			return "modules/user/teamTransfer";
		}
		UserUserinfo userinfo = new UserUserinfo();
		userinfo.setParentListLike(leadUser.getId());
		accountchange.setUserUserinfo(userinfo);
		List<Integer> changeTypes = new ArrayList<>();
		changeTypes.add(207);
		changeTypes.add(208);
		accountchange.setChangeTypeArray(changeTypes);
		List<UserAccountchange> accountchangeList = accountchangeService.findList(accountchange);
		BigDecimal teamMoneyToOut = accountchangeList.stream().filter(p ->new BigDecimal(p.getChangeMoney()).compareTo(BigDecimal.ZERO)<0).map(p ->new BigDecimal(p.getChangeMoney())).reduce(BigDecimal.ZERO,BigDecimal::add);
		BigDecimal teamMoneyToIn = accountchangeList.stream().filter(p ->new BigDecimal(p.getChangeMoney()).compareTo(BigDecimal.ZERO)>0).map(p ->new BigDecimal(p.getChangeMoney())).reduce(BigDecimal.ZERO,BigDecimal::add);

		//查询自己
		accountchange.setUserUserinfo(null);
		accountchange.setUserName(leadUser.getUserName());
		List<UserAccountchange> ownList = accountchangeService.findList(accountchange);
		BigDecimal ownMoneyToOut = ownList.stream().filter(p ->new BigDecimal(p.getChangeMoney()).compareTo(BigDecimal.ZERO)<0).map(p ->new BigDecimal(p.getChangeMoney())).reduce(BigDecimal.ZERO,BigDecimal::add);
		BigDecimal ownMoneyToIn = ownList.stream().filter(p ->new BigDecimal(p.getChangeMoney()).compareTo(BigDecimal.ZERO)>0).map(p ->new BigDecimal(p.getChangeMoney())).reduce(BigDecimal.ZERO,BigDecimal::add);

		BigDecimal allMoneyToOut = teamMoneyToOut.add(ownMoneyToOut).multiply(BigDecimal.valueOf(-1));
		BigDecimal allMoneyToIn = teamMoneyToIn.add(ownMoneyToIn);

		BigDecimal subResult = allMoneyToOut.subtract(allMoneyToIn);

		model.addAttribute("allMoneyToOut",allMoneyToOut);
		model.addAttribute("allMoneyToIn",allMoneyToIn);
		model.addAttribute("subResult",subResult);
		model.addAttribute("leadName",leadName);
		model.addAttribute("createDateBegin",accountchange.getCreateDateBegin());
		model.addAttribute("createDateEnd",accountchange.getCreateDateEnd());
		return "modules/user/teamTransfer";
	}

}