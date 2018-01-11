package cp.fr.aplicationtodo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.cp.database.DataBaseList;
import fr.cp.database.TacheDAO;


public class MainActivity extends AppCompatActivity {

    private DataBaseList db;
    private TacheDAO dao;
    private ListView tacheListView;
    private List<Tache> tacheList;

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
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //instancier gestionnaire de base de données

        this.db = new DataBaseList(this);

        //instancier la liste des contacts
        this.dao = new TacheDAO(this.db);

        tacheListView = findViewById(R.id.taCheListView);
        tacheList = this.dao.findALL();
        TacheArrayAdapter tacheAdapter = new TacheArrayAdapter(this, tacheList);
        tacheListView.setAdapter(tacheAdapter);



    }

    public void onAddList(View view) {
        Intent FormIntent = new Intent(this, CreaActivity.class);
        startActivity(FormIntent);


    }

}
