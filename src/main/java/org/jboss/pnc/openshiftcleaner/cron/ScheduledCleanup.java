package org.jboss.pnc.openshiftcleaner.cron;

import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.jboss.pnc.openshiftcleaner.cache.CacheStore;
import org.jboss.pnc.openshiftcleaner.client.OpenshiftClientLocal;
import org.jboss.pnc.openshiftcleaner.configuration.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.List;

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
    public void cleanup() {
        log.error("-------------");
        log.info("Namespace: " + config.getNamespace());
        log.info("Url: " + config.getMasterUrl());
        log.error("-------------");

        String now = Instant.now().toString();

        List<String> removed = oclient.cleanServices(3, config.getServiceQuery());
        List<String> removedRoutes = oclient.cleanRoutes(3, config.getRouteQuery());
        List<String> removedPods = oclient.cleanPods(3, config.getPodQuery());

        removed.addAll(removedRoutes);
        // cache results
        cacheStore.addItem(now, removed);
    }
}
