package appewtc.masterung.menurestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class ConfirmOrderListView extends ActionBarActivity {

    private TextView txtShowOfficer, txtShowDesk;
    private String strOfficer, strDesk;
    private ConfirmAdapter objConfirmAdapter;
    private OrderTABLE objOrderTABLE;
    private String[] strListFood, strListAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_list_view);

        //Bind widget
        bindWidget();

        //Setup TextView
        setupTextView();

        //Create List View
        createListView();

    }   // onCreate

    public void clickAddOrder(View view) {

        backToOrder();

    }

    private void backToOrder() {
        Intent objIntent = new Intent(ConfirmOrderListView.this, OrderActivity.class);
        objIntent.putExtra("Officer", strOfficer);
        startActivity(objIntent);
        finish();
    }

    public void clickUploadOrder(View view) {

    }

    private void createListView() {

        objOrderTABLE = new OrderTABLE(this);
        strListFood = objOrderTABLE.listFood();
        strListAmount = objOrderTABLE.listAmount();

        objConfirmAdapter = new ConfirmAdapter(ConfirmOrderListView.this, strListFood, strListAmount);
        ListView confirmListView = (ListView) findViewById(R.id.listView2);
        confirmListView.setAdapter(objConfirmAdapter);

        //Set on Click
        confirmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertConfirm(position);
            }
        });


    }   // createListView

    private void alertConfirm(final int intPositionDelete) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.restaurant);
        objBuilder.setTitle("What do you Want ?");
        objBuilder.setMessage("You want to delete or edit ?");
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteOrder(intPositionDelete);
                dialog.dismiss();
            }
        });
        objBuilder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteOrder(intPositionDelete);
                backToOrder();
                dialog.dismiss();
            }
        });
        objBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        objBuilder.show();

    }   // alertConfirm

    private void deleteOrder(int intDelete) {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("restaurant.db", MODE_PRIVATE, null);
        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
        objSqLiteDatabase.delete("orderTABLE", "_id" + "=" + intDelete + 1, null);

        Log.d("Restaurant", "id==>" + Integer.toString(intDelete));

        createListView();

    }   // deleteOrder

    private void setupTextView() {
        strOfficer = getIntent().getExtras().getString("Officer");
        strDesk = getIntent().getExtras().getString("Desk");

        Log.d("Restaurant", "Desk ======>" + strDesk);

        txtShowOfficer.setText(strOfficer);
        txtShowDesk.setText(strDesk);
    }   // setupTextView

    private void bindWidget() {
        txtShowOfficer = (TextView) findViewById(R.id.txtShowOfficerConfirm);
        txtShowDesk = (TextView) findViewById(R.id.txtShowDesk);
    }   // bindWidget


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_order_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}   // Main Class
