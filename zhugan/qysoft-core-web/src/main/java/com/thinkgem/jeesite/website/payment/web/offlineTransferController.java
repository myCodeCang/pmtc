package com.thinkgem.jeesite.website.payment.web;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.entity.UserBankCharge;
import com.thinkgem.jeesite.modules.user.entity.UserBankCommon;
import com.thinkgem.jeesite.modules.user.entity.UserCharge;
import com.thinkgem.jeesite.modules.user.service.UserBankChargeService;
import com.thinkgem.jeesite.modules.user.service.UserBankCommonService;
import com.thinkgem.jeesite.modules.user.service.UserChargeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by Administrator on 2017/7/31.
 */
@Controller
@RequestMapping(value = "/payment/offlineTransfer")
public class offlineTransferController extends BaseController {
    @Resource
    private UserBankChargeService userBankChargeService;
    @Resource
    private UserBankCommonService userBankCommonService;
    @Resource
    private UserChargeService userChargeService;

    /**
     * 充值银行
     * 表名:
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/userChargeBankList", method = RequestMethod.POST)
    public String userChargeBankList(HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
        UserBankCharge userBankCharge = new UserBankCharge();
        userBankCharge.setStatus(EnumUtil.YesNO.YES.toString());
        model.addAttribute ("userChargeBank", userBankChargeService.findList(userBankCharge));
        return success ("成功!!", response, model);
    }

    /**
     * 默认银行
     * 表名:
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/userCommonBankList", method = RequestMethod.POST)
    public String userCommonBankList(HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
        UserBankCommon userBankCommon = new UserBankCommon();
        userBankCommon.setStatus(EnumUtil.YesNO.YES.toString());
        model.addAttribute ("userBankCommon", userBankCommonService.findList(userBankCommon));
        return success ("成功!!", response, model);
    }

    /**
     * 默认银行
     * 表名:
     * 条件:
     * 其他:
     */
    @RequestMapping(value = "/userApplyCharge", method = RequestMethod.POST)
    public String userApplyCharge(UserCharge userCharge ,HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
        userCharge.setStatus(EnumUtil.CheckType.uncheck.toString());
        userCharge.setUserName(UserInfoUtils.getUser().getUserName());
        userCharge.setRecordcode(IdGen.uuid("seq_recordcode"));
        userCharge.setCommt("线下转账");
        try {
            userChargeService.save(userCharge);
        }catch (ValidationException e){
            return error ("失败:" + e.getMessage(), response, model);
        }
        return success ("成功!!", response, model);
    }

    @RequestMapping(value = "/imgFileUpload", method = RequestMethod.POST)
    public String imgFileUpload(@RequestParam(value = "file" ,required = false)MultipartFile file, @RequestParam("name")String name, HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
        if (file == null) {
            return error("上传失败,请更换图片重试", response, model);
        }
        String path = request.getSession().getServletContext().getRealPath("/AppUpload");
        String fileName = file.getOriginalFilename();
//        String fileName = new Date().getTime()+".jpg";
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            return error("失败:"+e.getMessage(), response, model);
        }
        model.addAttribute("path", request.getContextPath()+"/AppUpload/"+fileName);
        return success("成功!!", response, model);

    }
}
