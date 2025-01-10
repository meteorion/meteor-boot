package pers.meteor.common.file.core.client.s3;


import org.junit.jupiter.api.Test;
import pers.meteor.common.file.core.expection.FileUploadException;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public class S3FileClientTest {

    @Test
    public void upload() throws FileUploadException {
        S3FileClientConfig clientConfig = new S3FileClientConfig();
        clientConfig.setDomain("null");
        clientConfig.setEndpoint("http://oss-cn-shenzhen.aliyuncs.com");
        clientConfig.setBucket("lqbmposmer");
        clientConfig.setAccessKey("Lt53wikZ54U9RxYq");
        clientConfig.setAccessSecret("9lBrJXeCga3xAKpLy8IDDXQWU5OyYu");
        S3FileClient s3FileClient = new S3FileClient(1L, clientConfig);
        s3FileClient.doInit();
        byte[] content = s3FileClient.getContent("/xaaxa");
    }
}