package me.michaelbarcelo.proximitychat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public final class Proximitychat extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    public static boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

    @EventHandler
    public void OnPlayerChat(AsyncPlayerChatEvent event) {

        // Grabbing the player who sent the message
        Player player = event.getPlayer();

        // Creating an iterator so we can modify the list of recipients
        Iterator<Player> iterator = event.getRecipients().iterator();

        // Iterate through all recipients
        while (iterator.hasNext()) {

            // Declaring the recipient we're going to check the conditions of
            Player recipient = iterator.next();

            // Grabbing the distance from the player to the recipient
            double distance = player.getLocation().distance(recipient.getLocation());

            // Remove the recipient as a recipient if they are more than 20 blocks away AND they don't have a moderation role
            if (distance > 20.0 && !isPlayerInGroup(recipient, "moderation")) {

                // Actually removing the recipient from receiving the player's message
                iterator.remove();
            }
        }
    }
}
