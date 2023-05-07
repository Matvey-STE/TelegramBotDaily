package com.matveyvs1987.dao;

import com.matveyvs1987.entity.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataDAO extends JpaRepository<RawData,Long> {
}
