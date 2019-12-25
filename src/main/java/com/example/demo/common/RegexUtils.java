package com.example.demo.common;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Administrator
 */
public class RegexUtils {

    /**
     * 正则常量
     */
    public static final String PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    public static final String EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
    public static final String ID_CARD = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
    public static final String MOBILE = "(\\+\\d+)?1\\d{10}$";
    public static final String PHONE = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";

    /**
     * 正负整数
     */
    public static final String DIGIT = "\\-?[1-9]\\d*";
    public static final String DIGIT_REAL = "\\-?[0-9]+";
    public static final String DECIMALS = "\\-?[1-9]\\d+(\\.\\d+)?";
    public static final String CHARACTER = "^[A-Za-z]+$";
    public static final String BLANK_SPACE = "\\s+";
    public static final String CHINESE = "^[\u4E00-\u9FA5]+$";
    public static final String BIRTHDAY = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
    public static final String URL = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
    public static final String POST_CODE = "[1-9]\\d{5}";
    public static final String IP_ADDRESS = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
    //整数
    public static final String INTEGER = "([+|-])?[1-9]{1}[0-9]*";
    public static final String NUMBER = "^([+|-])?([1-9]\\d*\\.\\d*|0\\.\\d+|[1-9]\\d*|0)$";
    public static final String SCIENTIFICNUMBER = "^[+|-]?[1-9]{1}(\\.[0-9]+)?E[+|-][0-9]+$";
    public static final String ENGLISH = "^[a-zA-Z]+$";

    /**
     * 端口的正则表达式
     */
    public static final String PORT = "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";

    /**
     * 日期的正则表达式, 验证 1900-2999 年
     */
    public static final String DATE = "(19[0-9]{2}|2[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[01])";
    public static final String DATECHINA = "(19[0-9]{2}|2[0-9]{3})年(0[1-9]|1[0-2])月(0[1-9]|[1-2][0-9]|3[01])日";
    public static final String DATEPOINT = "(19[0-9]{2}|2[0-9]{3}).(0[1-9]|1[0-2]).(0[1-9]|[1-2][0-9]|3[01])";
    public static final String DATEXG = "(19[0-9]{2}|2[0-9]{3})/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[01])";

    /**
     * 年份 1900-2099
     */
    public static final String NORMALYEAR = "19[0-9]{2}|20[0-9]{2}";

    /**
     * <p>
     * 验证 password 是否为字母数字混合 6 - 20 位长度密码
     * </p>
     *
     * @param password 密码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPassword(String password) {
        return RegexUtils.matches(RegexUtils.PASSWORD, password);
    }

    /**
     * 验证是否是合法的端口
     *
     * @param port 端口
     * @return 是否合法, true: 合法, false: 不合法
     */
    public static boolean isValidPort(CharSequence port) {
        return matches(PORT, port);
    }

    /**
     * <p>
     * 验证Email
     * </p>
     *
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isEmail(String email) {
        return RegexUtils.matches(RegexUtils.EMAIL, email);
    }

    /**
     * <p>
     * 验证身份证号码
     * </p>
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIdCard(String idCard) {
        return RegexUtils.matches(RegexUtils.ID_CARD, idCard);
    }

    /**
     * <p>
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * </p>
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *               <p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMobile(String mobile) {
        return RegexUtils.matches(RegexUtils.MOBILE, mobile);
    }

    /**
     * <p>
     * 验证固定电话号码
     * </p>
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPhone(String phone) {
        return RegexUtils.matches(RegexUtils.PHONE, phone);
    }

    /**
     * <p>
     * 验证整数（正整数和负整数）
     * </p>
     *
     * @param digit 一位或多位0-9之间的整数(假的)
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDigit(String digit) {
        return RegexUtils.matches(RegexUtils.DIGIT, digit);
    }

    /**
     * <p>
     * 验证整数（正整数和负整数）
     * </p>
     *
     * @param digit 一位或多位0-9之间的整数(真的)
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDigitReal(String digit) {
        return RegexUtils.matches(RegexUtils.DIGIT_REAL, digit);
    }

    /**
     * <p>
     * 验证整数和浮点数（正负整数和正负浮点数）
     * </p>
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDecimals(String decimals) {
        return RegexUtils.matches(RegexUtils.DECIMALS, decimals);
    }

    /**
     * <p>
     * 验证空白字符
     * </p>
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isBlankSpace(String blankSpace) {
        return RegexUtils.matches(RegexUtils.BLANK_SPACE, blankSpace);
    }

    /**
     * <p>
     * 验证中文
     * </p>
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isChinese(String chinese) {
        return RegexUtils.matches(RegexUtils.CHINESE, chinese);
    }

    /**
     * 根据正则表达式判断字符是否为汉字
     * 字符串中包含汉字时返回true
     *
     * @param value 字符串
     * @return 验证是否包含汉字，成功返回true，验证失败返回false
     */
    public static boolean hasChinese(String value) {
        // 汉字的Unicode取值范围
        String regex = "[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(value);
        return match.find();
    }

