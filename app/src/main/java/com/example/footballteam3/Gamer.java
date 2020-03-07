package com.example.footballteam3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gamer")
public class Gamer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gamer_id")
    private int id;

    @ColumnInfo(name = "gamer_name")
    private String gamerName;

    @ColumnInfo(name = "gamer_skills")
    private String gamerSkills;

    @ColumnInfo(name = "gamer_false")
    private boolean gamerFalse;

    public Gamer(int id, String gamerName, String gamerSkills, boolean gamerFalse) {
        this.id = id;
        this.gamerName = gamerName;
        this.gamerSkills = gamerSkills;
        this.gamerFalse = gamerFalse;
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

    public boolean isGamerFalse() {
        return gamerFalse;
    }

    public void setGamerFalse(boolean gamerFalse) {
        this.gamerFalse = gamerFalse;
    }
}
