package org.mad.app.hokiehelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *  The database adapter class with methods for database
 *
 *  @author Eeshan
 *  @version Mar 9, 2011
 */
public class Dining_DBAdapter
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SERVING_SIZE = "servingSize";
    public static final String KEY_CAL = "calories";
    public static final String KEY_CAL_FAT = "caloriesFat";
    public static final String KEY_TOTAL_FAT = "totalFat";
    public static final String KEY_TOTAL_FAT_DV = "totalFatDv";
    public static final String KEY_TOTAL_CARB = "totalCarb";
    public static final String KEY_TOTAL_CARB_DV = "totalCarbDv";
    public static final String KEY_SAT_FAT = "satFat";
    public static final String KEY_SAT_FAT_DV = "satFatDv";
    public static final String KEY_FIBER = "fiber";
    public static final String KEY_FIBER_DV = "fiberDv";
    public static final String KEY_TRANS_FAT = "transFat";
    public static final String KEY_SUGARS = "sugars";
    public static final String KEY_CHOLESTEROL = "cholesterol";
    public static final String KEY_CHOLESTEROL_DV = "cholesterolDv";
    public static final String KEY_PROTEIN = "protein";
    public static final String KEY_SODIUM = "sodium";
    public static final String KEY_SODIUM_DV = "sodiumDv";
    public static final String KEY_CALCIUM = "calcium";
    public static final String KEY_IRON = "iron";
    public static final String KEY_VITAMIN_A = "vitaminA";
    public static final String KEY_VITAMIN_C = "vitaminC";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_ALLERGENS = "allergens";
    public static final String KEY_SUBRESTAURANT = "subrestaurant";
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "betaRelease17";

    private static final int DATABASE_VERSION = 1;
    private static final String D2_CREATE =
            "create table d2 (_id integer primary key autoincrement, " +
                    "name text not null, servingSize text not null, calories text not null, " +
                    "caloriesFat text not null, totalFat text not null, totalFatDv text not null," + 
                    " totalCarb text not null, totalCarbDv text not null, satFat text not null," +
                    " satFatDv text not null, fiber text not null, fiberDv text not null," +
                    " transFat text not null, sugars text not null, cholesterol text not null," +
                    " cholesterolDv text not null, protein text not null, " +
                    "sodium text not null, sodiumDv text not null, calcium text not null, " +
                    "iron text not null, vitaminA text not null, vitaminC text not null, " +
                    "ingredients text not null, allergens text not null, subrestaurant text not null);";
    private static final String DEETS_CREATE =
            "create table deets (_id integer primary key autoincrement, " +
                    "name text not null, servingSize text not null, calories text not null, " +
                    "caloriesFat text not null, totalFat text not null, totalFatDv text not null," + 
                    " totalCarb text not null, totalCarbDv text not null, satFat text not null," +
                    " satFatDv text not null, fiber text not null, fiberDv text not null," +
                    " transFat text not null, sugars text not null, cholesterol text not null," +
                    " cholesterolDv text not null, protein text not null, " +
                    "sodium text not null, sodiumDv text not null, calcium text not null, " +
                    "iron text not null, vitaminA text not null, vitaminC text not null, " +
                    "ingredients text not null, allergens text not null, subrestaurant text not null);";
    private static final String DX_CREATE =
            "create table dx (_id integer primary key autoincrement, " +
                    "name text not null, servingSize text not null, calories text not null, " +
                    "caloriesFat text not null, totalFat text not null, totalFatDv text not null," + 
                    " totalCarb text not null, totalCarbDv text not null, satFat text not null," +
                    " satFatDv text not null, fiber text not null, fiberDv text not null," +
                    " transFat text not null, sugars text not null, cholesterol text not null," +
                    " cholesterolDv text not null, protein text not null, " +
                    "sodium text not null, sodiumDv text not null, calcium text not null, " +
                    "iron text not null, vitaminA text not null, vitaminC text not null, " +
                    "ingredients text not null, allergens text not null, subrestaurant text not null);";
    private static final String HOKIEGRILL_CREATE =
            "create table hokieGrill (_id integer primary key autoincrement, " +
                    "name text not null, servingSize text not null, calories text not null, " +
                    "caloriesFat text not null, totalFat text not null, totalFatDv text not null," + 
                    " totalCarb text not null, totalCarbDv text not null, satFat text not null," +
                    " satFatDv text not null, fiber text not null, fiberDv text not null," +
                    " transFat text not null, sugars text not null, cholesterol text not null," +
                    " cholesterolDv text not null, protein text not null, " +
                    "sodium text not null, sodiumDv text not null, calcium text not null, " +
                    "iron text not null, vitaminA text not null, vitaminC text not null, " +
                    "ingredients text not null, allergens text not null, subrestaurant text not null);";
    private static final String OWENS_CREATE =
            "create table owens (_id integer primary key autoincrement, " +
                    "name text not null, servingSize text not null, calories text not null, " +
                    "caloriesFat text not null, totalFat text not null, totalFatDv text not null," + 
                    " totalCarb text not null, totalCarbDv text not null, satFat text not null," +
                    " satFatDv text not null, fiber text not null, fiberDv text not null," +
                    " transFat text not null, sugars text not null, cholesterol text not null," +
                    " cholesterolDv text not null, protein text not null, " +
                    "sodium text not null, sodiumDv text not null, calcium text not null, " +
                    "iron text not null, vitaminA text not null, vitaminC text not null, " +
                    "ingredients text not null, allergens text not null, subrestaurant text not null);";
    private static final String WESTEND_CREATE =
            "create table westend (_id integer primary key autoincrement, " +
                    "name text not null, servingSize text not null, calories text not null, " +
                    "caloriesFat text not null, totalFat text not null, totalFatDv text not null," + 
                    " totalCarb text not null, totalCarbDv text not null, satFat text not null," +
                    " satFatDv text not null, fiber text not null, fiberDv text not null," +
                    " transFat text not null, sugars text not null, cholesterol text not null," +
                    " cholesterolDv text not null, protein text not null, " +
                    "sodium text not null, sodiumDv text not null, calcium text not null, " +
                    "iron text not null, vitaminA text not null, vitaminC text not null, " +
                    "ingredients text not null, allergens text not null, subrestaurant text not null);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    /**
     * The constructor for the database adapter class
     * @param c the context object
     * @param folders the array of all the folders
     * @param resources the resources for the database
     */
    public Dining_DBAdapter(Context c, String[] folders, Resources resources)
    {
        this.context = c;
        DBHelper = new DatabaseHelper(context, c.getAssets(), folders, resources);
    }

    /**
     *  A private inner helper class for the database
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        private AssetManager assetManager;
        private Resources resources;

        DatabaseHelper(Context context, AssetManager assetManager, String[] folders, Resources resources)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.assetManager = assetManager;
            this.resources = resources;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(D2_CREATE);
            db.execSQL(DEETS_CREATE);
            db.execSQL(DX_CREATE);
            db.execSQL(HOKIEGRILL_CREATE);
            db.execSQL(OWENS_CREATE);
            db.execSQL(WESTEND_CREATE);

            try
            {
                populateDB(db);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS deets");
            db.execSQL("DROP TABLE IF EXISTS d2");
            db.execSQL("DROP TABLE IF EXISTS dx");
            db.execSQL("DROP TABLE IF EXISTS hokieGrill");
            db.execSQL("DROP TABLE IF EXISTS owens");
            db.execSQL("DROP TABLE IF EXISTS westend");
            onCreate(db);
        }


        private String[] getSubFolder(String restaurant) {

            if (restaurant.equals("d2"))
                return resources.getStringArray(R.array.d2Category);
            else if (restaurant.equals("deets"))
                return resources.getStringArray(R.array.deetCategory);
            else if (restaurant.equals("hokieGrill"))
                return resources.getStringArray(R.array.hokieGrillCategory);
            else if (restaurant.equals("owens"))
                return resources.getStringArray(R.array.owensCategory);
            else if (restaurant.equals("westend"))
                return resources.getStringArray(R.array.westendCategory);
            else
                return null;
        }

        private void populateDB(SQLiteDatabase db) throws IOException
        {
            ContentValues initialValues = new ContentValues();
            String[] folders = resources.getStringArray(R.array.dbFolders);
            try
            {
                for (String restaurantFolder : folders)
                {
                    String[] subFolders = getSubFolder(restaurantFolder);
                    for (String subFolder : subFolders)
                    {
                        String[] files = assetManager.list("files/" + restaurantFolder + "/" + subFolder);
                        for (String file : files)
                        {
                            InputStream is = assetManager.open("files/" + restaurantFolder + "/" + subFolder + "/" + file);
                            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF8"));
                            String word = br.readLine();
                            String[] details;
//                            file = file.substring(0, file.length());
                            while (word != null)
                            {
                                //System.out.println(file);
                                initialValues.clear();
                                details = word.split("\\|");
                                for (int l = 0; l < details.length; l++) {
                                    if (details[l].contains("nbsp"))
                                        details[l] = "";
                                }
                                initialValues.put(KEY_NAME, file);
                                initialValues.put(KEY_SERVING_SIZE, details[1]);
                                initialValues.put(KEY_CAL, details[2]);
                                initialValues.put(KEY_CAL_FAT, details[3]);
                                initialValues.put(KEY_TOTAL_FAT, details[4]);
                                initialValues.put(KEY_TOTAL_FAT_DV, details[5]);
                                initialValues.put(KEY_TOTAL_CARB, details[6]);
                                initialValues.put(KEY_TOTAL_CARB_DV, details[7]);
                                initialValues.put(KEY_SAT_FAT, details[8]);
                                initialValues.put(KEY_SAT_FAT_DV, details[9]);
                                initialValues.put(KEY_FIBER, details[10]);
                                initialValues.put(KEY_FIBER_DV, details[11]);
                                initialValues.put(KEY_TRANS_FAT, details[12]);
                                initialValues.put(KEY_SUGARS, details[13]);
                                initialValues.put(KEY_CHOLESTEROL, details[14]);
                                initialValues.put(KEY_CHOLESTEROL_DV, details[15]);
                                initialValues.put(KEY_PROTEIN, details[16]);
                                initialValues.put(KEY_SODIUM, details[17]);
                                initialValues.put(KEY_SODIUM_DV, details[18]);
                                initialValues.put(KEY_CALCIUM, details[19]);
                                initialValues.put(KEY_IRON, details[20]);
                                initialValues.put(KEY_VITAMIN_A, details[21]);
                                initialValues.put(KEY_VITAMIN_C, details[22]);
                                initialValues.put(KEY_INGREDIENTS, details[23]);
                                if (details.length != 24)
                                    initialValues.put(KEY_ALLERGENS, details[24]);
                                else
                                    initialValues.put(KEY_ALLERGENS, "   ");
                                initialValues.put(KEY_SUBRESTAURANT, subFolder);
                                db.insert(restaurantFolder, null, initialValues);
                                word = br.readLine();
                            }
                            is.close();
                            br.close();
                        }
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Method to open the database
     * @return DBAdapter the database
     * @throws SQLException if the database could not be opened
     */
    public Dining_DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    /**
     * Method to close the database
     */
    public void close()
    {
        DBHelper.close();
    }

    //    /**
    //     * Insert a title into the database
    //     * @param tableName the name of the table
    //     * @param name the name of the food item
    //     * @param cal the calories in the food item
    //     * @return the row where the title was inserted
    //     */
    //    public long insertTitle(String tableName, String name, String cal)
    //    {
    //        ContentValues initialValues = new ContentValues();
    //        initialValues.put(KEY_NAME, name);
    //        initialValues.put(KEY_CAL, cal);
    //        return db.insert(tableName, null, initialValues);
    //    }

    /**
     * Delete a title from the database
     * @param tableName the name of the table
     * @param rowId the row to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteTitle(String tableName, long rowId)
    {
        return db.delete(tableName, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    /**
     * Retreive all the titles from the database
     * @param tableName the name of the table
     * @return cursor the cursor object pointing to the first row
     */
    public Cursor getAllTitles(String tableName)
    {
        return db.query(tableName, new String[] {KEY_ROWID, KEY_NAME, KEY_SERVING_SIZE,
                KEY_CAL, KEY_CAL_FAT, KEY_TOTAL_FAT, KEY_TOTAL_FAT_DV,
                KEY_TOTAL_CARB, KEY_TOTAL_CARB_DV, KEY_SAT_FAT, KEY_SAT_FAT_DV,
                KEY_FIBER, KEY_FIBER_DV, KEY_TRANS_FAT, KEY_SUGARS, KEY_CHOLESTEROL,
                KEY_CHOLESTEROL_DV, KEY_PROTEIN, KEY_SODIUM, KEY_SODIUM_DV,
                KEY_CALCIUM, KEY_IRON, KEY_VITAMIN_A, KEY_VITAMIN_C, KEY_INGREDIENTS,
                KEY_ALLERGENS, KEY_SUBRESTAURANT},
                /*KEY_SUBRESTAURANT + "=" + tableName,*/ null, null, null, null, null);
    }

    /**
     * Get a particular title from the dataabase
     * @param tableName the name of the table
     * @param name the food item to retreive
     * @return Cursor pointing to that row
     * @throws SQLException if the title cannot be retreived
     */
    public Cursor getTitle(String tableName, String name) throws SQLException
    {
        //NOT USED
        Cursor mCursor =
                db.query(true, tableName, new String[] {KEY_ROWID, KEY_NAME, KEY_SERVING_SIZE,
                        KEY_CAL, KEY_CAL_FAT, KEY_TOTAL_FAT, /*KEY_TOTAL_FAT_DV,*/
                        KEY_TOTAL_CARB, /*KEY_TOTAL_CARB_DV,*/ KEY_SAT_FAT, /*KEY_SAT_FAT_DV,*/
                        KEY_FIBER, /*KEY_FIBER_DV,*/ KEY_TRANS_FAT, KEY_SUGARS, KEY_CHOLESTEROL,
                        /*KEY_CHOLESTEROL_DV,*/ KEY_PROTEIN, KEY_SODIUM, /*KEY_SODIUM_DV,*/
                        KEY_CALCIUM, KEY_IRON, KEY_VITAMIN_A, KEY_VITAMIN_C},
                        KEY_NAME + "=" + name, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //    /**
    //     * Update a particular title
    //     * @param tableName the name of the table
    //     * @param rowId the row of the title
    //     * @param name the name of the food item
    //     * @param cal the calories in the food item
    //     * @return true if the title was updated successfully, false otherwise
    //     */
    //    public boolean updateTitle(String tableName, long rowId, String name, String cal)
    //    {
    //        ContentValues args = new ContentValues();
    //        args.put(KEY_NAME, name);
    //        args.put(KEY_CAL, cal);
    //        return db.update(tableName, args,
    //                         KEY_ROWID + "=" + rowId, null) > 0;
    //    }
}
