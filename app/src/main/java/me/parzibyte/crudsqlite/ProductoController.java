package me.parzibyte.crudsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.parzibyte.crudsqlite.AyudanteBaseDeDatos;
import me.parzibyte.crudsqlite.modelos.Producto;


public class ProductoController {
    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "inventario";
    private SQLiteDatabase db;
    public ProductoController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }
    public SQLiteDatabase getDbConnection(){
        return this.db;
    }


    public int eliminarMascota(Producto producto) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(producto.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaMascota(Producto producto) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", producto.getNombre());
        valoresParaInsertar.put("tipo", producto.getTipo());
        valoresParaInsertar.put("velocidad", producto.getVelocidad());
        valoresParaInsertar.put("precio", producto.getPrecio());
        valoresParaInsertar.put("nucleos", producto.getNucleos());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Producto productoEditada) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", productoEditada.getNombre());
        valoresParaActualizar.put("tipo", productoEditada.getTipo());
        valoresParaActualizar.put("velocidad", productoEditada.getVelocidad());
        valoresParaActualizar.put("precio", productoEditada.getPrecio());
        valoresParaActualizar.put("nucleos", productoEditada.getNucleos());
        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idMascota
        String[] argumentosParaActualizar = {String.valueOf(productoEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Producto> obtenerMascotas() {
        ArrayList<Producto> productos = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"nombre", "tipo", "velocidad", "precio", "nucleos", "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from productos
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return productos;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return productos;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de productos
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String nombreObtenidoDeBD = cursor.getString(0);
            String tipoObtenidaDeBD = cursor.getString(1);
            Double velocidadObtenidaDeBD = cursor.getDouble(2);
            Double precioObtenidaDeBD = cursor.getDouble(3);
            Double nucleosObtenidaDeBD = cursor.getDouble(4);
            Integer idObtenidaDeBD = cursor.getInt(5);

            long idMascota = cursor.getLong(5);
            Producto productoObtenidaDeBD = new Producto(nombreObtenidoDeBD, tipoObtenidaDeBD, velocidadObtenidaDeBD, precioObtenidaDeBD, nucleosObtenidaDeBD, idObtenidaDeBD);
            productos.add(productoObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de productos :)
        cursor.close();
        return productos;
    }

    public String[] getAllSpinnerContent(){
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        String query = "Select * from inventario";
        Cursor cursor = baseDeDatos.rawQuery(query, null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                spinnerContent.add(word);
            }while(cursor.moveToNext());
        }
        cursor.close();

        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        return allSpinner;
    }
    public String obtenerprecio(String consulta){
        String word="";
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        String query = "Select precio from inventario where nombre='"+consulta+"'";
        Cursor cursor = baseDeDatos.rawQuery(query, null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                 word = cursor.getString(cursor.getColumnIndexOrThrow("precio"));
                spinnerContent.add(word);
            }while(cursor.moveToNext());
        }
        cursor.close();



        return word;
    }
}