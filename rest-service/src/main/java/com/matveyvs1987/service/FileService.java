package com.matveyvs1987.service;

import com.matveyvs1987.entity.AppDocument;
import com.matveyvs1987.entity.AppPhoto;
import com.matveyvs1987.entity.BinaryContent;
import org.springframework.core.io.FileSystemResource;

public interface FileService {
    AppDocument getDocument(String id);
    AppPhoto getPhoto(String id);
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
