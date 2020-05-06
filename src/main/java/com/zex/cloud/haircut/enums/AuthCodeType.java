package com.zex.cloud.haircut.enums;

public enum  AuthCodeType {

    AGENT_APPLY("1001"),SHOP_APPLY("1001");


    String templateCode;

    AuthCodeType(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }
}
