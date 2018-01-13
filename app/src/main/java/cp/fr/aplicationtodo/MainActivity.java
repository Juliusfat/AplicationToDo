package cp.fr.aplicationtodo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.cp.database.DataBaseList;
import fr.cp.database.TacheDAO;

import static cp.fr.aplicationtodo.R.id.ListTaCheViewName;
import static cp.fr.aplicationtodo.R.id.taCheListView;


public class MainActivity extends AppCompatActivity {

    private DataBaseList db;
    private TacheDAO dao;
    private ListView tacheListView;
    private List<Tache> tacheList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialiser le spinner
        final Spinner spinner = (Spinner) findViewById(R.id.taCheSpinner);

        // Initialiser la liste de choix
        String[] choix = new String[]{
                "Choisir...",
                "Tache(s) à réaliser",
                "Tache(s) faite(s)",
                "Tout afficher",
         };
        final List<String> plantsList = new ArrayList<>(Arrays.asList(choix));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //int selectedItem = (int) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                   /* Toast.makeText
                            (getApplicationContext(), "Selected : " + position, Toast.LENGTH_SHORT)
                            .show();*/
                    accessDb(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        accessDb(3);



    }

    private void accessDb(int position) {
        this.position = position;

        //instancier gestionnaire de base de données
        this.db = new DataBaseList(this);

        //instancier la liste des contacts
        this.dao = new TacheDAO(this.db);



        tacheListView = findViewById(taCheListView);
        tacheList = this.dao.findALL(this.position);
        TacheArrayAdapter tacheAdapter = new TacheArrayAdapter(this, tacheList);
        tacheListView.setAdapter(tacheAdapter);
    }

    public void onAddList(View view) {
        Intent FormIntent = new Intent(this, CreaActivity.class);
        startActivity(FormIntent);


    }

    //Fonction appelée au clic d'une des checkbox
    public void onItemClick(View v) {
        CheckBox cb = (CheckBox) v;

        Integer afaire;
        //on récupère la position à l'aide du tag défini dans la classe MyListAdapter
        int position = Integer.parseInt(cb.getTag().toString());

        // On récupère l'élément sur lequel on va changer la couleur
        View o = tacheListView.getChildAt(position).findViewById(
                R.id.blocCheck);

        //On change la couleur
        if (cb.isChecked()) {
            o.setBackgroundResource(R.color.Blé);
            afaire = 0;
        } else {
            o.setBackgroundResource(R.color.Céleste);
            afaire = 1;
        }
        Tache task = this.tacheList.get(position);
        TextView askafaire = o.findViewById(R.id.ListTaCheViewName);
        String tache = askafaire.getText().toString();

        ContentValues insertValues = new ContentValues();
        insertValues.put("list_tache", tache);
        insertValues.put("afaire", afaire);


        String[] params = {task.getId().toString()};

        Toast.makeText(this, "id =" + tache + " valeur " + afaire, Toast.LENGTH_SHORT).show();
        try {
            this.db.getWritableDatabase().update(
                    "taches",
                    insertValues,
                    "id=?",
                    params);
            Toast.makeText(this, "MAJ OK", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException ex) {
        Log.e("SQL EXCEPTION", ex.getMessage());
    }


    }

    public void onDelete (View v) {

        //Récupération de la position taguée
        int position = (int) v.getTag();
        Tache task = this.tacheList.get(position);
        //Affichage de la confirmation
        AlertDialog dialog = getConfirmDeleteDialog(task.getId());
        dialog.show();
        accessDb(3);
    }

    private AlertDialog getConfirmDeleteDialog(final Long id){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Voulez-vous vraiment supprimer cette tâche ?");
        //Gestion de la confirmation OK
        dialogBuilder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dao.deleteOneById(id);
                dialog.dismiss();
            }
        });

        //Gestion de la confirmation KO
        dialogBuilder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        return dialogBuilder.create();
    }




}
