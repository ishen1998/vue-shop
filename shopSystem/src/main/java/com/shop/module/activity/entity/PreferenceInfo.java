package com.shop.module.activity.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreferenceInfo {
    private String preferenceId;
    private String preferenceType;
    private List<PreferenceItem> preferenceItems;
}
