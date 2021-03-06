package com.example.demo;

import com.example.demo.common.JavaScriptUtil;
import com.example.demo.entity.MapJ;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liuyunkai
 * @Date 2019/12/3 0003
 * @Description
 */
public class JavaScriptTest {


    /**
     * @param args
     */
    public static void main(String[] args) {


        String sbt = "(B+D-(A-C)*A)/F";
        List<MapJ> all = new ArrayList<MapJ>();
        all.add(new MapJ("A","2"));
        all.add(new MapJ("B","3"));
        all.add(new MapJ("C","4"));
        all.add(new MapJ("D","5"));
        all.add(new MapJ("F","24"));
        Double d = JavaScriptUtil.getMathValue(all, sbt);
        if(d==null){
            System.out.println("                 无法计算这个表达式");
        }else{
            System.out.println(d*100+"%");
        }
    }
}
