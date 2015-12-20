package mum.dancemotion;

import android.provider.BaseColumns;

/**
 * Created by stephanie.dammann on 20.12.2015.
 */
public class DatenbankDaten {
    public DatenbankDaten(){

    }

    public static abstract class DatenbankInfo implements BaseColumns {
        public static final String DATENBANK_NAME = "Motionbank";

        public static final String TABLE_SESSION = "Session";
        public static final String SESSION_ID = "SessionID";
        public static final String SESSION_DATUM = "Datum";
        public static final String SESSION_DAUER = "Dauer";

        public static final String TABLE_SONG = "Song";
        public static final String SONG_ID = "SongID";
        public static final String SONG_INTERPRET = "Interpret";
        public static final String SONG_NAME = "Name";
        public static final String SONG_DAUER = "Dauer";

        public static final String TABLE_SESSION_SONG = "Session_Song";
        public static final String SES_ID = "SessionID";
        public static final String SON_ID = "SongID";
        public static final String SONG_INTENSITÄT = "Intensität";

    }
}

