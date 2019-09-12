package com.shop.module.resourceupload.service;

import cn.hutool.core.util.IdUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.resourceupload.FileProperties;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author zhoulanzhen
 * @date 2019/8/008 11:35
 */
@Service
public class FileService {
    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileProperties fileProperties) throws CustomizeExp {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 存储文件到系统
     *
     * @param file 文件
     * @return 文件名
     */
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String fileName = StringUtils.cleanPath(IdUtil.randomUUID()+"."+substring);

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new CustomizeExp("Sorry! Filename contains invalid path sequence ");
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (CustomizeExp | IOException ex) {
            ex.printStackTrace();
        }
        return fileName;
    }

    /**
     * 加载文件
     * @param fileName 文件名
     * @return 文件
     */
    public Resource loadFileAsResource(String fileName) throws CustomizeExp {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new CustomizeExp("File not found " + fileName);
            }
        } catch (MalformedURLException | CustomizeExp ex) {
            throw new CustomizeExp("File not found " + fileName, ex);
        }
    }
}
