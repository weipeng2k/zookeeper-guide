package io.github.weipeng2k.zookeeper.guide.znode;

import org.apache.zookeeper.ZooKeeper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author weipeng2k 2021年12月30日 下午22:25:03
 */
public class LsZnodeTest {

    static ZooKeeper zooKeeper;

    @BeforeClass
    public static void init() throws Exception {
        zooKeeper = new ZooKeeper("114.116.8.12:2181", (int) TimeUnit.MINUTES.toMillis(5), null);
    }

    @Test
    public void ls() throws Exception {
        List<String> children = zooKeeper.getChildren("/", false);
        Assert.assertTrue(children.contains("zookeeper"));
    }
}
