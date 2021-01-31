package me.lamacode.onlinetime.core.database;

import java.sql.Connection;

/**
 * @author lamacode
 * @since 31.01.2021 14:41
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public interface Database {

    void connect();

    void disconnect();

    boolean isConnected();

    Connection getConnection();

}
