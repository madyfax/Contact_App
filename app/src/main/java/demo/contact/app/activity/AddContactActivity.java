package demo.contact.app.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import demo.contact.app.R;
public class AddContactActivity extends AppCompatActivity {

    EditText cname;
    EditText cnumber;
    ImageView cpicker;
    EditText caddress;
    TextView save;
    CircleImageView profile_image;
    String cname1;
    String cnumber1;

    String caddress1;
    String imagepath = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcontact);
        cname = findViewById(R.id.cname);
        cnumber = findViewById(R.id.cnumber);
        caddress = findViewById(R.id.caddress);
        save = findViewById(R.id.save);
        profile_image = findViewById(R.id.profile_image);

        Glide.with(this).load(imagepath).into(profile_image);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.with(AddContactActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cname1 = cname.getText()+"".trim();
                cnumber1 = cnumber.getText()+"".trim();
                caddress1 = caddress.getText()+"".trim();

                Log.d("create", cname1);
                Log.d("create", cnumber1);
                Log.d("create", caddress1);

                if (imagepath.equals("")) {
                    Toast.makeText(AddContactActivity.this, "Add image", Toast.LENGTH_SHORT).show();
                }else if (cname1.equals("")) {
                    Toast.makeText(AddContactActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                } else if (cnumber1.equals("")) {
                    Toast.makeText(AddContactActivity.this, "Enter number", Toast.LENGTH_SHORT).show();
                } else if (caddress1.equals("")) {
                    Toast.makeText(AddContactActivity.this, "Enter address", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = Myapp.database.rawQuery("select * from Contact where cnumber1='"+cnumber1+"' and id='"+Myapp.GETUID()+"'  ", null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            Toast.makeText(AddContactActivity.this, "Already exist", Toast.LENGTH_SHORT).show();
                        } else {
                            Myapp.database.execSQL("Insert into Contact(imagepath, cname1 , cnumber1, caddress1,uid) values ('" + imagepath + "','" + cname1 + "','" + cnumber1 + "','" + caddress1 + "','"+Myapp.GETUID()+"')");
                            Toast.makeText(AddContactActivity.this, "New Contact added", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(AddContactActivity.this, MainContactActivity.class);
//                            startActivity(intent);
                            finish();
                        }

                    }

                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            imagepath = uri.getPath();
            Glide.with(this).load(uri).into(profile_image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
