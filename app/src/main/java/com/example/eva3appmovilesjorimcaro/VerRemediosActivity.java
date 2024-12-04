package com.example.eva3appmovilesjorimcaro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerRemediosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RemedioAdapter adapter;
    private DatabaseHelper databaseHelper;
    private EditText etBuscarNombre;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_remedios);

        // Inicializar vistas
        recyclerView = findViewById(R.id.rv_remedios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Configuración para scroll vertical

        etBuscarNombre = findViewById(R.id.et_buscar_nombre);
        btnBuscar = findViewById(R.id.btn_buscar);

        // Inicializar base de datos
        databaseHelper = new DatabaseHelper(this);

        // Cargar todos los remedios al iniciar la actividad
        cargarRemedios("");

        // Configurar acción para el botón Buscar
        btnBuscar.setOnClickListener(v -> {
            String query = etBuscarNombre.getText().toString().trim();
            cargarRemedios(query);
        });
    }

    private void cargarRemedios(String query) {
        ArrayList<Remedio> remedios;

        if (query.isEmpty()) {
            // Obtener todos los remedios si no hay consulta
            remedios = databaseHelper.getAllRemedios();
        } else {
            // Buscar remedios por nombre
            remedios = databaseHelper.buscarRemedioPorNombre(query);
            if (remedios.isEmpty()) {
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Configurar el adaptador para el RecyclerView
        adapter = new RemedioAdapter(remedios, new RemedioAdapter.OnItemClickListener() {
            @Override
            public void onModify(Remedio remedio) {
                // Acción al modificar un remedio
                Intent intent = new Intent(VerRemediosActivity.this, GestionarRemediosActivity.class);
                intent.putExtra("id_remedio", remedio.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Remedio remedio) {
                // Acción al eliminar un remedio
                databaseHelper.deleteRemedio(remedio.getId());
                Toast.makeText(VerRemediosActivity.this, "Remedio eliminado correctamente.", Toast.LENGTH_SHORT).show();
                cargarRemedios(""); // Recargar la lista después de eliminar
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar la lista de remedios cuando se vuelve a la actividad
        cargarRemedios("");
    }
}



