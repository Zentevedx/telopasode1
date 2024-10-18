package com.example.telopasode1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnuncioAdapter2 extends RecyclerView.Adapter<AnuncioAdapter2.AnuncioViewHolder> {
    private Context context;
    private List<Anuncio> anuncios;

    public AnuncioAdapter2(Context context, List<Anuncio> anuncios) {
        this.context = context;
        this.anuncios = anuncios;
    }

    @NonNull
    @Override
    public AnuncioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ad_xpand, parent, false);
        return new AnuncioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioViewHolder holder, int position) {
        Anuncio anuncio = anuncios.get(position);
        holder.tituloAnuncio.setText(anuncio.getTitulo());
        holder.descripcionAnuncio.setText(anuncio.getDescripcion());
        holder.precioAnuncio.setText("Precio: " + anuncio.getPrecio());
        holder.fechaAnuncio.setText("Actualizado: " + anuncio.getFechaActualizacion());

        // Verificar si hay imágenes
        if (anuncio.getImagenes() != null && !anuncio.getImagenes().isEmpty()) {
            // Mostrar el RecyclerView de imágenes
            holder.recyclerImagenes.setVisibility(View.VISIBLE);

            // Configurar el RecyclerView para mostrar las imágenes
            ImagenAdapter imagenAdapter = new ImagenAdapter(context, anuncio.getImagenes());
            holder.recyclerImagenes.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerImagenes.setAdapter(imagenAdapter);
        } else {
            // Ocultar el RecyclerView de imágenes si no hay imágenes
            holder.recyclerImagenes.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {
        TextView tituloAnuncio, descripcionAnuncio, precioAnuncio, fechaAnuncio;
        RecyclerView recyclerImagenes;

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloAnuncio = itemView.findViewById(R.id.tvTitulo);
            descripcionAnuncio = itemView.findViewById(R.id.tvDescripcion);
            precioAnuncio = itemView.findViewById(R.id.tv_precio);
            fechaAnuncio = itemView.findViewById(R.id.tv_fecha);
            recyclerImagenes = itemView.findViewById(R.id.recyclerImagenes);
        }
    }
}
