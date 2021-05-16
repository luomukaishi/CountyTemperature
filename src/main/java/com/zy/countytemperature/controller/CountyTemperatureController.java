package com.zy.countytemperature.controller;

import com.zy.countytemperature.common.base.CountyTemperatureException;
import com.zy.countytemperature.common.base.Response;
import com.zy.countytemperature.service.CountyTemperatureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 张雨
 * @date 2021/5/11 22:18
 */
@RestController
public class CountyTemperatureController {
    @Resource
    CountyTemperatureService countyTemperatureService;

    @PostMapping
    public Response getTemperature(String province, String city, String county) {
        Optional<Integer> temperature;
        try {
            temperature = countyTemperatureService.getTemperature(province, city, county);
        } catch (CountyTemperatureException e) {
            return Response.setNO(e.getMsg());
        } catch (Exception e) {
            return Response.setNO();
        }
        return Response.setOK(temperature);
    }
}
