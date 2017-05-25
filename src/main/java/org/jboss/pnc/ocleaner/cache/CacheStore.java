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
package org.jboss.pnc.ocleaner.cache;

import org.jboss.pnc.ocleaner.model.Item;

import javax.ejb.Singleton;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class CacheStore {

    private final static int CACHE_LENGTH = 48;

    private final List<Item> cache;

    public CacheStore() {
        cache = new LinkedList<>();
    }

    public void addItem(String date, List<String> resources) {

        Item item = new Item(date, resources);
        synchronized(cache) {
            cache.add(item);

            if (cache.size() > CACHE_LENGTH) {
                // remove earliest item
                cache.remove(0);
            }
        }
    }
    public List<Item> getValues() {
        // don't try to provide the underlying list in case the caller is
        // naughty and tries to modify the list returned by this method
        // that would mess up with `addItem`
        return new LinkedList<>(cache);
    }
}
