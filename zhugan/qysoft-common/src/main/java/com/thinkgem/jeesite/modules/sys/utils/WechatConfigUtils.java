package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yankai on 2017/7/24.
 */
public class WechatConfigUtils {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final WechatConfigUtils instance = new WechatConfigUtils();

    //private static Map<String, WechatConfig> wechatConfigs = Maps.newHashMap();
    private static WechatConfig wechatConfig = null;

    private WechatConfigUtils() {
        initWechatConfig();
    }

    public static WechatConfigUtils getInstance() {
        return instance;
    }

    public void initWechatConfig() {
        String appId = Global.getOption("wechat_config", "app_id");
        String appSecret = Global.getOption("wechat_config", "app_secret");
        String aesKey = Global.getOption("wechat_config", "aes_key");
        String token = Global.getOption("wechat_config", "token");

        wechatConfig = new WechatConfig(token, appId, appSecret, aesKey);
    }

    public static class WechatConfig {
        public WechatConfig() {
        }

        public WechatConfig(String token, String appid, String appSecret, String appKey) {
            this.token = token;
            this.appid = appid;
            this.appSecret = appSecret;
            this.aesKey = appKey;
        }

        private String token;

        private String appid;

        private String appSecret;

        private String aesKey;

        public String getToken() {
            return this.token;
        }

        public String getAppid() {
            return this.appid;
        }

        public String getAppsecret() {
            return this.appSecret;
        }

        public String getAesKey() {
            return this.aesKey;
        }
    }
}
