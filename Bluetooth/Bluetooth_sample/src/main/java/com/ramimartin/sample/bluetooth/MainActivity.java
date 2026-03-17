package com.ramimartin.sample.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ramimartin.bluetooth.activity.BluetoothActivity;

import java.util.UUID;


public class MainActivity extends BluetoothActivity {
    TextView mLogTxt;
    Button mScanBtn;
    Button mSendBtn;
    Button mClientBtn;
    Button mServeurBtn;
    EditText mEditText;
    Button mDiscovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogTxt = findViewById(R.id.log_txt);
        mScanBtn = findViewById(R.id.scan);
        mSendBtn = findViewById(R.id.send);
        mClientBtn = findViewById(R.id.client);
        mServeurBtn = findViewById(R.id.serveur);
        mEditText = findViewById(R.id.communication);
        mDiscovery = findViewById(R.id.discovery);

        mDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discovery();
            }
        });
        mServeurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serveur();
            }
        });
        mClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client();
            }
        });
        mScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public UUID myUUID() {
        return UUID.fromString("c47914a1-c8a2-45f0-a900-bc686b9328ab");
    }

    public void discovery() {
        setTimeDiscoverable(200);
        startDiscovery();
    }

    public void serveur() {
        setLogText("===> Start Serveur ...");
        createServeur();
    }

    public void client() {
        if (!TextUtils.isEmpty(mEditText.getText().toString())) {
            setLogText("===> Start Client connexion on device : " + mEditText.getText().toString());
            createClient(mEditText.getText().toString());
        }
    }

    public void scan() {
        setLogText("===> Start Scanning devices ...");
        scanAllBluetoothDevice();
    }

    public void send() {
        sendMessage(mEditText.getText().toString());
        setLogText("===> Send : " + mEditText.getText().toString());
    }

    @Override
    public void onBluetoothStartDiscovery() {
        mScanBtn.setEnabled(true);
        setLogText("===> Start discovering !");
        mServeurBtn.setEnabled(true);
    }

    @Override
    public void onBluetoothDeviceFound(BluetoothDevice device) {
        setLogText("===> Device detected : " + device.getAddress());
        mEditText.setText(device.getAddress());
        mClientBtn.setEnabled(true);
    }

    @Override
    public void onClientConnectionSuccess() {
        setLogText("===> Client Connexion success !");
        mEditText.setText("");
        mSendBtn.setEnabled(true);
    }

    @Override
    public void onClientConnectionFail() {
        setLogText("===> Client Connexion fail !");
        mClientBtn.setEnabled(false);
    }

    @Override
    public void onServeurConnectionSuccess() {
        setLogText("===> Serveur Connexion success !");
        mEditText.setText("");
        mSendBtn.setEnabled(true);
    }

    @Override
    public void onServeurConnectionFail() {
        setLogText("===> Serveur Connexion fail !");
    }

    @Override
    public void onBluetoothCommunicator(String messageReceive) {
        setLogText("===> receive msg : " + messageReceive);
    }

    @Override
    public void onBluetoothNotAviable() {
        if (mLogTxt == null) {
            return;
        }
        setLogText("===> Bluetooth not aviable on this device");
        mDiscovery.setEnabled(false);
        mClientBtn.setEnabled(false);
        mSendBtn.setEnabled(false);
        mScanBtn.setEnabled(false);
        mServeurBtn.setEnabled(false);
    }

    public void setLogText(String text) {
        mLogTxt.setText(mLogTxt.getText() + "\n" + text);
    }

}
