package com.glis.emotes;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Glis
 */
public class EmotePlugin extends JavaPlugin implements EmoteMessageProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getInsufficientPermissionsMessage() {
        return ""; //TODO CONFIG FILE
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCooldownMessage() {
        return ""; //TODO CONFIG FILE
    }
}
