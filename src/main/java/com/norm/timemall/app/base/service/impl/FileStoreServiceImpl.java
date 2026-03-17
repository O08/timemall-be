package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.OssFileMetadata;
import com.norm.timemall.app.base.util.AliOssClientUtil;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.base.pojo.OssUploadSignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileStoreServiceImpl implements FileStoreService {

    private  final String limitedFileAccessPrefix="/api/file/";

    private  final String standardLimitedFileAccessPrefix="api/file/";
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
    public String storeImageAndProcessAsAvifWithUnlimitedAccess(MultipartFile file, FileStoreDir dir) {
        Path destinationFile = generateDestinationFilePath(file,dir,true);
        return aliOssClientUtil.doImageUploadAndProcessAsAvifForPublic(file,destinationFile.toString());
    }



    private String storeFile(MultipartFile file, FileStoreDir dir,boolean isPublic){
        Path destinationFile = generateDestinationFilePath(file,dir,isPublic);

        String url="";
        if(isPublic){
             url =   aliOssClientUtil.fileUploadForPublic(file,destinationFile.toString());
        }
        if(!isPublic){
            url =   aliOssClientUtil.fileUploadForLimited(file,destinationFile.toString().replaceFirst("/",""));
        }
        if(StrUtil.isBlank(url)){
            throw new ErrorCodeException(CodeEnum.FILE_STORE_FAIL);
        }
        return url;
    }
    private Path generateDestinationFilePath(MultipartFile file, FileStoreDir dir,boolean isPublic){
        if(file.isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        // 后缀
        String originalName = file.getOriginalFilename();
        String extName = StringUtils.getFilenameExtension(originalName);

        // 如果文件名里拿不到后缀（如截屏粘贴），从 Content-Type 里推断
        if (!StringUtils.hasText(extName)) {
            String contentType = file.getContentType(); // 例如 "image/png"
            if (StringUtils.hasText(contentType) && contentType.contains("/")) {
                extName = contentType.substring(contentType.lastIndexOf("/") + 1);
            }
        }

        if (!StringUtils.hasText(extName)) {
            extName = "unknown"; // 终极兜底
        }

        // 强制转小写，防止混淆
        extName =extName.toLowerCase();

        // 重命名
        String fileName = IdUtil.simpleUUID() + "." + extName;
        // 存储 ，私有文件拼接前缀，私有文件访问入口 /api/file/
        String storeDir =  isPublic ? dir.getDir() : limitedFileAccessPrefix + dir.getDir();
        Path rootLocation = Paths.get(storeDir);
        // 文件存储路径
        Path destinationFile = rootLocation.resolve(Paths.get(fileName))
                .normalize();

        return destinationFile;

    }

    /**
     *  limited uri:
     *  public uri:
     * @param uri
     * @return
     */
    @Override
    public boolean deleteFile(String uri) {
        if(StrUtil.isEmpty(uri)){
            return true;
        }
        // 匹配到私有前缀 为私有文件 uri 需去掉 第一个斜杠以及etag
        if(uri.startsWith(limitedFileAccessPrefix)){
            aliOssClientUtil.deleteLimitedBucketFile(uri.substring(1,uri.lastIndexOf("?")));
        }
        if(!uri.startsWith(limitedFileAccessPrefix)){
            // 公共访问文件 去掉uri前缀从而获取objectName
            aliOssClientUtil.deletePublicBucketFile(uri.replace(aliOssClientUtil.getPublicFileEndpoint(),""));
        }
        return  true;
    }

    @Override
    public boolean deleteImageAndAvifFile(String uri) {
        if(StrUtil.isEmpty(uri)){
            return true;
        }
        deleteFile(uri); // delete avif file

        String imageUrl= uri.substring(0,uri.length()-5);// remove .avif suffix from url
        return deleteFile(imageUrl); // delete image file

    }

    @Override
    public boolean objectNameExists(String fileName) {
        return aliOssClientUtil.objectNameExists(fileName);
    }

    @Override
    public Resource downloadAsResource(String fileName, String tag) {
       return aliOssClientUtil.downloadToResource(fileName,tag);
    }

    @Override
    public OssUploadSignature findOssPostSignatureForLimited(String fileName) {
        return aliOssClientUtil.generatePostPolicy(standardLimitedFileAccessPrefix+fileName);
    }

    @Override
    public OssFileMetadata getObjectSimpleMetadata(String objectName, String tag) {
        return aliOssClientUtil.findObjectSimpleMetaData(objectName,tag);
    }

    @Override
    public String generatePresignedUrl(String objectName, long seconds) {
        return aliOssClientUtil.generatePresignedUrl(objectName, seconds);
    }
}
