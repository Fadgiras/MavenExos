package com.fadgiras.exos.enums;

public enum ErrorCodeEnum {
    SE001E("Failed to load file"),
    SE001D("Failed to load file"),
    SE002("The resource couldn't be loaded"),
    SE003("Failed to retrieve content-type"),
    SE004("Failed to get filename"),
    SE005("File extension not authorized!"),
    SE101("Failed to store empty file"),
    SE102("Failed to store file"),
    SE103("Failed to delete file"),
    SE104("Resource does not exist");

    private String msg;

    ErrorCodeEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
};