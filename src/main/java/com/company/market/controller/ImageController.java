package com.company.market.controller;

import com.company.market.dto.ImageDto;
import com.company.market.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class ImageController {
    @Autowired
    private ImageService service;

    @GetMapping("/load/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> saveFile(@PathVariable String filename) {
        Resource file = service.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDto> handleFileUpload(@RequestParam("file") MultipartFile file) {
        ImageDto result = service.saveToSystem(file);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/get/{link:.+}", produces = MediaType.IMAGE_PNG_VALUE)
    public
    @ResponseBody
    byte[] getImage(@PathVariable("link") String link) {
        if (link != null && link.length() > 0) {
            try {
                return service.getImg(link);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }
}
