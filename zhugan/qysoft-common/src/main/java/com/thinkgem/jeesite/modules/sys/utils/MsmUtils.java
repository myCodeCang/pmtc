package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.HttpClientUtil;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 短信工具类
 * Created by yankai on 2017/5/29.
 */
public class MsmUtils {
    //private static final String LING_KEI_URL = "https://sdk2.028lk.com/sdk2/BatchSend2.aspx";

    private static List<MsmAccountInfo> accountConfig = new ArrayList<>();

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final MsmUtils instance = new MsmUtils();

    private MsmUtils() {
        initLingKaiAccountConfig();
    }

    public void initLingKaiAccountConfig() {
        if (accountConfig != null) {
            accountConfig.clear();
        }
        String interfaceAddress = Global.getOption("system_sms", "lk_address");
        String account = Global.getOption("system_sms", "lk_acc");
        String pwd = Global.getOption("system_sms", "lk_pwd");
        String supplier = "LK";

        MsmAccountInfo accInfo = new MsmAccountInfo(interfaceAddress, account, pwd, supplier);
        accountConfig.add(accInfo);
    }

    public static MsmUtils getInstance() {
        return instance;
    }

    /**
     * 凌凯发送短信接口
     *
     * @param mobile
     * @param msg
     * @return
     * @since 1.8+
     */
    public boolean lingkaiSendMsg(String mobile, String msg) {
        if (StringUtils2.isEmpty(mobile, msg)) {
            return false;
        }

        Optional<MsmAccountInfo> accInfo = accountConfig.stream().filter(config -> "LK".equals(config.getMsmSupplier())).findAny();
        if (!accInfo.isPresent()) {
            return false;
        }

        MsmAccountInfo msmAccountInfo = accInfo.get();
        if (StringUtils2.isEmpty(msmAccountInfo.getAccount(), msmAccountInfo.getPassword())) {
            return false;
        }

        try {
            String message = Encodes.urlEncode(new String(msg.getBytes("UTF-8"), "GBK"));

            /*Map<String, String> param = Maps.newHashMap();
            param.put("CorpID", msmAccountInfo.getAccount());
            param.put("Pwd", msmAccountInfo.getPassword());
            param.put("Mobile", mobile);
            param.put("Content", Encodes.urlEncode(msg));
            String result = HttpClientUtil.sendHttpPost(LING_KEI_URL, param);*/

            StringBuilder sb = new StringBuilder(msmAccountInfo.getInterfaceAddress());
            sb.append("?CorpID=").append(msmAccountInfo.getAccount());
            sb.append("&Pwd=").append(msmAccountInfo.getPassword());
            sb.append("&Mobile=").append(mobile);
            sb.append("&Content=").append(URLEncoder.encode(
                    msg.replaceAll("<br/>", " "), "GBK"));
            String result = HttpClientUtil.sendHttpGet(sb.toString());
            int intResult = Integer.parseInt(result);
            if (intResult < 0) {
                Optional<String> errMsgByCode = LingKaiErrorEnum.getErrMsgByCode(intResult);
                if (errMsgByCode.isPresent()) {
                    logger.error(errMsgByCode.get());
                }
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }

    public static class MsmAccountInfo {
        private String interfaceAddress;
        private String msmSupplier;
        private String account;
        private String password;

        public MsmAccountInfo(String interfaceAddress, String account, String pwd, String supplier) {
            this.interfaceAddress = interfaceAddress;
            this.account = account;
            this.password = pwd;
            this.msmSupplier = supplier;
        }

        public String getMsmSupplier() {
            return msmSupplier;
        }

        public void setMsmSupplier(String msmSupplier) {
            this.msmSupplier = msmSupplier;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getInterfaceAddress() {
            return interfaceAddress;
        }

        public void setInterfaceAddress(String interfaceAddress) {
            this.interfaceAddress = interfaceAddress;
        }
    }

    public static enum LingKaiErrorEnum {
        NOT_REGISTER(-1, "账号为注册"),
        OTHER_ERROR(-2, "其他错误"),
        ACCOUNT_OR_PWD_ERROR(-3, "账号密码错误"),
        BALANCE_NOT_ENOUGH(-5, "余额不足"),
        DATE_FORMAT_ERROR(-6, "定时发送时间格式无效"),
        MSG_NOT_SIGN(-7, "提交信息末尾未签名"),
        LENGTH_ERROR(-8, "内容必须在1-300字之间"),
        NUMBER_IS_NULL(-9, "发送号码为空"),
        TIME_LESS_SYSTEM_TIME(-10, "定时时间不能小于系统时间"),
        IP_BLACKLIST(-100, "IP黑名单"),
        ACCOUNT_BLACKLIST(-102, "账号黑名单"),
        IP_NOT_IMPORT(-103, "IP未导白");

        private int code;
        private String msg;

        LingKaiErrorEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("[").append(code).append("-").append(msg).append("]");

            return result.toString();
        }

        public static Optional<String> getErrMsgByCode(int code) {
            return Arrays.stream(values())
                    .filter(errorEnum -> errorEnum.getCode() == code)
                    .map(errorEnum -> errorEnum.getMsg()).findAny();
        }
    }
}
