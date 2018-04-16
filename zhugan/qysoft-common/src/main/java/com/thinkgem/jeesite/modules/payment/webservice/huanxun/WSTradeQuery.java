package com.thinkgem.jeesite.modules.payment.webservice.huanxun;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2017-03-27T13:18:43.055+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "WSTradeQuery", 
                  wsdlLocation = "file:/E:/IPS接口对接DMEO/委托付款/issued-demo/src/main/java/cn/com/ips/payat/webservice/tradequery/trade.wsdl",
                  targetNamespace = "http://payat.ips.com.cn/WebService/TradeQuery") 
public class WSTradeQuery extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://payat.ips.com.cn/WebService/TradeQuery", "WSTradeQuery");
    public final static QName WSTradeQuerySoap = new QName("http://payat.ips.com.cn/WebService/TradeQuery", "WSTradeQuerySoap");
    static {
        URL url = null;
        try {
            url = new URL("file:/E:/IPS接口对接DMEO/委托付款/issued-demo/src/main/java/cn/com/ips/payat/webservice/tradequery/trade.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(WSTradeQuery.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/E:/IPS接口对接DMEO/委托付款/issued-demo/src/main/java/cn/com/ips/payat/webservice/tradequery/trade.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public WSTradeQuery(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public WSTradeQuery(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSTradeQuery() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns TradeQueryService
     */
    @WebEndpoint(name = "WSTradeQuerySoap")
    public TradeQueryService getWSTradeQuerySoap() {
        return super.getPort(WSTradeQuerySoap, TradeQueryService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TradeQueryService
     */
    @WebEndpoint(name = "WSTradeQuerySoap")
    public TradeQueryService getWSTradeQuerySoap(WebServiceFeature... features) {
        return super.getPort(WSTradeQuerySoap, TradeQueryService.class, features);
    }

}