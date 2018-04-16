package com.thinkgem.jeesite.modules.payment.utils;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class CxfCommunication {

	/**
	 *
	 * @param wsUrl webservcie地址
	 * @param wsPortClass
	 * @return
	 */
    public static Object getWsPort(String wsUrl, Class wsPortClass) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress(wsUrl);
        factory.setServiceClass(wsPortClass);
        Object port = factory.create();
        return  port;
    }

}
