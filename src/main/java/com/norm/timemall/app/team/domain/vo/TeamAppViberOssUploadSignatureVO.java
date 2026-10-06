package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.base.pojo.OssUploadSignature;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamAppViberOssUploadSignatureVO extends CodeVO {
    private OssUploadSignature signature;
}
