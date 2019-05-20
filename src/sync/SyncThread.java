package sync;

/**
 * 无论synchronized关键字加在方法上还是对象上，如果它作用的对象是非静态的，则它取得的锁是对象；
 * 如果synchronized作用的对象是一个静态方法或一个类，则它取得的锁是对类，该类所有的对象同一把锁
 *
 *
 *
 * // 这个类的class对象进行检查，这个类的所有对象
 * public static synchronized void doSomething() {}
 *
 * // 对这个类的实例化对象进行检查
 * public synchronized void doOtherthing() {}
 *
 */
public class SyncThread implements Runnable {
    private Integer value;

    private static Integer NOWVALUE;

    public SyncThread(int value) {
        this.value = value;
    }

    /**
     * 对这个类的实例化对象进行检查
     */
    private void doOtherthing() {
        synchronized (SyncThread.class) {
            NOWVALUE = this.value;
            System.out.println("当前NOWVALUE的值：" + NOWVALUE);
        }
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        Long id = currentThread.getId();
        this.doOtherthing();
    }

    public static void main(String[] args) throws Exception {
        Thread syncThread1 = new Thread(new SyncThread(10));
        Thread syncThread2 = new Thread(new SyncThread(100));

        syncThread1.start();
        syncThread2.start();
    }
}
