package me.lamacode.onlinetime.core.database.repository;

import me.lamacode.onlinetime.core.database.model.OnlineTimeModel;
import me.lamacode.onlinetime.core.database.ressource.Ressource;

import java.util.UUID;

/**
 * @author lamacode
 * @since 31.01.2021 14:46
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public interface Repository<T> {

    Ressource<T> get(UUID uuid);

    boolean exists(UUID uuid);

    Ressource<T> insert(OnlineTimeModel onlineTimeModel);

}
