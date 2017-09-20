package com.glis.emotes;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

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
        emotes.addAll((Collection<Emote>)getConfig().get("emotes"));

        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            emotes.forEach(iEmote -> commandMap.register(iEmote.getEmoteName(), new EmoteCommand(iEmote, this)));
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
