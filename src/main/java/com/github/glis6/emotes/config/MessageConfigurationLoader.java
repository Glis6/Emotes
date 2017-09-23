package com.github.glis6.emotes.config;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Glis
 */
public final class MessageConfigurationLoader extends YamlConfigurationLoader {
    /**
     * The name of the file this configuration is saved in.
     */
    private final static String FILE_NAME = "messages.yml";

    /**
     * The default message to be saved in the configuration
     * for the insufficient permissions message.
     */
    private final static String DEFAULT_INSUFFICIENT_PERMISSIONS_MESSAGE = "You do not have the required permissions to use this emote.";

    /**
     * The default message to be saved in the configuration
     * for the cooldown message.
     */
    private final static String DEFAULT_COOLDOWN_MESSAGE = "You have to wait another %s seconds before you can use this emote again.";

    /**
     * The default message to be saved in the configuration
     * for the no target message.
     */
    private final static String DEFAULT_NO_TARGET_MESSAGE = "Make sure you're aiming at something or someone before trying an emote!";

    /**
     * @param javaPlugin The {@link JavaPlugin} the configuration is for.
     */
    public MessageConfigurationLoader(JavaPlugin javaPlugin) {
        super(FILE_NAME, javaPlugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createDefaults() {
        getYamlConfiguration().addDefault("messages.insufficient_permission_message", DEFAULT_INSUFFICIENT_PERMISSIONS_MESSAGE);
        getYamlConfiguration().addDefault("messages.cooldown_message", DEFAULT_COOLDOWN_MESSAGE);
        getYamlConfiguration().addDefault("messages.no_target_message", DEFAULT_NO_TARGET_MESSAGE);
    }
}
