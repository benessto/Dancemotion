package mum.dancemotion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import mum.dancemotion.DatenbankDaten.DatenbankInfo;

/**
 * Created by stephanie.dammann on 20.12.2015.
 */
public class DatenbankOperations extends SQLiteOpenHelper {

    public static final int datenbank_version = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATE";
    private static final String TIME_TYPE = " TIME";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";
    public String CREATE_TABLE_SESSION = "CREATE TABLE "+ DatenbankDaten.DatenbankInfo.TABLE_SESSION + " (" +
            DatenbankInfo.SESSION_ID + " INTEGER PRIMARY KEY," +
            DatenbankDaten.DatenbankInfo.SESSION_DATUM + TEXT_TYPE + COMMA_SEP +
            DatenbankInfo.SESSION_DAUER + TIME_TYPE +
            " )";

    public String CREATE_TABLE_SONG = "CREATE TABLE "+ DatenbankInfo.TABLE_SONG + " (" +
            DatenbankInfo.SONG_ID + " INTEGER PRIMARY KEY," +
            DatenbankInfo.SONG_DAUER + TIME_TYPE + COMMA_SEP +
            DatenbankInfo.SONG_INTERPRET + TEXT_TYPE + COMMA_SEP +
            DatenbankInfo.SONG_NAME + TEXT_TYPE + COMMA_SEP +
            " )";

    public String CREATE_TABLE_SESSION_SONG = "CREATE TABLE "+ DatenbankInfo.TABLE_SESSION_SONG + " (" +
            DatenbankInfo.SON_ID + TEXT_TYPE + COMMA_SEP +
            DatenbankInfo.SES_ID + TEXT_TYPE + COMMA_SEP +
            DatenbankInfo.SONG_INTENSITÄT + INT_TYPE +
            " )";

    public DatenbankOperations(Context context){
        super(context, DatenbankDaten.DatenbankInfo.DATENBANK_NAME,null,datenbank_version);
        Log.d("DatenbankOperations", "Datenbank erstellt");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SESSION);
        db.execSQL(CREATE_TABLE_SONG);
        db.execSQL(CREATE_TABLE_SESSION_SONG);

        Log.d("DatenbankOperations", "Tabellen erstellt");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInformationIntoSong(DatenbankOperations op, String songName, String songDauer){
        SQLiteDatabase sQ = op.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DatenbankInfo.SONG_NAME, songName);

        //String dauer = Float.toString(songDauer);
        cv.put(DatenbankInfo.SONG_DAUER, songDauer);

        long k = sQ.insert(DatenbankInfo.TABLE_SONG, null, cv);
        Log.d("DatenbankOperations", "Song-Tabelle mit Daten gefüllt");
        //DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //String date = df.format(datum);
    }

    public Cursor getInformationFromSong(DatenbankOperations op){
        SQLiteDatabase sQ = op.getReadableDatabase();
        
        String[] spalten = {DatenbankInfo.SONG_NAME, DatenbankInfo.SONG_DAUER};
        Cursor cr = sQ.query(DatenbankInfo.TABLE_SONG, spalten,null, null, null, null, null);
        return cr;
    }
}
