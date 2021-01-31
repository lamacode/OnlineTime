package me.lamacode.onlinetime.core.database.maria;

import me.lamacode.onlinetime.core.PluginCore;
import me.lamacode.onlinetime.core.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author lamacode
 * @since 31.01.2021 14:47
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public class MariaDatabase implements Database {

    private final PluginCore core;
    private Connection connection = null;

    public MariaDatabase(PluginCore core) {
        this.core = core;
    }

    @Override
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/onlineTime", "root", "xxx");
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            core.getLogger().warning("Es konnte keine Datenbankverbindung hergestellt werden!");
        }
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                core.getLogger().warning("Die Datenbankverbindung wurde nicht richtig geschlossen!");
            } finally {
                connection = null;
            }
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
