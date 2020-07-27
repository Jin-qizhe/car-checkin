package com.jiuwei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiuwei.common.SysResult;
import com.jiuwei.entity.CarDaka;
import com.jiuwei.service.CarCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping(value = "/carcheckin", produces = "application/json; charset=UTF-8")
public class CarCheckinConroller {
    @Autowired
    private CarCheckinService carCheckinService;

    //接收code
    @PostMapping("/getcode")
    public SysResult getcode(String code){
        System.out.println("code:"+code);
        String APPID="wx8a4ce30900be71c1";
        String SECRET="643f3039af689f8a01e1bb2f0172e96d";
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
        System.out.println(url);
        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject(url,String.class);
        System.out.println(response);

        /*JSONObject json= JSON.parseObject(response);
        String ACCESS_TOKEN=json.getString("access_token");
        String OPENID=json.getString("openid");
        System.out.println(ACCESS_TOKEN);
        System.out.println(OPENID);
        String url1="https://api.weixin.qq.com/sns/userinfo?access_token="+ACCESS_TOKEN+"&openid="+OPENID+"&lang=zh_CN";
        String response1=restTemplate.getForObject(url1,String.class);
        System.out.println(response1);*/

        return SysResult.build(200, "获取成功!", response);
}

    //借车打卡
    @PostMapping("/borrowcar")
    public SysResult borrowCar(CarDaka carDaka,MultipartFile pic){
        try{
            List<CarDaka> list=carCheckinService.getstate(carDaka);
            //System.out.println("借用"+list.size());
            if (list.size()==0){
                carCheckinService.borrowCar(carDaka,pic);
                return SysResult.ok();
            }else{
                return SysResult.build(201, "上次借用未归还", null);
            }

        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201, "借用打卡失败", null);
        }

    }

    //还车打卡
    @PostMapping("/returncar")
    public SysResult returnCar(CarDaka carDaka,MultipartFile pic){
        try{
            List<CarDaka> list=carCheckinService.getstate(carDaka);
            //System.out.println("归还"+list.size());
            if(list.size()==0){
                return SysResult.build(201, "未查到借用信息", null);
            }else{
                carCheckinService.returnCar(carDaka,pic);
                return SysResult.ok();
            }
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201, "归还打卡失败", null);
        }
    }

    //查看所有使用信息
    @PostMapping("/search")
    public SysResult search(CarDaka carDaka){
        List<CarDaka> list=carCheckinService.search(carDaka);
        if(list.size()>0){
            return SysResult.build(200, "查询成功", list);
        }else{
            return SysResult.build(201, "查询失败", null);
        }
    }

    //查询借车状态
    /*@PostMapping("/getstate")
    public List<CarDaka> getstate(CarDaka carDaka){
        List<CarDaka> list=carCheckinService.getstate(carDaka);
        System.out.println(list.size());
        return list;
    }*/

    //查询某条使用信息
    @PostMapping("/getdetail")
    public SysResult getone(CarDaka carDaka){
        List<CarDaka> list=carCheckinService.getdetail(carDaka);
        if(list.size()>0){
            return SysResult.build(200, "查询成功", list);
        }else{
            return SysResult.build(201, "查询失败", null);
        }
    }

    //查询当前借用状态
    @PostMapping("/getcurrent")
    public SysResult getcurrent(CarDaka carDaka){
        List<CarDaka> list=carCheckinService.getstate(carDaka);
        if(list.size()>0){
            return SysResult.build(200, "正在借用", list);
        }else{
            return SysResult.build(201, "未查找正在借用记录", null);
        }
    }

    /*
    查询某个用户出车次数;总出车时长;最近一次出车时间
     */
    @PostMapping("use")
    public SysResult use(CarDaka carDaka) throws ParseException {
        int num=carCheckinService.getusetimes(carDaka);
        //System.out.println(num);
        if(num>0){
            List<CarDaka> list1=carCheckinService.getalltime(carDaka);
            // long day = 0;
            //long hours = 0;
            // minutes = 0;
            long seconds = 0;
            System.out.println(list1.size());
            for(int i=0;i<list1.size();i++){
                String t1=list1.get(i).getReturnTime();
                String t2=list1.get(i).getBorrowTime();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = format.parse(t1);
                Date date2 = format.parse(t2);

                //day += (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
                //hours += (date1.getTime() - date2.getTime()) / (60 * 60 * 1000);
                //minutes += (date1.getTime() - date2.getTime()) / (60 * 1000);
                seconds += (date1.getTime() - date2.getTime()) / (1000);
            }
            long hour = seconds/3600;
            long minute = (seconds%3600)/60;
            long second = (seconds%3600)%60;
            String usetime;
            //System.out.println(seconds);
            if(seconds>0){
                usetime="一共使用"+hour+"时"+minute+"分"+second+"秒";
            }else{
                usetime="没有完整的借用记录";
            }

            List<CarDaka> list2=carCheckinService.lastusetime(carDaka);
            String lasttime=list2.get(0).getBorrowTime();
            JSONObject object=new JSONObject();
            object.put("num",num);
            object.put("usetime",usetime);
            object.put("lasttime",lasttime);
            return SysResult.build(200, "查询成功", object);
        }else{
            return SysResult.build(201, "未查到我的使用记录", null);
        }
    }
}
