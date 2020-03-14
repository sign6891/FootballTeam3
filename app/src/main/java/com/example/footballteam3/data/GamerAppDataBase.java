package com.example.footballteam3.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.footballteam3.Gamer;

//Аннотацией Database помечаем основной класс по работе с базой данных.
// Этот класс должен быть абстрактным и наследовать RoomDatabase.
@Database(entities = {Gamer.class}, version = 1)
public abstract class GamerAppDataBase extends RoomDatabase {

    //Метод который возвращает объект ДАО, через который мы будем получать доступ к данным
    // в классе Gamer и манипулировать ими
    public abstract GamerDAO getGamerDAO();
}
