package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import me.parzibyte.crudsqlite.modelos.Producto;

public class AgregarProductoActivity extends AppCompatActivity {
    private Button btnAgregarMascota, btnCancelarNuevaMascota;
    private EditText etNombre,etVelocidad,etPrecio,etNucelos;
    private ProductoController productoController;
    Double etnucleos2=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);

        etVelocidad = findViewById(R.id.etVelocidad);
        etPrecio = findViewById(R.id.etPrecio);
        etNucelos = findViewById(R.id.etNucelos);
        btnAgregarMascota = findViewById(R.id.btnAgregarMascota);
        btnCancelarNuevaMascota = findViewById(R.id.btnCancelarNuevaMascota);
        // Crear el controlador
        productoController = new ProductoController(AgregarProductoActivity.this);
        final Spinner spinel = (Spinner) findViewById(R.id.spinnerp);


        String[] arraySpinner = new String[] {
                "Procesador", "Tarjeta Gráfica"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinel.setAdapter(adapter);

        spinel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getSelectedItem().toString();
                if (selectedItem.equals("Tarjeta Gráfica")) {
                    etNucelos.setVisibility(View.INVISIBLE);
                    etNucelos.setText("0.0");

                }else if (selectedItem.equals("Procesador")){
                    etNucelos.setVisibility(View.VISIBLE);

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Agregar listener del botón de guardar
        btnAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                etNombre.setError(null);

                etVelocidad.setError(null);
                etPrecio.setError(null);
                etNucelos.setError(null);
                String nombre = etNombre.getText().toString();
                        String tipo = spinel.getSelectedItem().toString();

                String velocidad=etVelocidad.getText().toString();
                Double velocidad2=Double.parseDouble(velocidad);
                String precio=etPrecio.getText().toString();
                Double precio2=Double.parseDouble(precio);
                String nucleos=etNucelos.getText().toString();
                etnucleos2=Double.parseDouble(nucleos);



                if ("".equals(nombre)) {
                    etNombre.setError("Escribe el nombre de la mascota");
                    etNombre.requestFocus();
                    return;
                }


                // Ver si es un entero

                // Ya pasó la validación
                Producto nuevaProducto = new Producto(nombre, tipo,velocidad2,precio2,etnucleos2);
                long id = productoController.nuevaMascota(nuevaProducto);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarProductoActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }
            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
