package com.restdeveloper.app.ws.io.repository;

import com.restdeveloper.app.ws.io.entity.IoTDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IoTDeviceRepository extends CrudRepository<IoTDevice, Long> {
    IoTDevice findByPublicDeviceId(String id);



}
