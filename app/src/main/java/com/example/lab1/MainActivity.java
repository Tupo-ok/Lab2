package com.example.lab1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab1.databinding.ActivityMainBinding;

import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'lab1' library on application startup.
    static {
        System.loadLibrary("lab1");
        System.loadLibrary("mbedcrypto");
    }

    private ActivityMainBinding binding;
    ActivityResultLauncher activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btn = (Button) findViewById(R.id.button);

        int res = initRng();
        byte [] v = randomBytes(16);
        String str = "Hello World";
        byte [] byteStr = str.getBytes(StandardCharsets.UTF_16);
        byte [] es = encrypt(v, byteStr);
        byte [] ds = decrypt(v, es);

        activityResultLauncher  = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // обработка результата
                            System.out.println("ВВЕДЕНЫЙ ПИН-КОД: " + data.getStringExtra("pin"));
                            // Toast.makeText(MainActivity.this, data.getStringExtra("pin"), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btn.setOnClickListener(view -> {
            Intent intent = new Intent (getApplicationContext(), PinpadActivity.class);
            activityResultLauncher.launch(intent);
        });
        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'lab1' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public static native int initRng();
    public static native byte[] randomBytes(int no);

    public static native byte[] encrypt (byte[] key, byte [] array);

    public static native byte[] decrypt (byte[] key, byte[] array);

}