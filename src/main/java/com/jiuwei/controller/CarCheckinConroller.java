package com.jiuwei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiuwei.common.SysResult;
import com.jiuwei.entity.Accident;
import com.jiuwei.entity.Car;
import com.jiuwei.entity.CarDaka;
import com.jiuwei.entity.Openid;
import com.jiuwei.service.CarCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public SysResult getcode(String code, HttpServletResponse res) throws IOException {
        System.out.println("code:" + code);
        String APPID = "wx8a4ce30900be71c1";
        String SECRET = "643f3039af689f8a01e1bb2f0172e96d";
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + SECRET + "&code=" + code + "&grant_type=authorization_code";
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = JSON.parseObject(response);
        String OPENID = json.getString("openid");
        System.out.println(OPENID);
        System.out.println(response);
        //res.sendRedirect("http://32499c3263.eicp.vip/pages/index.html?"+OPENID);
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
    public SysResult borrowCar(Openid openid,Car car, CarDaka carDaka, MultipartFile pic) {
        try {
            //System.out.println("controller1:"+pic);
            List<CarDaka> list = carCheckinService.getstate(carDaka);
            //car.setCarState("false");
            //List<Openid> openidList=carCheckinService.getOpenid(openid);
            //String userId=openidList.get(0).getName();
            //carDaka.setUserId(userId);
            //carCheckinService.changeCarstate(car);
            List<Car> carlist=carCheckinService.carstate(car);
            String state=carlist.get(0).getCarState();
            String bukejie="false";
            //System.out.println(state);
            //System.out.println("借用"+list.size());
            if (list.size() == 0) {
                //carCheckinService.borrowCar(car,carDaka, pic);
                //System.out.println(1);
                if(state.equals(bukejie)){
                    //System.out.println(2);
                    return SysResult.build(201, "车已被借用", null);
                }else{
                    //System.out.println(3);
                    List<Openid> openidList=carCheckinService.getOpenid(openid);
                    String userId=openidList.get(0).getName();
                    carDaka.setUserId(userId);
                    carCheckinService.borrowCar(car,carDaka, pic);
                    car.setCarState("false");
                    carCheckinService.changeCarstate(car);
                    return SysResult.ok();
                }
            } else {
                return SysResult.build(201, "上次借用未归还", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "借用打卡失败", null);
        }

    }

    //还车打卡
    @PostMapping("/returncar")
    public SysResult returnCar(Car car,CarDaka carDaka, MultipartFile pic) {
        try {
            List<CarDaka> list = carCheckinService.getstate(carDaka);
            //System.out.println("归还"+list.size());
            car.setCarState("true");
            carCheckinService.changeCarstate(car);
            if (list.size() == 0) {
                return SysResult.build(201, "未查到借用信息", null);
            } else {
                carCheckinService.returnCar(car,carDaka, pic);
                return SysResult.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "归还打卡失败", null);
        }
    }

    //查看所有使用信息
    @PostMapping("/search")
    public SysResult search(CarDaka carDaka) {
        List<CarDaka> list = carCheckinService.search(carDaka);
        if (list.size() > 0) {
            return SysResult.build(200, "查询成功", list);
        } else {
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
    public SysResult getone(CarDaka carDaka) {
        List<CarDaka> list = carCheckinService.getdetail(carDaka);
        if (list.size() > 0) {
            return SysResult.build(200, "查询成功", list);
        } else {
            return SysResult.build(201, "查询失败", null);
        }
    }

    //查询当前借用状态
    @PostMapping("/getcurrent")
    public SysResult getcurrent(CarDaka carDaka) {
        List<CarDaka> list = carCheckinService.getstate(carDaka);
        if (list.size() > 0) {
            return SysResult.build(200, "正在借用", list);
        } else {
            return SysResult.build(201, "未查找正在借用记录", null);
        }
    }

    /*
    查询某个用户出车次数;总出车时长;最近一次出车时间
     */
    @PostMapping("use")
    public SysResult use(Openid openid,CarDaka carDaka) throws ParseException {
        int num = carCheckinService.getusetimes(carDaka);
        //System.out.println(num);
        if (num > 0) {
            List<CarDaka> list1 = carCheckinService.getalltime(carDaka);
            // long day = 0;
            //long hours = 0;
            // minutes = 0;
            long seconds = 0;
            System.out.println(list1.size());
            for (int i = 0; i < list1.size(); i++) {
                String t1 = list1.get(i).getReturnTime();
                String t2 = list1.get(i).getBorrowTime();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = format.parse(t1);
                Date date2 = format.parse(t2);

                //day += (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
                //hours += (date1.getTime() - date2.getTime()) / (60 * 60 * 1000);
                //minutes += (date1.getTime() - date2.getTime()) / (60 * 1000);
                seconds += (date1.getTime() - date2.getTime()) / (1000);
            }
            long hour = seconds / 3600;
            long minute = (seconds % 3600) / 60;
            long second = (seconds % 3600) % 60;
            String usetime;
            //System.out.println(seconds);
            if (seconds > 0) {
                usetime = "一共使用" + hour + "时" + minute + "分" + second + "秒";
            } else {
                usetime = "没有完整的借用记录";
            }

            List<CarDaka> list2 = carCheckinService.lastusetime(carDaka);
            String lasttime = list2.get(0).getBorrowTime();
            List<Openid> openidList=carCheckinService.getOpenid(openid);
            String userId=openidList.get(0).getName();
            JSONObject object = new JSONObject();
            object.put("num", num);
            object.put("usetime", usetime);
            object.put("lasttime", lasttime);
            object.put("userId",userId);
            return SysResult.build(200, "查询成功", object);
        } else {
            return SysResult.build(201, "未查到我的使用记录", null);
        }
    }

    //出车异常
    @PostMapping("/accident")
    public SysResult accident(Openid openid,Accident accident, String[] pic) {
        try {
            List<Openid> openidList=carCheckinService.getOpenid(openid);
            String userId=openidList.get(0).getName();
            accident.setUserId(userId);
            carCheckinService.accident(accident, pic);
            return SysResult.build(200, "插入成功", accident);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "插入失败", null);
        }
    }

    //获取所有异常
    @PostMapping("/getaccident")
    public SysResult getaccident(Accident accident) {
        List<Accident> list = carCheckinService.getaccident(accident);
        if (list.size() > 0) {
            return SysResult.build(200, "查询成功", list);
        } else {
            return SysResult.build(201, "未查到记录", null);
        }
    }

    //故障详情
    @PostMapping("/accidentdetail")
    public SysResult accidentdetail(Accident accident) {
        List<Accident> list = carCheckinService.accidentdetail(accident);
        if (list.size() > 0) {
            return SysResult.build(200, "查询成功", list);
        } else {
            return SysResult.build(201, "未查到记录", null);
        }
    }

    //查询可使用车辆
    @PostMapping("/canUsecar")
    public SysResult canUsecar(Car car) {
        List<Car> list = carCheckinService.canUsecar(car);
        if (list.size() == 0) {
            return SysResult.build(201, "暂未可用车辆", list.size());
        } else {
            return SysResult.build(200, "这些车可使用", list);
        }
    }

    //出车记录模糊查询
    @PostMapping("/vaguesearch")
    public SysResult vaguesearch(CarDaka carDaka) {
        List<CarDaka> list = carCheckinService.vaguesearch(carDaka);
        try{
            if (list.size() > 0) {
                return SysResult.build(200, "查询成功", list);
            } else {
                return SysResult.build(200, "没有此条记录", list);
            }
        }catch(Exception e){
            return SysResult.build(201, "查询失败", null);
        }

    }

    //出车记录模糊查询
    @PostMapping("/accidentvaguesearch")
    public SysResult accidentvaguesearch(Accident accident) {
        List<Accident> list = carCheckinService.accidentvaguesearch(accident);
        try{
            if (list.size() > 0) {
                return SysResult.build(200, "查询成功", list);
            } else {
                return SysResult.build(200, "没有此条记录", list);
            }
        }catch (Exception e){
            return SysResult.build(201, "查询失败", null);
        }

    }
}
