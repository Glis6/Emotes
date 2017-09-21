package com.glis.emotes;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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
    }

    /**
     * The {@link Configuration} to be used by the plugin.
     */
    private final Configuration configuration = new Configuration(this);

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
        configuration.loadConfiguration();
        emotes.clear();
        Optional.ofNullable(getConfig().get("emotes")).ifPresent(o -> emotes.addAll((Collection<Emote>)o));

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
        return getConfig().getString("message.insufficient_permission_message");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCooldownMessage() {
        return getConfig().getString("message.cooldown_message");
    }
}
