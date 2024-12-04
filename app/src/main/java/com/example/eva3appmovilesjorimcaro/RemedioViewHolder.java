package com.example.eva3appmovilesjorimcaro;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RemedioViewHolder extends RecyclerView.ViewHolder {

    // Referencias a los elementos de la vista
    TextView tvNombre, tvCantidad, tvFechaVencimiento;
    Button btnModificar, btnEliminar;

    // Constructor que vincula las vistas al ViewHolder
    public RemedioViewHolder(@NonNull View itemView) {
        super(itemView);

        // Enlazar las vistas con los IDs definidos en item_remedio.xml
        tvNombre = itemView.findViewById(R.id.tv_nombre_remedio);
        tvCantidad = itemView.findViewById(R.id.tv_cantidad_remedio);
        tvFechaVencimiento = itemView.findViewById(R.id.tv_fecha_vencimiento_remedio);
        btnModificar = itemView.findViewById(R.id.btn_modificar);
        btnEliminar = itemView.findViewById(R.id.btn_eliminar);
    }
}
