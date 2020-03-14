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

    @Insert // Добовление объекта
    public long addGamer(Gamer gamer);

    @Update // обновление и редактирование записи
    public void updateGamer(Gamer gamer);

    @Delete // удаление объекта или записи
    public void deleteGamer(Gamer gamer);

    @Query("select * from gamer") // метод для извлечения всех записей из таблицы
    public List<Gamer> getAllGamer();

    @Query("select * from gamer where gamer_id ==:gamerId ")// возвращает один объект по id
    Gamer getGamerId(long gamerId);

    @Query("select * from gamer where gamer_ok ==:gamerOk ")
    Gamer getGamerOk(String gamerOk);
}
