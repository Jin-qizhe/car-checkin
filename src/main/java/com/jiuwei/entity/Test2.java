package com.jiuwei.entity;

import com.jiuwei.common.PicsUpload;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("/img/00ed8559-ba45-490e-85e0-311b3f935963.png");
        list.add("/img/00ed8559-ba45-490e-85e0-311b3f935963.png");
        list.add("/img/00ed8559-ba45-490e-85e0-311b3f935963.png");
        //char separator
        //String s=listToString(list,separator);
        //String url= list.toArray().toString();
        String url = String.join(",", list);
        System.out.println(url);

        String[] strings=new String[8];
        for(int i=0;i<strings.length;i++){
            strings[i]="北京";
        }
        System.out.println(strings.length);
    }
}
