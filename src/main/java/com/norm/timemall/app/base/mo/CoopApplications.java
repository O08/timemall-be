package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (coop_applications)实体类
 *
 * @author kancy
 * @since 2026-07-03 09:22:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("coop_applications")
public class CoopApplications extends Model<CoopApplications> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * programId
     */
    private String programId;
    /**
     * applicantBrandId
     */
    private String applicantBrandId;
    /**
     * message
     */
    private String message;
    /**
     * status
     */
    private String status;
    /**
     * createdAt
     */
    private Date createdAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}