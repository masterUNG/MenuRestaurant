package appewtc.masterung.menurestaurant;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ConfirmOrderListView extends ActionBarActivity {

    private TextView txtShowOfficer, txtShowDesk;
    private String strOfficer, strDesk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_list_view);

        //Bind widget
        bindWidget();

        //Setup TextView
        setupTextView();


    }   // onCreate

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
