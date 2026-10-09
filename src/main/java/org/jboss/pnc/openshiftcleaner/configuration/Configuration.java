package org.jboss.pnc.openshiftcleaner.configuration;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;

@ApplicationScoped
public class Configuration {

    @Getter
    @ConfigProperty(name = "service_query", defaultValue = "pnc-ba")
    String serviceQuery;

    @Getter
    @ConfigProperty(name = "route_query", defaultValue = "pnc-ba-route")
    String routeQuery;

    @Getter
    @ConfigProperty(name = "pod_query", defaultValue = "pnc-ba-pod")
    String podQuery;
}
