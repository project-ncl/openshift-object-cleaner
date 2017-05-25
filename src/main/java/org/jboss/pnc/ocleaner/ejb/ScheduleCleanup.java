/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.ocleaner.ejb;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.jboss.pnc.ocleaner.cache.CacheStore;
import org.jboss.pnc.ocleaner.client.OpenshiftClient;
import org.jboss.pnc.ocleaner.config.Configuration;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@Singleton
@Startup
public class ScheduleCleanup {

    private final static Logger LOGGER = Logger.getLogger(ScheduleCleanup.class.getName());

    @Inject
    private Configuration config;

    @Inject
    CacheStore cache;

    private OpenshiftClient oclient;

    public ScheduleCleanup() {}

    @PostConstruct
    public void init() {
        String tokenFile = config.getTokenFile();
        String tokenContent = null;
        try {
            tokenContent = Files.toString(new File(tokenFile), Charsets.UTF_8);
        } catch (IOException e) {
            LOGGER.warning("Could not read from file: " + tokenFile);
        }

        oclient = new OpenshiftClient(config.getOSEServer(), tokenContent);
    }


    @Schedule(hour = "*/12")
    public void periodicCleanup() {
        String now = Instant.now().toString();
        // cache results
        List<String> removed = oclient.cleanResources("Service",
                config.getOSENamespace(), 1, config.getServiceQuery());

        List<String> removedRoutes = oclient.cleanResources("Route",
                config.getOSENamespace(), 1, config.getRouteQuery());

        removed.addAll(removedRoutes);
        cache.addItem(now, removed);
    }
}
