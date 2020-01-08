package com.learn.util;

import java.util.regex.Pattern;

public class DataUtil {
    private static final Pattern ONLY_LETTER_OR_NUMBER = Pattern.compile("^[a-z0-9A-Z]+$");
    private static final Pattern CHINESE_PATTERN = Pattern.compile("^[\u4e00-\u9fa5]+$");
    private static final Pattern SPECIAL_SYMBOL = Pattern.compile("[!$^&*+=|{}';'\",<>/?~！#￥%……&*——|{}【】‘；：”“'。，、？]");

    public static boolean isOnlyLetterOrNumber(String str){
        return ONLY_LETTER_OR_NUMBER.matcher(str).matches();
    }

    public static boolean isChineseChar(String str) {
        return CHINESE_PATTERN.matcher(str).matches();
    }

    /**
     * 判断是否含有特殊符号
     * @param str 输入字符串
     * @return boolean
     */
    public static boolean hasSymbol(String str) {
        return SPECIAL_SYMBOL.matcher(str).find();

    }

    public static String convert(String str){
        return str.toLowerCase();
    }
}