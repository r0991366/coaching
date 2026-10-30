package be.ucll.fs.lab6.dirtyread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class DirtyReadController {

    @Autowired
    private DirtyReadService dirtyReadService;

    @GetMapping("/dirty-read-test")
    public void simulateDirtyRead() {
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {

            executor.submit(() -> dirtyReadService.updateCustomerName(1L));
            executor.submit(() -> {
                try {
                    Thread.sleep(3000); // Ensure read happens during write
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Read the following name in the DB: " + dirtyReadService.getCustomerName(1L));
            });

            executor.shutdown();
        }
    }
}
