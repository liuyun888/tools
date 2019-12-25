package com.example.demo.controller;

import com.example.demo.common.JavaScriptUtil;
import com.example.demo.common.RegexUtils;
import com.example.demo.service.ScaleFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liuyunkai
 * @Date 2019/12/3 0003
 * @Description
 */
@RestController
@RequestMapping("/formula")
public class FormulaController {

    @Autowired
    private ScaleFormatService scaleFormatService;

    /**
     * 四舍六入五成双的规则：
     *
     * 1. 被修约的数字小于5时，该数字舍去；
     *
     * 2. 被修约的数字大于5时，则进位；
     *
     * 3. 被修约的数字等于5时，要看5前面的数字，若是奇数则进位，若是偶数则将5舍掉，即修约后末尾数字都成为偶数；若5的后面还有不为“0”的任何数，则此时无论5的前面是奇数还是偶数，均应进位。
     */

    /**
     * 首先四舍六入五成双计算，然后根据修约规则修约
     * @param value  要进行修约的数据
     * @param request 修约规则
     * @return
     */
    @GetMapping("/scaleFormat")
    public String scaleFormat(String value, String request){

        if(!RegexUtils.isNumber(value) || !JavaScriptUtil.isJson(request)){
            return value;
        }

        return scaleFormatService.scaleFormat(value,request);
    }



}
