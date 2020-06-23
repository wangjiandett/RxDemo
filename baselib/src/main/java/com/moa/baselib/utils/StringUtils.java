package com.moa.baselib.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author wangjian
 */
public class StringUtils {

    /**
     * 金额验证
     *
     * @param input 输入的字符串
     * @return 是否是正确的金额数字
     */
    public static boolean isPrice(String input) {
        // 判断小数点后2位的数字的正则表达式
        Pattern pattern = Pattern.compile("(\\d+(\\.\\d{1,2})?)");
        Matcher match = pattern.matcher(input);
        return match.matches();
    }

    /**
     * list拼接成字符串
     *
     * @param list      数据
     * @param separator 分隔符
     * @return 字符串
     */
    public static String listToString(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }

}
