package com.norm.timemall.app.base.service;


import com.norm.timemall.app.base.enums.FileStoreDir;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStoreService {
    /**
     * file store
     * @param file
     * @param dir
     * @return url path
     */
    String storeWithSpecifiedDir(MultipartFile file, FileStoreDir dir);

    /**
     *
     * @param url
     * @return true : delete success ,false: delete fail
     */
    boolean deleteFile(String url);

}
