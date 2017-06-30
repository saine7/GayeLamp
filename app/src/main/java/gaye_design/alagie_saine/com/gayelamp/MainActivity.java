package gaye_design.alagie_saine.com.gayelamp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Camera camera;
    android.hardware.Camera.Parameters parameters;
    ImageButton imageButton;
    boolean isOn = false;
    boolean flashBuildIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton = (ImageButton) findViewById(R.id.imageButton);


        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                camera = camera.open();
                parameters = camera.getParameters();
                flashBuildIn = true;
            }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flashBuildIn) {
                    if (!isOn) {
                        imageButton.setImageResource(R.drawable.flash_on);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        isOn = true;


                    } else {
                        imageButton.setImageResource(R.drawable.flash_off);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        isOn = false;
                    }
                }
               else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("App Not Supported");
                    builder.setMessage("Sorry this app only runs on flash supported devices");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog safeExit = builder.create();
                    safeExit.show();
                }

            }

        });



    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null){
            camera.release();
            camera = null;
        }
    }
}
