package com.zy.countytemperature.service;

import com.zy.countytemperature.common.base.CountyTemperatureException;
import com.zy.countytemperature.common.base.CustomException;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author 张雨
 * @date 2021/5/11 22:34
 */
public interface CountyTemperatureService {
    /**
     * 获取温度
     *
     * @param province 省份
     * @param city 城市
     * @param county 县
     * @return 温度
     */
    Optional<Integer> getTemperature(String province, String city, String county) throws CountyTemperatureException, CustomException;

    /**
     * 获取省份对应的map
     *
     * @return 省份的HashMap
     */
    HashMap<String, String> getProvince() throws CountyTemperatureException, CustomException;

    /**
     * 获取城市的map
     *
     * @param provinceMap 省份的HashMap
     * @param province 省名
     * @return 城市的map
     */
    HashMap<String, String> getCity(HashMap<String, String> provinceMap,String province) throws CountyTemperatureException, CustomException;

    /**
     * 获取县的map
     *
     * @param cityMap 城市的map
     * @param city 城市名
     * @return 县的map
     */
    HashMap<String, String> getCounty(HashMap<String, String> cityMap,String city) throws CountyTemperatureException, CustomException;

    /**
     * 获取县的Id
     *
     * @param countyMap 县的map
     * @param county 县名
     * @return 县的Id
     */
    String getCountyId(HashMap<String, String> countyMap,String county) throws CountyTemperatureException, CustomException;
}
