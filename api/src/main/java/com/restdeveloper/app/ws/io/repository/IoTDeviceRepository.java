package com.restdeveloper.app.ws.io.repository;

import com.restdeveloper.app.ws.io.entity.IoTDevice;
import com.restdeveloper.app.ws.io.entity.PollEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IoTDeviceRepository extends CrudRepository<IoTDevice, Long> {
    IoTDevice findByPublicDeviceId(String id);

    List<IoTDevice> findAllByCurrentPollIsNull();

    List<IoTDevice> findAllByCurrentPoll(PollEntity poll);


}
