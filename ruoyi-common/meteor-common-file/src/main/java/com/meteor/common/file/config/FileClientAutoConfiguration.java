package com.meteor.common.file.config;

import com.meteor.common.file.core.client.FileClientFactory;
import com.meteor.common.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author meteor
 */
@AutoConfiguration
public class FileClientAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
