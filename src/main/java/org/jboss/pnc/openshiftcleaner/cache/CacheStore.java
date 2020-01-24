package org.jboss.pnc.openshiftcleaner.cache;

import org.jboss.pnc.openshiftcleaner.dto.Item;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class CacheStore {

    final static int CACHE_LENGTH = 120;

    final List<Item> cache;

    public CacheStore() {
        cache = new LinkedList<>();
    }

    public void addItem(String date, List<String> resources) {

        Item item = new Item(date, resources);
        synchronized (cache) {
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
        return cache;
    }
}
