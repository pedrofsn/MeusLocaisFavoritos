package br.pedrofsn.meuslocaisfavoritos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import br.pedrofsn.meuslocaisfavoritos.interfaces.IBancoDeDados;
import br.pedrofsn.meuslocaisfavoritos.model.Local;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public class DataBaseHelper extends SQLiteOpenHelper implements IBancoDeDados {

    private static final String DATABASE_NAME = "MeusLocaisFavoritos.db";
    private static final int VERSION = 6;
    private static final String TABELA_LOCAIS_FAVORITOS = "LOCAIS_FAVORITOS";
    private static final String COLUNA_ID = "ID";
    private static final String COLUNA_ENDERECO = "ENDERECO";
    private static final String COLUNA_NOME = "NOME";
    private static final String COLUNA_LATITUDE = "LATITUDE";
    private static final String COLUNA_LONGITUDE = "LONGITUDE";
    private static final String COLUNA_DATA_CHECKIN = "DATA_CHECKIN";
    private static DataBaseHelper instancia = null;
    private Context context;

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    public static DataBaseHelper getInstancia() {
        return instancia;
    }

    public static void instanciarDao(Context ctx) {
        if (instancia == null && ctx != null) {
            instancia = new DataBaseHelper(ctx.getApplicationContext());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String createTable = "CREATE TABLE " + TABELA_LOCAIS_FAVORITOS +
                "(" + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_ENDERECO + " STRING, " +
                COLUNA_NOME + " STRING, " +
                COLUNA_LATITUDE + " NUMBER NOT NULL, " +
                COLUNA_LONGITUDE + " NUMBER NOT NULL, " +
                COLUNA_DATA_CHECKIN + " NUMBER NOT NULL " +
                ");";
        database.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public boolean createLocal(Local local) {
        boolean isSucesso = true;
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(COLUNA_ENDERECO, local.getEndereco());
            values.put(COLUNA_NOME, local.getNome());
            values.put(COLUNA_LATITUDE, local.getLatitude());
            values.put(COLUNA_LONGITUDE, local.getLongitude());
            values.put(COLUNA_DATA_CHECKIN, local.getDataDoCheckin().getTime());
            database.insert(TABELA_LOCAIS_FAVORITOS, null, values);
        } catch (Exception e) {
            isSucesso = false;
            e.printStackTrace();
        }

        return isSucesso;
    }

    @Override
    public List<Local> readLocal() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;

        try {
            List<Local> listaLocais = new ArrayList<Local>();
            cursor = database.query(TABELA_LOCAIS_FAVORITOS, new String[]{
                    COLUNA_ID,
                    COLUNA_ENDERECO,
                    COLUNA_NOME,
                    COLUNA_LATITUDE,
                    COLUNA_DATA_CHECKIN,
                    COLUNA_LONGITUDE}, null, null, null, null, COLUNA_ID);

            if (cursor.moveToFirst()) do {
                Local local = new Local();
                local.setId(cursor.getLong(cursor.getColumnIndex(COLUNA_ID)));
                local.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO)));
                local.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                local.setLatLng(new LatLng((cursor.getDouble(cursor.getColumnIndex(COLUNA_LATITUDE))), (cursor.getDouble(cursor.getColumnIndex(COLUNA_LONGITUDE)))));
                local.setDataDoCheckin(cursor.getLong(cursor.getColumnIndex(COLUNA_DATA_CHECKIN)));
                listaLocais.add(local);
            } while (cursor.moveToNext());
            cursor.close();
            database.close();
            return listaLocais;
        } catch (Exception e) {
            if (cursor != null) cursor.close();
            database.close();
            throw e;
        }

    }

    @Override
    public Local readLocal(LatLng latLng) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;

        try {
            Local local = null;
            cursor = database.rawQuery("select * from " + TABELA_LOCAIS_FAVORITOS + " where " + COLUNA_LATITUDE + " like '" + String.valueOf(latLng.latitude) + "' and " + COLUNA_LONGITUDE + " like '" + String.valueOf(latLng.longitude) + "'", null);

            if (cursor.moveToFirst()) do {
                local = new Local();
                local.setId(cursor.getLong(cursor.getColumnIndex(COLUNA_ID)));
                local.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO)));
                local.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                local.setLatLng(new LatLng((cursor.getDouble(cursor.getColumnIndex(COLUNA_LATITUDE))), (cursor.getDouble(cursor.getColumnIndex(COLUNA_LONGITUDE)))));
                local.setDataDoCheckin(cursor.getLong(cursor.getColumnIndex(COLUNA_DATA_CHECKIN)));
            } while (cursor.moveToNext());
            cursor.close();
            database.close();
            return local;
        } catch (Exception e) {
            if (cursor != null) cursor.close();
            database.close();
            throw e;
        }
    }

    @Override
    public Local readLocal(long id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;

        try {
            Local local = null;
            cursor = database.rawQuery("select * from " + TABELA_LOCAIS_FAVORITOS + " where " + COLUNA_ID + " = " + id, null);

            if (cursor.moveToFirst()) do {
                local = new Local();
                local.setId(cursor.getLong(cursor.getColumnIndex(COLUNA_ID)));
                local.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO)));
                local.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                local.setLatLng(new LatLng((cursor.getDouble(cursor.getColumnIndex(COLUNA_LATITUDE))), (cursor.getDouble(cursor.getColumnIndex(COLUNA_LONGITUDE)))));
                local.setDataDoCheckin(cursor.getLong(cursor.getColumnIndex(COLUNA_DATA_CHECKIN)));
            } while (cursor.moveToNext());
            cursor.close();
            database.close();
            return local;
        } catch (Exception e) {
            if (cursor != null) cursor.close();
            database.close();
            throw e;
        }
    }

    @Override
    public boolean deleteLocal(long id) {
        boolean isSucesso = true;
        SQLiteDatabase database = getWritableDatabase();

        try {
            database.delete(TABELA_LOCAIS_FAVORITOS, " ID = ?", new String[]{String.valueOf(id)});
            database.close();
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
            isSucesso = false;
        }

        return isSucesso;
    }

    @Override
    public boolean existsLocal(LatLng latLng) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABELA_LOCAIS_FAVORITOS + " where " + COLUNA_LATITUDE + " like '" + String.valueOf(latLng.latitude) + "' and " + COLUNA_LONGITUDE + " like '" + String.valueOf(latLng.longitude) + "'", null);
        return cursor.moveToFirst();
    }

    @Override
    public int countLocal() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABELA_LOCAIS_FAVORITOS, null);
        return cursor.getCount();
    }

}
