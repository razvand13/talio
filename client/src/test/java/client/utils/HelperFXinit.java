package client.utils;

import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class HelperFXinit {

    static CountDownLatch initializedLatch = new CountDownLatch(1);
    static AtomicBoolean initializing = new AtomicBoolean(false);

    @BeforeAll
    static void initializeJavaFX() throws InterruptedException {
        if (initializing.getAndSet(true)) {
            System.out.println("[JavaFXTestHelper] Waiting for JavaFX toolkit initialization...");
            initializedLatch.await();
            return;
        }

        System.out.println("[JavaFXTestHelper] Initializing JavaFX toolkit...");
        Platform.startup(() -> {
            System.out.println("[JavaFXTestHelper] JavaFX toolkit is now initialized.");
            initializedLatch.countDown();
        });

        initializedLatch.await();
    }

    @AfterAll
    static void shutdownJavaFX() {
        Platform.exit();
    }

}

