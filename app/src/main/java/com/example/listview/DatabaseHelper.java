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

import com.github.mikephil.charting.data.PieEntry;

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
                                         "user_id integer not null," +
                                         "cat_id integer not null," +
                                         "foreign key(user_id) references user(id)," +
                                         "foreign key(cat_id) references category(id))");
        db.execSQL("Create table if not exists record(id integer primary key autoincrement," +
                                         "user_id integer not null," +
                                         "cat_id integer not null," +
                                         "memo text," +
                                         "total double not null," +
                                         "year int not null," +
                                         "month int not null," +
                                         "day int not null," +
                                         "type text not null," +
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

        // get user_id
        Cursor cursor = db.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();

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

        // get cat_id
        Cursor cursor3 = dbRead.rawQuery("Select id from category where cat_name = ?", new String[]{catName});
        if (cursor3.moveToFirst()) {
            cat_id = cursor3.getString(0);
        }
        cursor3.close();

        ContentValues userCatTable = new ContentValues();
        userCatTable.put("user_id", user_id);
        userCatTable.put("cat_id", cat_id);
        ins2 = dbWrite.insert("user_cat", null, userCatTable);

        if (ins1 == -1 || ins2 == -1)
            return false;
        else
            return true;
    }

    public boolean deleteItem (String type, int position, String username) {
        SQLiteDatabase dbRead = this.getWritableDatabase();
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        int count = 0;

        if (type == "expense") {
            type = "1";
        } else if (type == "income") {
            type = "2";
        }
        // get user_id
        Cursor cursor2 = dbRead.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor2.moveToFirst()) {
            user_id = cursor2.getString(0);
        }
        cursor2.close();

        Log.d("type", type);
        Log.d("position", Integer.toString(position));
        Log.d("user_id", user_id);
        final String MY_QUERY = "SELECT * FROM user_cat a INNER JOIN category b ON b.id=a.cat_id WHERE a.user_id=? AND b.icon_cat_id=?";
        Cursor cursor = dbRead.rawQuery(MY_QUERY, new String[]{user_id, type});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                count++;
                if (count == position) {
                    int user_cat_id = cursor.getInt(0);
                    Log.d("user_cat_id", Integer.toString(user_cat_id));
                    dbWrite.delete("user_cat", "id" + "=?", new String[]{String.valueOf(user_cat_id)});
                    cursor.close();
                    return true;
                }
                cursor.moveToNext();
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean saveRecord(String username, String drawableName, String iconName, String memo,
                                double total, int year, int month, int day, String type) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        Log.d("selected", type);
        long ins;
        // get user_id
        Cursor cursor = dbRead.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();
        // get cat_id
        Cursor cursor2 = dbRead.rawQuery("Select id from category where cat_name = ? and cat_icon = ?", new String[]{iconName, drawableName});
        if (cursor2.moveToFirst()) {
            cat_id = cursor2.getString(0);
        }
        cursor2.close();
