package gr.orestislef.roomhandling;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

import gr.orestislef.roomhandling.adapters.MyObjRVAdapter;
import gr.orestislef.roomhandling.room.RoomDB;
import gr.orestislef.roomhandling.room.RoomThread;
import gr.orestislef.roomhandling.room.tables.MyObj;
import gr.orestislef.roomhandling.utils.RandomTextGenerator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText nameET, idET;
    static RoomDB database;
    static RecyclerView myObjRV;
    static MyObjRVAdapter myObjRVAdapter;

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(ID_KEY, idET.getText().toString());
        outState.putString(NAME_KEY, nameET.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(), RoomDB.class, "myDatabase")
                .build();

        idET = findViewById(R.id.idET);
        nameET = findViewById(R.id.inputET);

        if (savedInstanceState != null) {
            idET.setText(savedInstanceState.getString(ID_KEY));
            nameET.setText(savedInstanceState.getString(NAME_KEY));
        }

        Button insertBTN, deleteBTN, updateBTN, fakeBTN;
        insertBTN = findViewById(R.id.insertBTN);
        deleteBTN = findViewById(R.id.deleteBTN);
        updateBTN = findViewById(R.id.updateBTN);
        fakeBTN = findViewById(R.id.fakeBTN);

        insertBTN.setOnClickListener(this::insert);
        deleteBTN.setOnClickListener(this::delete);
        updateBTN.setOnClickListener(this::update);
        fakeBTN.setOnClickListener(this::insertFake);

        myObjRV = findViewById(R.id.my_objRV);
        myObjRV.setLayoutManager(new LinearLayoutManager(this));
        myObjRVAdapter = new MyObjRVAdapter(new ArrayList<>());
        myObjRV.setAdapter(myObjRVAdapter);

        showMyObjs();
    }

    private void showMyObjs() {
        RoomThread.GetAllMyObjs getAllMyObjsThread = new RoomThread.GetAllMyObjs(database, myObjs ->
                myObjRVAdapter.add(myObjs));
        Thread thread = new Thread(getAllMyObjsThread);
        thread.start();

    }

    private void insert(View view) {
        String name = nameET.getText().toString();
        MyObj myObj = new MyObj();
        myObj.setName(name);

        RoomThread.Insert insertThread = new RoomThread.Insert(database, myObj, id ->
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Inserted with id: " + id, Toast.LENGTH_SHORT).show();
                    showMyObjs();
                }));
        Thread thread = new Thread(insertThread);
        thread.start();
    }

    private void insertFake(View view) {
        int amount = 100;
        for (int i = 0; i < amount; i++) {
            MyObj myObj = new MyObj();
            myObj.setName(RandomTextGenerator.generateRandomText(8));
            RoomThread.Insert insertThread = new RoomThread.Insert(database, myObj, id ->
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Inserted with id: " + id, Toast.LENGTH_SHORT).show();
                        showMyObjs();
                    }));
            Thread thread = new Thread(insertThread);
            thread.start();
        }
    }

    private void delete(View view) {
        String idString = idET.getText().toString();
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            Log.e(TAG, "delete: ", e);
            return;
        }

        RoomThread.GetMyObjById getMyObjById = new RoomThread.GetMyObjById(database, id, myObj -> {
            RoomThread.Delete deleteThread = new RoomThread.Delete(database, myObj, new RoomThread.Delete.CompleteListener() {
                @Override
                public void OnComplete() {
                    Toast.makeText(MainActivity.this, "deleted id: " + myObj.getId(), Toast.LENGTH_SHORT).show();
                    showMyObjs();
                }

                @Override
                public void OnNotExist() {
                    Toast.makeText(MainActivity.this, "not existed", Toast.LENGTH_SHORT).show();
                }
            });
            Thread thread = new Thread(deleteThread);
            thread.start();

        });
        Thread thread = new Thread(getMyObjById);
        thread.start();
    }

    private void update(View view) {
        String name = nameET.getText().toString();
        String idString = idET.getText().toString();
        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            Log.e(TAG, "delete: ", e);
            return;
        }

        RoomThread.GetMyObjById getMyObjByIdThread = new RoomThread.GetMyObjById(database, id, myObj -> {
            if (myObj != null)
                myObj.setName(name);
            RoomThread.Update updateThread = new RoomThread.Update(database, myObj, new RoomThread.Update.CompleteListener() {
                @Override
                public void OnComplete() {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        showMyObjs();
                    });
                }

                @Override
                public void OnNotExist() {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "Not exist", Toast.LENGTH_SHORT).show());
                }
            });
            Thread thread = new Thread(updateThread);
            thread.start();
        });
        Thread thread = new Thread(getMyObjByIdThread);
        thread.start();
    }
}