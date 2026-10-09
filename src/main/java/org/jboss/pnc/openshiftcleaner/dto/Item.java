package org.jboss.pnc.openshiftcleaner.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class Item {

    final String date;
    final List<String> items;

    public Item(String date, List<String> items) {
        this.date = date;
        this.items = items;
    }
}
