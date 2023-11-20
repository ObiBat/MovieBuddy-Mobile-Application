package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DataBaseManager {
    public static final String DB_NAME = "MoviesBuddy";
    public static final String DB_TABLE = "Movies";
    public static final String DB_TABLE_CINEMAS = "Cinemas";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (ID TEXT, title TEXT, directors TEXT, casts TEXT, release_date TEXT, poster TEXT);";
    private static final String CREATE_CINEMAS_TABLE = "CREATE TABLE " + DB_TABLE_CINEMAS + " (ID TEXT, name TEXT, location TEXT, movies TEXT);";

    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    private String whereClause;

    public DataBaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DataBaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }


    // Add data to the database
    public boolean addRow(String a, String b, String c, String d, String e, String h) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            newProduct.put("ID", a);
            newProduct.put("title", b);
            newProduct.put("directors", c);
            newProduct.put("casts", d);
            newProduct.put("release_date", e);
            newProduct.put("poster", h);

            try {
                db.insertOrThrow(DB_TABLE, null, newProduct);
            } catch (Exception o) {
                Log.e("Error in inserting rows", e.toString());
                o.printStackTrace();
                return false;
            }
            // db.close();
            return true;

        }
    }

    public boolean addCinema(String id, String name, String location, String st) {
        synchronized(this.db) {
            ContentValues newCinema = new ContentValues();
            newCinema.put("ID", id);
            newCinema.put("name", name);
            newCinema.put("location", location);
            newCinema.put("movies", st);

            try {
                db.insertOrThrow(DB_TABLE_CINEMAS, null, newCinema);
            } catch (Exception e) {
                Log.e("Error in inserting cinema", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }


    //Retrieve Row from the Table
    public ArrayList<String> retrieveRows() { //query the database and return records as a text
        ArrayList<String> productRows = new ArrayList<>();
        String[] columns = new String[] {"ID", "title", "directors", "casts", "release_date", "poster"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4) + ", " + cursor.getString(5));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }


    public ArrayList<String> retrieveCinemas() {
        ArrayList<String> cinemaList = new ArrayList<>();
        String[] columns = new String[] {"ID", "name", "location", "movies"};
        Cursor cursor = db.query(DB_TABLE_CINEMAS, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cinemaList.add(cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return cinemaList;
    }


    // Edit a row item
    public boolean UpdateRows(String id, String b, String c, String d, String e, String h) {
        synchronized (this.db) { //update

            ContentValues values = new ContentValues();
            values.put("title", b);
            values.put("directors", c);
            values.put("casts", d);
            values.put("release_date", e);
            values.put("poster", h);

            try {
                db.update(DB_TABLE, values, "ID =" + id, null);
            } catch (Exception o) {
                Log.e("Error in updating rows", e.toString());
                o.printStackTrace();
                return false;
            }
//            db.close();
            return true;

        }
    }


    public boolean editCinema(String id, String name, String location, String selectedMovies) {
        synchronized(this.db) {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("location", location);
            values.put("movies", selectedMovies);

            try {
                db.update(DB_TABLE_CINEMAS, values, "ID = " + id, null);
            } catch (Exception e) {
                Log.e("Error in updating cinema", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }


    // Delete Row from the table

    public boolean deleteRow(String id) {
        synchronized(this.db) {
            try {
                int rowsDeleted = db.delete(DB_TABLE, "ID = ?", new String[]{id});
                return rowsDeleted > 0;
            } catch (Exception e) {
                Log.e("Error in deleting row", e.toString());
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean deleteCinema(String id) {
        synchronized(this.db) {
            try {
                int rowsDeleted = db.delete(DB_TABLE_CINEMAS, "ID = ?", new String[]{id});
                return rowsDeleted > 0;
            } catch (Exception e) {
                Log.e("Error in deleting cinema", e.toString());
                e.printStackTrace();
                return false;
            }
        }
    }



// Clear record of the table
    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

    public void clearRecords_cinema() {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE_CINEMAS, null, null);
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("DatabaseManager", "Creating tables: " + CREATE_TABLE + ", " + CREATE_CINEMAS_TABLE);
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_CINEMAS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");

            // Drop and recreate the "Movies" table
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);

            // Drop and recreate the "Cinemas" table
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CINEMAS);
            onCreate(db);
        }

    }
}
