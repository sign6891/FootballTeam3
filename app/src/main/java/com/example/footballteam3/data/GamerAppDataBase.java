package com.example.footballteam3.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.footballteam3.Gamer;

@Database(entities = {Gamer.class}, version = 1)
public abstract class GamerAppDataBase extends RoomDatabase {

    public abstract GamerDAO getGamerDAO();
}
