package com.ruoyi.file.service;

import com.meteor.common.core.client.FileClient;
import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.file.controller.vo.config.FileConfigCreateReqVO;
import com.ruoyi.file.controller.vo.config.FileConfigPageReqVO;
import com.ruoyi.file.controller.vo.config.FileConfigRespVO;
import com.ruoyi.file.controller.vo.config.FileConfigUpdateReqVO;
import com.ruoyi.file.domain.FileStrogeClient;

import javax.annotation.PostConstruct;

/**
 * @author meteor
 */
public interface FileStrogeClientService {
    /**
     * 初始化文件客户端
     */
    @PostConstruct
    void initFileClient();

    /**
     * 新增文件客户端配置
     *
     * @param createReqVo /
     * @return /
     */
    Long createFileConfig(FileConfigCreateReqVO createReqVo);

    /**
     * 修改文件客户端配置、
     *
     * @param updateReqVO /
     */
    void updateFileConfig(FileConfigUpdateReqVO updateReqVO);

    /**
     * 设置默认文件客户端
     *
     * @param id /
     */
    void updateFileConfigMaster(Long id);

    /**
     * 删除文件客户端
     *
     * @param id /
     */
    void deleteFileConfig(Long id);

    /**
     * 获取文件客户端
     *
     * @param id /
     * @return /
     */
    FileClient getFileClient(Long id);

    /**
     * 获取默认文件客户端
     *
     * @return /
     */
    FileClient getMasterFileClient();

    /**
     * 测试文件客户端
     *
     * @param id /
     * @return /
     * @throws Exception /
     */
    String testFileConfig(Long id) throws Exception;

    /**
     * 获取文件配置
     *
     * @param id /
     * @return /
     */
    FileStrogeClient getFileConfig(Long id);

    /**
     * 分页获取文件配置
     *
     * @param pageReqVo /
     * @return /
     */
    PageResult<FileStrogeClient> getFileConfigPage(FileConfigPageReqVO pageReqVo);

}
