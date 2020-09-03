package com.example.easybuy2.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easybuy2.Homes;
import com.example.easybuy2.R;


import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
private List<Item> items;
public ListAdapter (Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }
@Override
public int getCount() {
        return this.items.size();
    }
@Override
public Object getItem(int  i) {
        return this. items.get(i);
    }
@Override
public long getItemId(int i) {
        return this.items.get(i).getId();
    }
@Override
public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflate =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflate.inflate(R.layout.item_ui,null);
        }
        //TextView directions = (TextView)view.findViewById(R.id.titletxt);
        TextView description = (TextView)view.findViewById(R.id.description);
        TextView cantDormitorios = (TextView)view.findViewById(R.id.cantDormitorios);
        TextView cantBaños = (TextView)view.findViewById(R.id.cantBaños);
        TextView supTerreno = (TextView)view.findViewById(R.id.supTerreno);
        TextView precio = (TextView)view.findViewById(R.id.precio);
        TextView oferta = (TextView)view.findViewById(R.id.oferta);
        TextView yearCons = (TextView)view.findViewById(R.id.yearCons);

        ImageView img = (ImageView)view.findViewById(R.id.imagesource);
        //Glide.with(context).load(Services.HOST).into(primary_photo);
        //directions.setText(this.items.get(i).getDirections());
        description.setText(this.items.get(i).getDescription());
        cantDormitorios.setText(this.items.get(i).getNumDormitorios());
        cantBaños.setText(this.items.get(i).getNumBanos());
        supTerreno.setText(this.items.get(i).getSupTerreno());
        precio.setText(this.items.get(i).getPrecio());
        oferta.setText(this.items.get(i).getOferta());
        yearCons.setText(this.items.get(i).getYearCOns());

        Glide.with(this.context).load(items.get(i).getUrl()).into(img);
        return view;
    }
}
