package io.github.weipeng2k.zookeeper.guide.watch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.zookeeper.WatchedEvent;
import org.junit.After;
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
import java.util.concurrent.TimeUnit;

/**
 * @author weipeng2k 2022年01月03日 下午17:52:26
 */
@SpringBootTest(classes = WatchTest.Config.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
public class WatchTest {

    private final String path = "/watch-node";
    @Autowired
    private CuratorFramework curatorFramework;

    @Before
    public void create() throws Exception {
        curatorFramework.create().forPath(path, "data".getBytes(StandardCharsets.UTF_8));
    }

    @After
    public void delete() throws Exception {
        curatorFramework.delete().forPath(path);
    }

    @Test
    public void watch_node() throws Exception {
        curatorFramework.getData().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
                try {
                    curatorFramework.getData().usingWatcher(this).forPath(event.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).forPath(path);

        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void watch_recipes_node() throws Exception {
        CuratorCache curatorCache = CuratorCache.build(curatorFramework, "/recipes-node",
                CuratorCache.Options.SINGLE_NODE_CACHE);

        curatorCache.listenable().addListener((type, oldData, data) -> {
            System.out.println(Thread.currentThread());
            System.out.println(type);
            if (oldData != null) {
                System.out.println("old data:" + new String(oldData.getData()));
            }
            if (data != null) {
                System.out.println("new data:" + new String(data.getData()));
            }
            System.out.println("=========================");
        });
        curatorCache.start();

        TimeUnit.SECONDS.sleep(30);
    }

    @Configuration
    static class Config {

    }

}
