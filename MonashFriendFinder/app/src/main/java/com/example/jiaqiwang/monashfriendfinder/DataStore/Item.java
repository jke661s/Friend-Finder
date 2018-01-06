package com.example.jiaqiwang.monashfriendfinder.DataStore;

import org.json.JSONObject;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class Item implements JSONPopulator {

    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void populate(JSONObject data) {

        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));

    }
}