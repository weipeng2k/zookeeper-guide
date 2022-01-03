package io.github.weipeng2k.zookeeper.guide.znode;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

/**
 * @author weipeng2k 2022年01月03日 下午14:22:28
 */
@SpringBootTest(classes = CreateEZnodeTest.Config.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test-application.properties")
@EnableAutoConfiguration
public class CreateEZnodeTest {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void create_node() throws Exception {
        String path = curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/ephemeral-node",
                "data".getBytes(
                        StandardCharsets.UTF_8));

        System.out.println(path);
    }

    @Test
    public void create_serial_node() throws Exception {
        String path = curatorFramework.create().creatingParentsIfNeeded().withMode(
                CreateMode.EPHEMERAL_SEQUENTIAL).forPath(
                "/ephemeral-serial-node/x", "data".getBytes(
                        StandardCharsets.UTF_8));

        System.out.println(path);
    }

    @Configuration
    static class Config {

    }
}
