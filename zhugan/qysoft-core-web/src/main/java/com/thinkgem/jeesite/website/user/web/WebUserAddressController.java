package com.thinkgem.jeesite.website.user.web;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserInfoUtils;
import com.thinkgem.jeesite.modules.user.entity.UserAddress;
import com.thinkgem.jeesite.modules.user.service.UserAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yankai on 2017/7/13.
 */
@Controller
@RequestMapping(value = "${frontPath}/webUser/userAddr")
public class WebUserAddressController extends BaseController {
    @Resource
    private UserAddressService userAddressService;
    /**
     * 个人地址修改/添加
     * 表名: userinfo
     * 条件:
     * 其他:
     */

    @RequestMapping(value = "/updateUserAddress", method = RequestMethod.POST)
    public String updateUserAddress(UserAddress userAddress, HttpServletRequest request, HttpServletResponse response, Model model) throws ValidationException {

        if (StringUtils2.isNotBlank (userAddress.getId ())) {
            String userName = userAddressService.get (userAddress.getId ()).getUserName ();
            if (!UserInfoUtils.getUser ().getUserName ().equals (userName)) {
                return error ("只可修改本人地址!!", response, model);
            }
        }
        userAddress.setUserName (UserInfoUtils.getUser ().getUserName ());
        userAddress.setPostcode("");//手动设置空邮编
        try {
            userAddressService.save (userAddress);
        }catch (ValidationException e){
            return error (e.getMessage (), response, model);
        }
        return success ("个人地址修改成功!!", response, model);
    }

    /**
     * 个人地址删除
     * 表名: userinfo
     * 条件:
     * 其他:
     */

    @RequestMapping(value = "/deleteUserAddress", method = RequestMethod.POST)
    public String deleteUserAddress(HttpServletRequest request, HttpServletResponse response, Model model) throws ValidationException {
        String id = request.getParameter ("id");
        if (StringUtils2.isBlank(id)) {
            return error ("失败!!", response, model);
        }
        UserAddress userAddress = userAddressService.get (id);

        if(!userAddress.getUserName().equals(UserInfoUtils.getUser().getUserName())){
            return error ("只可删除本人地址!!", response, model);
        }

        userAddressService.delete (userAddress);
        return success ("成功!!", response, model);
    }

    /**
     * 个人地址查询
     * 表名:
     * 条件: 用户名
     * 其他:
     */
    @RequestMapping(value = "/userAddressList", method = RequestMethod.POST)
    public String userAddressList(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserName (UserInfoUtils.getUser ().getUserName ());
        Page<UserAddress> page = userAddressService.findPage (new Page<UserAddress>(request, response), userAddress);
        model.addAttribute ("userAddressList", page);
        return success ("成功!!", response, model);
    }

    /**
     * 地址详细信息查询
     * 表名:
     * 条件:id
     * 其他:
     */
    @RequestMapping(value = "/addressaInfo", method = RequestMethod.POST)
    public String addressaInfo(HttpServletRequest request, HttpServletResponse response, Model model) {

        UserAddress address =  userAddressService.get (request.getParameter ("id"));
        if(!address.getUserName().equals(UserInfoUtils.getUser().getUserName())){
            return error ("只可查询个人地址!!", response, model);
        }
        model.addAttribute ("addressaInfo",address );
        return success ("成功!!", response, model);
    }
}
