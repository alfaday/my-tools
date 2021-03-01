package test.yali;

import http.HttpClientUtil;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 压力测试的工具类
 */
public class StressTestTool {

    public static void main(String[] args) {
        stressTest(
                8,
                2000,
                "http://127.0.0.1:8089/random",
                100);
    }

    public static void stressTest(int concurrentNum,int requestNum,String url,int qps){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                concurrentNum,
                concurrentNum,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1000 * 100));
        long start = System.currentTimeMillis();
        int requestAdd = 0;
        for(int i=0 ; i<requestNum ; i++){
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String ret = HttpClientUtil.sendHttpGet(url);
                    System.out.println("result:" + ret);
                }
            });
            requestAdd++;
            if(requestAdd >= qps){
                long sleep = 1000 - (System.currentTimeMillis() - start);
                if(sleep > 0){
                    try {
                        Thread.sleep(sleep);
                        System.out.println("sleep=" +sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("not sleep=" +sleep);
                }
                start = System.currentTimeMillis();
                requestAdd = 0;
            }
        }

        while (true){
            int activeCount = threadPoolExecutor.getActiveCount();
            if(activeCount == 0){
                System.exit(0);
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
