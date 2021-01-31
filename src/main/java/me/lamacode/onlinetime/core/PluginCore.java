package me.lamacode.onlinetime.core;

import me.lamacode.onlinetime.core.commands.CommandOnlineTime;
import me.lamacode.onlinetime.core.database.maria.MariaDatabase;
import me.lamacode.onlinetime.core.database.repository.OnlineTimeRepository;
import me.lamacode.onlinetime.core.database.ressource.OnlineTimeRessource;
import me.lamacode.onlinetime.core.listeners.PlayerConnectionListener;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author lamacode
 * @since 31.01.2021 14:40
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public class PluginCore extends Plugin {

    private final OnlineTimeRepository repository = new OnlineTimeRepository(new MariaDatabase(this));

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new CommandOnlineTime("onlineTime", repository));
        getProxy().getPluginManager().registerListener(this, new PlayerConnectionListener(repository));
    }

    @Override
    public void onDisable() {
        OnlineTimeRessource.CACHE.forEach((key, model) -> {
            model.calculateTime();
            repository.get(key).update(model);
        });
    }
}
