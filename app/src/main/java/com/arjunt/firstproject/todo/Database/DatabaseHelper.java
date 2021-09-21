package com.arjunt.firstproject.todo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arjunt.firstproject.todo.DataModel.ToDoItem;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME ="todo_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TODO = "todo_list";

    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    private static final String CREATE_TABLE_TODO= " CREATE TABLE "+
            TABLE_TODO + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+"title TEXT NOT NULL,"
            + "date TEXT NOT NULL,"+"time TEXT NOT NULL);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS '"+ TABLE_TODO+ "'");
        onCreate(db);
    }

    public long addToDoItem(ToDoItem toDoItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",toDoItem.getTitle());
        values.put("date",toDoItem.getDate());
        values.put("time",toDoItem.getTime());

        long id= db.insertWithOnConflict(TABLE_TODO,null,values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        return id;
    }

    public void deleteToDoItem(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO,"id=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<ToDoItem> getAllToDoItems()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ToDoItem> toDoItems = new ArrayList<>();

        String selectQuery = " SELECT * FROM "+ TABLE_TODO + " ORDER BY id DESC ";

        Cursor c = db.rawQuery(selectQuery, null);
            if(c.moveToFirst())
            {
                do{
                    ToDoItem toDoItem= new ToDoItem();
                    toDoItem.setId(c.getInt(c.getColumnIndex("id")));
                    toDoItem.setTitle(c.getString(c.getColumnIndex("title")));
                    toDoItem.setDate(c.getString(c.getColumnIndex("date")));
                    toDoItem.setTime(c.getString(c.getColumnIndex("time")));

                    toDoItems.add(toDoItem);
                }
                while(c.moveToNext());
            }
            c.close();
            db.close();
            return toDoItems;
    }

}
