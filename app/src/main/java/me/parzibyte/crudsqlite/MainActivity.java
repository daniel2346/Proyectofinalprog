package me.parzibyte.crudsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.parzibyte.crudsqlite.modelos.Producto;

public class MainActivity extends AppCompatActivity {
    private List<Producto> listaDeProductos;
    private RecyclerView recyclerView;
    private AdaptadorProductos adaptadorProductos;
    private ProductoController productoController;
    private FloatingActionButton fabAgregarMascota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ojo: este código es generado automáticamente, pone la vista y ya, pero
        // no tiene nada que ver con el código que vamos a escribir
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Lo siguiente sí es nuestro ;)
        // Definir nuestro controlador
        productoController = new ProductoController(MainActivity.this);

        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewMascotas);
        fabAgregarMascota = findViewById(R.id.fabAgregarMascota);


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeProductos = new ArrayList<>();
        adaptadorProductos = new AdaptadorProductos(listaDeProductos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorProductos);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeMascotas();

        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarProductoActivity.java
                Producto productoSeleccionada = listaDeProductos.get(position);
                Intent intent = new Intent(MainActivity.this, EditarProductoActivity.class);
                intent.putExtra("idProducto", productoSeleccionada.getId());
                intent.putExtra("nombreProducto", productoSeleccionada.getNombre());
                intent.putExtra("velocidad", productoSeleccionada.getVelocidad());
                intent.putExtra("tipoProducto", productoSeleccionada.getTipo());
                intent.putExtra("precio", productoSeleccionada.getPrecio());
                intent.putExtra("nucleos", productoSeleccionada.getNucleos());
                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Producto productoParaEliminar = listaDeProductos.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                productoController.eliminarMascota(productoParaEliminar);
                                refrescarListaDeMascotas();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar a la mascota " + productoParaEliminar.getNombre() + "?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        fabAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this, AgregarProductoActivity.class);
                startActivity(intent);
            }
        });

        // Créditos
        fabAgregarMascota.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Acerca de")
                        .setMessage("CRUD de Android con SQLite creado por parzibyte [parzibyte.me]\n\nIcons made by Freepik from www.flaticon.com ")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo, int which) {
                                dialogo.dismiss();
                            }
                        })
                        .setPositiveButton("Sitio web", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parzibyte.me"));
                                startActivity(intentNavegador);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeMascotas();
    }

    public void refrescarListaDeMascotas() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorProductos == null) return;
        listaDeProductos = productoController.obtenerMascotas();
        adaptadorProductos.setListaDeProductos(listaDeProductos);
        adaptadorProductos.notifyDataSetChanged();
    }
}
