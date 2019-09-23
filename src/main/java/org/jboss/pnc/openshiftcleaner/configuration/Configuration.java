package org.jboss.pnc.openshiftcleaner.configuration;

import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@Getter
public class Configuration {

    @ConfigProperty(name = "openshift_server", defaultValue = "")
    String openshiftServer;

    @ConfigProperty(name = "namespaces", defaultValue = "")
    List<String> namespaces;

    @ConfigProperty(name = "openshift_token", defaultValue = "")
    String token;

    @ConfigProperty(name = "service_query", defaultValue = "pnc-ba")
    String serviceQuery;

    @ConfigProperty(name = "route_query", defaultValue = "pnc-ba-route")
    String routeQuery;
}
