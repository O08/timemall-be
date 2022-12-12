package com.norm.timemall.app.base.util;

import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.AliOssConfigureBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class AliOssClientUtil {
    @Autowired
    private AliOssConfigureBean aliOssConfigure;
    // 可公共读文件存储
    public  String fileUploadForPublic(MultipartFile file,String objectName){
        return doFileUpload(file,objectName,true);
    }
    // 个人文件存储，不可公共读
    public  String fileUploadForLimited(MultipartFile file,String objectName){
        return doFileUpload(file,objectName,false);
    }


    private  String doFileUpload(MultipartFile file,String objectName, boolean publicAccess){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliOssConfigure.getEndpoint(), aliOssConfigure.getAccessKeyId(),
                aliOssConfigure.getAccessKeySecret());
       String bucket = publicAccess ? aliOssConfigure.getPublicBucket() : aliOssConfigure.getLimitedBucket();

        String url = "";
        try {
            // 创建上传文件的元信息
            ObjectMetadata meta = new ObjectMetadata();

            meta.addUserMetadata("x-oss-meta-company","7norm.com");

            // 创建PutObject请求。
            PutObjectResult putObjectResult = ossClient.putObject(bucket, objectName, file.getInputStream(), meta);
            if(publicAccess){
                url = "https://"+ bucket+"."+aliOssConfigure.getEndpoint().replace("https://","")+"/"+objectName;
            }
            if(!publicAccess){
                url= "/" + objectName + "?etag=" + putObjectResult.getETag();
            }
        } catch (OSSException oe) {
            log.error("oss upload exception:",oe);
        } catch (ClientException | IOException ce) {
            log.error("oss upload ClientException:",ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
    public void downloadToFile(String objectName,String c,HttpServletResponse response){
        OSS ossClient = new OSSClientBuilder().build(aliOssConfigure.getEndpoint(), aliOssConfigure.getAccessKeyId(),
                aliOssConfigure.getAccessKeySecret());
        try {
            OSSObject ossObject = ossClient.getObject(aliOssConfigure.getLimitedBucket(), objectName);
            ObjectMetadata meta = ossObject.getObjectMetadata();
            if(!meta.getETag().equals(c)){
                throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
            }
            BufferedInputStream reader=new BufferedInputStream(ossObject.getObjectContent());
            BufferedOutputStream out=new BufferedOutputStream(response.getOutputStream());
            response.setContentType("image/jpg");

            byte[] buffer=new byte[1024];
            int L=0;
            while((L=reader.read(buffer))!=-1){
                out.write(buffer, 0,L);
            }

            if(out!=null){
                out.flush();
                out.close();
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            if(reader!=null){
                reader.close();
            }
            // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            ossObject.close();
        } catch (OSSException oe) {
            log.error("oss download exception:",oe);
        }catch (ErrorCodeException ec) {
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        } catch (Throwable ce) {
            log.error("oss download ClientException:",ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }


    }

    }
