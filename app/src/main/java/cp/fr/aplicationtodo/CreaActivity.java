package cp.fr.aplicationtodo;

import android.app.ActionBar;
import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.cp.database.DataBaseList;

public class CreaActivity extends AppCompatActivity {

    private EditText newTaCheText;
    private int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea);

        this.newTaCheText = findViewById(R.id.newTaCheText);
    }

    public void onValid(View view) {

        String name = this.newTaCheText.getText().toString();
        check = 0;

        //Instanciation de connexion à la base de données
        DataBaseList db = new DataBaseList(this);

        // initialisation des valeurs
        ContentValues insertValues = new ContentValues();
        insertValues.put("list_tache", name);
        insertValues.put("afaire", check);

        // initialisation base de données
    try {
        db.getWritableDatabase().insert("taches", null, insertValues);
        Toast.makeText(this, "Insertion OK", Toast.LENGTH_SHORT).show();
        ((EditText) findViewById(R.id.newTaCheText)).setText("");
    } catch (SQLiteException ex) {
        Log.e("SQL EXCEPTION", ex.getMessage());
    }


        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }
}
