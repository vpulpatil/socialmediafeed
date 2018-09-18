package com.vpulpatil.socialmediafeed.db;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vpulpatil.socialmediafeed.model.Post;

import java.util.ArrayList;
import java.util.List;

public class Database_Post extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_post";
    private static final String TABLE_POST = "table_post";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMG_URL = "img_url";
    private static final String COLUMN_POSTED_TIME_IN_MILLIS = "posted_time_in_millis";

    private Context context;

    public Database_Post(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PNR_DATA_TABLE = "create table " + TABLE_POST + "("
                + COLUMN_TITLE + " TEXT , "
                + COLUMN_DESCRIPTION + " TEXT , "
                + COLUMN_IMG_URL + " TEXT ,"
                + COLUMN_POSTED_TIME_IN_MILLIS + " TEXT "
                + ")";

        sqLiteDatabase.execSQL(CREATE_PNR_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public void addAlarmInDb(String title, String description, String imgUrl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMG_URL, imgUrl);

        db.insert(TABLE_POST, null, values);
        db.close();
    }

    public List<Post> getAllPostList() {
        ArrayList<Post> postArrayList = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_POST + " ORDER BY " + COLUMN_POSTED_TIME_IN_MILLIS + " ASC";
        Cursor mCursor = db.rawQuery(query, null);
        if (mCursor != null && mCursor.getCount() > 0 && mCursor.moveToFirst()) {
            postArrayList = new ArrayList<>();
            do {
                postArrayList.add(0, new Post(mCursor.getString(1), mCursor.getString(2), mCursor.getString(3)));
            } while (mCursor.moveToNext());
            mCursor.close();
        }
        db.close();
        return postArrayList;
    }
}
