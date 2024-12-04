package com.example.eva3appmovilesjorimcaro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RemedioAdapter extends RecyclerView.Adapter<RemedioAdapter.RemedioViewHolder> {

    public interface OnItemClickListener {
        void onModify(Remedio remedio);
        void onDelete(Remedio remedio);
    }

    private ArrayList<Remedio> remedios; // Lista de remedios a mostrar
    private OnItemClickListener listener; // Escucha de eventos de clic

    public RemedioAdapter(ArrayList<Remedio> remedios, OnItemClickListener listener) {
        this.remedios = remedios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RemedioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remedio, parent, false);
        return new RemedioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemedioViewHolder holder, int position) {
        Remedio remedio = remedios.get(position);

        // Asignar los datos del remedio a los elementos del ViewHolder
        holder.tvNombre.setText(remedio.getNombre());
        holder.tvCantidad.setText("Cantidad: " + remedio.getCantidad());
        holder.tvFechaVencimiento.setText("Vence: " + remedio.getFechaVencimiento());

        // Mostrar los campos adicionales
        holder.tvMiligramos.setText(remedio.getMiligramos() != null ? remedio.getMiligramos() + " mg" : "Sin especificar");
        holder.tvPresentacion.setText("Presentación: " + (remedio.getPresentacion() != null ? remedio.getPresentacion() : "Sin especificar"));
        holder.tvDescripcion.setText("Descripción: " + (remedio.getDescripcion() != null ? remedio.getDescripcion() : "Sin descripción"));

        // Colorear la fecha si está cerca de vencer
        if (isExpiringSoon(remedio.getFechaVencimiento())) {
            holder.tvFechaVencimiento.setTextColor(holder.itemView.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            holder.tvFechaVencimiento.setTextColor(holder.itemView.getResources().getColor(android.R.color.black));
        }

        // Botón Modificar
        holder.btnModificar.setOnClickListener(v -> listener.onModify(remedio));

        // Botón Eliminar
        holder.btnEliminar.setOnClickListener(v -> listener.onDelete(remedio));
    }

    @Override
    public int getItemCount() {
        return remedios.size();
    }

    // Verificar si un remedio está por vencer en los próximos 3 meses
    private boolean isExpiringSoon(String fechaVencimiento) {
        // Implementación simple basada en la comparación de fechas
        // Aquí puedes agregar lógica para manejar fechas reales
        return fechaVencimiento.endsWith("2023") || fechaVencimiento.startsWith("01/2024");
    }

    public static class RemedioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCantidad, tvFechaVencimiento, tvMiligramos, tvPresentacion, tvDescripcion;
        Button btnModificar, btnEliminar;

        public RemedioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_remedio);
            tvCantidad = itemView.findViewById(R.id.tv_cantidad_remedio);
            tvFechaVencimiento = itemView.findViewById(R.id.tv_fecha_vencimiento_remedio);
            tvMiligramos = itemView.findViewById(R.id.tv_miligramos_remedio);
            tvPresentacion = itemView.findViewById(R.id.tv_presentacion_remedio);
            tvDescripcion = itemView.findViewById(R.id.tv_descripcion_remedio);
            btnModificar = itemView.findViewById(R.id.btn_modificar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}


