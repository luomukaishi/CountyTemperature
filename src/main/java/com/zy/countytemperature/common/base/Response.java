package com.zy.countytemperature.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 张雨
 * @date 2021/5/16 18:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int code;
    private String msg;
    private Object data;

    public static Response setOK(){
        return new Response(200,"ok",null);
    }

    public static Response setOK(Object date){
        return new Response(200,"ok",date);
    }

    public static Response setNO(){
        return new Response(500,"no",null);
    }

    public static Response setNO(String msg){
        return new Response(500,msg,null);
    }
}
