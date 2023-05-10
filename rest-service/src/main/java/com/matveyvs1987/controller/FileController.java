package com.matveyvs1987.controller;

import com.matveyvs1987.service.FileService;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j
@RequestMapping("/file")
@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @RequestMapping(method = RequestMethod.GET,value = "/get-doc")
    public ResponseEntity<?> getDoc(@RequestParam("id") String id) {
        //todo for badRequest add controllerAdvice
        var doc = fileService.getDocument(id);
        if (doc == null) {
            return ResponseEntity.badRequest().build();
        }
        var binaryContent = doc.getBinaryContent();
        var fileSystemResource = fileService.getFileSystemResource(binaryContent);

        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getMimeType()))
                .header("Content-disposition", "attachment: fileName " + doc.getMimeType())
                .body(fileSystemResource);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get-photo")
    public ResponseEntity<?> getPhoto(@RequestParam("id") String id) {
        //todo for badRequest add controllerAdvice
        var photo = fileService.getPhoto(id);
        if (photo == null) {
            return ResponseEntity.badRequest().build();
        }
        var binaryContent = photo.getBinaryContent();
        var fileSystemResource = fileService.getFileSystemResource(binaryContent);

        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header("Content-disposition", "attachment;")
                .body(fileSystemResource);
    }
}
