package cp.fr.aplicationtodo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Callable;

import static cp.fr.aplicationtodo.R.layout.tache_list_view;

/**
 * Created by Formation on 11/01/2018.
 */

public class TacheArrayAdapter extends ArrayAdapter{
    private Activity context;
    private List<Tache> data;
    private int resource;
    private LayoutInflater inflater;


    public TacheArrayAdapter(@NonNull Context context, @NonNull List<Tache> data) {
        super(context,0, data);

        // création du constructeur
        this.data = data;
        this.resource =  resource;
        this.context = (Activity) context;
        this.inflater = this.context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        // instenciation de la vue

        View view = this.inflater.inflate(R.layout.tache_list_view,parent,false);

        Tache tachedata = this.data.get(position);
        // liaison entre les données et la ligne
        TextView tachetextview = view .findViewById((R.id.ListTaCheViewName));
        tachetextview.setText(tachedata.getListtache());


        return view;

    }



}
