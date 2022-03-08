package com.example.instagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = "EditProfileActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 30;
    EditText etDescription;
    ImageView ivPictureProf;
    Button btnTakePictureProf;
    Button btnSave;
    private File photoFile;
    public String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etDescription = findViewById(R.id.etDescription);
        ivPictureProf = findViewById(R.id.ivPictureProf);
        btnTakePictureProf = findViewById(R.id.btnTakePictureProf);
        btnSave = findViewById(R.id.btnSave);

        btnTakePictureProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etDescription.getText().toString();
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, photoFile);
            }
        });


    }

    private void savePost(String description, File photoFile) {
        User user = new User();
        user.setProfileDescription(description);
        user.setImage(new ParseFile(photoFile));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error while saving profile changes", e);
                    Toast.makeText(EditProfileActivity.this, "Error while saving post", Toast.LENGTH_LONG).show();
                }
                Log.i(TAG, "Profile was edited successfully");
                etDescription.setText("");
                ivPictureProf.setImageResource(0);
            }
        });
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(EditProfileActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivPictureProf.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(EditProfileActivity.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }


}