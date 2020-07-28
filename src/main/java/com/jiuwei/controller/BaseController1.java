package com.jiuwei.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
public class BaseController1 {

    @PostMapping("base641")
    public String base641(String imgStr) throws Exception {
        if (imgStr == null) // 图像数据为空
            return "";
        BASE64Decoder decoder = new BASE64Decoder();

        // Base64解码,对字节数组字符串进行Base64解码并生成图片
        imgStr = imgStr.replaceAll(" ", "+");
        String use=imgStr.substring(imgStr.indexOf(",")+1);
        System.out.println(imgStr);
        //byte[] b = decoder.decodeBuffer(imgStr.replace("data:image/png;base64,", ""));
        byte[] b = decoder.decodeBuffer(use);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        String imgName = getRandomFileName()+".jpg";
        String dbUrl = "";
        // 生成jpeg图片D:\test\attendance\src\main\webapp\assets\images\leave
        String imgFilePath = "D:\\picture\\img\\"+imgName;//新生成的图片
        //String imgFilePath2 = "D:\\test\\attendance\\target\\assets\\images\\leave\\"+imgName;
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(b);
        out.flush();
        out.close();
        String dir = "/" + "img" + "/";
        String path = "D:\\picture" + dir;
        dbUrl = path+imgName;
        return dbUrl;
    }
    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return rannum + str;// 当前时间
    }
}
