package me.lamacode.onlinetime.core.listeners;

import me.lamacode.onlinetime.core.database.model.OnlineTimeModel;
import me.lamacode.onlinetime.core.database.repository.Repository;
import me.lamacode.onlinetime.core.database.ressource.Ressource;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * @author lamacode
 * @since 31.01.2021 15:09
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public class PlayerConnectionListener implements Listener {

    private final Repository<OnlineTimeModel> timeModelRepository;

    public PlayerConnectionListener(Repository<OnlineTimeModel> timeModelRepository) {
        this.timeModelRepository = timeModelRepository;
    }

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        final ProxiedPlayer proxiedPlayer = event.getPlayer();

        Ressource<OnlineTimeModel> modelRessource;

        if (!timeModelRepository.exists(proxiedPlayer.getUniqueId())) {
            modelRessource = timeModelRepository.insert(new OnlineTimeModel(proxiedPlayer.getUniqueId(), 0L));
        } else {
            modelRessource = timeModelRepository.get(proxiedPlayer.getUniqueId());
        }

        modelRessource.fetch().setJoinTime(System.currentTimeMillis());
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        final ProxiedPlayer proxiedPlayer = event.getPlayer();

        OnlineTimeModel onlineTimeModel = timeModelRepository.get(proxiedPlayer.getUniqueId()).fetch();

        onlineTimeModel.calculateTime();

        timeModelRepository.get(proxiedPlayer.getUniqueId()).update(onlineTimeModel);
    }

}
