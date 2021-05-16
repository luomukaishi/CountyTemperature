package zy.com.server;

import com.google.gson.Gson;
import com.zy.countytemperature.CountyTemperatureApplication;
import com.zy.countytemperature.common.base.CountyTemperatureException;
import com.zy.countytemperature.common.base.CustomException;
import com.zy.countytemperature.service.CountyTemperatureService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author 张雨
 * @date 2021/5/16 15:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CountyTemperatureApplication.class)
public class ServiceImplTest {
    @Resource
    CountyTemperatureService countyTemperatureService;

    @Resource
    RestTemplate restTemplate;

    Gson gson = new Gson();

    @Test
    public void getProvince() {
        HashMap province = null;
        try {
            province = countyTemperatureService.getProvince();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(province.toString());
    }

    @Test
    public void getCity() {
        HashMap city = null;
        try {
            city = countyTemperatureService.getCity(countyTemperatureService.getProvince(),"江苏");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(city.toString());
    }

    @Test
    public void getCounty() {
        HashMap city = null;
        try {
            city = countyTemperatureService.getCounty(countyTemperatureService.getCity(countyTemperatureService.getProvince(),"江苏"),"苏州");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(city.toString());
    }

    @Test
    public void getCountyId() {
        String CountyId = null;
        try {
            CountyId = countyTemperatureService.getCountyId(countyTemperatureService.getCounty(countyTemperatureService.getCity(countyTemperatureService.getProvince(),"江苏"),"苏州"),"苏州");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(CountyId);
    }

    @Test
    public void getTemperature() {
        Optional<Integer> temperature = null;
        try {
            temperature = countyTemperatureService.getTemperature("江苏", "苏州", "苏州");
        } catch (CountyTemperatureException e) {
            System.out.println(e.getMsg());
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(temperature);
    }

    @Test
    public void temperature() throws Exception {
        HashMap<String, String> hashMap = gson.fromJson(restTemplate.getForObject("http://www.weather.com.cn/data/city3jdata/china.html", String.class), HashMap.class);
        Assert.assertEquals(countyTemperatureService.getProvince(),hashMap);
    }
}
