package com.example.eva3appmovilesjorimcaro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre de la base de datos y tabla
    private static final String DATABASE_NAME = "remedios.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_REMEDIOS = "remedios";

    // Columnas de la tabla
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_CANTIDAD = "cantidad";
    private static final String COLUMN_FECHA_VENCIMIENTO = "fecha_vencimiento";
    private static final String COLUMN_MILIGRAMOS = "miligramos";
    private static final String COLUMN_PRESENTACION = "presentacion";
    private static final String COLUMN_DESCRIPCION = "descripcion";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_REMEDIOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_CANTIDAD + " INTEGER NOT NULL, " +
                COLUMN_FECHA_VENCIMIENTO + " TEXT NOT NULL, " +
                COLUMN_MILIGRAMOS + " INTEGER, " +
                COLUMN_PRESENTACION + " TEXT, " +
                COLUMN_DESCRIPCION + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMEDIOS);
        onCreate(db);
    }

    // Insertar un remedio
    public long insertarRemedio(String nombre, int cantidad, String fechaVencimiento, Integer miligramos, String presentacion, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_FECHA_VENCIMIENTO, fechaVencimiento);
        values.put(COLUMN_MILIGRAMOS, miligramos);
        values.put(COLUMN_PRESENTACION, presentacion);
        values.put(COLUMN_DESCRIPCION, descripcion);

        long result = db.insert(TABLE_REMEDIOS, null, values);
        db.close();
        return result;
    }

    // Actualizar un remedio existente
    public int actualizarRemedio(int id, String nombre, int cantidad, String fechaVencimiento, Integer miligramos, String presentacion, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_FECHA_VENCIMIENTO, fechaVencimiento);
        values.put(COLUMN_MILIGRAMOS, miligramos);
        values.put(COLUMN_PRESENTACION, presentacion);
        values.put(COLUMN_DESCRIPCION, descripcion);

        int rowsUpdated = db.update(TABLE_REMEDIOS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated;
    }

    // Eliminar un remedio
    public void deleteRemedio(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMEDIOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Obtener todos los remedios
    public ArrayList<Remedio> getAllRemedios() {
        ArrayList<Remedio> remedios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REMEDIOS, null, null, null, null, null, COLUMN_NOMBRE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD));
                String fechaVencimiento = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_VENCIMIENTO));
                Integer miligramos = cursor.isNull(cursor.getColumnIndexOrThrow(COLUMN_MILIGRAMOS)) ? null : cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MILIGRAMOS));
                String presentacion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRESENTACION));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION));

                remedios.add(new Remedio(id, nombre, cantidad, fechaVencimiento, miligramos, presentacion, descripcion));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return remedios;
    }

    // Buscar remedios por nombre
    public ArrayList<Remedio> buscarRemedioPorNombre(String nombre) {
        ArrayList<Remedio> remedios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REMEDIOS,
                null,
                COLUMN_NOMBRE + " LIKE ?",
                new String[]{"%" + nombre + "%"},
                null,
                null,
                COLUMN_NOMBRE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String nombreRemedio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD));
                String fechaVencimiento = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_VENCIMIENTO));
                Integer miligramos = cursor.isNull(cursor.getColumnIndexOrThrow(COLUMN_MILIGRAMOS)) ? null : cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MILIGRAMOS));
                String presentacion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRESENTACION));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION));

                remedios.add(new Remedio(id, nombreRemedio, cantidad, fechaVencimiento, miligramos, presentacion, descripcion));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return remedios;
    }

    // Obtener remedio por ID
    public Cursor obtenerRemedioPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_REMEDIOS,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
    }
}




