package com.company.market.service;

import com.company.market.dto.ImageDto;
import com.company.market.entity.ImageEntity;
import com.company.market.exception.ItemNotFoundException;
import com.company.market.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImageRepository repository;

    @Value("${image.folder.url}")
    private String attachFolderUrl;
    @Value("${image.url}")
    private String attachUrl;

    public ImageDto saveToSystem(MultipartFile file) {
        try {
            String filePath = getYmDString();
            String fileType = file.getContentType().split("/")[1];
            String fileToken = UUID.randomUUID().toString();
            String fileUrl = filePath + "/" + fileToken + "." + fileType;

            File folder = new File(attachFolderUrl + filePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Path path = Paths.get(attachFolderUrl).resolve(fileUrl);
            Files.copy(file.getInputStream(), path);

            return createImage(file, filePath, fileType, fileToken);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String token) {
        try {
            ImageEntity entity = getImage(token);
            Path file =  Paths.get(attachFolderUrl).resolve(entity.getPath() + "/" + entity.getToken() + "." + entity.getType());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public byte[] getImg(String token) {
        try {
            ImageEntity entity = getImage(token);
            String path = attachFolderUrl + "/" + entity.getPath() + entity.getToken() + "." + entity.getType();

            byte[] imageInByte;
            BufferedImage originalImage;
            try {
                originalImage = ImageIO.read(new File(path));
            } catch (Exception e) {
                return new byte[0];
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(originalImage, "png", baos);

            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public ImageDto createImage(MultipartFile file, String filePath, String fileType, String fileToken) {
        long size = file.getSize();
        ImageEntity entity = new ImageEntity();
        entity.setPath(filePath);
        entity.setType(fileType);
        entity.setSize(size);
        entity.setToken(fileToken);
        entity.setCreatedDate(LocalDateTime.now());

        repository.save(entity);

        ImageDto dto = new ImageDto();
        dto.setPath(filePath);
        dto.setType(fileType);
        dto.setSize(size);
        dto.setToken(fileToken);
        dto.setUrl(attachUrl + fileToken);
        dto.setId(entity.getId());
        return dto;
    }

    public static String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day + "/";
    }

    public ImageEntity getImage(String token) {
        return repository.findByToken(token).orElseThrow(() -> new ItemNotFoundException("Image not found"));
    }

    public ImageEntity getImage(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException("Image not found"));
    }

    public ImageDto getImageDto(Integer id) {
        ImageEntity entity = getImage(id);
        ImageDto dto = new ImageDto();
        dto.setId(entity.getId());
        dto.setUrl(attachUrl + entity.getToken());
        return dto;
    }
}
