package org.jboss.pnc.openshiftcleaner.configuration;

import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class Configuration {

    @Getter
    @ConfigProperty(name = "openshift_server",
                    defaultValue = "")
    String openshiftServer;

    @Getter
    @ConfigProperty(name = "namespaces",
                    defaultValue = "")
    List<String> namespaces;

    @ConfigProperty(name = "openshift_token",
                    defaultValue = "")
    String token;

    @Getter
    @ConfigProperty(name = "service_query",
                    defaultValue = "pnc-ba")
    String serviceQuery;

    @Getter
    @ConfigProperty(name = "route_query",
                    defaultValue = "pnc-ba-route")
    String routeQuery;

    public String getToken() {
        // sometimes with env vars there might be a newline at the end. just remove it
        return token.trim();
    }
}
