package fr.cp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

/**
 * Created by Formation on 11/01/2018.
 */

public class DataBaseList extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taChe_database";
    private static final int DATABASE_VERSION = 2;

    private static final String CONTACT_TABLE_SQL = "CREATE TABLE taches(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "list_tache TEXT NOT NULL," +
            "afaire INTEGER NOT NULL)";

    private Boolean isNew =false;
    private Boolean isUpdated =false;

    public DataBaseList(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONTACT_TABLE_SQL);
        this.isNew=true;

    }

    /**
     * Mise à jour de la base de données
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


        this.isUpdated = true;
    }

    public Boolean isNew() {
        return isNew;
    }

    public Boolean isUpdate() {
        return isUpdated;
    }


}
