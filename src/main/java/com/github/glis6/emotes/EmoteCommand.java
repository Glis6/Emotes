package com.github.glis6.emotes;

import org.bukkit.EntityEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * @author Glis
 */
public final class EmoteCommand extends Command {
    /**
     * The {@link IEmote} that the command is for.
     */
    private final IEmote emote;

    /**
     * The {@link EmoteMessageProvider} to use after executing the command.
     */
    private final EmoteMessageProvider emoteMessageProvider;

    /**
     * @param emote                The {@link IEmote} that the command is for.
     * @param emoteMessageProvider The {@link EmoteMessageProvider} to use after executing the command.
     */
    public EmoteCommand(IEmote emote, EmoteMessageProvider emoteMessageProvider) {
        super(emote.getEmoteName(), "This command will perform the " + emote.getEmoteName() + " emote.", "Use as: /" + emote.getEmoteName() + " while facing an entity.", new ArrayList<>());
        this.emote = emote;
        this.emoteMessageProvider = emoteMessageProvider;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Sorry, only players can use this command!");
            return true;
        }
        final Player player = (Player) commandSender;
        EmoteReceiver emoteReceiver = null;

        for (Entity e : player.getNearbyEntities(3, 3, 3)) {
            if (e != null && !(e instanceof Item) && e != player && player.hasLineOfSight(e)) {
                emoteReceiver = new EmoteReceiver(e.getName(), e::sendMessage, particleData -> {
                    if (e instanceof Player) {
                        ((Player) e).spawnParticle(particleData.getParticle(), e.getLocation(), 1, particleData.getX(), particleData.getY(), particleData.getZ());
                    } else {
                        try {
                            e.playEffect(EntityEffect.valueOf(particleData.getParticle().name()));
                        } catch (IllegalArgumentException ignored) {
                            //Ignoring the exception as it is controlled.
                        }
                    }
                });
            }
        }
        if (emoteReceiver == null) {
            emoteReceiver = new EmoteReceiver(player.getTargetBlock(null, 3).getType().name().replace("_", " ").toLowerCase(), s1 -> { /*Do nothing*/ }, particleData -> { /* Do nothing */});
        }

        emote.execute(player, emoteReceiver)
                .result()
                .accept(emoteMessageProvider);
        return true;
    }
}
