package com.example.eva3appmovilesjorimcaro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GestionarRemediosActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etNombre, etCantidad, etFechaVencimiento, etMiligramos, etDescripcion;
    private Spinner spinnerPresentacion;
    private Button btnGuardar, btnEliminar, btnVerLista;

    private int remedioId = -1; // ID del remedio que se está editando (-1 si es nuevo)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_remedios);

        // Inicializar componentes
        dbHelper = new DatabaseHelper(this);
        etNombre = findViewById(R.id.et_nombre);
        etCantidad = findViewById(R.id.et_cantidad);
        etFechaVencimiento = findViewById(R.id.et_fecha_vencimiento);
        etMiligramos = findViewById(R.id.et_miligramos);
        etDescripcion = findViewById(R.id.et_descripcion);
        spinnerPresentacion = findViewById(R.id.sp_presentacion);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        btnVerLista = findViewById(R.id.btn_ver_lista);

        // Configurar el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.presentacion_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPresentacion.setAdapter(adapter);

        // Recuperar el ID del remedio desde el Intent
        remedioId = getIntent().getIntExtra("id_remedio", -1);

        if (remedioId != -1) {
            // Cargar datos del remedio existente
            cargarDatosRemedio(remedioId);
            btnEliminar.setVisibility(View.VISIBLE); // Mostrar botón Eliminar si es edición
        } else {
            btnEliminar.setVisibility(View.GONE); // Ocultar botón Eliminar si es un nuevo remedio
        }

        // Configurar botón Guardar
        btnGuardar.setOnClickListener(v -> guardarRemedio());

        // Configurar botón Eliminar
        btnEliminar.setOnClickListener(v -> eliminarRemedio());

        // Configurar botón Ver Lista
        btnVerLista.setOnClickListener(v -> {
            Intent intent = new Intent(GestionarRemediosActivity.this, VerRemediosActivity.class);
            startActivity(intent);
        });
    }

    private void cargarDatosRemedio(int id) {
        Cursor cursor = dbHelper.obtenerRemedioPorId(id);
        if (cursor != null && cursor.moveToFirst()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"));
            String fechaVencimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_vencimiento"));
            Integer miligramos = cursor.isNull(cursor.getColumnIndexOrThrow("miligramos")) ? null : cursor.getInt(cursor.getColumnIndexOrThrow("miligramos"));
            String presentacion = cursor.getString(cursor.getColumnIndexOrThrow("presentacion"));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));

            // Asignar datos a los elementos de la interfaz
            etNombre.setText(nombre);
            etCantidad.setText(String.valueOf(cantidad));
            etFechaVencimiento.setText(fechaVencimiento);
            etMiligramos.setText(miligramos == null ? "" : String.valueOf(miligramos));
            etDescripcion.setText(descripcion);

            // Configurar spinner para la presentación
            String[] presentaciones = getResources().getStringArray(R.array.presentacion_array);
            for (int i = 0; i < presentaciones.length; i++) {
                if (presentaciones[i].equals(presentacion)) {
                    spinnerPresentacion.setSelection(i);
                    break;
                }
            }
        } else {
            Toast.makeText(this, "No se encontró el remedio.", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void guardarRemedio() {
        String nombre = etNombre.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();
        String fechaVencimiento = etFechaVencimiento.getText().toString().trim();
        String miligramosStr = etMiligramos.getText().toString().trim();
        String presentacion = spinnerPresentacion.getSelectedItem() != null ? spinnerPresentacion.getSelectedItem().toString() : "";
        String descripcion = etDescripcion.getText().toString().trim();

        // Validar campos obligatorios
        if (nombre.isEmpty() || cantidadStr.isEmpty() || fechaVencimiento.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Cantidad debe ser un número válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer miligramos = miligramosStr.isEmpty() ? null : Integer.parseInt(miligramosStr);

        if (remedioId == -1) {
            // Insertar nuevo remedio
            long id = dbHelper.insertarRemedio(nombre, cantidad, fechaVencimiento, miligramos, presentacion, descripcion);
            if (id != -1) {
                Toast.makeText(this, "Producto Grabado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al agregar el producto.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Actualizar remedio existente
            int filasActualizadas = dbHelper.actualizarRemedio(remedioId, nombre, cantidad, fechaVencimiento, miligramos, presentacion, descripcion);
            if (filasActualizadas > 0) {
                Toast.makeText(this, "Producto actualizado correctamente.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar el producto.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eliminarRemedio() {
        if (remedioId != -1) {
            dbHelper.deleteRemedio(remedioId);
            Toast.makeText(this, "Remedio eliminado correctamente.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se puede eliminar un remedio inexistente.", Toast.LENGTH_SHORT).show();
        }
    }
}





