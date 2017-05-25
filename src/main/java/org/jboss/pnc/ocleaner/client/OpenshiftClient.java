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
package org.jboss.pnc.ocleaner.client;

import com.openshift.restclient.ClientBuilder;
import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class OpenshiftClient {

    private static final Logger logger = LoggerFactory.getLogger(OpenshiftClient.class);

    private final String REST_URL;
    private final String TOKEN;

    public OpenshiftClient(final String restURL, final String token) {
        this.REST_URL = restURL;
        this.TOKEN = token;
    }

    /**
     * Clean items of a particular kind which matches the label query.
     * The intervalHours is the number of hours elapsed before it is ok to remove
     * that resource
     *
     * @param kind         Kind of resource
     * @param namespace    Namespace of the resources
     * @param intervalDays number of hours elapsed before removing resource
     * @param query        query to run to find resources
     * @return list of resources removed
     */
    public List<String> cleanResources(String kind, String namespace,
                                       long intervalDays, String query) {

        List<String> deletedResources = new LinkedList<>();

        IClient client = getClient();

        List<IResource> resources = client.list(kind, namespace).stream()
                .filter(a -> a.getName().contains(query))
                .collect(Collectors.toList());
        for (IResource resource : resources) {
            Map<String, String> metadata = resource.getMetadata();

            if (metadata.containsKey("creationTimestamp")) {

                LocalDate dateCreated = parseTimestamp(metadata.get("creationTimestamp"));
                long days = dayDuration(dateCreated);

                if (days > intervalDays) {
                    deleteResource(client, resource);
                    deletedResources.add(resource.getName());
                }
            }
        }
        return deletedResources;
    }

    private IClient getClient() {
        return new ClientBuilder(REST_URL)
                .usingToken(TOKEN)
                .build();
    }

    /**
     * Given a timestamp, return a LocaleDate object
     * <p>
     * timestamp in the format: 2016-08-16T15:23:01Z
     *
     * @param timestamp: timestamp
     * @return LocaleDate object
     */
    private LocalDate parseTimestamp(String timestamp) {
        Instant instant = Instant.parse(timestamp);
        LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
        return result.toLocalDate();
    }

    private void deleteResource(IClient client, IResource resource) {
        logger.info("Removing resource (remove commented line dcheung): " + resource.getName());
        System.out.println("Removing resource (remove commented line dcheung): " + resource.getName());
        client.delete(resource);
    }

    private long dayDuration(LocalDate start) {
        LocalDate today = LocalDate.now();
        return DAYS.between(start, today);
    }

}
