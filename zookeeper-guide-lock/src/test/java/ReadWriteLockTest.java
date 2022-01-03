import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author weipeng2k 2022年01月03日 下午21:23:13
 */
@SpringBootTest(classes = ReadWriteLockTest.Config.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test-application.properties")
@EnableAutoConfiguration
public class ReadWriteLockTest {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void get_read_lock() throws Exception {
        // 会在/read-write-lock下面建立子节点
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(curatorFramework, "/read-write-lock");

        InterProcessMutex readLock = readWriteLock.readLock();
        readLock.acquire();

        try {
            IntStream.range(0, 20)
                    .forEach(i -> {
                        System.out.println(i);
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
        } finally {
            readLock.release();
        }
    }

    @Test
    public void get_read_lock_1() throws Exception {
        // 会在/read-write-lock下面建立子节点
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(curatorFramework, "/read-write-lock");

        InterProcessMutex readLock = readWriteLock.readLock();
        readLock.acquire();

        try {
            IntStream.range(0, 20)
                    .forEach(i -> {
                        System.out.println(i);
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
        } finally {
            readLock.release();
        }
    }

    @Test
    public void get_write_lock() throws Exception {
        // 会在/read-write-lock下面建立子节点
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(curatorFramework, "/read-write-lock");

        InterProcessMutex readLock = readWriteLock.writeLock();
        readLock.acquire();

        try {
            IntStream.range(0, 20)
                    .forEach(i -> {
                        System.out.println(i);
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
        } finally {
            readLock.release();
        }
    }

    @Configuration
    static class Config {

    }
}
