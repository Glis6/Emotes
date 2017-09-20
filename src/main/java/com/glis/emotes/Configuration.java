package com.glis.emotes;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * @author Glis
 */
public class Configuration {
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
     * The {@link JavaPlugin} the configuration is for.
     */
    private final JavaPlugin javaPlugin;

    /**
     * @param javaPlugin The {@link JavaPlugin} the configuration is for.
     */
    public Configuration(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    /**
     * Loads the configuration.
     */
    public void loadConfiguration(){
        createDefaults();
        javaPlugin.getConfig()
                .options()
                .copyDefaults(true);
        javaPlugin.saveConfig();
    }

    /**
     * Creates the default configuration.
     */
    private void createDefaults() {
        javaPlugin.getConfig().addDefault("message.insufficient_permission_message", DEFAULT_INSUFFICIENT_PERMISSIONS_MESSAGE);
        javaPlugin.getConfig().addDefault("message.cooldown_message", DEFAULT_COOLDOWN_MESSAGE);
        final List<Emote> emotes = new ArrayList<>();
        final Map<String, MessagePair> easterEggs = new HashMap<>();
        easterEggs.put("cactus", new SimpleMessagePair("This strings!", ""));
        emotes.add(new Emote("hug", 8000, "You give a hug to %r.", "%e approaches you and hugs you!", new ArrayList<>(), easterEggs));
        javaPlugin.getConfig().addDefault("emotes", emotes);
    }
}
