package org.jboss.pnc.openshiftcleaner.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class Item {

    final String date;
    final List<String> items;

    public Item(String date, List<String> items) {
        this.date = date;
        this.items = items;
    }
}
