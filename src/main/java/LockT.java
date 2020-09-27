import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockT {
        static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        static Lock readLock = reentrantReadWriteLock.readLock();
        static Lock writeLock = reentrantReadWriteLock.writeLock();
        static Logger log = LogManager.getLogger(LockT.class);

    public static void main(String[] args) {
        new Thread(()->{
            readLock.lock();
            log.info("获取读锁");
            try {
                for (int i = 0; i < 5; i++) {
                    m1();
                }
            }finally {
                readLock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            writeLock.lock();
            log.info("获取写锁");
            try {
                for (int i = 0; i < 5; i++) {
                    m1();
                }
            }finally {
                writeLock.unlock();
            }
        },"t2").start();
    }

    private static void m1( ) {
        log.debug("xxxx");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
