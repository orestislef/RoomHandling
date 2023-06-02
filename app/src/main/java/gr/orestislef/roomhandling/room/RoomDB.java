package gr.orestislef.roomhandling.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import gr.orestislef.roomhandling.room.tables.MyObj;

@Database(entities = MyObj.class, version = 1)
public abstract class RoomDB extends RoomDatabase {
    public abstract MyDao myDao();
}
