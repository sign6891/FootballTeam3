package com.example.footballteam3.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.footballteam3.Gamer;

import java.util.List;

@Dao //В объекте Dao мы будем описывать методы для работы с базой данных.
public interface GamerDAO {

    @Insert
    public long addGamer(Gamer gamer);

    @Update
    public void updateGamer(Gamer gamer);

    @Delete
    public void deleteGamer(Gamer gamer);

    @Query("select * from gamer")
    public List<Gamer> getAllGamer();

    @Query("select * from gamer where gamer_id ==:gamerId ")
    Gamer getGamer(long gamerId);

    @Query("select * from gamer where gamer_ok ==:gamerOk ")
    Gamer getGamerOk(String gamerOk);
}
