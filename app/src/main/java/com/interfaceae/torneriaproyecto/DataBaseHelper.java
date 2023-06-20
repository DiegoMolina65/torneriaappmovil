package com.interfaceae.torneriaproyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    // Nombre de la base de datos
    public static final String DATABASE_NAME = "torneriamontero.db";

    // Versión de la base de datos. Si cambias el esquema de la base de datos, debes incrementar la versión de la base de datos.
    public static final int DATABASE_VERSION = 1;

    // Definimos el nombre de la tabla y todas sus columnas.
    public static final String TABLE_NAME = "Registro";
    public static final String COLUMN_NAME = "Nombre";
    public static final String COLUMN_SURNAME = "Apellido";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Contraseña";

    // Creación de la tabla
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_SURNAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                    COLUMN_PASSWORD + " TEXT)";

    // Eliminación de la tabla
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Esta base de datos es solo un caché para datos en línea, por lo que su actualización consiste
        // simplemente en descartar los datos existentes y comenzar de nuevo
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = null;

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME}, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_NAME);
            if (columnIndex >= 0) {
                username = cursor.getString(columnIndex);
            }
        }
        cursor.close();

        return username;
    }

    public String obtenerUsername(Context context) {
        String email = getEmailFromSharedPreferences(context);
        return getUserName(email);
    }

    public String obtenerEmail(Context context) {
        return getEmailFromSharedPreferences(context);
    }

    private String getEmailFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }
}
