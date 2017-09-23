package com.github.glis6.emotes;

import com.github.glis6.emotes.particles.movement.CircleMovement;
import com.github.glis6.emotes.particles.ParticleMovement;
import com.github.glis6.emotes.particles.ParticleReceiverType;
import org.bukkit.Particle;
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
     * The default message to be saved in the configuration
     * for the no target message.
     */
    private final static String DEFAULT_NO_TARGET_MESSAGE = "Make sure you're aiming at something or someone before trying an emote!";

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
        javaPlugin.getConfig().addDefault("messages.insufficient_permission_message", DEFAULT_INSUFFICIENT_PERMISSIONS_MESSAGE);
        javaPlugin.getConfig().addDefault("messages.cooldown_message", DEFAULT_COOLDOWN_MESSAGE);
        javaPlugin.getConfig().addDefault("messages.no_target_message", DEFAULT_NO_TARGET_MESSAGE);
        final List<Emote> emotes = new ArrayList<>();
        final Map<String, MessagePair> easterEggs = new HashMap<>();
        final ParticleMovement particleMovement = new CircleMovement(Collections.singletonList(Particle.HEART), ParticleReceiverType.BOTH, 50);
        easterEggs.put("cactus", new SimpleMessagePair("This stings!", ""));
        emotes.add(new Emote("hug", 8000, "You give a hug to %r.", "%e approaches you and hugs you!", particleMovement, new ArrayList<>(), easterEggs));
        javaPlugin.getConfig().addDefault("emotes", emotes);
    }
}