    /**
     * <p>
     * 验证日期（年月日）
     * </p>
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isBirthday(String birthday) {
        return RegexUtils.matches(RegexUtils.BIRTHDAY, birthday);
    }

    /**
     * <p>
     * 验证URL地址
     * </p>
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isURL(String url) {
        return RegexUtils.matches(RegexUtils.URL, url);
    }

    /**
     * <p>
     * 获取网址 URL 的一级域
     * </p>
     *
     * @param url
     * @return
     */
    public static String getSubDomain(String url) {
        Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        return matcher.group();
    }

    /**
     * <p>
     * 获取网址 URL 域名
     * </p>
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        return matcher.group();
    }

    /**
     * <p>
     * 匹配中国邮政编码
     * </p>
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPostcode(String postcode) {
        return RegexUtils.matches(RegexUtils.POST_CODE, postcode);
    }

    /**
     * <p>
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * </p>
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIpAddress(String ipAddress) {
        return RegexUtils.matches(RegexUtils.IP_ADDRESS, ipAddress);
    }

    /**
     * <p>
     * 匹配 input 字符串是否全为字母
     * </p>
     *
     * @param input 字符串
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isCharacter(String input) {
        return RegexUtils.matches(RegexUtils.CHARACTER, input);
    }

    /**
     * <p>
     * 正则匹配
     * </p>
     *
     * @param regex 正则
     * @param input 字符串
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean matches(String regex, CharSequence input) {
        if (null == input) {
            return false;
        }
        return Pattern.matches(regex, input);
    }

    /**
     * <p>
     * 正则匹配是否数字
     * </p>
     */
    public static boolean isNumber(String number) {
        return RegexUtils.matches(RegexUtils.NUMBER, number);
    }

    /**
     * <p>
     * 正则匹配是否数字
     * </p>
     */
    public static boolean isInteger(String number) {
        return RegexUtils.matches(RegexUtils.INTEGER, number);
    }

    /**
     * <p>
     * 正则匹配是否是科学计数法
     * </p>
     *
     * @param number
     * @return
     */
    public static boolean isScientificNumber(String number) {
        return RegexUtils.matches(RegexUtils.SCIENTIFICNUMBER, number);
    }

    /**
     * <p>
     * 正则匹配是纯英文
     * </p>
     */
    public static boolean isEnglish(String english) {
        return RegexUtils.matches(RegexUtils.ENGLISH, english);
    }

    /**
     * <p>
     * 正则获取字符串中正常年份
     * </p>
     */
    public static List<String> getNormalYear(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(NORMALYEAR);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    public static boolean isValidDate(String str) {
        return matches(DATE, str);
    }

    public static boolean isValidDateChina(String str) {
        return matches(DATECHINA, str);
    }

    public static boolean isValidDatePoint(String str) {
        return matches(DATEPOINT, str);
    }

    public static boolean isValidDateXg(String str) {
        return matches(DATEXG, str);
    }

    public static List<BigDecimal> getBigDecimalFromString(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
//        String str = "abcd123和345.56jia567.23.23jian345and23or345.56";
        //先判断有没有整数，如果没有整数那就肯定就没有小数
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(str);
        String result = "";
        List<BigDecimal> resultList = null;
        if (m.find()) {
            resultList = new ArrayList<>();
            Map<Integer, String> map = new TreeMap();
            Pattern p2 = Pattern.compile("(\\d+\\.\\d+)");
            m = p2.matcher(str);
            //遍历小数部分
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = str.indexOf(result);
                String s = str.substring(i, i + result.length());
                map.put(i, s);
                //排除小数的整数部分和另一个整数相同的情况下，寻找整数位置出现错误的可能，还有就是寻找重复的小数
                // 例子中是排除第二个345.56时第一个345.56产生干扰和寻找整数345的位置时，前面的小数345.56会干扰
                str = str.substring(0, i) + str.substring(i + result.length());
            }
            //遍历整数
            Pattern p3 = Pattern.compile("(\\d+)");
            m = p3.matcher(str);
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = str.indexOf(result);
                //排除jia567.23.23在第一轮过滤之后留下来的jia.23对整数23产生干扰
                if (i > 0 && String.valueOf(str.charAt(i - 1)).equals(".")) {
                    //将这个字符串删除
                    str = str.substring(0, i - 1) + str.substring(i + result.length());
                    continue;
                }
                String s = str.substring(i, i + result.length());
                map.put(i, s);
                str = str.substring(0, i) + str.substring(i + result.length());
            }
            for (Map.Entry<Integer, String> e : map.entrySet()) {
                try {
                    BigDecimal bigDecimal = new BigDecimal(e.getValue());
                    resultList.add(bigDecimal);
                } catch (Exception ignored) {
                }
            }
        }
        return resultList;
    }


}
