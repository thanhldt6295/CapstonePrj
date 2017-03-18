package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;

public class OrderActivity extends AppCompatActivity {

    List<CartItem> cart = new ArrayList<>();
    TextView roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordercart);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.order));
        LinearLayout layoutCart = (LinearLayout) findViewById(R.id.layoutCart);
        layoutCart.getBackground().setAlpha(80);
        LinearLayout layoutOrder = (LinearLayout) findViewById(R.id.layoutOrder);
        layoutCart.getBackground().setAlpha(80);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.getBackground().setAlpha(102);
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        cart = (List<CartItem>) getIntent().getSerializableExtra("storeItem");

        TextView total = (TextView) findViewById(R.id.txtCartTotal);
        final Button btnFinalize = (Button) findViewById(R.id.btnFinalizeOrder);
        btnFinalize.setFocusable(true);
        btnFinalize.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    btnFinalize.setTextColor(Color.parseColor("#E2FFE600"));
                }
                else {
                    btnFinalize.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        final Button btnClear = (Button) findViewById(R.id.btnClearOrder);
        btnClear.setFocusable(true);
        btnClear.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    btnClear.setTextColor(Color.parseColor("#E2FFE600"));
                }
                else {
                    btnClear.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        Spinner s = (Spinner) findViewById(R.id.spinner);

        if(cart!=null){
            ListView listView = (ListView) findViewById(R.id.orderListView);
            OrderAdapter a = new OrderAdapter(this, listView, cart, total, btnFinalize, btnClear, s);
            listView.setAdapter(a);
        }

        // Datetime & Calendar
        final TextView txtDate;
        txtDate = (TextView) findViewById(R.id.txtDate);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date()) + "  "
                                        + DateFormat.getDateInstance().format(new Date());
                                txtDate.setText(currentDateTimeString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        final Calendar myCalen = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalen.set(Calendar.YEAR, year);
                myCalen.set(Calendar.MONTH, monthOfYear);
                myCalen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(OrderActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Gson gson = new Gson();
        SharedPreferences sp = getSharedPreferences("cart", Context.MODE_PRIVATE);
        String cartinfo = gson.toJson(cart);
        final SharedPreferences.Editor editor = sp.edit();
        editor.putString("cartinfo", cartinfo);
        editor.commit();
        super.onBackPressed();
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}

