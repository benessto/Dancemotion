package mum.dancemotion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
            DatenbankDaten.DatenbankInfo.SESSION_DATUM + DATE_TYPE + COMMA_SEP +
            DatenbankInfo.SESSION_DAUER + TIME_TYPE +
            " )";

    public String CREATE_TABLE_SONG = "CREATE TABLE "+ DatenbankInfo.TABLE_SONG + " (" +
            DatenbankInfo.SONG_ID + " INTEGER PRIMARY KEY," +
            DatenbankInfo.SONG_DAUER + TIME_TYPE + COMMA_SEP +
            DatenbankInfo.SONG_INTERPRET + TEXT_TYPE + COMMA_SEP +
            DatenbankInfo.SONG_NAME + TEXT_TYPE +
            " )";

    public String CREATE_TABLE_SESSION_SONG = "CREATE TABLE "+ DatenbankInfo.TABLE_SESSION_SONG + " (" +
            " FOREIGN KEY ("+DatenbankInfo.SON_ID +") REFERENCES "+ DatenbankInfo.TABLE_SONG+" ("+ DatenbankInfo.SONG_ID +")"+ COMMA_SEP +
            " FOREIGN KEY ("+DatenbankInfo.SES_ID +") REFERENCES "+ DatenbankInfo.TABLE_SESSION+" ("+ DatenbankInfo.SESSION_ID +")"+ COMMA_SEP +
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

        putInformationsIntoSong();
        putInformationsIntoSession();
        putInformationsIntoSessionSong();
    }

    private void putInformationsIntoSessionSong() {
        SQLiteDatabase sQ;
        sQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //SessionSong1
        cv.put(DatenbankInfo.SES_ID, 1);
        cv.put(DatenbankInfo.SON_ID, 2);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 3);

        //SessionSong2
        cv.put(DatenbankInfo.SES_ID, 2);
        cv.put(DatenbankInfo.SON_ID, 1);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 1);

        //SessionSong3
        cv.put(DatenbankInfo.SES_ID, 3);
        cv.put(DatenbankInfo.SON_ID, 5);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 4);

        //SessionSong4
        cv.put(DatenbankInfo.SES_ID, 5);
        cv.put(DatenbankInfo.SON_ID, 4);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 3);

        //SessionSong5
        cv.put(DatenbankInfo.SES_ID, 4);
        cv.put(DatenbankInfo.SON_ID, 3);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 2);

        //SessionSong6
        cv.put(DatenbankInfo.SES_ID, 10);
        cv.put(DatenbankInfo.SON_ID, 9);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 5);

        //SessionSong7
        cv.put(DatenbankInfo.SES_ID, 8);
        cv.put(DatenbankInfo.SON_ID, 8);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 2);

        //SessionSong8
        cv.put(DatenbankInfo.SES_ID, 9);
        cv.put(DatenbankInfo.SON_ID, 6);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 4);

        //SessionSong9
        cv.put(DatenbankInfo.SES_ID, 6);
        cv.put(DatenbankInfo.SON_ID, 10);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 3);

        //SessionSong10
        cv.put(DatenbankInfo.SES_ID, 7);
        cv.put(DatenbankInfo.SON_ID, 7);
        cv.put(DatenbankInfo.SONG_INTENSITÄT, 4);

        long k = sQ.insert(DatenbankInfo.TABLE_SESSION_SONG, null, cv);
        Log.d("DatenbankOperations", "SessionSong-Tabelle mit Daten gefüllt");
    }

    private void putInformationsIntoSession() {
        SQLiteDatabase sQ;
        sQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //Session1
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session2
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session3
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session4
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session5
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session6
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session7
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session8
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session9
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        //Session10
        cv.put(DatenbankInfo.SESSION_DAUER, getTime());
        cv.put(DatenbankInfo.SESSION_DATUM, getDateTime());

        long k = sQ.insert(DatenbankInfo.TABLE_SESSION, null, cv);
        Log.d("DatenbankOperations", "Session-Tabelle mit Daten gefüllt");
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older books table if existed
            db.execSQL("DROP TABLE IF EXISTS "+DatenbankInfo.TABLE_SESSION);
            db.execSQL("DROP TABLE IF EXISTS "+DatenbankInfo.TABLE_SESSION_SONG);
            db.execSQL("DROP TABLE IF EXISTS "+DatenbankInfo.TABLE_SONG);
            // create fresh books table
            this.onCreate(db);
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

    public void putInformationsIntoSong(){
        SQLiteDatabase sQ;
        sQ = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //Song1
        cv.put(DatenbankInfo.SONG_NAME, "Don't Stop 'Till You Get Enough");
        cv.put(DatenbankInfo.SONG_DAUER, "6:05");
        cv.put(DatenbankInfo.SONG_INTERPRET, "Michael Jackson");

        //Song2
        cv.put(DatenbankInfo.SONG_NAME, "Rock With You");
        cv.put(DatenbankInfo.SONG_DAUER, "3:40");
        cv.put(DatenbankInfo.SONG_INTERPRET, "Michael Jackson");

        //Song3
        cv.put(DatenbankInfo.SONG_NAME, "Lose Yourself To Dance");
        cv.put(DatenbankInfo.SONG_DAUER, "5:53");
        cv.put(DatenbankInfo.SONG_INTERPRET, "Daft Punk");

        //Song4
        cv.put(DatenbankInfo.SONG_NAME, "It Won’t Be Long");
        cv.put(DatenbankInfo.SONG_DAUER, "2:14");
        cv.put(DatenbankInfo.SONG_INTERPRET, "The Beatles");

        //Song5
        cv.put(DatenbankInfo.SONG_NAME, "Billie Jean");
        cv.put(DatenbankInfo.SONG_DAUER, "4:54");
        cv.put(DatenbankInfo.SONG_INTERPRET, "Michael Jackson");

        //Song6
        cv.put(DatenbankInfo.SONG_NAME, "Thriller");
        cv.put(DatenbankInfo.SONG_DAUER, "5:59");
        cv.put(DatenbankInfo.SONG_INTERPRET, "Michael Jackson");

        //Song7
        cv.put(DatenbankInfo.SONG_NAME, "Heart Shaped Box");
        cv.put(DatenbankInfo.SONG_DAUER, "4:41");
        cv.put(DatenbankInfo.SONG_INTERPRET, "Nirvana");

        //Song8
        cv.put(DatenbankInfo.SONG_NAME, "Off The Wall");
        cv.put(DatenbankInfo.SONG_DAUER, "4:05");
        cv.put(DatenbankInfo.SONG_INTERPRET, "Michael Jackson");

        //Song9
        cv.put(DatenbankInfo.SONG_NAME, "Get On The Floor");
        cv.put(DatenbankInfo.SONG_DAUER, "4:39");
        cv.put(DatenbankInfo.SONG_INTERPRET, "Michael Jackson");

        //Song10
        cv.put(DatenbankInfo.SONG_NAME, "Happiness Is A Warm Gun");
        cv.put(DatenbankInfo.SONG_DAUER, "2:44");
        cv.put(DatenbankInfo.SONG_INTERPRET, "The Beatles");

        long k = sQ.insert(DatenbankInfo.TABLE_SONG, null, cv);
        Log.d("DatenbankOperations", "Song-Tabelle mit Daten gefüllt");
    }

    public Cursor getInformationFromSong(DatenbankOperations op){
        SQLiteDatabase sQ = op.getReadableDatabase();
        
        String[] spalten = {DatenbankInfo.SONG_NAME, DatenbankInfo.SONG_DAUER};
        Cursor cr = sQ.query(DatenbankInfo.TABLE_SONG, spalten,null, null, null, null, null);
        return cr;
    }
}
