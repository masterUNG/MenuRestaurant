package appewtc.masterung.menurestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 2/24/15 AD.
 */
public class OrderTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String ORDER_TABLE = "orderTABLE";
    public static final String COLUMN_ID_ORDER = "_id";
    public static final String COLUMN_OFFICER_ORDER = "OfficerOrder";
    public static final String COLUMN_DESK = "Desk";
    public static final String COLUMN_NAME_FOOD = "NameFood";
    public static final String COLUMN_AMOUNT = "Amount";

    public OrderTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    public long addValueOrder(String strOfficer, String strDesk, String strFood, String strAmount) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_OFFICER_ORDER, strOfficer);
        objContentValues.put(COLUMN_DESK, strDesk);
        objContentValues.put(COLUMN_NAME_FOOD, strFood);
        objContentValues.put(COLUMN_AMOUNT, strAmount);

        return writeSQLite.insert(ORDER_TABLE, null, objContentValues);
    }   // addValue

}   // Main Class
