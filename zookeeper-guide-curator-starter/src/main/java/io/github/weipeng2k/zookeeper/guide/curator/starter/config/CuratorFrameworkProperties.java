package io.github.weipeng2k.zookeeper.guide.curator.starter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weipeng2k 2022年01月02日 下午22:26:24
 */
@ConfigurationProperties(prefix = Constants.PREFIX)
@Getter
@Setter
public class CuratorFrameworkProperties {

    /**
     * ZK Server 地址
     */
    private String connectString;
    /**
     * 连接超时时间
     */
    private int connectionTimeoutMs = 60 * 1000;
    /**
     * 会话超时时间
     */
    private int sessionTimeoutMs = 15 * 1000;
    /**
     * 重试次数
     */
    private int retryCount = 5;
    /**
     * 重试时间
     */
    private int elapsedTimeMs = 5 * 1000;
}
