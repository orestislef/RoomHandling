package gr.orestislef.roomhandling.room;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;

import gr.orestislef.roomhandling.room.tables.MyObj;

public class RoomThread {

    public static class Insert implements Runnable {
        private final RoomDB roomDB;
        private final MyObj myObj;
        private final CompleteListener completeListener;

        public Insert(RoomDB roomDB, MyObj myObj, CompleteListener completeListener) {
            this.roomDB = roomDB;
            this.myObj = myObj;
            this.completeListener = completeListener;
        }

        @Override
        public void run() {
            long id = roomDB.myDao().insert(myObj);
            new Handler(Looper.getMainLooper()).post(() -> completeListener.OnComplete(id));
        }

        public interface CompleteListener {
            void OnComplete(long id);
        }
    }

    public static class Delete implements Runnable {
        private final RoomDB roomDB;
        private final MyObj myObj;
        private final CompleteListener completeListener;

        public Delete(RoomDB roomDB, MyObj myObj, CompleteListener completeListener) {
            this.roomDB = roomDB;
            this.myObj = myObj;
            this.completeListener = completeListener;
        }

        @Override
        public void run() {
            if (myObj == null) {
                new Handler(Looper.getMainLooper()).post(completeListener::OnNotExist);
                return;
            }
            roomDB.myDao().delete(myObj);
            new Handler(Looper.getMainLooper()).post(completeListener::OnComplete);
        }

        public interface CompleteListener {
            void OnComplete();

            void OnNotExist();
        }
    }

    public static class Update implements Runnable {
        private final RoomDB roomDB;
        private final MyObj myObj;
        private final CompleteListener completeListener;

        public Update(RoomDB roomDB, MyObj myObj, CompleteListener completeListener) {
            this.roomDB = roomDB;
            this.myObj = myObj;
            this.completeListener = completeListener;
        }

        @Override
        public void run() {
            if (myObj == null) {
                new Handler(Looper.getMainLooper()).post(completeListener::OnNotExist);
                return;
            }
            roomDB.myDao().update(myObj);
            new Handler(Looper.getMainLooper()).post(completeListener::OnComplete);
        }

        public interface CompleteListener {
            void OnComplete();

            void OnNotExist();
        }
    }

    public static class GetMyObjById implements Runnable {
        private final RoomDB roomDB;
        private final int id;
        private final CompleteListener completeListener;

        public GetMyObjById(RoomDB roomDB, int id, CompleteListener completeListener) {
            this.roomDB = roomDB;
            this.id = id;
            this.completeListener = completeListener;
        }

        @Override
        public void run() {
            MyObj myObj = roomDB.myDao().getObjById(id);
            new Handler(Looper.getMainLooper()).post(() -> completeListener.OnComplete(myObj));
        }

        public interface CompleteListener {
            void OnComplete(MyObj myObj);
        }
    }

    public static class GetAllMyObjs implements Runnable {
        private final RoomDB roomDB;
        private final CompleteListener completeListener;

        public GetAllMyObjs(RoomDB roomDB, CompleteListener completeListener) {
            this.roomDB = roomDB;
            this.completeListener = completeListener;
        }

        @Override
        public void run() {
            ArrayList<MyObj> myObjs = new ArrayList<>(roomDB.myDao().getAllMyObjs());
            new Handler(Looper.getMainLooper()).post(() -> completeListener.OnComplete(myObjs));
        }

        public interface CompleteListener {
            void OnComplete(ArrayList<MyObj> myObjs);
        }
    }

}