package com.norm.timemall.app.base.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.AliOssConfigureBean;
import com.norm.timemall.app.base.pojo.OssResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


/***
 * oss reference book: https://help.aliyun.com/document_detail/84842.html
 *
 */
@Slf4j
@Component
public class AliOssClientUtil {
    private final String avifSuffix="avif";

    @Autowired
    private AliOssConfigureBean aliOssConfigure;

    @Autowired
    private OSS ossClient; // 自动复用全局唯一的连接池

    // 可公共读文件存储
    public  String fileUploadForPublic(MultipartFile file,String objectName){
        return doFileUpload(file,objectName,true);
    }
    // 个人文件存储，不可公共读
    public  String fileUploadForLimited(MultipartFile file,String objectName){
        return doFileUpload(file,objectName,false);
    }
    public String getPublicFileEndpoint(){
        return "https://"+ aliOssConfigure.getPublicBucket()+"."+aliOssConfigure.getEndpoint().replace("https://","")+"/";
    }
    public String doImageUploadForPublic(MultipartFile file,String objectName){

        // process oss image as avif format if image not avif
        if(file.getContentType().equals("image/avif")){
            throw new ErrorCodeException(CodeEnum.FILE_IMAGE_AVIF_NOT_SUPPORT);
        }
        // upload file to oss
        fileUploadForPublic(file,objectName);
        return doImageProcessAsAvif(objectName,true);
    }
    public String doImageProcessAsAvif(String objectName, boolean publicAccess){

        String bucket = publicAccess ? aliOssConfigure.getPublicBucket() : aliOssConfigure.getLimitedBucket();
        String url ="";
        try (OSSObject avifObj = toAvif(ossClient, bucket, objectName)) {

            String targetName=objectName+"."+avifSuffix;
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/avif");

            url=  uploadFileToOss(avifObj.getObjectContent(),targetName,publicAccess,meta);
        }catch (OSSException | IOException oe) {
            log.error("oss upload exception:",oe);
        } catch (ClientException  ce) {
            log.error("oss upload ClientException:",ce);
        }
        return  url;
    }

    private OSSObject toAvif(OSS ossClient,String bucketName,String objectName){
        String style = "image/format,avif";
        GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        OSSObject imgObj= ossClient.getObject(request);
        return  imgObj;
    }


    private  String doFileUpload(MultipartFile file,String objectName, boolean publicAccess){
        String url="";
        try {
            url=  uploadFileToOss(file.getInputStream(),objectName,publicAccess,new ObjectMetadata());
        } catch (IOException e) {
            log.error("oss upload IOException:",e);
        }
        return url;
    }
    private String uploadFileToOss(InputStream file, String objectName, boolean publicAccess, ObjectMetadata meta){

        String bucket = publicAccess ? aliOssConfigure.getPublicBucket() : aliOssConfigure.getLimitedBucket();

        String url = "";
        try {
            // 创建上传文件的元信息

            meta.addUserMetadata("x-oss-meta-company","bluvarri.com");

            // 创建PutObject请求。
            PutObjectResult putObjectResult = ossClient.putObject(bucket, objectName, file, meta);
            if(publicAccess){
                url = "https://"+ bucket+"."+aliOssConfigure.getEndpoint().replace("https://","")+"/"+objectName;
            }
            if(!publicAccess){
                url= "/" + objectName + "?etag=" + putObjectResult.getETag();
            }
        } catch (OSSException oe) {
            log.error("oss upload exception:",oe);
        } catch (ClientException  ce) {
            log.error("oss upload ClientException:",ce);
        }
        return url;
    }

    public void deletePublicBucketFile(String objectName){
        deleteBucketFile(aliOssConfigure.getPublicBucket(),objectName);
    }
    public void deleteLimitedBucketFile(String objectName){
        deleteBucketFile(aliOssConfigure.getLimitedBucket(),objectName);
    }

    public void deleteBucketFile(String bucketName, String objectName){
        log.info("deleting oss file:"+objectName);
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucketName, objectName);
        } catch (OSSException oe) {
            log.error("oss delete exception:",oe);
        } catch (ClientException ce) {
            log.error("oss delete ClientException:",ce);
        }
    }
    public boolean objectNameExists(String objectName){

        try {
            return ossClient.doesObjectExist(aliOssConfigure.getLimitedBucket(), objectName);
        } catch (Exception e) {
            log.error("Error checking OSS file existence: {}", objectName, e);
            return false;
        }
    }


    public Resource downloadToResource(String objectName, String tag) {
        OSSObject ossObject = null;
        try {
            ossObject = ossClient.getObject(aliOssConfigure.getLimitedBucket(), objectName);
            ObjectMetadata meta = ossObject.getObjectMetadata();
            if(!meta.getETag().equals(tag)){
                throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
            }
            return new OssResource(ossObject, objectName);
        } catch (Exception e) {
            // 如果出错，必须手动关闭 ossObject，否则连接池会被占满
            if (ossObject != null) {
                try {
                    ossObject.close();
                } catch (IOException ex) {
                    log.error("Error closing OSS object", ex);
                }
            }
            log.error("Failed to load OSS file: {}", objectName, e);
            return null;
        }
    }
}
