package com.norm.timemall.app.base.service;


import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.pojo.OssUploadSignature;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface FileStoreService {
    /**
     * file store , file can access for anyone
     * @param file
     * @param dir
     * @return url path
     */
    String storeWithUnlimitedAccess(MultipartFile file, FileStoreDir dir);
    /**
     * file store , file access is limited
     * @param file
     * @param dir
     * @return url path
     */
    String storeWithLimitedAccess(MultipartFile file, FileStoreDir dir);

    String storeImageAndProcessAsAvifWithUnlimitedAccess(MultipartFile file, FileStoreDir dir);

    /**
     *
     * @param url
     * @return true : delete success ,false: delete fail
     */
    boolean deleteFile(String url);
    boolean deleteImageAndAvifFile(String url);

    boolean objectNameExists(String fileName);

    Resource downloadAsResource(String fileName, String tag);

    OssUploadSignature findOssPostSignatureForLimited(String fileName);
}
