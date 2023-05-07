import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Scheduled(cron = "0 0 0 * * ?") // 매일 0시 0분에 실행
    public void myScheduledTask() {
        // 실행될 작업 내용
        System.out.println("Scheduled excute...");
    }
}