/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import com.thinkgem.jeesite.common.config.EnumUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.modules.sys.utils.MsmUtils;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.entity.UserMsm;
import com.thinkgem.jeesite.modules.user.dao.UserMsmDao;

/**
 * 保存用户短信Service
 *
 * @author yankai
 * @version 2017-05-28
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "ValidationException"})
public class UserMsmService extends CrudService<UserMsmDao, UserMsm> {

    @Autowired
    public UserUserinfoService userinfoService;

    public UserMsm get(String id) {
        return super.get(id);
    }

    public List<UserMsm> findList(UserMsm userMsm) {
        return super.findList(userMsm);
    }

    public Page<UserMsm> findPage(Page<UserMsm> page, UserMsm userMsm) {
        return super.findPage(page, userMsm);
    }


    public void save(UserMsm userMsm) throws ValidationException {
        super.save(userMsm);
    }

    public void delete(UserMsm userMsm) throws ValidationException {
        super.delete(userMsm);
    }


    public Optional<UserMsm> addOrUpdateMsm(String mobile) throws ValidationException {
        if (StringUtils2.isEmpty(mobile)) {
            return Optional.empty();
        }

        String verifyCode = UserMsm.generateVerifyCode(6);
        UserMsm userMsm = dao.getByMobile(mobile);
        if (userMsm == null) {
            userMsm = new UserMsm();
            userMsm.setMobile(mobile);
        }
        userMsm.setMsg(verifyCode);
        userMsm.setCreateTime(new Date());
        if (StringUtils2.isEmpty(userMsm.getId()) || "0".equals(userMsm.getId())) {
            save(userMsm);
            userMsm = dao.getByMobile(mobile);
        } else {
            dao.updateMsg(verifyCode, new Date(), mobile);
        }

        return Optional.of(userMsm);
    }


    public Optional<UserMsm> getByUserName(String userName) {
        if (StringUtils2.isEmpty(userName)) {
            return Optional.empty();
        }

        UserMsm byUserName = dao.getByMobile(userName);
        if (null == byUserName) {
            return Optional.empty();
        }

        return Optional.of(byUserName);
    }


    /**
     * 选择哪种方式发送短信
     *
     * @param mobile
     * @param type
     * @return
     */
    public String choceSendType(String mobile, String type) {
        if (EnumUtil.YesNO.YES.toString().equals(type)) {
            return sendInternVerifyCode(mobile);
        } else if (EnumUtil.YesNO.NO.toString().equals(type)) {
            return sendVerifyCode(mobile);
        } else {
            UserUserinfo userinfo = userinfoService.getByMobile(mobile);
            if (userinfo == null) {
                return StringUtils2.EMPTY;
            }
            //判断是否是美国用户
            if (EnumUtil.YesNO.YES.toString().equals(userinfo.getIsUsercenter())) {
                return sendInternVerifyCode(mobile);
            }
            return sendVerifyCode(mobile);
        }


    }


    /**
     * 发送短信
     *
     * @param mobile
     * @return
     */

    public String sendVerifyCode(String mobile) throws ValidationException {

        Optional<UserMsm> userMsmOptional = addOrUpdateMsm(mobile);
        if (!userMsmOptional.isPresent()) {
            return StringUtils2.EMPTY;
        }

        UserMsm userMsm = userMsmOptional.get();
        String msg = userMsm.getMsg();
        String msgTemplate = Global.getOption("system_sms", "lk_msg_template");
        if (!StringUtils2.isEmpty(msgTemplate)) {
            msg = msgTemplate.replace("{{phoneNum}}", msg);
        }

        boolean sendSuccess = MsmUtils.getInstance().lingkaiSendMsg(mobile, msg);
        if (sendSuccess) {
            return userMsm.getMsg();
        }

        return StringUtils2.EMPTY;
    }

    /**
     * 发送短信
     *
     * @param mobile
     * @return
     */

    public String pushMessage(String mobile, String userName, String userNiceName, String mobileMode) throws ValidationException {

        Optional<UserMsm> userMsmOptional = addOrUpdateMsm(mobile);
        if (!userMsmOptional.isPresent()) {
            return StringUtils2.EMPTY;
        }
        UserMsm userMsm = userMsmOptional.get();
        //String msg = userMsm.getMsg();
        String msg = mobileMode;
        if (!StringUtils2.isEmpty(msg)) {
            msg = msg.replace("{{userName}}", userName);
            msg = msg.replace("{{userNiceName}}", userNiceName);
        }
        boolean sendSuccess = MsmUtils.getInstance().lingkaiSendMsg(mobile, msg);
        if (sendSuccess) {
            return userMsm.getMsg();
        }
        return StringUtils2.EMPTY;
    }

    /**
     * 发送短信
     *
     * @param mobile
     * @return
     */

    public String pushMessage(String mobile, String userName, String mobileMode) throws ValidationException {
        Optional<UserMsm> userMsmOptional = addOrUpdateMsm(mobile);
        if (!userMsmOptional.isPresent()) {
            return StringUtils2.EMPTY;
        }
        UserMsm userMsm = userMsmOptional.get();
        //String msg = userMsm.getMsg();
        String msg = mobileMode;
        boolean sendSuccess = MsmUtils.getInstance().lingkaiSendMsg(mobile, msg);
        if (sendSuccess) {
            return userMsm.getMsg();
        }
        return StringUtils2.EMPTY;
    }

    /**
     * 用户注册发送短信
     *
     * @param mobile
     * @return
     */

    public String smsToUser(String mobile, String userName, String userPass) throws ValidationException {
        Optional<UserMsm> userMsmOptional = addOrUpdateMsm(mobile);
        if (!userMsmOptional.isPresent()) {
            return StringUtils2.EMPTY;
        }

        UserMsm userMsm = userMsmOptional.get();
        String msg = userMsm.getMsg();
        String msgTemplate = Global.getOption("system_sms", "lk_reg_template");
        if (!StringUtils2.isEmpty(msgTemplate)) {
            msgTemplate = msgTemplate.replace("{{userName}}", userName);
            msg = msgTemplate.replace("{{password}}", userPass);
        }

        boolean sendSuccess = MsmUtils.getInstance().lingkaiSendMsg(mobile, msg);
        if (sendSuccess) {
            return userMsm.getMsg();
        }

        return StringUtils2.EMPTY;
    }

    /**
     * 校验验证码是否有效
     *
     * @param mobile
     * @param verifyCode
     * @return
     */

    public boolean checkVerifyCode(String mobile, String verifyCode) {
        if (StringUtils2.isEmpty(mobile, verifyCode)) {
            return false;
        }

        Optional<UserMsm> userMsmOptional = getByUserName(mobile);
        if (!userMsmOptional.isPresent()) {
            return false;
        }

        UserMsm userMsm = userMsmOptional.get();
        if (!verifyCode.equals(userMsm.getMsg())) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createTime = LocalDateTime.ofInstant(userMsm.getCreateTime().toInstant(), ZoneId.systemDefault());
        long minutes = Duration.between(createTime, now).toMinutes();
        if (minutes > 10) {
            return false;
        }
        return true;
    }


    /**
     * 初始化配置
     *
     * @return
     */
    public Map<String, String> getInitConfig() {

        String interfaceAddress = Global.getOption("system_sms", "lk2_address");
        String account = Global.getOption("system_sms", "lk2_acc");
        String password = Global.getOption("system_sms", "lk2_pwd");

        if (StringUtils2.isBlank(interfaceAddress) || StringUtils2.isBlank(account) || StringUtils2.isBlank(password)) {
            throw new ValidationException("后台短信国际端配置有误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("address", interfaceAddress);
        map.put("account", account);
        map.put("password", password);

        return map;
    }

    /**
     * 发送注册验证码
     *
     * @return
     */

    public String sendInternVerifyCode(String mobile) throws ValidationException {

        Optional<UserMsm> userMsmOptional = this.addOrUpdateMsm(mobile);
        if (!userMsmOptional.isPresent()) {
            return StringUtils2.EMPTY;
        }


        UserMsm userMsm = userMsmOptional.get();
        String msg = userMsm.getMsg();
        String msgTemplate = Global.getOption("system_sms", "lk_msg_template");
        if (!StringUtils2.isEmpty(msgTemplate)) {
            msg = msgTemplate.replace("{{phoneNum}}", msg);
        }

        boolean result = false;
        try {
            result = sendSMSPost(getInitConfig(), mobile, msg, "");
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result) {
            return userMsm.getMsg();
        }

        return StringUtils2.EMPTY;

    }

    /**
     * 注册成功短信
     *
     * @param mobile
     * @return
     */

    public String sendSuccessCode(String mobile, String userName, String userNiceName, String mobileMode) throws ValidationException {
        Optional<UserMsm> userMsmOptional = this.addOrUpdateMsm(mobile);

        if (!userMsmOptional.isPresent()) {
            return StringUtils2.EMPTY;
        }

        UserMsm userMsm = userMsmOptional.get();
        //String msg = userMsm.getMsg();
        String msg = mobileMode;
        if (!StringUtils2.isEmpty(msg)) {
            msg = msg.replace("{{userName}}", userName);
            msg = msg.replace("{{userNiceName}}", userNiceName);
        }

        boolean result = false;
        try {
            result = sendSMSPost(getInitConfig(), mobile, msg, "");
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result) {
            return userMsm.getMsg();
        }

        return StringUtils2.EMPTY;
    }


    /**
     * 国际号段
     *
     * @param Mobile    电话号码
     * @param Content   发送内容
     * @param send_time 参数格式例：20171222162230   ---代表2017年12月22日16点22分30秒
     *                  定时发送时间，为空时，为及时发送
     * @return
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException
     */
    public boolean sendSMSPost(Map<String, String> map, String Mobile, String Content,
                               String send_time) throws MalformedURLException,
            UnsupportedEncodingException {
        String strUrl = map.get("address");
        String account = map.get("account");
        String password = map.get("password");

        String inputLine = "";
        int value = 0;
        String send_content = URLEncoder.encode(
                Content.replaceAll("<br/>", " "), "GBK");    // 对发送内容进行GBK编码处理

        StringBuilder sb = new StringBuilder();
        sb.append("CorpID=").append(account);
        sb.append("&Pwd=").append(password);
        sb.append("&Mobile=").append(Mobile);
        sb.append("&Content=").append(send_content);
        sb.append("&Cell=&SendTime=").append(send_time);

        inputLine = sendPost(strUrl, sb.toString());

        int intResult = Integer.parseInt(inputLine);
        if (intResult < 0) {
            Optional<String> errMsgByCode = MsmUtils.LingKaiErrorEnum.getErrMsgByCode(intResult);
            if (errMsgByCode.isPresent()) {
                logger.error(errMsgByCode.get());
            }
            return false;
        }

        return true;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}