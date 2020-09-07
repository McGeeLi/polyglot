package me.bytebeats.polyglot.ui;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import me.bytebeats.polyglot.util.StringResUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author bytebeats
 * @version 1.0
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/7 11:41
 * @description PolyglotSettingState offers settings for Polyglot
 */

@State(name = "me.bytebeats.polyglot.ui.PolyglotSettingState", storages = {@Storage("polyglot_setting.xml")})
public class PolyglotSettingState implements PersistentStateComponent<PolyglotSettingState> {
    private String from = StringResUtils.LANG_DESC_ZH;
    private String to = StringResUtils.LANG_DESC_EN;

    public static PolyglotSettingState getInstance() {
        return ServiceManager.getService(PolyglotSettingState.class);
    }

    @Nullable
    @Override
    public PolyglotSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PolyglotSettingState polyglotSettingState) {
        XmlSerializerUtil.copyBean(polyglotSettingState, this);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
