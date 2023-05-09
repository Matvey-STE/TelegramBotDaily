package com.matveyvs1987.dao;

import com.matveyvs1987.entity.AppDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppDocumentDAO extends JpaRepository<AppDocument,Long> {
}
