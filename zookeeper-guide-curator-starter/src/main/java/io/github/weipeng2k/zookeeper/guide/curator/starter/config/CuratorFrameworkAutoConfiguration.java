package io.github.weipeng2k.zookeeper.guide.curator.starter.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author weipeng2k 2022年01月02日 下午22:37:33
 */
@Configuration
@ConditionalOnProperty(prefix = Constants.PREFIX, name = "connect-string")
@ConditionalOnClass(CuratorFrameworkFactory.class)
@EnableConfigurationProperties(CuratorFrameworkProperties.class)
public class CuratorFrameworkAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    @Bean(value = "curatorFramework", initMethod = "start", destroyMethod = "close")
    public CuratorFramework curatorFramework() {
        Binder binder = Binder.get(environment);
        BindResult<CuratorFrameworkProperties> bindResult = binder.bind(Constants.PREFIX,
                Bindable.of(CuratorFrameworkProperties.class));
        CuratorFrameworkProperties curatorFrameworkProperties = bindResult.get();
        return CuratorFrameworkFactory.builder()
                .connectString(curatorFrameworkProperties.getConnectString())
                .connectionTimeoutMs(curatorFrameworkProperties.getConnectionTimeoutMs())
                .sessionTimeoutMs(curatorFrameworkProperties.getSessionTimeoutMs())
                .retryPolicy(new RetryNTimes(curatorFrameworkProperties.getRetryCount(),
                        curatorFrameworkProperties.getElapsedTimeMs()))
                .build();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
