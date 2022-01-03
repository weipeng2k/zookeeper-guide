package io.github.weipeng2k.zookeeper.guide.znode;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.ACL;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author weipeng2k 2022年01月03日 下午15:18:54
 */
@SpringBootTest(classes = GetPZnodeTest.Config.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
public class GetPZnodeTest {

    @Autowired
    private CuratorFramework curatorFramework;

    private final String path = "/get-node1";

    @Before
    public void create() throws Exception {
        curatorFramework.create().forPath(path, "data".getBytes(StandardCharsets.UTF_8));
    }

    @After
    public void delete() throws Exception {
        curatorFramework.delete().forPath(path);
    }

    @Test
    public void get_node_data() throws Exception {
        byte[] bytes = curatorFramework.getData().forPath(path);

        Assert.assertEquals("data", new String(bytes));
    }

    @Test
    public void get_node_acl() throws Exception {
        List<ACL> acls = curatorFramework.getACL().forPath(path);

        acls.forEach(acl -> {
            System.out.println("ID:" + acl.getId());
            System.out.println("Perms:" + acl.getPerms());
            System.out.println("---------");
        });

        Assert.assertTrue(acls.size() > 0);
    }

    @Test
    public void get_node_children() throws Exception {
        List<String> strings = curatorFramework.getChildren().forPath(path);

        Assert.assertTrue(strings.isEmpty());
    }

    @Configuration
    static class Config {

    }
}
