package com.example.cnpmbe.common.enums;


public enum ResultMessages {
    API_SUCCESS(200, "api.success");

    private int status;

    private String messageCode;

    ResultMessages(int status, String messageCode) {
        this.status = status;
        this.messageCode = messageCode;
    }

    public int getStatus() {
        return status;
    }

    public String getMessageCode() {
        return messageCode;
    }

}

