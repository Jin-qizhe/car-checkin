package com.jiuwei.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;
import com.jiuwei.common.PicUploadResult;
import com.jiuwei.common.UUIDUtil;
import com.jiuwei.common.UploadUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("pic")
public class PicUploadController {
	@RequestMapping("uploadImg")
	public PicUploadResult uploadFile(MultipartFile pic){
		PicUploadResult result=new PicUploadResult();
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
			result.setUrl(url+newName);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return result;
		}


	}
	@RequestMapping("uploadImgs")
	public List<String> uploadFiles(List<MultipartFile> pic){
		List<String> pics=new ArrayList<String>();
		try{
			for(int  i=0;i<pic.size();i++){
				MultipartFile picture=pic.get(0);
				BufferedImage img = ImageIO
						.read(picture.getInputStream());
				//1 拿到上传的数据对象,解析后缀是否合法
				//获取文件名称,原名
				String oldName=picture.getOriginalFilename();
				//****.jpg 截取后缀.jpg
				String extName=oldName
						.substring(oldName.lastIndexOf("."));
				String dir="/"+"img"+"/";
				String path="D:\\picture"+dir;//相对的是当前项目根目录
				//File生成多级目录
				File file=new File(path);
				//判断存在,不存在才创建多级目录
				if(!file.exists()){
					file.mkdirs();}
				String url=dir;
				//5 重命名文件,每次上传都生成一个唯一的名称UUID
				String newName=UUIDUtil.getUUID()+extName;
				//6 将文件数据,按照path+newName存储到磁盘空间
				picture.transferTo(new File(path+newName));
				//7 将数据封装到result返回给jax的回调函数
				System.out.println(url+newName);
				pics.add(url+newName);


			}
			System.out.println(pics);
			return pics;
		}catch(Exception e){
			e.printStackTrace();
			return pics;
		}

	}
}
