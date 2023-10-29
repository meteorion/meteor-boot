package com.ruoyi.file.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.meteor.common.core.client.db.DBFileClient;
import lombok.*;

/**
 * 文件内容表
 * 专门用于存储 {@link DBFileClient} 的文件内容
 *
 * @author 芋道源码
 */
@TableName("sys_file_content")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileContent {

    /**
     * 编号，数据库自增
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 配置编号
     * 关联 {@link FileStrogeClient#getId()}
     */
    private Long configId;
    /**
     * 路径，即文件名
     */
    private String path;
    /**
     * 文件内容
     */
    private byte[] content;

}
