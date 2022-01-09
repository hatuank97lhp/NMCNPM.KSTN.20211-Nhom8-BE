package com.example.cnpmbe.common;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class APIResponse {
    private int status;

    private String messageCode;

    private String message;

    private Object result;

    public APIResponse(int status, String messageCode, String message) {
        this.status = status;
        this.messageCode = messageCode;
        this.message = message;
    }

    public APIResponse(int status, String messageCode, String message, Object result) {
        this.status = status;
        this.messageCode = messageCode;
        this.message = message;
        this.result = result;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "status='" + status + '\'' +
                ", messageCode='" + messageCode + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}