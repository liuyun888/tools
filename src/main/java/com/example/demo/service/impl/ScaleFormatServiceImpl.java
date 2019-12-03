package com.example.demo.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.example.demo.common.JavaScriptUtil;
import com.example.demo.entity.MapJ;
import com.example.demo.service.ScaleFormatService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @Author liuyunkai
 * @Date 2019/12/3 0003
 * @Description
 */
@Service
public class ScaleFormatServiceImpl implements ScaleFormatService {


    /**
     * 修约要求 "request" 字段说明如下：
     * numberRange：数值范围，其中 gt >, lt < , ge >=, le <=，eq =; V 为数字占位符
     * type：修约类型，其中 1:小数点，2：有效数字，3：科学计数法的有效数字
     * digit：位数，配合type
     * <p>
     * 例如：
     * {"request": [{"numberRange": "Vgt0.1&&Vlt1000","type": "2","digit":"3"},{"numberRange": "Vle0.1","type": "1","digit":"3"},{"numberRange": "Vge1000","type": "3","digit":"2"}]}
     * 表示：
     * 当小于等于0.1时，保留小数点后三位；当大于0小于1000时，保存有效数字三位；当大等于1000时，改为科学计数法，并保留有效位数2位。
     *
     * @param value
     * @param request
     * @return
     */
    @Override
    public String scaleFormat(String value, String request) {
//        //判断参数是否合规
//        RestAssert.fail(!StringUtils.isNoneBlank(value, request), "参数不正确");
//        RestAssert.fail(!isNumber(value), "value不是有效数字");

        String result;

        //解析 request，获取修约规则
        Map<String, Integer> requestMap = initRequest(value, request);

        //根据修约规则，修约
        result = rounding(value, requestMap);


        return result;
    }

    /**
     * 解析 request，获取修约规则
     * @param value
     * @param request
     * @return
     */
    private Map initRequest(String value, String request) {

        JSONObject modeJson = new JSONObject(request);
//        RestAssert.fail(!modeJson.has("numberRange") || !modeJson.has("type") || !modeJson.has("digit"), "参数不正确");
//        RestAssert.fail(!isNumber(modeJson.get("digit")), "digit不是有效数字");

        JSONArray modeJArray = modeJson.getJSONArray("request");

        Map<String, Integer> requestMap = new HashMap<>(6);
        List<MapJ> all = new ArrayList<MapJ>();
        all.add(new MapJ("V", value));
        all.add(new MapJ("gt", ">"));
        all.add(new MapJ("lt", "<"));
        all.add(new MapJ("ge", ">="));
        all.add(new MapJ("le", "<="));
        all.add(new MapJ("eq", "=="));

        int digit = 2;

        int type = 1;

        for (int i = 0; i < modeJArray.length(); i++) {

            String numberRange = modeJArray.getJSONObject(i).getString("numberRange");
            //调用JS，计算String类型的公式
            if (JavaScriptUtil.isMatch(all, numberRange)) {

                digit = modeJArray.getJSONObject(i).getInt("digit");

                type = modeJArray.getJSONObject(i).getInt("type");

                break;
            }
        }

        requestMap.put("type", type);
        requestMap.put("digit", digit);
        return requestMap;
    }

    /**
     * 根据修约规则，修约
     * @param value
     * @param requestMap
     * @return
     */
    private String rounding(String value, Map<String, Integer> requestMap) {
        String result = "0";

        //type = 2 有效数字位数 todo 魔法值修改
        if (requestMap.get("type") == 2) {
            result = roundToSignificantFigures(Double.parseDouble(value), requestMap.get("digit"));
        }
        //type = 3 科学计数法的有效数字位数
        else if (requestMap.get("type") == 3) {



        }else {
            //四舍六入五成双 计算
            result = sciCal(Double.parseDouble(value), requestMap.get("digit"));

        }

        return result;
    }

    /**
     * 计算有效位数
     * @param value
     * @param digit
     */
    private String roundToSignificantFigures(Double value, Integer digit) {

        if(value == 0) {
            return "0";
        }

        //获取数据位数
        double d = Math.ceil(Math.log10(value < 0 ? -value: value));
        //获取截取位数
        int power = digit - (int) d;

        BigDecimal bg = doCalculate(value, power);


        return bg.toPlainString();

    }

    /**
     * 四舍六入五成双 计算法，同时也是 type = 1 的小数位数计算法
     * @param value 待计算数字
     * @param digit 小数位数
     * @return
     */
    private String sciCal(Double value, Integer digit) {

        String result;
        try {
            BigDecimal bg = doCalculate(value, digit);

            result = bg.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
        } catch (RuntimeException e) {
            throw e;
        }

        return result;
    }

    private BigDecimal doCalculate(Double value, Integer digit) {

        //步距
        double ratio = Math.pow(10, digit);
        //保留部位
        double num = value * ratio;
        //四舍六入五成双 部位
        double mod = num % 1;
        //保留部位 取整
        double integer = Math.floor(num);
        BigDecimal returnNum;
        //todo 魔法数字修改
        if (mod > 0.5) {
            returnNum = (BigDecimal.valueOf(integer).add(BigDecimal.valueOf(1))).divide(BigDecimal.valueOf(ratio)) ;
        } else if (mod < 0.5) {
            returnNum = BigDecimal.valueOf(integer).divide(BigDecimal.valueOf(ratio));
        } else {
            returnNum = (integer % 2 == 0 ? BigDecimal.valueOf(integer) : BigDecimal.valueOf(integer).add(BigDecimal.valueOf(1))).divide(BigDecimal.valueOf(ratio));
        }

        return returnNum;
    }
}
