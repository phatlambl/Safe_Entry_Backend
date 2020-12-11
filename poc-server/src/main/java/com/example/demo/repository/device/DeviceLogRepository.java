package com.example.demo.repository.device;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.device.DeviceLog;

@Repository
public interface DeviceLogRepository extends JpaRepository<DeviceLog, Integer> {
	
   	DeviceLog findDeviceLogById(int id);

    Page<DeviceLog> findByUserIdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(String userId, Long fromTimestamp, Long toTimestamp, Pageable pageable);
    
    
    //user to draw chart
    @Query(value="select * from device_log where user_id= ?1 and name=?2 and timestamp >= ?3 and timestamp <= ?4 order by timestamp asc", nativeQuery =true)
    public List<DeviceLog> getListByTime(String userId, String name, long fromTimestamp, long toTimestamp);
    
    
    @Query(value="select * from device_log l join user u on l.user_id = u.id join device d on l.device_id = d.id where l.name LIKE ?1 "
    		+ "AND d.id LIKE ?2 AND l.timestamp >= ?3 AND l.timestamp <= ?4", nativeQuery = true)    				
    public Page<DeviceLog> findByFilter(String name, String deviceID, Long fromTime, Long toTime , Pageable pageable);
    
   


   

    
  
}
