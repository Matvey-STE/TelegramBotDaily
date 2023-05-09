package com.matveyvs1987.dao;

import com.matveyvs1987.entity.AppPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppPhotoDAO extends JpaRepository<AppPhoto,Long> {
}
