package com.example.demo.repository.conf;

import com.example.demo.model.conf.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {
	
    Temperature findById(int id);
}
