package pt.ubi.di.pmd.a45511_T1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QuotesDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME= "Quotes.db";
    private static final int DATABASE_VERSION= 1;

    private static final String TABLE_NAME= "cinemaQuotes";
    private static final String TABLE_NAME_GUESS= "guessQuotes";
    private static final String GUESS_QUOTE= "guessQuote";
    private static final String COLUMN_ID= "_id";
    private static final String SECRETWORD= "secret";
    private static final String QUOTE= "quote";
    private static final String MOVIE= "movie";


    public QuotesDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_1= "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SECRETWORD + " TEXT, " +QUOTE + " TEXT, " + MOVIE + " TEXT); ";

        String query_2= "CREATE TABLE " + TABLE_NAME_GUESS + "(" + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GUESS_QUOTE + " TEXT); ";

        db.execSQL(query_1);
        db.execSQL(query_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME +";");
        onCreate(db);
    }

    void addQuotes(String secret, String quote, String movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SECRETWORD,secret);
        values.put(QUOTE,quote);
        values.put(MOVIE,movie);

        long result = db.insert(TABLE_NAME, null,values);
        System.out.println(result);

    }

    SQLiteDatabase cleanTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        return db;
    }

    Cursor selectAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);
        return cursor;
    }

    Cursor selectAllGuess(){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_NAME_GUESS;
        Cursor cursor = db.rawQuery(select,null);
        return cursor;
    }

    void addGuess(String secret){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GUESS_QUOTE,secret);
        long result = db.insert(TABLE_NAME_GUESS, null,values);
        System.out.println(result);
    }
}
;