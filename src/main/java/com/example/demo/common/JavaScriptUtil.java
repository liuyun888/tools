package com.example.demo.common;

import com.example.demo.entity.MapJ;
import org.json.JSONObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * @Author liuyunkai
 * @Date 2019/12/3 0003
 * @Description
 */
public class JavaScriptUtil {


    final static ScriptEngineManager factory = new ScriptEngineManager();
    final static ScriptEngine engine = factory.getEngineByName("JavaScript");

    public static Double getMathValue(List<MapJ> map, String option){
        double d = 0;
        try {
            for(int i=0; i<map.size();i++){
                MapJ mapj = map.get(i);
                option = option.replaceAll(mapj.getKey(), mapj.getValue());
            }
            Object o = engine.eval(option);
            d = Double.parseDouble(o.toString());
        } catch (ScriptException e) {
            System.out.println("无法识别表达式");
            return null;
        }
        return d;
    }

    public static Boolean isMatch(List<MapJ> map, String option){
        boolean isMatch = false;
        try {
            for(int i=0; i<map.size();i++){
                MapJ mapj = map.get(i);
                option = option.replaceAll(mapj.getKey(), mapj.getValue());
            }
            Object o = engine.eval(option);
            isMatch = Boolean.parseBoolean(o.toString());
        } catch (ScriptException e) {
            System.out.println("无法识别表达式");
            return null;
        }
        return isMatch;
    }

    public static boolean isJson(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
