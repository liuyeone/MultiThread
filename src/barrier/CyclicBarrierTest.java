package barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * public CyclicBarrier(int parties, Runnable barrierAction) {
 * }
 *  
 * public CyclicBarrier(int parties) {
 * }
 *
 * public int await() throws InterruptedException, BrokenBarrierException { }; // 用来挂起当前线程，直至所有线程都到达barrier状态再同时执行后续任务
 * public int await(long timeout, TimeUnit unit)throws InterruptedException,BrokenBarrierException,TimeoutException { }; // 让这些线程等待至一定的时间，如果还有线程没有到达barrier状态就直接让到达barrier的线程执行后续任务
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(4);
        for (int i = 0; i < 4; i++)
            new Writer(barrier).start();
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
