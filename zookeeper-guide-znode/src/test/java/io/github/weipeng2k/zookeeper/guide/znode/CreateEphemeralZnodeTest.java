package io.github.weipeng2k.zookeeper.guide.znode;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * ephemeral node
 * run : create_ephemeral_znode
 * run : get_ephemeral_znode
 * </pre>
 * @author weipeng2k 2022年01月01日 下午23:18:51
 */
public class CreateEphemeralZnodeTest {
    static ZooKeeper zooKeeper;

    @BeforeClass
    public static void init() throws Exception {
        zooKeeper = new ZooKeeper("114.116.8.12:2181", (int) TimeUnit.MINUTES.toMillis(5), null);
    }

    @Test
    public void create_ephemeral_znode() throws InterruptedException, KeeperException {
        String path = zooKeeper.create("/ephemeral-node", "data".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);

        System.out.println(path);
        Assert.assertNotNull(path);
    }

    /**
     * WatchedEvent state:SyncConnected type:NodeDeleted path:/ephemeral-node
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void get_ephemeral_znode() throws InterruptedException, KeeperException {
        zooKeeper.getData("/ephemeral-node", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
            }
        }, null);
        TimeUnit.SECONDS.sleep(50);
    }


}
