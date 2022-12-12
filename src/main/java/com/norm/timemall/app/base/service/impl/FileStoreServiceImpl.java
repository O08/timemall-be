package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.util.AliOssClientUtil;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.service.FileStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileStoreServiceImpl implements FileStoreService {



    @Autowired
    private AliOssClientUtil aliOssClientUtil;

    @Override
    public String storeWithUnlimitedAccess(MultipartFile file, FileStoreDir dir) {
        return storeFile(file,dir,true);
    }


    @Override
    public String storeWithLimitedAccess(MultipartFile file, FileStoreDir dir) {
        return storeFile(file,dir,false);
    }

    @Override
    public void download(String filename,String c, HttpServletResponse response) {
        aliOssClientUtil.downloadToFile(filename,c,response);
    }

    private String storeFile(MultipartFile file, FileStoreDir dir,boolean isPublic){
        if(file.isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        // 后缀
        String extName = FileUtil.extName(file.getOriginalFilename());
        // 重命名
        String fileName = IdUtil.simpleUUID() + "." + extName;
        // 存储
        String storeDir =  dir.getDir();
        Path rootLocation = Paths.get(storeDir);
        // 文件存储路径
        Path destinationFile = rootLocation.resolve(Paths.get(fileName))
                .normalize();
        String url="";
        if(isPublic){
             url =   aliOssClientUtil.fileUploadForPublic(file,destinationFile.toString());
        }
        if(!isPublic){
            url =   aliOssClientUtil.fileUploadForLimited(file,destinationFile.toString());
        }
        if(StrUtil.isBlank(url)){
            throw new ErrorCodeException(CodeEnum.FILE_STORE_FAIL);
        }
        return url;
    }


    @Override
    public boolean deleteFile(String uri) {
        if(StrUtil.isEmpty(uri)){
            return true;
        }
        Path filepath = Paths.get(uri);
        return  FileUtil.del(filepath);
    }
}
