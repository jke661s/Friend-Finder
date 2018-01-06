package com.example.jiaqiwang.monashfriendfinder.DataStore;

import org.json.JSONObject;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class Channel implements JSONPopulator {

    private Item item;
    private Units units;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    @Override
    public void populate(JSONObject data) {

        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

        location = new Location();
        location.populate(data.optJSONObject("location"));
    }
}
