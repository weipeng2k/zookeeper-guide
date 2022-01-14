package io.github.weipeng2k.zookeeper.guide.watch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author weipeng2k 2022年01月09日 下午23:12:43
 */
@SpringBootTest(classes = GetDataWatchTest.Config.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
public class GetDataWatchTest {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void look_previous() throws Exception {
        String basePath = "/previous-node";

        String path = curatorFramework.create().creatingParentsIfNeeded().withMode(
                CreateMode.EPHEMERAL_SEQUENTIAL).forPath(basePath + "/p");

        System.out.println(path);

        curatorFramework.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(Thread.currentThread() + "@" + event);
            }
        }).forPath(path);

        curatorFramework.delete().forPath(path);

        Thread.sleep(1000);
    }

    @Configuration
    static class Config {

    }
}
