package pers.meteor.common.file.core.client;

import pers.meteor.common.file.core.expection.FileUploadException;

/**
 * @author meteor
 */
public interface FileClient {

    /**
     * 获得客户端编号
     *
     * @return 客户端编号
     */
    Long getId();

    /**
     * 上传文件
     *
     * @param content 文件流
     * @param path 相对路径
     * @return 完整路径，即 HTTP 访问地址
     * @throws FileUploadException 上传文件时，抛出 Exception 异常
     */
    String upload(byte[] content, String path, String type) throws FileUploadException;

    /**
     * 删除文件
     *
     * @param path 相对路径
     * @throws FileUploadException 删除文件时，抛出 Exception 异常
     */
    void delete(String path) throws FileUploadException;

    /**
     * 获得文件的内容
     *
     * @param path 相对路径
     * @return 文件的内容
     */
    byte[] getContent(String path) throws FileUploadException;
}
