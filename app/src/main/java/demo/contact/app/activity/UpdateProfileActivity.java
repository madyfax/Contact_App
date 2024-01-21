package demo.contact.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

import de.hdodenhof.circleimageview.CircleImageView;
import demo.contact.app.R;

public class UpdateProfileActivity extends AppCompatActivity {

    ImageView save;
    EditText name, number, address;
    CircleImageView image;

    String cname1;
    String cnumber1;
    String caddress1;

    String imagepath = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofilr);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        address = findViewById(R.id.address);
        image = findViewById(R.id.profile_image);
        save = findViewById(R.id.save);

        dataload();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cname1 = name.getText().toString().trim();
                cnumber1 = number.getText().toString().trim();
                caddress1 = address.getText().toString().trim();



                Log.d("TAG", "onClick: " + imagepath);
                Log.d("TAG", "onClick: " + name);
                Log.d("TAG", "onClick: " + number);
                Log.d("TAG", "onClick: " + address);


//validation

                if (imagepath.equals("")) {
                    Toast.makeText(UpdateProfileActivity.this, "Please select photo", Toast.LENGTH_SHORT).show();
                } else if (cname1.equals("")) {
                    name.setError("Please Fill name");
                    Toast.makeText(UpdateProfileActivity.this, "Please Fill name", Toast.LENGTH_SHORT).show();
                } else if (cnumber1.equals("")) {
                    number.setError("Please Fill number");
                    Toast.makeText(UpdateProfileActivity.this, "Please Fill number", Toast.LENGTH_SHORT).show();
                } else if (caddress1.equals("")) {
                    address.setError("Please Fill email");
                    Toast.makeText(UpdateProfileActivity.this, "Please Fill address", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor=Myapp.database.rawQuery("select * from Reg where id='"+Myapp.GETUID()+"'",null);
                    if (cursor!=null) {
                        if (cursor.moveToNext()) {

                        }
                    }
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(UpdateProfileActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    public void dataload() {
        Cursor cursor = Myapp.database.rawQuery("select * from reg where id='" + Myapp.GETUID() + "'", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                imagepath = cursor.getString(1);
                String named = cursor.getString(2);
                String numd = cursor.getString(4);
                String adsd = cursor.getString(5);

                Glide.with(this).load(imagepath).into(image);
                name.setText(named);
                number.setText(numd);
                address.setText(adsd);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Uri Uri = data.getData();
            imagepath = Uri.getPath();
            Log.d("TAG", "onActivityResult: " + imagepath);
            Glide.with(UpdateProfileActivity.this).load(imagepath).into(image);

        } else {
            Toast.makeText(this, "TASK CANCELLED!!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}