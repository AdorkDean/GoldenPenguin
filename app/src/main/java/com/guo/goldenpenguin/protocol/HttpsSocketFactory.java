package com.guo.goldenpenguin.protocol;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 *  https SSL
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 16 11
 */
public class HttpsSocketFactory {

    public static SSLSocketFactory getSSLSocketFactory(){
        try{
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new MyTrustManager[]{new MyTrustManager()}, new SecureRandom());
            return sc.getSocketFactory();
        }catch(Exception e){
            e.printStackTrace();;
        }
        return null;
    }

    private static class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    }


}
