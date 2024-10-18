package com.example.telopasode1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {

    private ArrayList<Anuncio> anunciosList;
    private Context context;

    public AnuncioAdapter(ArrayList<Anuncio> anunciosList, Context context) {
        this.anunciosList = anunciosList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnuncioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_anuncio, parent, false);
        return new AnuncioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioViewHolder holder, int position) {
        Anuncio anuncio = anunciosList.get(position);

        // Mostrar los datos del anuncio
        holder.tvTitulo.setText(anuncio.getTitulo());
        holder.tvFecha.setText(anuncio.getFechaActualizacion());
        holder.tvPrecio.setText(anuncio.getPrecio() != null ? String.valueOf(anuncio.getPrecio()) : "Sin precio");

        // Mostrar la primera imagen (si existe) usando Glide
        if (!anuncio.getImagenes().isEmpty()) {
            String imageUrl = anuncio.getImagenes().get(0); // Obtener la primera imagen
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.logo_proyecto) // Imagen por defecto mientras se carga
                    .error(R.drawable.logo_proyecto)      // Imagen por defecto si falla la carga
                    .into(holder.ivAnuncio);           // ivAnuncio es tu ImageView en el layout del RecyclerView
        } else {
            // Si no hay imagen, mostrar una imagen por defecto
            holder.ivAnuncio.setImageResource(R.drawable.logo_proyecto);
        }
    }

    @Override
    public int getItemCount() {
        return anunciosList.size();
    }

    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo, tvFecha, tvPrecio;
        ImageView ivAnuncio;

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            tvPrecio = itemView.findViewById(R.id.tv_precio);
            ivAnuncio = itemView.findViewById(R.id.img_item);
        }
    }
}
