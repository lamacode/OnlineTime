package me.lamacode.onlinetime.core.database.ressource;

import me.lamacode.onlinetime.core.database.Database;
import me.lamacode.onlinetime.core.database.model.OnlineTimeModel;
import net.md_5.bungee.api.plugin.Listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lamacode
 * @since 31.01.2021 14:44
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public class OnlineTimeRessource implements Ressource<OnlineTimeModel> {

    public static final Map<UUID, OnlineTimeModel> CACHE = new HashMap<>();

    private final UUID key;
    private final Database database;

    public OnlineTimeRessource(UUID key, Database database) {
        this.key = key;
        this.database = database;
    }

    public UUID getKey() {
        return key;
    }

    public OnlineTimeModel fetch() {
        if (CACHE.containsKey(key)) return CACHE.get(key);

        if (!database.isConnected()) database.connect();
        final Connection connection = database.getConnection();

        final String sql = "SELECT * FROM onlineTime WHERE uuid LIKE ?;";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, key.toString());

            ResultSet result = statement.executeQuery();

            if (result != null) {
                if (result.next()) {
                    long time = result.getLong("time");

                    OnlineTimeModel model = new OnlineTimeModel(key, time);

                    CACHE.put(key, model);

                    return model;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.disconnect();
        }

        return null;
    }

    public void update(OnlineTimeModel onlineTimeModel) {
        if (!database.isConnected()) database.connect();

        final Connection connection = database.getConnection();
        final String sql = "UPDATE onlineTime SET time = ? WHERE uuid LIKE ?;";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, onlineTimeModel.getOnlineTime());
            statement.setString(2, onlineTimeModel.getUuid().toString());

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            database.disconnect();
        }

        if (CACHE.containsKey(onlineTimeModel.getUuid()))
            CACHE.remove(onlineTimeModel.getUuid());
    }
}
