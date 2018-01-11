package cp.fr.aplicationtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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



        //instancier gestionnaire de base de données

        this.db = new DataBaseList(this);

        //instancier la liste des contacts
        this.dao = new TacheDAO(this.db);

        tacheListView = findViewById(R.id.taCheListView);
        tacheList = this.dao.findALL();
        TacheArrayAdapter contactAdapter = new TacheArrayAdapter(this, tacheList);
        tacheListView.setAdapter(contactAdapter);



    }

    public void onAddList(View view) {
        Intent FormIntent = new Intent(this, CreaActivity.class);
        startActivity(FormIntent);


    }
}
