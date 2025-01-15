package pers.meteor.file.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import pers.meteor.common.core.web.domain.BaseEntity;
import lombok.*;

/**
 * 文件表
 * 每次文件上传，都会记录一条记录到该表中
 *
 * @author meteor
 */
@TableName(value = "sys_file", excludeProperty = {"searchValue", "params", "remark"})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseEntity {
    /**
     * 编号，数据库自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 配置编号
     * 关联 {@link FileStrogeClient#getId()}
     */
    private Long configId;
    /**
     * 原文件名
     */
    private String name;
    /**
     * 路径，即文件名
     */
    private String path;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 文件的 MIME 类型，例如 "application/octet-stream"
     */
    private String type;
    /**
     * 文件大小
     */
    private Integer size;

}
