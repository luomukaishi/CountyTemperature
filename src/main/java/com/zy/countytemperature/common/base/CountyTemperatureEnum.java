package com.zy.countytemperature.common.base;

/**
 * @author 张雨
 * @date 2021/5/16 18:37
 */
public enum  CountyTemperatureEnum {
    PROVINCE_IS_NULL(400001,"provinceId is null"),
    CITY_IS_NULL(400002,"cityId is null"),
    COUNTY_IS_NULL(400003,"countyId is null");

    private int id;
    private String msg;

    CountyTemperatureEnum(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}
