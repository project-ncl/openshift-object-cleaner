package org.jboss.pnc.openshiftcleaner.configuration;

import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@Getter
public class Configuration {

    @ConfigProperty(name = "oc-cleaner.openshift_server", defaultValue = "")
    String openshiftServer;

    @ConfigProperty(name = "oc-cleaner.namespaces", defaultValue = "")
    List<String> namespaces;

    @ConfigProperty(name = "oc-cleaner.token", defaultValue = "")
    String token;

    @ConfigProperty(name = "oc-cleaner.service_query", defaultValue = "pnc-ba")
    String serviceQuery;

    @ConfigProperty(name = "oc-cleaner.route_query", defaultValue = "pnc-ba-route")
    String routeQuery;
}
