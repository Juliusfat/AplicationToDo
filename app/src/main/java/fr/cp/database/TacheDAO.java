package fr.cp.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import cp.fr.aplicationtodo.Tache;

/**
 * Created by Formation on 11/01/2018.
 */

public class TacheDAO {

    private DataBaseList db;

    public TacheDAO(DataBaseList db) {
        this.db = db;
    }

    public Tache findOneByID(long id) throws SQLiteException {
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
    }

    private  Tache hydrateTache(Cursor cursor) {
        Tache tache = new Tache();
        tache.setId(cursor.getLong(0));
        tache.setListtache(cursor.getString(1));
        tache.setAfaire(cursor.getInt(2));

        return tache;
    }

    public List<Tache> findALL() {

        //instancier la liste des taches
        List<Tache> contactList = new ArrayList<>();

        //executer la requete sql

        String sql = "SELECT id, list_tache, afaire FROM taches";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql,null);

        // boucle sur le curseur

        while (cursor.moveToNext()) {

            contactList.add(this.hydrateTache(cursor));
        }

        //fermer cursor

        cursor.close();

        return contactList;
    }
}
