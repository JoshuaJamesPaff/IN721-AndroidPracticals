package nz.ac.op.paffjj1student.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GalleryActivity extends AppCompatActivity {

    private File mPhotoFile;
    private ImageView[] frames;
    private int imageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        frames = new ImageView[4];

        frames[0] = (ImageView) findViewById(R.id.imageView1);
        frames[1] = (ImageView) findViewById(R.id.imageView2);
        frames[2] = (ImageView) findViewById(R.id.imageView3);
        frames[3] = (ImageView) findViewById(R.id.imageView4);

        Button buttonCaptureImage = (Button) findViewById(R.id.buttonCameraIntent);
        buttonCaptureImage.setOnClickListener(new ClickHandler());

    }

    private class ClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            calculateImageSize();
            startCameraIntent();
        }
    }

    //sets size of image
    private void calculateImageSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        imageSize = size.y/3;
    }

    //creates file for image to be loaded into
    private File createImageFile() {
        File imageRootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageStorageDirectory = new File(imageRootPath, "CameraApp");

        if(!imageStorageDirectory.exists()){
            imageStorageDirectory.mkdirs();
        }
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date currentTime = new Date();
        String timeStamp = timeStampFormat.format((currentTime));

        String mPhotoFileName = "IMG_" + timeStamp + ".jpg";

        File photoFile = new File(imageStorageDirectory.getPath() + File.separator + mPhotoFileName);

        return photoFile;
    }

    //starts camera using file
    private void startCameraIntent(){
        mPhotoFile = createImageFile();

        Uri mPhotoFileUri = Uri.fromFile(mPhotoFile);

        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoFileUri);

        startActivityForResult(imageCaptureIntent, 1);
    }

    //sets image views to the photo taken
    private void setPictures(Bitmap image){
        for(ImageView iv : frames){
            iv.setImageBitmap(Bitmap.createScaledBitmap(image, imageSize, imageSize, false));
        }
    }

    //gets the photo taken, converts to a bitmap and sets it to image views
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String imageFilePath = mPhotoFile.getPath();
                Bitmap photoTakenBitmap = BitmapFactory.decodeFile(imageFilePath);
                setPictures(photoTakenBitmap);
            }
            else{
                Toast.makeText(this, "Error taking photo",Toast.LENGTH_LONG).show();
            }
        }

    }
}
