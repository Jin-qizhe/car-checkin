package com.jiuwei.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {
    public static void main(String[] args) throws ParseException {
        String t2="2020-07-27 10:13:09";
        String t1="2020-07-27 16:05:06";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse(t1);
        Date date2 = format.parse(t2);

        //long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
        //long hours = (date1.getTime() - date2.getTime()) / (60 * 60 * 1000);
        //long minutes = (date1.getTime() - date2.getTime()) / (60 * 1000);
        long seconds = (date1.getTime() - date2.getTime()) / (1000);

        long h = seconds/3600;
        long m = (seconds%3600)/60;
        long s = (seconds%3600)%60;
        //System.out.println("本次使用"+hours+"时"+(minutes-60*hours)+"分"+(seconds-60*minutes)+"秒");
        System.out.println(seconds);
        System.out.println("s:"+s);
        System.out.println("m:"+m);
        System.out.println("h:"+h);
    }
}
