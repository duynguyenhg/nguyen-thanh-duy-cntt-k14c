package com.example.garuuu.dubaott;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;


public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnChangeActivity;
    TextView txtName, txtCountry, txtTemp, txtStatus, txtHumidity, txtCloud, txtWind, txtDay;
    ImageView imgIcon; //khai bao nhung thuoc tinh va gan id cho cac thuoc tinh
    String City = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();//tao anh xa
        GetCurrentWeatherData("HaNoi");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //bat su kien nut tim kiem
                String city = edtSearch.getText().toString();
                if(city.equals("")){//neu nhu thanh pho rong thi se gan = HaNoi
                    City = "HaNoi";
                    GetCurrentWeatherData(City);
                }else{//nguoc lai ton tai thi se lay gia tri do
                    City = city;
                    GetCurrentWeatherData(City);
                }
                GetCurrentWeatherData(city);
            }
        });
        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //bat su kien chuyen man hinh, lay du lieu tu edittext(lay du lieu cua city chuyen du lieu qua man hinh thu 2 xem nhiet do 5 ngay tiep)
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });
    }
    public void GetCurrentWeatherData(final String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this); //tao bien request nay se thuc thi nhung yeu cau ma chung ta gui di
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=2e7d961b68174c71444aa3addc3b7909";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,    //doc du lieu duong dan va tra ve du lieu cho nguoi dung
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response); // khoi tao jsonObject, gan bien response vao tham so khoi tao doc duoc gia tri khoi tao ben trong obj
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name"); //lay 2 gia tri ngay va ten thanh pho
                            txtName.setText("Ten Thanh Pho: "+name); //settext cho name

                            long l = Long.valueOf(day); //chuyen bien day ve dang long, truyen bien day vao
                            Date date = new Date(l*1000L); //goi ham tao bien day, tham so khoi tao, du lieu bien unit dang giay chuyen ve mac dinh la ms. lay bien long * 1000 la lay dc gia tri millis
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");//tao bien simple truyen thu, ngay, thang, nam, gio, phut, giay
                            String Day = simpleDateFormat.format(date); //tao bien du lieu, truyen gia tri date khi da format day

                            txtDay.setText(Day); //settext bien day
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0); //lay gia tri thoi tiet cua json
                            String status = jsonObjectWeather.getString("main"); //truyen vao trang thai tu main
                            String icon = jsonObjectWeather.getString("icon");//truyen vao icon lay tu jsonobj

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgIcon); //duong link trang thai
                            txtStatus.setText(status); //settext trag thai

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");//goi jsonobj de lay nhiet do va do am trong main
                            String nhietdo = jsonObjectMain.getString("temp");//tinh do C
                            String doam = jsonObjectMain.getString("humidity");//tinh %

                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue()); //doi kieu int ve kieu chuoi

                            txtTemp.setText(Nhietdo+"°C"); //settext nhiet do
                            txtHumidity.setText(doam+"%"); //settext doam;

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind"); //khai bao jsonObjectWind lay jsonobj ngoai cung thong qua text name wind
                            String gio = jsonObjectWind.getString("speed");//tao bien va huong gia tri ben trong wind
                            txtWind.setText(gio+"m/s"); //settext

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            txtCloud.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtCountry.setText("Ten Quoc Gia:"+country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest); // thuc thi stringRequest
    }
        private void Anhxa() {
            edtSearch = (EditText) findViewById(R.id.edittextSearch); //goi lai nhung thuoc tinh da khai bao o phia tren
            btnSearch = (Button) findViewById(R.id.buttonSearch);
            btnChangeActivity = (Button) findViewById(R.id.buttonChangeActivity);
            txtName = (TextView) findViewById(R.id.textviewName);
            txtCountry = (TextView) findViewById(R.id.textviewCountry);
            txtTemp = (TextView) findViewById(R.id.textviewTemp);
            txtStatus = (TextView) findViewById(R.id.textviewStatus);
            txtHumidity = (TextView) findViewById(R.id.textviewHummidity);
            txtCloud = (TextView) findViewById(R.id.textviewCloud);
            txtWind = (TextView) findViewById(R.id.textviewWind);
            txtDay = (TextView) findViewById(R.id.textviewDay);
            imgIcon = findViewById(R.id.imageIcon);

        }
    }

