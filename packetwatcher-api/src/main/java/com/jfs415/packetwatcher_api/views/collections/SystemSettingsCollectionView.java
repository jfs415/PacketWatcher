package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.SystemSettingView;
import java.util.ArrayList;
import java.util.List;

public record SystemSettingsCollectionView(List<SystemSettingView> settings) {
    public SystemSettingsCollectionView {
        settings = settings == null ? new ArrayList<>() : settings;
    }
}
