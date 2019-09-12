package com.shop.module.activity.entity;

public class PreferenceDown extends Preference {
    public PreferenceDown(String price, String preferenceDiscount) {
        super(price, preferenceDiscount);
    }

    @Override
    public String getActivityPrice() {
        return this.preferenceDiscount;
    }
}
