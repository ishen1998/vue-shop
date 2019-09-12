package com.shop.module.activity.entity;

public class PreferenceFactory {

    public static Preference createPreference(String type,String price,String discount) {
        Preference preference = null;
        if (type.equals("折扣")) {
            preference = new PreferenceDiscount(price, discount);
        } else if (type.equals("全场优惠")) {
            preference = new PreferenceDown(price, discount);
        }
        return preference;
    }
}
