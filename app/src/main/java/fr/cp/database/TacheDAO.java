package fr.cp.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cp.fr.aplicationtodo.Tache;

/**
 * Created by Formation on 11/01/2018.
 */

public class TacheDAO {

    private DataBaseList db;
    private int position;
    Cursor cursor;
    String sql;


    public TacheDAO(DataBaseList db) {
        this.db = db;
    }

    /*public Tache findOneByID(long id) throws SQLiteException {
        String[] param = {String.valueOf(id)};
        String sql = "SELECT id, list_tache, afaire FROM contacts WHERE id=?";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, param);

        //Instancier un objet
        Tache tache = new Tache();

        //Hydratation du contact
        if (cursor.moveToNext()) {
            tache = hydrateTache(cursor);
        }
        cursor.close();
        return tache;
    }*/

    private  Tache hydrateTache(Cursor cursor) {
        Tache tache = new Tache();
        tache.setId(cursor.getLong(0));
        tache.setListtache(cursor.getString(1));
        tache.setAfaire(cursor.getInt(2));
        tache.setUser (cursor.getString(3));

        return tache;
    }

    public List<Tache> findALL(int position) {
        this.position = position;

       //instancier la liste des taches
        List<Tache> tacheList = new ArrayList<>();

        //executer la requete sql
      if (this.position == 3) {
          this.sql = "SELECT id, list_tache, afaire, user FROM taches";
          this.cursor = this.db.getReadableDatabase().rawQuery(sql, null);
      } else {
            if (this.position == 1) {
                this.sql = "SELECT id, list_tache, afaire, user FROM taches WHERE afaire = 1";
                this.cursor = this.db.getReadableDatabase().rawQuery(sql, null);
            }
            else {
                this.sql = "SELECT id, list_tache, afaire, user FROM taches WHERE afaire = 0";
                this.cursor = this.db.getReadableDatabase().rawQuery(sql, null);
        }}
        // boucle sur le curseur

        while (this.cursor.moveToNext()) {

            tacheList.add(this.hydrateTache(this.cursor));
        }

        //fermer cursor

        this.cursor.close();

        return tacheList;
    }

    public void deleteOneById(Long id) throws SQLiteException{
        String[] params = {id.toString()};
        String sql = "DELETE FROM taches WHERE id=?";
        this.db.getWritableDatabase().execSQL(sql, params);
    }

    public void insertTodo (SQLiteDatabase db) {

        if(this.db.isNew()) {

            String sql = "INSERT INTO taches (list_tache, afaire) VALUES (?,?,?)";
            //compilation de la requete
            SQLiteStatement statement = db.compileStatement(sql);

            // défintion des données et exécutions multiples de la requête
            statement.bindString(1, "Sortir le chat");
            statement.bindLong(2, 1);
            statement.bindString(3,"Toto");
            statement.executeInsert();

            // défintion des données et exécutions multiples de la requête
            statement.bindString(1, "Sortir la poubelle");
            statement.bindLong(2, 1);
            statement.bindString(3,"Toto");
            statement.executeInsert();
        }
    }

    public void upgarede() {

        SQLiteDatabase db = this.db.getWritableDatabase();
        //Début de la transaction
        db.beginTransaction();
        try {

            // la ou les commandes SQL a exécuter dans une transcation
            db.execSQL("ALTER TABLE taches ADD user TEXT");
            // definir le user
            db.execSQL("UPDATE taches SET user='papa'");

            //Definir que la Transaction est un succés
            db.setTransactionSuccessful();

        } catch (Exception ex){
            Log.d("DatabaseHandler", ex.getMessage());
        } finally {
            //finalisation de l a transaction
            db.endTransaction();
        }
    }
}
