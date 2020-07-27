package com.jiuwei.dao;

import com.jiuwei.entity.CarDaka;
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

    //List<CarDaka> getcurrent(CarDaka carDaka);

    //List<CarDaka> selectState(CarDaka carDaka);
}
