package com.fadgiras.exos.enums;

@SuppressWarnings("unused")//To get rid of stupid warning
public enum ErrorCodeEnum {
    SE001E("Failed to load file"),
    SE001D("Failed to load file"),
    SE002("The resource couldn't be loaded"),
    SE003("Failed to retrieve content-type"),
    SE004("Failed to get filename"),
    SE101("Failed to store empty file"),
    SE102("Failed to store file"),
    SE103("Failed to delete file"),
    SE104("Resource does not exist");

    private String str;

    ErrorCodeEnum(String str) {
        this.str = str;
    }
};