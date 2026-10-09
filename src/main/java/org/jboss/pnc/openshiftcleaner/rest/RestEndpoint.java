package org.jboss.pnc.openshiftcleaner.rest;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jboss.pnc.openshiftcleaner.cache.CacheStore;
import org.jboss.pnc.openshiftcleaner.cron.ScheduledCleanup;
import org.jboss.pnc.openshiftcleaner.dto.Item;

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