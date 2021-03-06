package com.example.demo.repository.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.device.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
	
    Device findDeviceById(String id);
    
    @Modifying
    @Transactional
    @Query(value ="delete from device where id=?1", nativeQuery = true)
    public void deleteDeviceById(String id);
    
}
