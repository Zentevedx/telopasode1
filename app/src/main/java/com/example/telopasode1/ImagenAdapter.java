package com.example.telopasode1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder> {
    private Context context;
    private ArrayList<String> imagenes;

    public ImagenAdapter(Context context, ArrayList<String> imagenes) {
        this.context = context;
        this.imagenes = imagenes;
    }

    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_imagen, parent, false);
        return new ImagenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {
        String urlImagen = imagenes.get(position);
        // Cargar la imagen usando Glide o Picasso
        Glide.with(context).load(urlImagen).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public static class ImagenViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;

        public ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenAnuncio);
        }
    }
}
