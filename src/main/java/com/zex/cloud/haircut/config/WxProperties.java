package com.zex.cloud.haircut.config;


import java.util.HashMap;
import java.util.Map;

public class WxProperties {

    public static final String SUCCESS = "SUCCESS";

    public static final String WX_PAY_GATEWAY = "https://api.mch.weixin.qq.com/";
    public static final String WX_GATEWAY = "https://api.weixin.qq.com/";

    public static final String APP_ID = "wxbfe3af412df84deb";
//    public static final  String MERCHANT_APP_ID = "wx755a4118f4c1dc9b";

    public static final String PARTNER_ID = "1521228231";

    public static final String SIGN_KEY = "6eaf81dbe70df60d0e9456fc7f27fac1";

    public static final Map<String,String> APPS = new HashMap<String,String>(){
        {
            put("wxbfe3af412df84deb", "6eaf81dbe70df60d0e9456fc7f27fac1");
            put("wxbfe3af412df84deb1", "6eaf81dbe70df60d0e9456fc7f27fac1");
        }
    };
}
