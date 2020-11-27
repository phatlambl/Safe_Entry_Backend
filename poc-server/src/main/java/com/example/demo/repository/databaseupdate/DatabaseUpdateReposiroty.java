package com.example.demo.repository.databaseupdate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.databaseupdate.DatabaseUpdate;

@Repository
public interface DatabaseUpdateReposiroty extends JpaRepository<DatabaseUpdate, Integer>{

}
