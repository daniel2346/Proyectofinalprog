package me.parzibyte.crudsqlite;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    Context context;
    List<String> listanombres;
    List<String> listaprecios;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, List<String> listanombres, List<String> listaprecios) {
        this.context = context;
        this.listanombres = listanombres;
        this.listaprecios = listaprecios;
    }

    public GridAdapter() {
    }

    @Override
    public int getCount() {
        return listanombres.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Holder holder = new Holder();
        View rowView;

        rowView = layoutInflater.inflate(R.layout.grid_view_items, null);
        holder.tvnombre =(TextView) rowView.findViewById(R.id.txtnombre3);
        holder.tvprecio = (TextView) rowView.findViewById(R.id.txtprecio3);

        holder.tvnombre.setText(listanombres.get(position));
        holder.tvprecio.setText(listaprecios.get(position));




        return rowView;
    }

    public class Holder
    {
        TextView tvnombre;
        TextView tvprecio;
    }

}






