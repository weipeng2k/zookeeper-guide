package io.github.weipeng2k.zookeeper.guide.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author weipeng2k 2022年01月12日 下午15:32:25
 */
@SpringBootTest(classes = MutexLockTest.Config.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test-application.properties")
@EnableAutoConfiguration
public class MutexLockTest {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void acquireLock() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/member-123");
        lock.acquire(5, TimeUnit.MINUTES);
        try {
            System.out.println("locked");
        } finally {
            lock.release();
        }
    }

    @Test
    public void acquireLock2() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/member-111");

        Thread thread = new Thread(() -> {
            try {
                lock.acquire(5, TimeUnit.MINUTES);
                try {
                    System.out.println("winner locked.");
                    TimeUnit.SECONDS.sleep(10);
                } finally {
                    lock.release();
                }
            } catch (Exception ex) {
                // Ignore.
            }
        }, "winner");
        thread.start();

        TimeUnit.SECONDS.sleep(3);

        lock.acquire(10, TimeUnit.MINUTES);
        try {
            System.out.println("main locked");
        } finally {
            lock.release();
        }
    }

    @Configuration
    static class Config {

    }
}
