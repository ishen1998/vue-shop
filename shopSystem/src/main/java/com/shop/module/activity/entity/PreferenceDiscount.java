package com.shop.module.activity.entity;

public class PreferenceDiscount extends Preference {
    public PreferenceDiscount(String price, String preferenceDiscount) {
        super(price, preferenceDiscount);
    }

    @Override
    public String getActivityPrice() {
        double activityPrice=Double.valueOf(this.price)*Double.valueOf(this.preferenceDiscount);
        return String.valueOf(activityPrice);
    }
}
