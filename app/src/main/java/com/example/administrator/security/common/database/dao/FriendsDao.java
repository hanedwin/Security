package com.example.administrator.security.common.database.dao;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 好友表
 */

@DatabaseTable
public class FriendsDao {
    @DatabaseField
    private String friendId;
    @DatabaseField
    private String friendName;

}
