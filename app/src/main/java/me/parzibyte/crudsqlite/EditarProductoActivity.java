package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.modelos.Producto;

public class EditarProductoActivity extends AppCompatActivity {
    private EditText etEditarNombre, etEditartipo,eteditarvelocidad,eteditarprecio,eteditarnucleo;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Producto producto;//La producto que vamos a estar editando
    private ProductoController productoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        productoController = new ProductoController(EditarProductoActivity.this);

        // Rearmar la producto
        // Nota: igualmente solamente podríamos mandar el id y recuperar la producto de la BD
        long idproducto = extras.getLong("idProducto");
        String nombreproducto = extras.getString("nombreProducto");
        String tipoproducto = extras.getString("tipoProducto");
        Double precio=extras.getDouble("precio");
        Double Nucleos=extras.getDouble("nucleos");
        Double velocidad=extras.getDouble("velocidad");


        producto = new Producto(nombreproducto, tipoproducto, velocidad,precio,Nucleos,idproducto);


        // Ahora declaramos las vistas
        etEditartipo = findViewById(R.id.etEditarTipo);
        etEditarNombre = findViewById(R.id.etEditarNombre);
        eteditarvelocidad = findViewById(R.id.etEditarVelocidad);
        eteditarprecio = findViewById(R.id.etEditarPrecio);
        eteditarnucleo = findViewById(R.id.etEditarNucelos);
        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionMascota);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosMascota);


        // Rellenar los EditText con los datos de la producto
        eteditarvelocidad.setText(String.valueOf(producto.getVelocidad()));
        eteditarprecio.setText(String.valueOf(producto.getPrecio()));
        eteditarnucleo.setText(String.valueOf(producto.getNucleos()));
        etEditarNombre.setText(producto.getNombre());
        etEditartipo.setText(producto.getTipo());

        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etEditarNombre.setError(null);
                etEditartipo.setError(null);
                // Crear la producto con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoNombre = etEditarNombre.getText().toString();
                String nuevotipo = etEditartipo.getText().toString();
                String nuevovelocidad = eteditarvelocidad.getText().toString();
                String nuevoprecios = eteditarprecio.getText().toString();
                String nuevonucleos = eteditarnucleo.getText().toString();

                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Escribe el nombre");
                    etEditarNombre.requestFocus();
                    return;
                }

                // Si no es entero, igualmente marcar error
                Double nuevavelocidad2,nuevoprecios2,nuevonucleos2;
                try {
                    nuevavelocidad2 = Double.parseDouble(nuevovelocidad);
                    nuevoprecios2 = Double.parseDouble(nuevoprecios);
                    nuevonucleos2 = Double.parseDouble(nuevonucleos);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                Producto productoConNuevosCambios = new Producto(nuevoNombre, nuevotipo,nuevavelocidad2,nuevoprecios2,nuevonucleos2, producto.getId());
                int filasModificadas = productoController.guardarCambios(productoConNuevosCambios);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarProductoActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }
}
