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
package org.jboss.pnc.ocleaner.config;

import javax.ejb.Singleton;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

@Singleton
public class Configuration {
    private final static Logger LOGGER = Logger.getLogger(Configuration.class.getName());

    private Properties prop;

    public Configuration() {
        prop = new Properties();
        InputStream input = null;

        try {
            String configFile = System.getProperty("ocleaner-config");
            if (configFile == null) {
                LOGGER.warning("No Config file specified");
                return;
            }
            input = new FileInputStream(configFile);
            prop.load(input);
        } catch (IOException e) {
            LOGGER.warning("Could not load config file");
        }
    }

    public String getOSEServer() {
        return prop.getProperty("ose_server");
    }

    public String getOSENamespace() {
        return prop.getProperty("namespace");
    }

    public String getTokenFile() {
        return prop.getProperty("token_file");
    }

    public String getServiceQuery() {
        return prop.getProperty("service_query");
    }

    public String getRouteQuery() {
        return prop.getProperty("route_query");
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "prop=" + prop +
                '}';
    }
}
