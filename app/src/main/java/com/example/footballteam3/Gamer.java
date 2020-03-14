package com.example.footballteam3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//Аннотацией Entity нам необходимо пометить объект, который мы хотим хранить в базе данных.
@Entity(tableName = "gamer")
public class Gamer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gamer_id")
    private int id;

    @ColumnInfo(name = "gamer_name")
    private String gamerName;

    @ColumnInfo(name = "gamer_skills")
    private String gamerSkills;

    @ColumnInfo(name = "gamer_ok")
    private String gamerOk;
    @Ignore
    public Gamer(int id, String gamerName, String gamerSkills) {
        this.id = id;
        this.gamerName = gamerName;
        this.gamerSkills = gamerSkills;
    }

    public Gamer(int id, String gamerName, String gamerSkills, String gamerOk) {
        this.id = id;
        this.gamerName = gamerName;
        this.gamerSkills = gamerSkills;
        this.gamerOk = gamerOk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGamerName() {
        return gamerName;
    }

    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public String getGamerSkills() {
        return gamerSkills;
    }

    public void setGamerSkills(String gamerSkills) {
        this.gamerSkills = gamerSkills;
    }

    public String getGamerOk() {
        return gamerOk;
    }

    public void setGamerOk(String gamerOk) {
        this.gamerOk = gamerOk;
    }
}
