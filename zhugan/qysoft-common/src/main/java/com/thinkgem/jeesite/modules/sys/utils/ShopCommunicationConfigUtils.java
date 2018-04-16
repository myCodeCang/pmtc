package com.thinkgem.jeesite.modules.sys.utils;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by yankai on 2017/6/1.
 */
@Component
public class ShopCommunicationConfigUtils {
    //商城通信配置
    private static Map<String, Map<String, String>> shopConfigs = Maps.newHashMap();

    static {
        initShopConfigs();
    }

    public static Set<String> shopIdSet() {
        return shopConfigs.keySet();
    }

    public static Collection<Map<String, String>> configValues() {
        return shopConfigs.values();
    }

    public static void initShopConfigs() {
        shopConfigs.clear();
        Map<String, String> authoParam = Global.getOptionMap("auth_shop");
        authoParam.forEach((key, value) -> {
            if (key.startsWith("shopid_")) {
                String shopId = value;
                Map<String, String> shopConfig = shopConfigs.putIfAbsent(shopId, Maps.newHashMap());
                if (null == shopConfig) {
                    shopConfig = shopConfigs.get(shopId);
                }

                shopConfig.put("shopName", Global.getOption("auth_shop", "shopName_" + shopId));
                shopConfig.put("syncUser", Global.getOption("auth_shop", "syncUser_" + shopId));
                shopConfig.put("syncPws", Global.getOption("auth_shop", "syncPws_" + shopId));
                shopConfig.put("syncMoney", Global.getOption("auth_shop", "syncMoney_" + shopId));
                shopConfig.put("syncScore", Global.getOption("auth_shop", "syncScore_" + shopId));
                shopConfig.put("syncScoreField", Global.getOption("auth_shop", "syncScoreField_" + shopId));
                shopConfig.put("shopUrl", Global.getOption("auth_shop", "url") + "/app/index.php?i=" + shopId + "&c=entry&m=ewei_shopv2&do=mobile&r=qyapi.client.");
                shopConfig.put("shopKey", Global.getOption("auth_shop", "shopKey_" + shopId));
                shopConfig.put("shopId", shopId);
            }
        });
    }

    /**
     * 获取指定商城的指定配置参数
     *
     * @param shopId
     * @param paramName
     * @return
     */
    public static Optional<String> getConfigValue(String shopId, String paramName) {
        if (StringUtils2.EMPTY.equals(shopId)) {
            shopId = Collections3.getFirst(shopConfigs.keySet());
        }

        if (!shopConfigs.containsKey(shopId)) {
            return Optional.empty();
        }

        Map<String, String> config = shopConfigs.get(shopId);
        if (config == null || config.isEmpty()) {
            return Optional.empty();
        }

        if (!config.containsKey(paramName)) {
            return Optional.empty();
        }


        return Optional.ofNullable(config.get(paramName));
    }
}
