package com.jfs415.packetwatcher_api.views.collections;

import com.jfs415.packetwatcher_api.views.SystemSettingView;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@AllArgsConstructor
@Getter
@Immutable
public class SystemSettingsCollectionView {

    private final List<SystemSettingView> settings;

    public SystemSettingsCollectionView() {
        this.settings = new ArrayList<>();
    }
}
