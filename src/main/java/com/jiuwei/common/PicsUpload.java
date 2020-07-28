package com.jiuwei.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PicsUpload {
    public static List<String> uploadFile(MultipartFile[] pic) {
        List<String> pics = new ArrayList<String>();
        try {
            for (int i = 0; i < pic.length; i++) {
                MultipartFile picture = pic[i];
                BufferedImage img = ImageIO
                        .read(picture.getInputStream());
                //1 拿到上传的数据对象,解析后缀是否合法
                //获取文件名称,原名
                String oldName = picture.getOriginalFilename();
                //System.out.println(oldName);
                //****.jpg 截取后缀.jpg
                String extName = oldName
                        .substring(oldName.lastIndexOf("."));
                String dir = "/" + "img" + "/";
                String path = "D:\\picture" + dir;//相对的是当前项目根目录
                //File生成多级目录
                File file = new File(path);
                //判断存在,不存在才创建多级目录
                if (!file.exists()) {
                    file.mkdirs();
                }
                String url = dir;
                //5 重命名文件,每次上传都生成一个唯一的名称UUID
                String newName = UUIDUtil.getUUID() + extName;
                //6 将文件数据,按照path+newName存储到磁盘空间
                picture.transferTo(new File(path + newName));
                //7 将数据封装到result返回给jax的回调函数
                pics.add(url + newName);

            }
            //System.out.println("pics"+pics);
            return pics;
        } catch (Exception e) {
            e.printStackTrace();
            return pics;
        }

    }
}
