package com.jiuwei.service;

import com.jiuwei.common.PicUpload;
import com.jiuwei.dao.CarCheckinMapper;
import com.jiuwei.entity.CarDaka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class CarCheckinService {

    @Autowired
    private CarCheckinMapper carCheckinMapper;

    public void borrowCar(CarDaka carDaka,MultipartFile pic) {
        SimpleDateFormat a=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        carDaka.setDakaId(UUID.randomUUID().toString().replaceAll("-",""));
        carDaka.setUseState("已借用!");
        String url=PicUpload.uploadFile(pic);
        carDaka.setBorrowPic(url);
        Date date=new Date();
        //long times=date.getTime()+28800000;
        //date=new Date(times);
        carDaka.setBorrowTime(a.format(date));
        carCheckinMapper.borrowCar(carDaka);
    }

    public void returnCar(CarDaka carDaka,MultipartFile pic) {
        SimpleDateFormat a=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String url=PicUpload.uploadFile(pic);
        carDaka.setReturnPic(url);
        Date date=new Date();
        //long times=date.getTime()+28800000;
        //date=new Date(times);
        carDaka.setReturnTime(a.format(date));
        carDaka.setUseState("已归还!");
        carCheckinMapper.returnCar(carDaka);
    }

    public List<CarDaka> search(CarDaka carDaka) {
        List<CarDaka> exist=carCheckinMapper.search(carDaka);
        return exist;
    }

    public List<CarDaka> getstate(CarDaka carDaka) {
        List<CarDaka> exist=carCheckinMapper.getstate(carDaka);
        return exist;
    }

    public List<CarDaka> getdetail(CarDaka carDaka) {
        List<CarDaka> exist=carCheckinMapper.getdetail(carDaka);
        return exist;
    }

    public int getusetimes(CarDaka carDaka) {
        int num=carCheckinMapper.getusetimes(carDaka);
        return num;
    }

    public List<CarDaka> usetime(CarDaka carDaka) {
        List<CarDaka> exist=carCheckinMapper.getdetail(carDaka);
        return exist;
    }

    public List<CarDaka> lastusetime(CarDaka carDaka) {
        List<CarDaka> exist=carCheckinMapper.lastusetime(carDaka);
        return exist;
    }

    public List<CarDaka> getalltime(CarDaka carDaka) {
        List<CarDaka> exist=carCheckinMapper.getalltime(carDaka);
        return exist;
    }



    /*public List<CarDaka> getcurrent(CarDaka carDaka) {
        List<CarDaka> exist=carCheckinMapper.getcurrent(carDaka);
        return exist;
    }*/

    /*public List<CarDaka> getstate(CarDaka carDaka) {
        List<CarDaka> num=carCheckinMapper.getstate(carDaka);
        return num;
    }*/
}
