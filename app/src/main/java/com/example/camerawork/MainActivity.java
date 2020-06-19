  package com.example.camerawork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

  public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private final int CAMERA_REQUEST_CODE = 1;
    private final int CAPTURE_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageWork);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAPTURE_IMAGE);
        }
    }

      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          switch (requestCode){
              case CAMERA_REQUEST_CODE :
                  if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      startActivityForResult(intent, CAPTURE_IMAGE);
                  }
                  else{
                      Toast.makeText(this, "Permission Denided", Toast.LENGTH_SHORT).show();
                  }
                  break;
              default:
                  break;
          }
      }

      @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult(requestCode, resultCode, data);

          if(requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK){
              try{
                  Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                  imageView.setImageBitmap(bitmap);
              }catch (NullPointerException e){e.printStackTrace();}
          }
      }
  }
