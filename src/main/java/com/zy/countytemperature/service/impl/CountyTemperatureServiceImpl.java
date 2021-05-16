package com.zy.countytemperature.service.impl;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.zy.countytemperature.common.base.CountyTemperatureEnum;
import com.zy.countytemperature.common.base.CountyTemperatureException;
import com.zy.countytemperature.common.base.CustomException;
import com.zy.countytemperature.service.CountyTemperatureService;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author 张雨
 * @date 2021/5/11 22:35
 */
@Service
public class CountyTemperatureServiceImpl implements CountyTemperatureService {

    @Resource
    RestTemplate restTemplate;

    Gson gson = new Gson();

    @Override
    @Retryable(value = CustomException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 0))
    public Optional<Integer> getTemperature(String province, String city, String county) throws CountyTemperatureException, CustomException {
        String countyId;
        synchronized (this) {
            countyId = getCountyId(getCounty(getCity(getProvince(), province), city), county);
        }
        if (countyId == null) {
            throw new CountyTemperatureException(CountyTemperatureEnum.COUNTY_IS_NULL.getId()
                    , CountyTemperatureEnum.COUNTY_IS_NULL.getMsg());
        }
        String forObject;
        try {
            forObject = restTemplate.getForObject("http://www.weather.com.cn/data/sk/" + countyId + ".html", String.class);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        HashMap<String, LinkedTreeMap<String, String>> hashMap = gson.fromJson(forObject, HashMap.class);
        LinkedTreeMap<String, String> weatherInfo = hashMap.get("weatherinfo");
        String temp = weatherInfo.get("temp");
        int idx = temp.lastIndexOf(".");
        String strNum = temp.substring(0, idx);
        int num = Integer.parseInt(strNum);
        return Optional.of(num);
    }

    @Override
    public HashMap<String, String> getProvince() throws CustomException {
        String forObject;
        try {
            forObject = restTemplate.getForObject("http://www.weather.com.cn/data/city3jdata/china.html", String.class);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return gson.fromJson(forObject, HashMap.class);
    }

    @Override
    public HashMap<String, String> getCity(HashMap<String, String> provinceMap, String province) throws CountyTemperatureException, CustomException {
        String provinceId = getKey(getProvince(), province);
        if (provinceId == null) {
            throw new CountyTemperatureException(CountyTemperatureEnum.PROVINCE_IS_NULL.getId()
                    , CountyTemperatureEnum.PROVINCE_IS_NULL.getMsg());
        }
        String forObject;
        try {
            forObject = restTemplate.getForObject("http://www.weather.com.cn/data/city3jdata/provshi/" + provinceId + ".html ", String.class);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("CountyId", provinceId);
        hashMap.put("City", forObject);
        return hashMap;
    }

    @Override
    public HashMap<String, String> getCounty(HashMap<String, String> cityMap, String city) throws CountyTemperatureException, CustomException {
        String countyId = getKey(gson.fromJson(cityMap.get("City"), HashMap.class), city);
        if (countyId == null) {
            throw new CountyTemperatureException(CountyTemperatureEnum.CITY_IS_NULL.getId()
                    , CountyTemperatureEnum.CITY_IS_NULL.getMsg());
        }
        String cityId = cityMap.get("CountyId") + countyId;
        String forObject;
        try {
            forObject = restTemplate.getForObject("http://www.weather.com.cn/data/city3jdata/station/" + cityId + ".html ", String.class);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        cityMap.put("CountyId", cityId);
        cityMap.put("City", forObject);
        return cityMap;
    }

    @Override
    public String getCountyId(HashMap<String, String> countyMap, String county) throws CountyTemperatureException {
        String cityId = getKey(gson.fromJson(countyMap.get("City"), HashMap.class), county);
        if (cityId == null) {
            throw new CountyTemperatureException(CountyTemperatureEnum.COUNTY_IS_NULL.getId()
                    , CountyTemperatureEnum.COUNTY_IS_NULL.getMsg());
        }
        return countyMap.get("CountyId") + cityId;
    }

    private static String getKey(HashMap<String, String> map, String value) {
        String key = null;
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                key = entry.getKey();
            }
        }
        return key;
    }
}
