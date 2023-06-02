package gr.orestislef.roomhandling.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import gr.orestislef.roomhandling.room.tables.MyObj;

@Dao
public interface MyDao {

    @Query("SELECT * FROM `obj`")
    List<MyObj> getAllMyObjs();

    @Insert
    long insert(MyObj myObj);

    @Update
    void update(MyObj myObj);

    @Delete
    void delete(MyObj myObj);

    @Query("SELECT * FROM " + "obj" + " WHERE `id` = :objId")
    MyObj getObjById(long objId);
}
