package com.thinkgem.jeesite.website.web;


import com.alibaba.druid.support.json.JSONUtils;
import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import com.thinkgem.jeesite.modules.user.dao.UserUserinfoDao;
import com.thinkgem.jeesite.modules.user.entity.UserOptions;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import com.thinkgem.jeesite.modules.user.service.UserMsmService;
import com.thinkgem.jeesite.modules.user.service.UserOptionsService;
import com.thinkgem.jeesite.modules.user.service.UserTransOutService;
import com.thinkgem.jeesite.modules.user.service.UserUserinfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/trans")
public class UserTransApiController extends WebBaseController {

    @Resource
    private UserUserinfoDao userinfoDao;
    @Resource
    UserUserinfoService userUserinfoService;
    @Resource
    private UserMsmService msmService;
    @Resource
    private UserOptionsService optionsService ;
    @Resource
    private UserTransOutService userTransOutService;



    @RequestMapping(value = "checkShopApi")
    public String checkShopApi(HttpServletRequest request, HttpServletResponse response, Model model) {
        Optional<Object> result = userTransOutService.checkUserapi();
        if (!result.isPresent()) {
            return error("失败", response, model);
        }
        Map<String, Object> resMap = (Map<String, Object>) result.get();
        if ("1".equals(resMap.get("status").toString())) {
            model.addAttribute("data", resMap.get("siteName"));
            return success("成功!!", response, model);
        }
        if ("0".equals(resMap.get("status").toString())){
            model.addAttribute("data", resMap.get("info"));
            return error("失败", response, model);
        }
        return error("失败", response, model);
    }

    /**
     * 根据钱包地址获取用户信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getMessageByAddress")
    public String getMessageByAddress(HttpServletRequest request, HttpServletResponse response, Model model) {

        if (!userTransOutService.checkIpAndPwd(request)){
            return error("通信未成功",response,model);
        }

        String address = request.getParameter("address");
        if(StringUtils2.isBlank(address)){
            return error("失败,地址为空",response,model);
        }
        UserUserinfo userinfo = userinfoDao.getByRemarks(address);
        if(userinfo == null){
            return error("失败,未检测到该矿机钱包地址",response,model);
        }

        model.addAttribute("userName",userinfo.getUserName());
        model.addAttribute("money2",userinfo.getMoney2());
        return success("成功",response,model);
    }


    /**
     * 判断短信验证码是否正确
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/verifyCode")
    public String verifyCode(HttpServletRequest request, HttpServletResponse response, Model model){

        if (!userTransOutService.checkIpAndPwd(request)){
            return error("通信未成功",response,model);
        }

        String address = request.getParameter("address");
        String validCode = request.getParameter("validCode");

        if(StringUtils2.isBlank(address) || StringUtils2.isBlank(validCode)){
            return error("失败,参数有误",response,model);
        }

        UserUserinfo userinfo = userinfoDao.getByRemarks(address);
        if(userinfo == null){
            return error("失败,未检测到该矿机钱包地址",response,model);
        }

        boolean result = msmService.checkVerifyCode(userinfo.getMobile(), validCode);
        if (!result){
            return error("验证码错误",response,model);
        }
        return success("成功",response,model);
    }

    /**
     * 根据钱包地址修改总钱包金额
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateMoneyByAddress")
    public String updateMoneyByAddress(HttpServletRequest request, HttpServletResponse response, Model model){

        if (!userTransOutService.checkIpAndPwd(request)){
            return error("通信未成功",response,model);
        }

        String address = request.getParameter("address");
        String moneyTemp = request.getParameter("money");
        String message = request.getParameter("message");
        BigDecimal money;
        if(StringUtils2.isBlank(address) || StringUtils2.isBlank(moneyTemp)){
            return error("失败,参数有误",response,model);
        }
        UserUserinfo userinfo = userinfoDao.getByRemarks(address);
        if(userinfo == null){
            return error("失败,未检测到该矿机钱包地址",response,model);
        }
        try {
            money = new BigDecimal(moneyTemp);
        } catch (Exception e) {
            return error("失败,金额有误",response,model);
        }

        try {

            userUserinfoService.updateUserMoney(userinfo.getUserName(),money,"交易钱包从交易端转入"+message, EnumUtil.MoneyChangeType.UPDATE_MONEY_BYTRANS);
        } catch (ValidationException e) {
            return error("修改失败,原因:"+e.getMessage(),response,model);
        }catch (Exception e){
            return error("失败,原因:"+e.getMessage(),response,model);
        }

        return success("成功",response,model);
    }


    /**
     * 更新最新价格
     * @return
     */
    @RequestMapping(value = "/updateNowPrice")
    public String updateNowPrice(HttpServletRequest request, HttpServletResponse response, Model model){

        if (!userTransOutService.checkIpAndPwd(request)){
            return error("通信未成功",response,model);
        }

        String nowPrice = request.getParameter("nowPrice");
        if(StringUtils2.isBlank(nowPrice)){
            return error("失败,参数有误",response,model);
        }
        UserOptions options = optionsService.getByOptionName("trans_price");
        if (options == null){
            options = new UserOptions();
            options.setGroupName("trans_group");
            options.setOptionLabel("最新价格");
            options.setOptionName("trans_price");
        }

        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("optName","trans_price");
        stringMap.put("nowPrice",nowPrice);
        String json = JSONUtils.toJSONString(stringMap);
        options.setOptionValue(json);

        try {
            optionsService.save(options);
        } catch (ValidationException e) {
            return error("失败",response,model);
        }
        return success("成功",response,model);
    }

    /**
     * 检查连接是否成功
     * @return
     */
    @RequestMapping(value = "/checkConnection")
    public String checkConnection(HttpServletRequest request, HttpServletResponse response, Model model){

        if (!userTransOutService.checkIpAndPwd(request)){
            return error("通信未成功",response,model);
        }

        String siteName = Global.getOption("system_sys", "site_name");
        model.addAttribute("siteName",siteName);
        return success("成功",response,model);
    }

    /**
     * 根据钱包地址发送短信验证码
     * @return
     */
    @RequestMapping(value = "/sendVerifyCode")
    public String sendVerifyCode(HttpServletRequest request, HttpServletResponse response, Model model){

        if (!userTransOutService.checkIpAndPwd(request)){
            return error("通信未成功",response,model);
        }

        String address = request.getParameter("address");

        if(StringUtils2.isBlank(address)){
            return error("失败,地址为空",response,model);
        }

        UserUserinfo userinfo = userinfoDao.getByRemarks(address);
        if(userinfo == null){
            return error("失败,未检测到该矿机钱包地址",response,model);
        }
        Boolean result = VerifyUtils.isMobile(userinfo.getMobile());
        if(!result){
            return error("失败,手机号有误",response,model);
        }

        try {
            msmService.sendVerifyCode(userinfo.getMobile());
        } catch (ValidationException e) {
            return error("失败.",response,model);
        }
        return success("成功",response,model);
    }

}
