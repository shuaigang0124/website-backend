import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author shuaigang
 * @date 2022/8/7 16:35
 */
@SpringBootTest
public class test {

    public static void main(String[] args) throws ParseException {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = df.parse("2022-08-07 18:00:30");
//        Date nowDate = new Date();
//        long delay = date.getTime() - nowDate.getTime();
//        long delay = time / (1000 * 60 * 60 * 24);
//        System.out.println(time);
//        System.out.println(delay);
//        // 设置定时任务
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("1111111");
//                timer.cancel();
//            }
//        }, date);

//        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
//                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
//
//        // 在一定延时(delay)之后，运行Runnable任务。
//        //此任务只运行一次。
//        executorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("1111111");
//            }
//        }, delay,TimeUnit.SECONDS);
        /*//在一定延时(initialDelay)之后，开始周期性的运行Runnable任务。
        //周期性：每过一段时间(period)，就开始运行一次Runnable任务。
        //如果任务的执行时间大于等待周期(period)：
        //上一次任务执行完成之后，立即开始下一次任务。
        //也就是说：每过一段时间(任务执行时间)，就开始运行一次Runnable任务。
        executorService.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("2222222");
            }
        }, 0,0,TimeUnit.SECONDS);*/
    }


}
