package me.parzibyte.crudsqlite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.parzibyte.crudsqlite.modelos.Producto;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.MyViewHolder> {

    private List<Producto> listaDeProductos;

    public void setListaDeProductos(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
    }

    public AdaptadorProductos(List<Producto> productos) {
        this.listaDeProductos = productos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaMascota = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_producto, viewGroup, false);
        return new MyViewHolder(filaMascota);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener la producto de nuestra lista gracias al índice i
        Producto producto = listaDeProductos.get(i);

        // Obtener los datos de la lista
        String nombreMascota = producto.getNombre();
        String tipoproducto = producto.getTipo();
        Double velocidad= producto.getVelocidad();
        Double precio= producto.getPrecio();
        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombreMascota);
        myViewHolder.tipo.setText(tipoproducto);
        myViewHolder.velocidad.setText(String.valueOf(velocidad));
        myViewHolder.precio.setText(String.valueOf(precio));
    }

    @Override
    public int getItemCount() {
        return listaDeProductos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, tipo,velocidad,precio;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.tipo = itemView.findViewById(R.id.tvTipo);
            this.velocidad = itemView.findViewById(R.id.tvvelocidad);
            this.precio = itemView.findViewById(R.id.tvprecio);
        }
    }
}
