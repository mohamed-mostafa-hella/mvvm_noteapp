package com.example.mvvm_noteapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = Note.class , version = 1)
public abstract class NoteDateBase extends RoomDatabase {

    private static NoteDateBase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDateBase getInstance(Context context){
        if(instance ==null){
            instance= Room.databaseBuilder(context.getApplicationContext() ,
                    NoteDateBase.class , "note_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynctask(instance).execute();
        }
    };

    private static class PopulateDbAsynctask extends AsyncTask<Void , Void , Void>{
        private NoteDao noteDao;

        public PopulateDbAsynctask(NoteDateBase noteDateBase) {
            noteDao=noteDateBase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("welcome" , "welcome to our app" , 1));
            return null;
        }
    }

}
