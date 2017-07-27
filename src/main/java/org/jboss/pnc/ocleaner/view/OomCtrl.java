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
package org.jboss.pnc.ocleaner.view;


import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
/**
 * This class is intended to be used to test for NCL-2958, to make sure the OutOfMemoryError JVM parameters are working
 * as intended
 */
public class OomCtrl {

    public OomCtrl() {
    }

    public String createOOM() {
        long[] ll = new long[Integer.MAX_VALUE];
        return "Should create an OOM";
    }
}
