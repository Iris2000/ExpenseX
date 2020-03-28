package com.example.listview;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    String user_id;
    String cat_id;

    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
//        context.deleteDatabase("Login.db");
        this.context = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists user(id integer primary key autoincrement," +
                                     "username text not null," +
                                     "email text not null," +
                                     "password text not null)");
        db.execSQL("Create table if not exists icon_category(id integer primary key autoincrement," +
                                             "icon_cat_name text not null)");
        db.execSQL("Create table if not exists category(id integer primary key autoincrement," +
                                         "cat_name text not null," +
                                         "cat_icon text not null," +
                                         "icon_cat_id int not null," +
                                         "foreign key(icon_cat_id) references icon_category(id))");
        db.execSQL("Create table if not exists user_cat(id integer primary key autoincrement," +
                                         "user_id integer," +
                                         "cat_id integer," +
                                         "foreign key(user_id) references user(id)," +
                                         "foreign key(cat_id) references category(id))");

        // insert data into icon_category and category
        db.execSQL("Insert into icon_category(icon_cat_name) values" +
                "('expense')," +
                "('income')");

        DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(db, "category");
        final int catName = ih.getColumnIndex("cat_name");
        final int catIcon = ih.getColumnIndex("cat_icon");
        final int iconCatId = ih.getColumnIndex("icon_cat_id");

        Resources res = this.context.getResources();
        TypedArray iconsDrawable = res.obtainTypedArray(R.array.icons_drawable);
        TypedArray iconsName = res.obtainTypedArray(R.array.icons_name);
        TypedArray iconsCat = res.obtainTypedArray(R.array.icons_cat);
        String iconDrawable;
        String iconName;
        long ins = -1;
        int iconCat;

        for (int i = 0; i < 36; i++) {
            ih.prepareForInsert();
            iconDrawable = iconsDrawable.getString(i);
            iconName = iconsName.getString(i);
            iconCat = iconsCat.getInt(i, 0);

            ih.bind(catName, iconName);
            ih.bind(catIcon, iconDrawable);
            ih.bind(iconCatId, iconCat);
            ih.execute();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists icon_category");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists user_cat");
    }

    // inserting in user table
    public boolean insertUserTable(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;
    }

    // inserting in user_cat table
    public boolean insertUserCatTable(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(db, "user_cat");
        final int userId = ih.getColumnIndex("user_id");
        final int catId = ih.getColumnIndex("cat_id");
        String user_id = "";

        // get user_id
        Cursor cursor = db.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();

        for (int i = 0; i < 36; i++) {
            ih.prepareForInsert();
            ih.bind(userId, user_id);
            ih.bind(catId, i+1);
            ih.execute();
        }
        return true;
    }

    // inserting in cat table
    public void insertCatTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(db, "category");
//        ContentValues contentValues = new ContentValues();
        final int catName = ih.getColumnIndex("cat_name");
        final int catIcon = ih.getColumnIndex("cat_icon");
        final int iconCatId = ih.getColumnIndex("icon_cat_id");

        Resources res = this.context.getResources();
        TypedArray iconsDrawable = res.obtainTypedArray(R.array.icons_drawable);
        TypedArray iconsName = res.obtainTypedArray(R.array.icons_name);
        TypedArray iconsCat = res.obtainTypedArray(R.array.icons_cat);
        String iconDrawable;
        String iconName;
        long ins = -1;
        int iconCat;

        for (int i = 0; i < 36; i++) {
            ih.prepareForInsert();
            iconDrawable = iconsDrawable.getString(i);
            iconName = iconsName.getString(i);
            iconCat = iconsCat.getInt(i, 0);

            ih.bind(catName, iconName);
            ih.bind(catIcon, iconDrawable);
            ih.bind(iconCatId, iconCat);
            ih.execute();
//            Bitmap bitmap = ((BitmapDrawable)iconDrawable).getBitmap();
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            int size = bitmap.getRowBytes() * height;
//            java.nio.ByteBuffer byteBuffer = ByteBuffer.allocate(size);
//            bitmap.copyPixelsToBuffer(byteBuffer);
//            byte byteArray[] = byteBuffer.array();
//            bitmap.recycle();
//            contentValues.put("cat_name", iconName);
//            contentValues.put("cat_icon", iconDrawable);
//            contentValues.put("icon_cat_id", iconCat);
//            ins = db.insertWithOnConflict("category", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
//            iconsDrawable.recycle();
//            iconsName.recycle();
//            iconsCat.recycle();
        }
