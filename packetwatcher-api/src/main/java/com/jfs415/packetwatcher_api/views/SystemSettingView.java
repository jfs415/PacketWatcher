package com.jfs415.packetwatcher_api.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public class SystemSettingView {

    private final String settingKey;
    private final String settingValue;

}
