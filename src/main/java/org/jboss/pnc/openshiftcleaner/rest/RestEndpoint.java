package org.jboss.pnc.openshiftcleaner.rest;

import org.jboss.pnc.openshiftcleaner.cache.CacheStore;
import org.jboss.pnc.openshiftcleaner.cron.ScheduledCleanup;
import org.jboss.pnc.openshiftcleaner.dto.Item;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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