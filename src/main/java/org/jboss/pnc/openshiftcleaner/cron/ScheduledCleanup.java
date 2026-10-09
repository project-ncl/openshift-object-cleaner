package org.jboss.pnc.openshiftcleaner.cron;

import java.time.Instant;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.jboss.pnc.openshiftcleaner.cache.CacheStore;
import org.jboss.pnc.openshiftcleaner.client.OpenshiftClientLocal;
import org.jboss.pnc.openshiftcleaner.configuration.Configuration;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ScheduledCleanup {

    @Inject
    OpenshiftClientLocal oclient;

    @Inject
    Configuration config;

    @Inject
    CacheStore cacheStore;

    @Scheduled(every = "12h")
    @WithSpan
    public void cleanup() {

        String now = Instant.now().toString();

        List<String> removed = oclient.cleanServices(3, config.getServiceQuery());
        List<String> removedRoutes = oclient.cleanRoutes(3, config.getRouteQuery());
        List<String> removedPods = oclient.cleanPods(3, config.getPodQuery());

        removed.addAll(removedRoutes);
        removed.addAll(removedPods);
        // cache results
        cacheStore.addItem(now, removed);
    }
}
