package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @Author liuyunkai
 * @Date 2019/12/3 0003
 * @Description
 */
@SpringBootTest
public class numberFormat {

    @Test
    public void dataFormat() {
        double d = 756.2345566;

        //方法一：最简便的方法，调用DecimalFormat类
        DecimalFormat df = new DecimalFormat(".00");
        System.out.println(df.format(d));

        //方法二：直接通过String类的format函数实现
        System.out.println(String.format("%.2f", d));

        //方法三：通过BigDecimal类实现
        BigDecimal bg = new BigDecimal(d);
        double d3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(d3);

        //方法四：通过NumberFormat类实现
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(d));

    }

    /**
     * 四舍六入，五成双
     * @param value 需要修约的数字
     * @param digit 保留的小数位
     * @return
     */
    @Test
    public void sciCal(){

        double value = 9.8259;

        int digit = 2;

        String result = "-999";
        try {
            double ratio = Math.pow(10, digit);
            double _num = value * ratio;
            double mod = _num % 1;
            double integer = Math.floor(_num);
            double returnNum;
            if(mod > 0.5){
                returnNum=(integer + 1) / ratio;
            }else if(mod < 0.5){
                returnNum=integer / ratio;
            }else{
                returnNum=(integer % 2 == 0 ? integer : integer + 1) / ratio;
            }
            BigDecimal bg = new BigDecimal(returnNum);
            result = bg.setScale((int)digit, BigDecimal.ROUND_HALF_UP).toString();
        } catch (RuntimeException e) {
            throw e;
        }

        System.out.println(result);





    }

}
