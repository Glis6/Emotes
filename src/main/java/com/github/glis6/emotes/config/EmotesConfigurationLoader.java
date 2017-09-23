package com.github.glis6.emotes.config;

import com.github.glis6.emotes.Emote;
import com.github.glis6.emotes.MessagePair;
import com.github.glis6.emotes.SimpleMessagePair;
import com.github.glis6.emotes.particles.ParticleMovement;
import com.github.glis6.emotes.particles.ParticleReceiverType;
import com.github.glis6.emotes.particles.movement.CircleMovement;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * @author Glis
 */
public final class EmotesConfigurationLoader extends YamlConfigurationLoader {
    /**
     * The name of the file this configuration is saved in.
     */
    private final static String FILE_NAME = "emotes.yml";

    /**
     * @param javaPlugin The {@link JavaPlugin} the configuration is for.
     */
    public EmotesConfigurationLoader(JavaPlugin javaPlugin) {
        super(FILE_NAME, javaPlugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createDefaults() {
        final List<Emote> emotes = new ArrayList<>();
        final Map<String, MessagePair> easterEggs = new HashMap<>();
        final ParticleMovement particleMovement = new CircleMovement(Collections.singletonList(Particle.HEART), ParticleReceiverType.BOTH, 50);
        easterEggs.put("cactus", new SimpleMessagePair("This stings!", ""));
        emotes.add(new Emote("hug", 8000, "You give a hug to %r.", "%e approaches you and hugs you!", particleMovement, new ArrayList<>(), easterEggs));
        getYamlConfiguration().addDefault("emotes", emotes);
    }
}
