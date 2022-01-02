package io.github.weipeng2k.zookeeper.guide.znode;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooDefs;
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
 * @author weipeng2k 2022年01月02日 下午22:54:40
 */
@SpringBootTest(classes = CreatePZnodeTest.Config.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test-application.properties")
@EnableAutoConfiguration
public class CreatePZnodeTest {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void create_node() throws Exception {
        curatorFramework.create().withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath("/curator-node", "data".getBytes(
                StandardCharsets.UTF_8));

        System.out.println(new String(curatorFramework.getData().forPath("/curator-node")));

        curatorFramework.delete().forPath("/curator-node");
    }

    @Configuration
    static class Config {

    }
}