//        db.close();
//        if (ins == -1)
//            return false;
//        else
//            return true;
    }

    // checking if email exists;
    public Boolean checkMail (String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }
    // checking if username exists;
    public Boolean checkUsername (String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }
    // checking if account exists;
    public Boolean checkAcc (String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email = ? AND password = ?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    // get username if login successfully;
    public String getUsername (String email) {
        String username = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email ='" + email + "'", null);
        if (cursor.moveToFirst()) {
            username = cursor.getString(1);
        }
        cursor.close();
        return username;
    }

    public ArrayList<CatClass> getIconAndName (String username, String type) {
        ArrayList<CatClass> catArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        Log.d("username", username);
//        Log.d("type", type);
        // get user_id
        Cursor cursor = db.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();
//        Log.d("user_id", user_id);

        if (type == "expense") {
            final String MY_QUERY = "SELECT * FROM category a INNER JOIN user_cat b ON a.id=b.cat_id WHERE b.user_id=? AND a.icon_cat_id=1";
            Cursor cursor2 = db.rawQuery(MY_QUERY, new String[]{user_id});
            if (cursor2.getCount() > 0) {
                cursor2.moveToFirst();
                while (!cursor2.isAfterLast()) {
                    String catName = cursor2.getString(1);
                    String catIcon = cursor2.getString(2);
                    CatClass expense = new CatClass();
                    expense.setCatName(catName);
                    expense.setCatIcon(catIcon);
                    catArrayList.add(expense);
//                Log.d("catName", catName);
//                Log.d("catIcon", catIcon);
                    cursor2.moveToNext();
                }
            }
//        Log.d("size", Integer.toString(expenseCat.size()));
//        Log.d("catIcon", expenseCat.get(29).getCatIcon());
            cursor2.close();
        } else if (type == "income"){
            final String MY_QUERY = "SELECT * FROM category a INNER JOIN user_cat b ON a.id=b.cat_id WHERE b.user_id=? AND a.icon_cat_id=2";
            Cursor cursor2 = db.rawQuery(MY_QUERY, new String[]{user_id});
            if (cursor2.getCount() > 0) {
                cursor2.moveToFirst();
                while (!cursor2.isAfterLast()) {
                    String catName = cursor2.getString(1);
                    String catIcon = cursor2.getString(2);
                    CatClass income = new CatClass();
                    income.setCatName(catName);
                    income.setCatIcon(catIcon);
                    catArrayList.add(income);
//                Log.d("catName", catName);
//                Log.d("catIcon", catIcon);
                    cursor2.moveToNext();
                }
            }
//        Log.d("size", Integer.toString(expenseCat.size()));
//        Log.d("catIcon", expenseCat.get(29).getCatIcon());
            cursor2.close();
        }
        return catArrayList;
    }
    // checking if category name exists;
    public Boolean checkCatName (String username, String catName) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        // get user_id
        Cursor cursor2 = dbRead.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor2.moveToFirst()) {
            user_id = cursor2.getString(0);
        }
        cursor2.close();
        Log.d("user_id", user_id);
        Log.d("cat_name", catName);
        final String MY_QUERY = "SELECT * FROM category a INNER JOIN user_cat b ON a.id=b.cat_id WHERE b.user_id=? AND a.cat_name=?";
        Cursor cursor = dbRead.rawQuery(MY_QUERY, new String[]{user_id, catName});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    // save new category
    public boolean saveCategory (String drawableName, String catName, String username, int position) {
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        SQLiteDatabase dbRead = this.getReadableDatabase();
        long ins1=0;
        long ins2;

        // check if same cat_name and cat_icon exist in the table
        Cursor cursor1 = dbRead.rawQuery("Select * from category where cat_name = ? and cat_icon = ?", new String[]{catName, drawableName});
        if (cursor1.moveToFirst()) {
            Log.d("info", "same category exist");
        } else {
            // save data to category table
            ContentValues categoryTable = new ContentValues();
            categoryTable.put("cat_name", catName);
            categoryTable.put("cat_icon", drawableName);
            categoryTable.put("icon_cat_id", position+1);
            ins1 = dbWrite.insert("category", null, categoryTable);
        }
        cursor1.close();
        // get user_id
        Cursor cursor2 = dbRead.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor2.moveToFirst()) {
            user_id = cursor2.getString(0);
        }
        cursor2.close();
//        Log.d("user_id", user_id);
        // get cat_id
        Cursor cursor3 = dbRead.rawQuery("Select id from category where cat_name = ?", new String[]{catName});
        if (cursor3.moveToFirst()) {
            cat_id = cursor3.getString(0);
        }
        cursor3.close();
//        Log.d("cat_id", cat_id);

        ContentValues userCatTable = new ContentValues();
        userCatTable.put("user_id", user_id);
        userCatTable.put("cat_id", cat_id);
        ins2 = dbWrite.insert("user_cat", null, userCatTable);

        if (ins1 == -1 || ins2 == -1)
            return false;
        else
            return true;
    }
}
