package org.jboss.pnc.openshiftcleaner.rest;

import org.jboss.pnc.openshiftcleaner.cache.CacheStore;
import org.jboss.pnc.openshiftcleaner.cron.ScheduledCleanup;
import org.jboss.pnc.openshiftcleaner.dto.Item;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/info")
public class RestEndpoint {

    @Inject
    CacheStore cacheStore;

    @Inject
    ScheduledCleanup scheduledCleanup;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> cleanedItems() {
        return cacheStore.getValues();
    }
}