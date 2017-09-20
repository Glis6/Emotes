package com.glis.emotes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
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
            if (e != null && e != player && player.hasLineOfSight(e)) {
                emoteReceiver = new EmoteReceiver(e.getName(), e::sendMessage);
            }
        }
        if (emoteReceiver == null) {
            emoteReceiver = new EmoteReceiver(player.getTargetBlock(null, 3).getType().name().replace("_", " ").toLowerCase(), s1 -> { /*Do nothing*/ });
        }

        emote.execute(player, emoteReceiver)
                .result()
                .accept(emoteMessageProvider);
        return true;
    }
}
