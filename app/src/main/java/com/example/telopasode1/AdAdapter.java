package com.example.telopasode1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.ViewHolder> {

    private List<String> anuncios;
    private Context context;
    private boolean[] expandedState; // Para controlar el estado de expansión

    public AdAdapter(List<String> anuncios, Context context) {
        this.anuncios = anuncios;
        this.context = context;
        this.expandedState = new boolean[anuncios.size()]; // Inicializa el estado de expansión
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String anuncio = anuncios.get(position);
        holder.tvTitulo.setText(anuncio);

        // Manejar la visibilidad de los detalles
        holder.tvDetalles.setVisibility(expandedState[position] ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v -> {
            expandedState[position] = !expandedState[position]; // Alterna el estado
            notifyItemChanged(position); // Notifica el cambio para actualizar la vista
        });
    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        TextView tvDetalles;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDetalles = itemView.findViewById(R.id.tv_precio); // Este TextView será opcional para detalles
        }
    }
}
