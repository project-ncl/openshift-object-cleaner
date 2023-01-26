package org.jboss.pnc.openshiftcleaner.runtime;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
@Slf4j
public class ApplicationLifecycle {
    void onStart(@Observes StartupEvent event) {
        log.info("Application is starting...");
    }

    void onStop(@Observes ShutdownEvent event) {
        log.info("Application is shutting down...");
    }
}