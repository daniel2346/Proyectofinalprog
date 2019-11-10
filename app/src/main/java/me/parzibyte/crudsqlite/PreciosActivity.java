package me.parzibyte.crudsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreciosActivity extends AppCompatActivity {
    TextView txtsuma;
    GridView gridView;
    Double suma=0.0;

    String[] values = {


    } ;

    String[] precios = {


    } ;
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precios);

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerpre);

        final ProductoController dbBackend = new ProductoController(PreciosActivity.this);
        String[] spinnerLists = dbBackend.getAllSpinnerContent();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(PreciosActivity.this,android.R.layout.simple_spinner_item, spinnerLists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gridView = (GridView) findViewById(R.id.gridvp2);
        txtsuma=(TextView) findViewById(R.id.txtsumap2);

        Button button=(Button)findViewById(R.id.buttonp2);
        Button btnsummar=(Button)findViewById(R.id.btnsumarp2);
        final List<String> nombreslist = new ArrayList<String>(Arrays.asList(values));
        final List<String> precioslist = new ArrayList<String>(Arrays.asList(precios));

        final GridAdapter gridAdapter = new GridAdapter(this,nombreslist ,precioslist );
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog
                        .Builder(PreciosActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nombreslist.remove(position);
                                precioslist.remove(position);
                                gridAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar el producto seleccionado de la lista? ")
                        .create();
                dialog.show();
            }
        });
        btnsummar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suma=0.0;
                for (int indice=0;indice<precioslist.size();indice++){
                    suma=suma+ Double.parseDouble(precioslist.get(indice));
                }
                txtsuma.setText(""+suma);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombreslist.add(nombreslist.size(),spinner.getSelectedItem().toString());
                precioslist.add(precioslist.size(),dbBackend.obtenerprecio(spinner.getSelectedItem().toString()));
                gridAdapter.notifyDataSetChanged();


            }
        });
    }
}
