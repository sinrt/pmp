
package com.payamak.panel.client;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 */
@WebServiceClient(name = "Send", targetNamespace = "http://tempuri.org/", wsdlLocation = "file:/D:/Projects/Payvaran/gs-consuming-web-service-master/simple.wsdl")
public class SendSMSService extends Service {

    private final static QName SEND_QNAME = new QName("http://tempuri.org/", "Send");

    public SendSMSService(String wsdlUrl) throws Exception {
        super(new URL(wsdlUrl), SEND_QNAME);
    }

    /*public SendSMSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SEND_QNAME, features);
    }

    public SendSMSService(URL wsdlLocation) {
        super(wsdlLocation, SEND_QNAME);
    }

    public SendSMSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SEND_QNAME, features);
    }

    public SendSMSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SendSMSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }*/

    /**
     * @return returns SendSoap
     */
    @WebEndpoint(name = "SendSoap")
    public SendSoap getSendSoap() {
        return super.getPort(new QName("http://tempuri.org/", "SendSoap"), SendSoap.class);
    }

    /**
     * @param features A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns SendSoap
     */
    @WebEndpoint(name = "SendSoap")
    public SendSoap getSendSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "SendSoap"), SendSoap.class, features);
    }
}
