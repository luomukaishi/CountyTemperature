package com.zy.countytemperature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author 张雨
 * @date 2021/5/11 22:31
 */
@EnableRetry
@SpringBootApplication
public class CountyTemperatureApplication {
    public static void main(String[] args) {
        SpringApplication.run(CountyTemperatureApplication.class, args);
    }
}
