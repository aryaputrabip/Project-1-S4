package com.kevar.lossfound;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class camTest extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPUTER = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camtest);

        Button btn = (Button)findViewById(R.id.camtest_btn_capture);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "Button Captured", Toast.LENGTH_SHORT).show();
                dispatchTakePictureIntent();
            }
        });
    }



    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException e){
                Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG);
            }


            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(this, "com.kevar.lossfound", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView img = (ImageView)findViewById(R.id.camtest_imageview_capture);

        try{
            if(requestCode == REQUEST_IMAGE_CAPUTER && resultCode == RESULT_OK){
                //Bundle extras = data.getExtras();
                //Bitmap imageBitmap = (Bitmap)extras.get("data");
                //img.setImageBitmap(imageBitmap);
                setPic();

            }
        }catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private File createImageFile()throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd__HHmmss").format(new Date());
        String imageFileName = "thumbnail_picture";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic(){
        ImageView img = (ImageView)findViewById(R.id.camtest_imageview_capture);

        int targetW = img.getWidth();
        int targetH = img.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        img.setImageBitmap(bitmap);
    }

}
