package com.example.demo.common.enums;

/**
 * @Author liuyunkai
 * @Date 2019/12/4 0004
 * @Description 将数字转为10乘以N次方格式用的后缀
 */
public enum SignificantDigit {
    //
    one(1,"×10¹"),
    two(2,"×10²"),
    three(3,"×10³"),
    four(4,"×10⁴"),
    five(5,"×10⁵"),
    six(6,"×10⁶"),
    seven(7,"×10⁷"),
    eight(8,"×10⁸"),
    nine(9,"×10⁹"),

    negativeOne(-1,"×10⁻¹"),
    negativeTwo(-2,"×10⁻²"),
    negativeThree(-3,"×10⁻³"),
    negativeFour(-4,"×10⁻⁴"),
    negativeFive(-5,"×10⁻⁵"),
    negativeSix(-6,"×10⁻⁶"),
    negativeSeven(-7,"×10⁻⁷"),
    negativeEight(-8,"×10⁻⁸"),
    negativeNine(-9,"×10⁻⁹")
    ;

    private int digit;
    private String suffix;


    SignificantDigit(int digit, String suffix) {
        this.digit = digit;
        this.suffix = suffix;
    }

    public int getDigit() {
        return digit;
    }

    public String getSuffix() {
        return suffix;
    }

    public static String getSuffixByDigit(int digit){
        for (SignificantDigit significantDigit : SignificantDigit.values()) {
            if (significantDigit.digit == digit) {
                return significantDigit.getSuffix();
            }
        }
        return "";
    }


}
