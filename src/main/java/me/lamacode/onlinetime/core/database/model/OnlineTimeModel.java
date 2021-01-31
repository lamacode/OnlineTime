package me.lamacode.onlinetime.core.database.model;

import java.util.UUID;

/**
 * @author lamacode
 * @since 31.01.2021 14:42
 * Copyright © 2021 | lamacode | All rights reserved.
 */

public class OnlineTimeModel {

    private final UUID uuid;

    private long onlineTime;

    private long calcTime = onlineTime;

    private long joinTime;

    public OnlineTimeModel(UUID uuid) {
        this.uuid = uuid;
    }

    public OnlineTimeModel(UUID uuid, long onlineTime) {
        this.uuid = uuid;
        this.onlineTime = onlineTime;
    }

    public long getOnlineTime() {
        return onlineTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public void calculateTime() {
        onlineTime = calcTime;
    }

    public void calculateTimeLocal() {
        calcTime = onlineTime + (System.currentTimeMillis() - joinTime);
    }

    public long getCalcTime() {
        return calcTime;
    }
}
