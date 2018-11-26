package criawebmobile.com.br.selecaomedphonee.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Bd_MEDPHONE extends SQLiteOpenHelper {
    public static final String NOME_DB = "bd_medphone.sqlite";
    public static final String TABLE_NAME = "cache_dados";
    public static final int VERSAO = 1;

    public Bd_MEDPHONE(Context context) {
        super(context, NOME_DB, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + "(_id integer primary key autoincrement, " +
                "id integer, createdAt text, name text, avatar text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //faz alguma coisa quando é trocada a versão do banco de dados;
    }


    public void save(DadosMEDPHONE dadosMEDPHONE){
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put("id", dadosMEDPHONE.getId());
            values.put("createdAt", dadosMEDPHONE.getCreatedAt());
            values.put("name", dadosMEDPHONE.getName());
            values.put("avatar", dadosMEDPHONE.getAvatar());

            db.insert("cache_dados", "", values);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    public List<DadosMEDPHONE> findAll(){
        SQLiteDatabase db = getWritableDatabase();

        try {
            Cursor c = db.query("cache_dados", null, null, null, null, null, null, null);

            List<DadosMEDPHONE> dadosMEDPHONEList = new ArrayList<>();

            if (c.moveToFirst()){
                do {
                    DadosMEDPHONE dadosMEDPHONE = new DadosMEDPHONE();
                    dadosMEDPHONE.setId(c.getInt(c.getColumnIndex("id")));
                    dadosMEDPHONE.setCreatedAt(c.getString(c.getColumnIndex("createdAt")));
                    dadosMEDPHONE.setName(c.getString(c.getColumnIndex("name")));
                    dadosMEDPHONE.setAvatar(c.getString(c.getColumnIndex("avatar")));

                    dadosMEDPHONEList.add(dadosMEDPHONE);

                }while (c.moveToNext());
            }

            return dadosMEDPHONEList;


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            db.close();
        }
    }

    public void deletar(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    /*
    //Ou, usando query SQL:
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DELETE FROM resultado);
        db.close();
    }
    //O método para dropar ficaria da seguinte forma:

    public void dropar(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DROP TABLE IF EXISTS resultado);
        db.close();
    }
    */
}
