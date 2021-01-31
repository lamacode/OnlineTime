package me.lamacode.onlinetime.core.database.ressource;

import java.util.UUID;

/**
 * @author lamacode
 * @since 31.01.2021 14:43
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public interface Ressource<T> {

    UUID getKey();

    T fetch();

    void update(T type);

}
