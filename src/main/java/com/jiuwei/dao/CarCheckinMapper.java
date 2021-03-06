package com.jiuwei.dao;

import com.jiuwei.entity.Accident;
import com.jiuwei.entity.Car;
import com.jiuwei.entity.CarDaka;
import com.jiuwei.entity.Openid;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarCheckinMapper {

    void borrowCar(CarDaka carDaka);

    void returnCar(CarDaka carDaka);

    List<CarDaka> search(CarDaka carDaka);

    List<CarDaka> getstate(CarDaka carDaka);

    List<CarDaka> getdetail(CarDaka carDaka);

    int getusetimes(CarDaka carDaka);

    List<CarDaka> lastusetime(CarDaka carDaka);

    List<CarDaka> getalltime(CarDaka carDaka);

    void accident(Accident accident);

    List<Accident> getaccident(Accident accident);

    List<Accident> accidentdetail(Accident accident);

    List<Car> canUsecar(Car car);

    void changeCarstate(Car car);

    List<Openid> getOpenid(Openid openid);

    List<Car> carstate(Car car);

    List<CarDaka> vaguesearch(CarDaka carDaka);

    List<Accident> accidentvaguesearch(Accident accident);


    //List<CarDaka> getcurrent(CarDaka carDaka);

    //List<CarDaka> selectState(CarDaka carDaka);
}
