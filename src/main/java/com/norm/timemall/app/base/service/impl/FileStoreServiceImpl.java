package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.pod.constant.PodConstants;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.service.FileStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileStoreServiceImpl implements FileStoreService {

    @Autowired
    private PodConstants constants;

    @Override
    public String storeWithSpecifiedDir(MultipartFile file, FileStoreDir dir) {
        if(file.isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        // 后缀
        String extName = FileUtil.extName(file.getOriginalFilename());
        // 重命名
        String fileName = IdUtil.simpleUUID() + "." + extName;
        // 存储
        String storeDir = constants.getUPLOAD_FILE_DIR() + FileUtil.FILE_SEPARATOR + dir.getDir();
        Path rootLocation = Paths.get(storeDir);
        // 文件存储路径
        Path destinationFile = rootLocation.resolve(Paths.get(fileName))
                .normalize().toAbsolutePath();
        try(InputStream inputStream = file.getInputStream()){
            if(!Files.exists(rootLocation))
            {
                Files.createDirectories(rootLocation);
            }
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            return destinationFile.toString();

        } catch (IOException e) {
            log.error("file store error: "+e);
            throw new ErrorCodeException(CodeEnum.FILE_STORE_FAIL);
        }

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
