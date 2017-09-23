package com.github.glis6.emotes;

import com.github.glis6.emotes.config.EmotesConfigurationLoader;
import com.github.glis6.emotes.config.MessageConfigurationLoader;
import com.github.glis6.emotes.particles.movement.CircleMovement;
import com.github.glis6.emotes.particles.movement.HelixMovement;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;

/**
 * @author Glis
 */
public class EmotePlugin extends JavaPlugin implements EmoteMessageProvider {
    /*
     * Register the serialization.
     */
    static {
        ConfigurationSerialization.registerClass(Emote.class, "Emote");
        ConfigurationSerialization.registerClass(SimpleMessagePair.class, "SimpleMessagePair");
        ConfigurationSerialization.registerClass(HelixMovement.class, "HelixMovement");
        ConfigurationSerialization.registerClass(CircleMovement.class, "CircleMovement");
    }

    /**
     * The {@link FileConfiguration} used to store/load the messages.
     */
    private FileConfiguration messagesConfiguration;

    /**
     * The emotes currently loaded for the plugin.
     */
    private final Collection<IEmote> emotes = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        FileConfiguration emotesConfiguration;
        try {
            messagesConfiguration = new MessageConfigurationLoader(this).loadConfiguration();
            emotesConfiguration = new EmotesConfigurationLoader(this).loadConfiguration();
        } catch(Exception e) {
            getLogger().log(Level.SEVERE, "Something went wrong loading configurations. Not loading plugin.", e);
            return;
        }
        emotes.clear();
        Optional.ofNullable(emotesConfiguration.get("emotes")).ifPresent(o -> emotes.addAll((Collection<Emote>)o));

        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            emotes.forEach(emote -> commandMap.register(emote.getEmoteName(), new EmoteCommand(emote, this)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInsufficientPermissionsMessage() {
        return messagesConfiguration.getString("messages.insufficient_permission_message");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCooldownMessage() {
        return messagesConfiguration.getString("messages.cooldown_message");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNoTargetMessage() {
        return messagesConfiguration.getString("messages.no_target_message");
    }
}
