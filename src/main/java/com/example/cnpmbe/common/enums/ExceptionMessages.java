package com.example.cnpmbe.common.enums;


public enum ExceptionMessages {

    USER_ID_NOT_FOUND(601, "api.user-id-not-found"),
    USER_USERNAME_EMAIL_EXIST(501, "api.user-username-email-exist"),
    NHAN_KHAU_ID_NOT_FOUND(701, "api.nhankhau.id-not-found"),
    NHAN_KHAU_CCCD_DUPLICATE(702, "api.nhankhau.cccd-duplicate"),
    TAM_VANG_ID_NOT_FOUND(703, "api.nhakhau.tam-vang-not-found"),
    TAM_TRU_ID_NOT_FOUND(704, "api.nhakhau.tam-tru-not-found"),
    HO_KHAU_ID_NOT_FOUND(801, "api.hokhau.id-not-found"),
    CUOC_HOP_ID_NOT_FOUND(801, "api.cuochop.id-not-found");

    private int status;

    private String messageCode;

    ExceptionMessages(int status, String messageCode) {
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