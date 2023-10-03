package com.example.ui1.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ui1.Blockchain.Blockchain;
import com.example.ui1.Introduction.CloseContactIntroduction;
import com.example.ui1.Models.ContactModel;
import com.example.ui1.Models.PositivePatient;
import com.example.ui1.R;
import com.example.ui1.SQLite.DbHandler;
import com.example.ui1.SelfAssessment.ReportActivity;
import com.example.ui1.SelfAssessment.SelfAssessment;
import com.example.ui1.SelfAssessment.SelfAssessmentHome;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {
    private TextView status;
    VideoView videoView;

    private Button btnStats;
    private  Button btnSelfAss;
    private Button btnProf;
    private Button btnRepo;
    public static String health;

    public static final int REQUEST_ENABLE_BLUETOOTH = 11;
    private static final String TAG = "BluetoothActivity";
    BluetoothAdapter mBluetoothAdapter;
    public ArrayList<BluetoothDevice> mBTDevices;
    Handler handlerBluetooth,handlerBlockchain;
    Runnable runnable,runnable1;
    int delay = 60000*10;

    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        handlerBluetooth = new Handler();
        handlerBlockchain = new Handler();

        //System.out.println("Helath "+health);

        status = findViewById(R.id.tvStatusValue);

        SharedPreferences sharedPreferences = getSharedPreferences(SelfAssessment.SHARED_PREFS, MODE_PRIVATE);
        health = sharedPreferences.getString(SelfAssessment.TEXT, "");

        status.setText("" + health);

        this.videoView = findViewById(R.id.vvBlueScan);
        this.videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.blue_scan2));
        MediaController videoControl = new MediaController(this);
        videoControl.setAnchorView(this.videoView);
        //this.videoView.setMediaController(videoControl);
        this.videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        //receiving positive mobile address from blockchain
        List positiveMobileNumbers = new ArrayList();
        Blockchain blockchain =  new Blockchain();
        try {
            positiveMobileNumbers = blockchain.getPositiveMobileNumbers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //update positive bluetooth mac address from firebase
        PositivePatient positivePatient = new PositivePatient(positiveMobileNumbers);

        if (health.equals("CLOSE CONTACT")){
            System.out.println("Yor are close contact");
        }else{
            handlerBlockchain.postDelayed(runnable1 = new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    compare(positivePatient);
                }
            },5000);
        }



        btnStats = (Button) findViewById(R.id.btnStats);
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStats();
            }
        });
        btnSelfAss = (Button) findViewById(R.id.btnSelfAss);
        btnSelfAss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelfAss();
            }
        });
        btnProf = (Button) findViewById(R.id.btnProf);
        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProf();
            }
        });

        btnRepo = (Button)findViewById(R.id.btnReport);
        btnRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReport();;
            }
        });

        mBTDevices = new ArrayList<>();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        dbHandler = new DbHandler(Home.this);
        checkBluetoothState();
        startScan();
    }

    public void openStats(){
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
        finish();
    }
    public void openSelfAss(){
        Intent intent = new Intent(this, SelfAssessmentHome.class);
        startActivity(intent);
        finish();
    }
    public void openProf(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
    public void openReport(){
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onPostResume() {
        videoView.resume();
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        videoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }

    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
//            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
//                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                String address  = device.getAddress();
                addToDatabase(address);
            }
        }
    };


    private void startScan() {

        View v= this.findViewById(android.R.id.content);
        btnDiscover(v);
        Method method;
        try {
            method = mBluetoothAdapter.getClass().getMethod("setScanMode", int.class, int.class);
            method.invoke(mBluetoothAdapter,BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE,600);
            //Log.e("invoke","method invoke successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        handlerBluetooth.postDelayed(runnable = new Runnable() {
            public void run() {
                handlerBluetooth.postDelayed(runnable, delay);
                //Toast.makeText(Home.this, "Scanning",Toast.LENGTH_SHORT).show();
                mBTDevices.clear();
                btnDiscover(v);
            }
        }, delay);
    }

    public void btnDiscover(View view) {
//        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
//            Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering()){

            //check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }
//        else{
//            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
//        }
    }

    @SuppressLint("MissingPermission")
    private void checkBluetoothState() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "NOT supported", Toast.LENGTH_LONG).show();
        }
        else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
        }
//        else if( mBluetoothAdapter.isEnabled()){
//            Toast.makeText(this, "Bluetooth is already turned on", Toast.LENGTH_LONG).show();
//        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
        }
//        else {
//            Toast.makeText(this, "Need to turn on Bluetooth", Toast.LENGTH_LONG).show();
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addToDatabase(String mac_Address){

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        ContactModel contactModel= new ContactModel(mac_Address,now);

        dbHandler.addContactData(contactModel);
        //Toast.makeText(Home.this,"Close Contacts Added",Toast.LENGTH_LONG).show();
        removeFromDatabase();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void removeFromDatabase(){

        int i = dbHandler.removeData();
        if (i>0) {
            Log.d(TAG, "removeFromDatabase:" + "Old data removed");
        }
//        else {
//            Toast.makeText(Home.this, "failed ", Toast.LENGTH_LONG).show();
//        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void compare(PositivePatient positivePatient){
        //receive positive bluetooth mac address from firebase
        List positivePatientMacAddress = positivePatient.getPositiveList();
        for(Object macAddress:positivePatientMacAddress){
            System.out.println( "Positive patient macAddress "+macAddress);
        }
        //receive close contact bluetooth mac address from local storage
        List closeContactsList = dbHandler.getCloseContacts();
        for(Object closeMacAddress:closeContactsList){
            System.out.println("Close contacts: "+closeMacAddress);
        }
        List rtnList = new ArrayList();
        for(Object dto : closeContactsList) {
            if(positivePatientMacAddress.contains(dto)) {
                rtnList.add(dto.toString());
            }
        }
        if(rtnList.size()> 0){

            SelfAssessment.healthStatus = "CLOSE CONTACT";
            SharedPreferences sharedPreferences = getSharedPreferences(SelfAssessment.SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            health = sharedPreferences.getString(SelfAssessment.TEXT, "");
            editor.putString(SelfAssessment.TEXT, SelfAssessment.healthStatus);
            editor.apply();
            status.setText("" + health);

            Intent intent = new Intent(Home.this, CloseContactIntroduction.class);
            startActivity(intent);
            finish();

            Toast.makeText(Home.this,"You have made close contacts with " +
                            rtnList.size() +" positive patients in last 14 days",Toast.LENGTH_LONG)
                    .show();
        }
        else{
            //Toast.makeText(Home.this,"No risk",Toast.LENGTH_LONG).show();
        }
    }
}