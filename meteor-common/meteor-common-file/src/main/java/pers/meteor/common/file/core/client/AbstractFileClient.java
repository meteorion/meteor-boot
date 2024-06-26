package pers.meteor.common.file.core.client;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author meteor
 */
@Slf4j
public abstract class AbstractFileClient<T extends FileClientConfig> implements FileClient {
    /**
     * 配置编号
     */
    private final Long id;
    /**
     * 文件配置
     */
    protected T config;

    protected AbstractFileClient(Long id, T config) {
        this.id = id;
        this.config = config;
    }

    protected final void init() {
        doInit();
        log.info("[init][配置({}) 初始化完成]", config);
    }

    /**
     * 自定义初始化
     */
    protected abstract void doInit();

    public final void refresh(T config) {
        // 判断是否更新
        if (config.equals(this.config)) {
            return;
        }
        log.info("[refresh][配置({})发生变化，重新初始化]", config);
        this.config = config;
        // 初始化
        this.init();
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * 格式化文件的 URL 访问地址
     * 使用场景：local、ftp、db，通过 FileController 的 getFile 来获取文件内容
     *
     * @param domain 自定义域名
     * @param path 文件路径
     * @return URL 访问地址
     */
    protected String formatFileUrl(String domain, String path) {
        return CharSequenceUtil.format("{}/file/{}/get/{}", domain, getId(), path);
    }
}
