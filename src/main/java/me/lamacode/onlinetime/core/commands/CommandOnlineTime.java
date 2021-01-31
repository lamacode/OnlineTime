package me.lamacode.onlinetime.core.commands;

import me.lamacode.onlinetime.core.database.model.OnlineTimeModel;
import me.lamacode.onlinetime.core.database.repository.OnlineTimeRepository;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author lamacode
 * @since 31.01.2021 14:41
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public class CommandOnlineTime extends Command {

    private final OnlineTimeRepository onlineTimeRepository;

    public CommandOnlineTime(String name, OnlineTimeRepository onlineTimeRepository) {
        super(name);
        this.onlineTimeRepository = onlineTimeRepository;
    }

    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) return;

        final ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if (strings.length == 0) {

            final OnlineTimeModel model = onlineTimeRepository.get(player.getUniqueId()).fetch();

            model.calculateTimeLocal();

            long seconds = model.getCalcTime() / 1000;
            int hours = (int) (seconds / 3600);
            int minutes = (int) ((seconds % 3600) / 60);

            player.sendMessage("§8┃ §e§lKyoroDE §8┃ §7Onlinezeit von: §c " + player.getName());
            player.sendMessage("§8┃ §e§lKyoroDE §8┃ §7Stunden: §6" + hours);
            player.sendMessage("§8┃ §e§lKyoroDE §8┃ §7Minuten: §6" + minutes);
        }
    }
}
