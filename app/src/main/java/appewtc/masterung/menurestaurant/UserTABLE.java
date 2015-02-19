package appewtc.masterung.menurestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 2/19/15 AD.
 */
public class UserTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String USER_TABLE = "userTABLE";
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_OFFICER = "Officer";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_TEL = "Tel";

    public UserTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    //Add Value to userTABLE
    public long addValueToUser(String strUser, String strPassword, String strOfficer, String strAddress, String strTel) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_OFFICER, strOfficer);
        objContentValues.put(COLUMN_ADDRESS, strAddress);
        objContentValues.put(COLUMN_TEL, strTel);
        return writeSQLite.insert(USER_TABLE, null, objContentValues);
    }   // addValueToUser

}   // Main Class
