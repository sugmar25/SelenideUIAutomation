package org.gallup.access.utils;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.codeborne.selenide.Selenide.$x;

public class CookieWatcher {

    private static final Logger logger = LoggerFactory.getLogger(CookieWatcher.class);
    private static final int POLLING_INTERVAL_MILLIS = 2000;

    static {
        Thread watcherThread = new Thread((Runnable) new CookieWatcher());
        watcherThread.setDaemon(true); // ensures it won't block JVM shutdown
        watcherThread.start();
        logger.info("CookieWatcher thread started automatically.");
    }

    private volatile boolean running = true;

    public void stopWatching() {
        running = false;
        logger.info("CookieConsentWatcher stopped.");
    }

    public void run() {
        while (running) {
            try {
                Optional.ofNullable($x("//button[@name='consent' and contains(text(),'Accept')]"))
                        .filter(SelenideElement::isDisplayed)
                        .ifPresent(button -> {
                            button.click();
                            logger.info("Cookie consent popup detected and accepted.");
                            running = false;
                        });
            } catch (Exception e) {
                logger.debug("Cookie popup not visible or already handled.");
            }

            try {
                Thread.sleep(POLLING_INTERVAL_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("CookieConsentWatcher thread interrupted.");
                break;
            }
        }
    }
}
