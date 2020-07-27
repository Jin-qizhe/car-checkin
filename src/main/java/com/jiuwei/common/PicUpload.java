package com.jiuwei.common;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PicUpload {
    public static String uploadFile(MultipartFile pic){
        try{
            BufferedImage img = ImageIO
                    .read(pic.getInputStream());
            //1 拿到上传的数据对象,解析后缀是否合法
            //获取文件名称,原名
            String oldName=pic.getOriginalFilename();
            //****.jpg 截取后缀.jpg
            String extName=oldName
                    .substring(oldName.lastIndexOf("."));
            //定义合法范围 符合jpg png gif结尾都是合法
            Boolean able=extName.matches("^.(jpg|png|gif)$");
            //处理不和法!able
            if(!able){//不合法,返回上传失败error=1
                //result.setError(1);
                //return result;
            }
            //2 合法的数据开始处理存储,生成url的逻辑,封装result 高和宽
            //文件夹结构计算逻辑,交给UploadUtil完成,根据文件原名,
            //生成通过一个结构的upload多级文件夹
            String dir="/"+"img"+"/";
            //3 生成磁盘路径,指定upload之上的路径,绝对路径/相对路径
            //绝对路径;String path="F://emSoftware/workspace/1812-SSM-EASYMALL/src/main/webapp"+dir;
            String path="D:\\picture"+dir;//相对的是当前项目根目录
            //File生成多级目录
            File file=new File(path);
            //判断存在,不存在才创建多级目录
            if(!file.exists()){
                file.mkdirs();}
            //4 生成url地址 http://image.jt.com/+dir
            //String url="http://192.168.0.125/"+dir;
            String url=dir;
            //5 重命名文件,每次上传都生成一个唯一的名称UUID
            String newName=UUIDUtil.getUUID()+extName;
            //6 将文件数据,按照path+newName存储到磁盘空间
            pic.transferTo(new File(path+newName));
            //7 将数据封装到result返回给jax的回调函数
            return url+newName;
        }catch(Exception e){
            e.printStackTrace();
            return "错误!";
        }

    }
}
