package io.github.weipeng2k.zookeeper.guide.znode;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author weipeng2k 2021年12月31日 下午22:40:27
 */
public class CreatePersistenceZnodeTest {
    static ZooKeeper zooKeeper;

    @BeforeClass
    public static void init() throws Exception {
        zooKeeper = new ZooKeeper("114.116.8.12:2181", (int) TimeUnit.MINUTES.toMillis(5), null);
    }

    @Test
    public void create_persistence_znode() throws InterruptedException, KeeperException {
        String ret = zooKeeper.create("/create-persistence-test", "data".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(ret);

        byte[] data = zooKeeper.getData("/create-persistence-test", false, null);

        System.out.println("data:" + new String(data));

        zooKeeper.delete("/create-persistence-test", 0);
    }

    @Test
    public void create_persistence_serial_znode() throws InterruptedException, KeeperException {
        String node1 = zooKeeper.create("/create-persistence-serial-test", "data".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

        String node2 = zooKeeper.create("/create-persistence-serial-test", "data".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

        System.out.println(node1);
        System.out.println(node2);

        zooKeeper.delete(node1, 0);
        zooKeeper.delete(node2, 0);
    }

    @Test(expected = KeeperException.class)
    public void create_persistence_ttl_znode() throws InterruptedException, KeeperException {
        String ret = zooKeeper.create("/create-persistence-ttl", "data".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_WITH_TTL, null, 1000 * 30);

        System.out.println(ret);

        zooKeeper.delete(ret, 0);
    }

}
