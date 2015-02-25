package appewtc.masterung.menurestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;
    private EditText edtUser, edtPassword;
    private String strUserChoose, strPassowordChoose, strPasswordTrue, strOfficer;
    private OrderTABLE objOrderTABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial Widget
        initialWidget();

        //Create or Connect SQLite
        createOrConncet();

        //Tester Add Value
        //testerAddValue();

        //Delete All data
        deleteAllData();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    }   // onCreate

    private void initialWidget() {
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
    }   // initialWidget

    public void clickLogin(View view) {

        strUserChoose = edtUser.getText().toString().trim();
        strPassowordChoose = edtPassword.getText().toString().trim();

        if (strUserChoose.equals("") || strPassowordChoose.equals("") ) {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.alertDialog(MainActivity.this, "Have Space", "Please Fill All EveryBlank");
        } else {
            //Check User
            checkUser();
        }

    }   // clickLogin

    private void checkUser() {

        try {

            String strMyResult[] = objUserTABLE.searchUser(strUserChoose);
            strPasswordTrue = strMyResult[2];
            strOfficer = strMyResult[3];

            Log.d("Restaurant", "Welcom ==> " + strOfficer);

            //Check Password
            checkPassword();

        } catch (Exception e) {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.alertDialog(MainActivity.this, "No this User", "No " + strUserChoose + " in my Database");
        }

    }   // checkUser

    private void checkPassword() {

        if (strPassowordChoose.equals(strPasswordTrue)) {

            welcomeOfficer();

        } else {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.alertDialog(MainActivity.this, "Password False", "Please try again Password False");
        }

    }   // checkPassword

    private void welcomeOfficer() {

        AlertDialog.Builder objAlert = new AlertDialog.Builder(this);
        objAlert.setIcon(R.drawable.restaurant);
        objAlert.setTitle("Welcome Officer");
        objAlert.setMessage("Welcome " + strOfficer + "\n" + "To my Restaurant");
        objAlert.setCancelable(false);
        objAlert.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtUser.setText("");
                edtPassword.setText("");
                dialog.dismiss();
            }
        });
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
                objIntent.putExtra("Officer", strOfficer);
                startActivity(objIntent);
                dialog.dismiss();
            }
        });
        objAlert.show();

    }   // welcomeOfficer

    private void deleteAllData() {
        SQLiteDatabase objSQLite = openOrCreateDatabase("restaurant.db", MODE_PRIVATE, null);
        objSQLite.delete("userTABLE", null, null);
        objSQLite.delete("foodTABLE", null, null);
    }   // deleteAllData

    private void synJSONtoSQLite() {

        //Change Policy
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);
        }   // if

        InputStream objInputStream = null;
        String strJSON = "";

        //Create InputStream
        try {

            HttpClient objHttplient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/poy/get_data_user_poy.php");
            HttpResponse objHttpResponse = objHttplient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

        } catch (Exception e) {
            Log.d("Restaurant", "InputStream ==> " + e.toString());
        }

        //Create strJSON
        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine()) != null) {
                objStringBuilder.append(strLine);
            }   // while

            objInputStream.close();
            strJSON = objStringBuilder.toString();

        } catch (Exception e) {
            Log.d("Restaurant", "strJSON ==> " + e.toString());
        }

        //Update userTABLE
        try {

            final JSONArray objJSONArray = new JSONArray(strJSON);
            for (int i = 0; i < objJSONArray.length(); i++) {

                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                String strUser = objJSONObject.getString("User");
                String strPassword = objJSONObject.getString("Password");
                String strOfficer = objJSONObject.getString("Officer");
                String strAddress = objJSONObject.getString("Address");
                String strTel = objJSONObject.getString("Tel");

                //Update Data to userTABLE
                long insertValut = objUserTABLE.addValueToUser(strUser, strPassword, strOfficer, strAddress, strTel);

            }   // for

        } catch (Exception e) {
            Log.d("Restaurant", "Update SQLite ==> " + e.toString());
        }



    }   // synJSONtoSQLite

    private void testerAddValue() {
        objUserTABLE.addValueToUser("User", "Password", "Officer", "Address", "Tel");
        objFoodTABLE.addValueToFood("Food", "Price");
        objOrderTABLE.addValueOrder("Officer", "Desk", "Food", "Amount");
    }   // testerAddValue

    private void createOrConncet() {
        objUserTABLE = new UserTABLE(this);
        objFoodTABLE = new FoodTABLE(this);
        objOrderTABLE = new OrderTABLE(this );
    }   // createOrConncet


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
