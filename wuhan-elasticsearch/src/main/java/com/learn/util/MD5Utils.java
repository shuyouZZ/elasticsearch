package com.learn.util;

import org.springframework.util.DigestUtils;

import java.util.Arrays;

/**
 * @author dshuyou
 * @date 2019/12/11 16:14
 */
public class MD5Utils {
    //盐，用于混交md5
    private static final String slat = "123456";

    public static String getMD5(String... str) {
        String base = Arrays.toString(str) +"/"+slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public static void main(String[] args){
        String[] str = new String[]{"a","s","d"};
        System.out.println(MD5Utils.getMD5("a","s","d"));
        System.out.println(MD5Utils.getMD5(str));
    }
}