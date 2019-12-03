package com.example.demo.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.example.demo.common.JavaScriptUtil;
import com.example.demo.entity.MapJ;
import com.example.demo.service.ScaleFormatService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

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

        String result = "0";

        //解析 request，获取修约规则
        Map<String, Integer> requestMap = initRequest(value, request);

        //四舍六入五成双 计算
        result = sciCal(Double.parseDouble(value), requestMap.get("digit"));

        //根据修约规则，修约


        return result;
    }

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

    private String sciCal(Double value, Integer digit) {

        String result = "0";
        try {
            double ratio = Math.pow(10, digit);
            double _num = value * ratio;
            double mod = _num % 1;
            double integer = Math.floor(_num);
            double returnNum;
            if (mod > 0.5) {
                returnNum = (integer + 1) / ratio;
            } else if (mod < 0.5) {
                returnNum = integer / ratio;
            } else {
                returnNum = (integer % 2 == 0 ? integer : integer + 1) / ratio;
            }
            BigDecimal bg = new BigDecimal(returnNum);
            result = bg.setScale((int) digit, BigDecimal.ROUND_HALF_UP).toString();
        } catch (RuntimeException e) {
            throw e;
        }

        return result;
    }
}
