package org.jboss.pnc.openshiftcleaner.cron;

import io.quarkus.scheduler.Scheduled;
import org.jboss.pnc.openshiftcleaner.cache.CacheStore;
import org.jboss.pnc.openshiftcleaner.client.OpenshiftClient;
import org.jboss.pnc.openshiftcleaner.configuration.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class ScheduledCleanup {

    @Inject
    OpenshiftClient oclient;

    @Inject
    Configuration config;

    @Inject
    CacheStore cacheStore;

    @Scheduled(every="12h")
    void cleanup() {
        String now = Instant.now().toString();

        for (String namespace: config.getNamespaces()) {

            List<String> removed = oclient.cleanResources("Service", namespace,2, config.getServiceQuery());
            List<String> removedRoutes = oclient.cleanResources("Route", namespace, 2, config.getRouteQuery());

            removed.addAll(removedRoutes);
            // cache results
            cacheStore.addItem(now, removed);
        }
    }
}