//        Log.d ("user_id", user_id);
//        Log.d("cat_id", cat_id);

        // save data into record table
        ContentValues recordTable = new ContentValues();
        recordTable.put("user_id", user_id);
        recordTable.put("cat_id", cat_id);
        recordTable.put("memo", memo);
        recordTable.put("total", total);
        recordTable.put("year", year);
        recordTable.put("month", month);
        recordTable.put("day", day);
        recordTable.put("type", type);

        ins = dbWrite.insert("record", null, recordTable);
        if (ins == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<RecordClass> getRecord(String username, int year, int month) {
        ArrayList<RecordClass> recordList = new ArrayList<>();
        String yearString = Integer.toString(year);
        String monthString = Integer.toString(month);
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();

        // get record
        Cursor cursor3 = dbRead.rawQuery("Select * from record where user_id = ? and year = ? and month = ? order by day desc", new String[]{user_id, yearString, monthString});
        if (cursor3.getCount() > 0) {
            cursor3.moveToFirst();
            while(!cursor3.isAfterLast()) {
                int recordId = cursor3.getInt(0);
                String catId = cursor3.getString(2);
                String memo = cursor3.getString(3);
                Double total = cursor3.getDouble(4);
                int displayYear = cursor3.getInt(5);
                int displayMonth = cursor3.getInt(6);
                int displayDay = cursor3.getInt(7);
                String type = cursor3.getString(8);
                RecordClass record = new RecordClass();
                record.setRecordId(recordId);
                record.setMemo(memo);
                record.setTotal(total);
                record.setYear(displayYear);
                record.setMonth(displayMonth);
                record.setDay(displayDay);
                record.setType(type);
//                Log.d("memo", memo);
//                Log.d("total", Double.toString(total));
//                Log.d("year", Integer.toString(displayYear));
//                Log.d("month", Integer.toString(displayMonth));
//                Log.d("day", Integer.toString(displayDay));
//                Log.d("type", type);
                // get catIcon and catName
                String MY_QUERY = "SELECT * FROM category a INNER JOIN record b ON a.id=b.cat_id WHERE b.user_id=? and b.cat_id=?";
                Cursor cursor2 = dbRead.rawQuery(MY_QUERY, new String[]{user_id, catId});
                if (cursor2.moveToFirst()) {
                    String catName = cursor2.getString(1);
                    String catIcon = cursor2.getString(2);
                    int image_id = this.context.getResources().getIdentifier(catIcon, "drawable", this.context.getApplicationContext().getPackageName());
                    record.setCatName(catName);
                    record.setCatIcon(image_id);
//                    Log.d("catName", catName);
//                    Log.d("catIcon", catIcon);
                }
                cursor2.close();
                recordList.add(record);
                cursor3.moveToNext();
            }
        }
        cursor3.close();
        return recordList;
    }

    public ArrayList<IncomeExpenseClass> getIncomeExpense(String username, String month, int year) {
        ArrayList<IncomeExpenseClass> incomeExpense = new ArrayList<>();
        SQLiteDatabase dbRead = this.getReadableDatabase();
        String selectedYear = Integer.toString(year);
        String selectedMonth = "1";
        double expense = 0;
        double income = 0;
        double balance = 0;
        Cursor cursor = dbRead.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();

        switch(month) {
            case "JAN":
                selectedMonth = "1";
                break;
            case "FEB":
                selectedMonth = "2";
                break;
            case "MAR":
                selectedMonth = "3";
                break;
            case "APR":
                selectedMonth = "4";
                break;
            case "MAY":
                selectedMonth = "5";
                break;
            case "JUN":
                selectedMonth = "6";
                break;
            case "JUL":
                selectedMonth = "7";
                break;
            case "AUG":
                selectedMonth = "8";
                break;
            case "SEP":
                selectedMonth = "9";
                break;
            case "OCT":
                selectedMonth = "10";
                break;
            case "NOV":
                selectedMonth = "11";
                break;
            case "DEC":
                selectedMonth = "12";
                break;
            default:
                break;
        }

        Cursor cursor2 = dbRead.rawQuery("Select total from record where user_id=? and type=? and month=? and year=?", new String[]{user_id, "expense", selectedMonth, selectedYear});
        if (cursor2.getCount() > 0) {
            cursor2.moveToFirst();
            while(!cursor2.isAfterLast()) {
                expense += cursor2.getDouble(0);
                cursor2.moveToNext();
            }
        }
        cursor2.close();

        Cursor cursor3 = dbRead.rawQuery("Select total from record where user_id=? and type=? and month=? and year=?", new String[]{user_id, "income", selectedMonth, selectedYear});
        if (cursor3.getCount() > 0) {
            cursor3.moveToFirst();
            while(!cursor3.isAfterLast()) {
                income += cursor3.getDouble(0);
                cursor3.moveToNext();
            }
        }
        cursor3.close();

        balance = income - expense;

        IncomeExpenseClass record = new IncomeExpenseClass();
        record.setExpense(expense);
        record.setIncome(income);
        record.setBalance(balance);
        incomeExpense.add(record);
        Log.d("expense", Double.toString(record.getExpense()));
        Log.d("income", Double.toString(record.getIncome()));
        Log.d("balance", Double.toString(record.getBalance()));
        return incomeExpense;
    }

    public ArrayList<PieEntry> getChartData2(String username, String month, int year, String type) {
        ArrayList<PieEntry> dataEntries = new ArrayList<>();
        String selectedMonth = "1";
        String[] catName;
        float[] value;
        int c = 0;
        String cat_id;
        boolean found = false;
        String stringYear = Integer.toString(year);
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();

        switch(month) {
            case "JAN":
                selectedMonth = "1";
                break;
            case "FEB":
                selectedMonth = "2";
                break;
            case "MAR":
                selectedMonth = "3";
                break;
            case "APR":
                selectedMonth = "4";
                break;
            case "MAY":
                selectedMonth = "5";
                break;
            case "JUN":
                selectedMonth = "6";
                break;
            case "JUL":
                selectedMonth = "7";
                break;
            case "AUG":
                selectedMonth = "8";
                break;
            case "SEP":
                selectedMonth = "9";
                break;
            case "OCT":
                selectedMonth = "10";
                break;
            case "NOV":
                selectedMonth = "11";
                break;
            case "DEC":
                selectedMonth = "12";
                break;
            default:
                break;
        }

//        Log.d("selectedMonth", selectedMonth);
        // get category name and expenses
        String MY_QUERY = "SELECT * FROM category a INNER JOIN record b ON a.id=b.cat_id WHERE b.user_id=? and b.type=? and b.year=? and b.month=?";
        Cursor cursor2 = dbRead.rawQuery(MY_QUERY, new String[]{user_id, type, stringYear, selectedMonth});
        if (cursor2.getCount() > 0) {
            catName = new String[cursor2.getCount()];
            value = new float[cursor2.getCount()];
            cursor2.moveToFirst();
            while(!cursor2.isAfterLast()) {
                float total = 0;
                for (int i = 0; i < catName.length; i++) {
                    if (cursor2.getString(1).equals(catName[i])) {
                        found = true;
                    }
                }
                if (!found) {
                    cat_id = cursor2.getString(0);
                    catName[c] = cursor2.getString(1);
                    Cursor cursor3 = dbRead.rawQuery("Select total from record where user_id=? and type=? and month=? and year=? and cat_id=?",
                            new String[]{user_id, type, selectedMonth, stringYear, cat_id});
                    if (cursor3.getCount() > 0) {
                        cursor3.moveToFirst();
                        while (!cursor3.isAfterLast()) {
                            total += cursor3.getDouble(0);
                            cursor3.moveToNext();
                        }
                    }
                    cursor3.close();
                    value[c] = total;
                    cursor2.moveToNext();
                    dataEntries.add(new PieEntry(value[c], catName[c]));
                    Log.d("catId", cat_id);
                    Log.d("catName", catName[c]);
                    Log.d("total", Double.toString(value[c]));
                    c++;
                } else {
                    cursor2.moveToNext();
                }
            }
            cursor2.close();
        } else {
            dataEntries.clear();
//            Log.d("clear", "clear");
        }
        return dataEntries;
    }

    public Double getTotalValue(String username, String month, int year, String type) {
        Double total = 0.0;
        String selectedMonth = "1";
        String selectedYear = Integer.toString(year);
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("Select id from user where username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();

        switch(month) {
            case "JAN":
                selectedMonth = "1";
                break;
            case "FEB":
                selectedMonth = "2";
                break;
            case "MAR":
                selectedMonth = "3";
                break;
            case "APR":
                selectedMonth = "4";
                break;
            case "MAY":
                selectedMonth = "5";
                break;
            case "JUN":
                selectedMonth = "6";
                break;
            case "JUL":
                selectedMonth = "7";
                break;
            case "AUG":
                selectedMonth = "8";
                break;
            case "SEP":
                selectedMonth = "9";
                break;
            case "OCT":
                selectedMonth = "10";
                break;
            case "NOV":
                selectedMonth = "11";
                break;
            case "DEC":
                selectedMonth = "12";
                break;
            default:
                break;
        }

        Cursor cursor2 = dbRead.rawQuery("Select total from record where user_id=? and type=? and month=? and year=?", new String[]{user_id, type, selectedMonth, selectedYear});
        if (cursor2.getCount() > 0) {
            cursor2.moveToFirst();
            while(!cursor2.isAfterLast()) {
                total += cursor2.getDouble(0);
                cursor2.moveToNext();
            }
        }
        cursor2.close();

        return total;
    }

    public void deleteRecord(int tag) {
        SQLiteDatabase dbRead = this.getWritableDatabase();
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        String stringTag = Integer.toString(tag);

        final String MY_QUERY = "SELECT * FROM record WHERE id=?";
        Cursor cursor = dbRead.rawQuery(MY_QUERY, new String[]{stringTag});
        if (cursor.moveToFirst()) {
            dbWrite.delete("record", "id" + "=?", new String[]{String.valueOf(stringTag)});
        }
        cursor.close();
    }
}
