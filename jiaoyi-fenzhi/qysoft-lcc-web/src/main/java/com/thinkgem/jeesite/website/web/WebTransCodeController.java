/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.website.web;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO2;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.DateUtils2;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.config.EnumLccUtil;
import com.thinkgem.jeesite.config.EnumTransUtil;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.*;
import com.thinkgem.jeesite.modules.user.service.*;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 用户交易
 *
 * @author ThinkGem
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}/trans")
public class WebTransCodeController extends WebBaseController {

    @Resource
    private UserUserinfoService userUserinfoService;

    @Resource
    private TranscodePriceDaylogService daylogService ;

    @Resource
    private TranscodeBuyService transcodeBuyService;

    @Resource
    private  TranscodeBuyLogService transcodeBuyLogService;

    @Resource
    private TranscodePriceDaylogService priceDaylogService;



    @ModelAttribute
    public void init(HttpServletRequest request, Model model) {

        //设置用户对象
        if (request.getMethod().equals(RequestMethod.GET.toString()) || StringUtils2.isNotBlank(request.getParameter("_pjax"))) {
            model.addAttribute("userinfo", userUserinfoService.get(UserInfoUtils.getUser().getId()));
            model.addAttribute("siteBar",request.getParameter("siteBar"));
        }

    }


    /**
     * 跳转交易页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/buysell"}, method = RequestMethod.GET)
    public String buysell(HttpServletRequest request, HttpServletResponse response, Model model) {
        TranscodePriceDaylog nowLog = daylogService.getNowLog();
        BigDecimal nowMoney = BigDecimal.ZERO;
        if (nowLog != null){
            nowMoney = nowLog.getNowMoney();
        }
        BigDecimal buyNowNum = transcodeBuyService.sumNowNumByTypeAndDate(EnumUtil.TransCodeBuyType.buy.toString(), new Date());
        BigDecimal sellNowNum = transcodeBuyService.sumNowNumByTypeAndDate(EnumUtil.TransCodeBuyType.sell.toString(),new Date());
        model.addAttribute("nowMoney",nowMoney);
        model.addAttribute("buyNowNum",buyNowNum);
        model.addAttribute("sellNowNum",sellNowNum);
        model.addAttribute("transMultiple",Global.getOption("system_trans","trans_multiple"));
        model.addAttribute("guaranty",Global.getOption("system_trans","auction_money"));
        return qyview("/trans/buysell", request, model);
    }


    /**
     * 跳转行情图表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/pricedaylog"}, method = RequestMethod.GET)
    public String pricedaylog(HttpServletRequest request, HttpServletResponse response, Model model) {
        String type = request.getParameter("type");
        List<TranscodePriceDaylog> list;
        if(type == null){
            type = "day";
        }

        List<TranscodePriceDaylog> allList = daylogService.findList(new TranscodePriceDaylog());
        List<TranscodePriceDaylog> weekList = daylogService.findWeekData();
        List<TranscodePriceDaylog> monthList = daylogService.findMonthData();

        //周成交,月成交
        //周成交
        TranscodePriceDaylog priceDaylogTemp = new TranscodePriceDaylog();
        priceDaylogTemp.setCreateDateBegin(DateUtils2.getDiffDatetime(new Date(),-6));
        priceDaylogTemp.setCreateDateEnd(new Date());
        List<TranscodePriceDaylog> weekListTemp = daylogService.findList(priceDaylogTemp);
        BigDecimal  weekMoney = weekListTemp.stream().map(p -> p.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);

        //月成交
        priceDaylogTemp.setCreateDateBegin(DateUtils2.getDiffDatetime(new Date(),-29));
        List<TranscodePriceDaylog> monthListTemp = daylogService.findList(priceDaylogTemp);
        BigDecimal monthMoney = monthListTemp.stream().map(p ->p.getAmount()).reduce(BigDecimal.ZERO,BigDecimal::add);

        if("day".equals(type)){
            list = allList;
        }else if("week".equals(type)){
            list = weekList;
        }else if("month".equals(type)){
            list = monthList;
        }else {
            list = daylogService.findList(new TranscodePriceDaylog());
        }
        if("week".equals(type) || "month".equals(type)){
            for (TranscodePriceDaylog priceDaylog :list) {
                priceDaylog.setStartMoney(daylogService.get(priceDaylog.getMinId()).getStartMoney());
                priceDaylog.setNowMoney(daylogService.get(priceDaylog.getMaxId()).getNowMoney());
            }
        }

        model.addAttribute("weekAmount",weekMoney);
        model.addAttribute("monthAmount",monthMoney);
        if(allList != null && allList.size() > 0){
            model.addAttribute("dayAmount",allList.get(allList.size()-1).getAmount());
        }else {
            model.addAttribute("dayAmount",BigDecimal.ZERO);
        }

        BigDecimal allAmount = allList.stream().map(p -> p.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        TranscodePriceDaylog nowLog = daylogService.getNowLog();
        BigDecimal nowMoney = BigDecimal.ZERO;
        if (nowLog != null){
            nowMoney = nowLog.getNowMoney();
        }
        model.addAttribute("nowMoney", nowMoney);
        model.addAttribute("type",type);
        model.addAttribute("priceList", list);
        model.addAttribute("allAmount",allAmount);
        return qyview("/trans/pricedaylog", request, model);
    }


    /**
     * 跳转买入委托
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/buyentrust"}, method = RequestMethod.GET)
    public String buyentrust(TranscodeBuy transcodeBuy,HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser());
        if (userUserinfo == null){
            return qyview("/login", request, model);
        }
        Page<TranscodeBuy> transcodeBuyPage = new Page<>(request, response);
        transcodeBuyPage.setPageSize(10);
        transcodeBuy.setUserName(userUserinfo.getUserName());
        transcodeBuy.setStatus(EnumTransUtil.TransBuyStatus.Selling.toString());
        transcodeBuy.setType(EnumUtil.TransCodeBuyType.buy.toString());
        Page<TranscodeBuy> page = transcodeBuyService.findPage(transcodeBuyPage, transcodeBuy);
        model.addAttribute("page", page);
        return qyview("/trans/buyentrust", request, model);
    }


    /**
     * 跳转卖出委托
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/sellentrust"}, method = RequestMethod.GET)
    public String sellentrust(TranscodeBuy transcodeBuy,HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser());
        if (userUserinfo == null){
            return qyview("/login", request, model);
        }
        Page<TranscodeBuy> transcodeBuyPage = new Page<>(request, response);
        transcodeBuyPage.setPageSize(10);
        transcodeBuy.setUserName(userUserinfo.getUserName());
        transcodeBuy.setStatus(EnumTransUtil.TransBuyStatus.Selling.toString());
        transcodeBuy.setType(EnumUtil.TransCodeBuyType.sell.toString());
        Page<TranscodeBuy> page = transcodeBuyService.findPage(transcodeBuyPage, transcodeBuy);
        model.addAttribute("page", page);
        return qyview("/trans/sellentrust", request, model);
    }


    /**
     * 跳转历史委托
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/entrusthistory"}, method = RequestMethod.GET)
    public String entrusthistory(TranscodeBuy transcodeBuy,HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser());
        if (userUserinfo == null){
            return qyview("/login", request, model);
        }
        Page<TranscodeBuy> transcodeBuyPage = new Page<>(request, response);
        transcodeBuyPage.setPageSize(10);
        transcodeBuy.setUserName(userUserinfo.getUserName());
        transcodeBuy.setStatusBegin("1");
        Page<TranscodeBuy> page = transcodeBuyService.findPage(transcodeBuyPage, transcodeBuy);
        model.addAttribute("page", page);
        return qyview("/trans/entrusthistory", request, model);
    }



    /**
     * 买方交易确认
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/buys"}, method = RequestMethod.GET)
    public String buys(TranscodeBuyLog transcodeBuyLog,HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser());
        if (userUserinfo == null){
            return qyview("/login", request, model);
        }
        Page<TranscodeBuyLog> transcodeBuyPage = new Page<>(request, response);
        transcodeBuyPage.setPageSize(10);
        transcodeBuyLog.setAllUser(userUserinfo.getUserName());
        transcodeBuyLog.setStatusEnd(EnumTransUtil.TransCodeLogStatus.WaitSeller.toString());
        Page<TranscodeBuyLog> page = transcodeBuyLogService.findPage(transcodeBuyPage, transcodeBuyLog);
        model.addAttribute("page", page);
        return qyview("/trans/buys", request, model);
    }

    /**
     * 根据id查询匹配订单信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/selectLogById"}, method = RequestMethod.POST)
    public String selectLogById(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser());
        String id = request.getParameter("id");
        if (userUserinfo == null){
            return error("账户登录超时,请重新登录!", response, model);
        }

        TranscodeBuyLog  transcodeBuyLog =  transcodeBuyLogService.get(id);
        if(!transcodeBuyLog.getBuyUserName().equals(userUserinfo.getUserName()) &&   !transcodeBuyLog.getSellUserName().equals(userUserinfo.getUserName()) ){
            return error("当前订单不存在!", response, model);
        }

        model.addAttribute("transBuy", transcodeBuyLogService.get(id));
        return success("成功!!", response, model);
    }




    /**
     * 成交记录
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/allList"}, method = RequestMethod.GET)
    public String allList(TranscodeBuyLog transcodeBuyLog,HttpServletRequest request, HttpServletResponse response, Model model) {
        UserUserinfo userUserinfo = userUserinfoService.get(UserInfoUtils.getUser());
        if (userUserinfo == null){
            return qyview("/login", request, model);
        }
        Page<TranscodeBuyLog> transcodeBuyPage = new Page<>(request, response);
        transcodeBuyPage.setPageSize(10);
        transcodeBuyLog.setAllUser(userUserinfo.getUserName());
        transcodeBuyLog.setStatusBegin(EnumTransUtil.TransCodeLogStatus.TransSuccess.toString());
        Page<TranscodeBuyLog> page = transcodeBuyLogService.findPage(transcodeBuyPage, transcodeBuyLog);
        model.addAttribute("page", page);
        return qyview("/trans/allList", request, model);
    }




    /**
     * 买入action
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = {"/buyaction"}, method = RequestMethod.POST)
    public String buyaction(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        UserUserinfo userinfo = userUserinfoService.getByNameLock(UserInfoUtils.getUser().getUserName());
        String num = request.getParameter("qty");
        if (userinfo == null){
            return error("账户登录超时,请重新登录!", response, model);
        }

        try {
            transcodeBuyService.buysell(request,userinfo.getUserName(), EnumUtil.TransCodeBuyType.buy,new BigDecimal(num));
            return success("成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        } catch (Exception e){
            return error("操作失败", response, model);
        }

    }



    /**
     * 卖出action
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = {"/sellaction"}, method = RequestMethod.POST)
    public String sellaction(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        String num = request.getParameter("qty");
        if (userinfo == null){
            return error("账户登录超时,请重新登录!", response, model);
        }

        try {
            transcodeBuyService.buysell(request,userinfo.getUserName(), EnumUtil.TransCodeBuyType.sell,new BigDecimal(num));

            return success("成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }catch (Exception e){
            return error("操作失败", response, model);
        }

    }



    /**
     * 交易撤销action
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = {"/revokeaction"}, method = RequestMethod.POST)
    public String revokeaction(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        String id = request.getParameter("id");
        if (userinfo == null){
            return error("账户登录超时,请重新登录!", response, model);
        }
        try {

            transcodeBuyService.saleOut(userinfo.getUserName(),id,EnumTransUtil.TransBuyStatus.downUser);
            return success("成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }catch (Exception e){
            return error("操作失败", response, model);
        }

    }




    /**
     * 交易确认action
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = {"/confirmaction"}, method = RequestMethod.POST)
    public String confirmaction(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        String recordId = request.getParameter("id");
        String imageUrl = request.getParameter("url");
        if (userinfo == null){
            return error("账户登录超时,请重新登录!", response, model);
        }
        try {

            transcodeBuyLogService.buyerConfirmaction(userinfo.getUserName(),recordId,imageUrl);
            return success("成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }catch (Exception e){
            return error("操作失败", response, model);
        }

    }

    /**
     * 交易确认action
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value = {"/confirmactionSell"}, method = RequestMethod.POST)
    public String confirmactionSell(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) {
        UserUserinfo userinfo = userUserinfoService.get(UserInfoUtils.getUser());
        String recordId = request.getParameter("id");
        if (userinfo == null){
            return error("账户登录超时,请重新登录!", response, model);
        }
        try {

            transcodeBuyLogService.sellerConfirmaction(userinfo.getUserName(),recordId);
            return success("成功!!", response, model);
        } catch (ValidationException e) {
            return error(e.getMessage(), response, model);
        }catch (Exception e){
            return error("操作失败", response, model);
        }

    }

}
