package com.isuwang.bankenterpirse.vo;

public class Res<T> {
    private int code;
    private String message;
    private T data;

    public Res(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Res ok(T data) {
        return new Res<T>(200, "success", data);
    }

    public static <T> Res error(String message) {
        return new Res<T>(500, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
