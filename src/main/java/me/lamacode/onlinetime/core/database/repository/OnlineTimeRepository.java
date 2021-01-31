package me.lamacode.onlinetime.core.database.repository;

import me.lamacode.onlinetime.core.database.Database;
import me.lamacode.onlinetime.core.database.model.OnlineTimeModel;
import me.lamacode.onlinetime.core.database.ressource.OnlineTimeRessource;
import me.lamacode.onlinetime.core.database.ressource.Ressource;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author lamacode
 * @since 31.01.2021 14:46
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public class OnlineTimeRepository implements Repository<OnlineTimeModel> {

    private final Database database;

    public OnlineTimeRepository(Database database) {
        this.database = database;
    }

    @Override
    public Ressource<OnlineTimeModel> get(UUID uuid) {
        return new OnlineTimeRessource(uuid, database);
    }

    public boolean exists(UUID uuid) {
        if (!database.isConnected()) database.connect();

        String sql = "SELECT * FROM onlineTime WHERE uuid LIKE ?;";

        final Connection connection = database.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();

            return resultSet != null && resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.disconnect();
        }

        return false;
    }

    @Override
    public Ressource<OnlineTimeModel> insert(OnlineTimeModel onlineTimeModel) {
        if (!database.isConnected()) database.connect();

        String sql = "INSERT INTO onlineTime VALUES (?, ?);";

        final Connection connection = database.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, onlineTimeModel.getUuid().toString());
            statement.setLong(2, onlineTimeModel.getOnlineTime());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.disconnect();
        }

        return get(onlineTimeModel.getUuid());
    }

    public Database getDatabase() {
        return database;
    }
}
