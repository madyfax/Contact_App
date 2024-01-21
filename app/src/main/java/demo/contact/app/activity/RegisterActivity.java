package demo.contact.app.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import demo.contact.app.R;

public class RegisterActivity extends AppCompatActivity {


    EditText name, email, number, address;
    ShowHidePasswordEditText confirmPassword;
    ShowHidePasswordEditText simplePassword;
    ImageView picker, register;
    RadioButton r1, r2;
    CheckBox cb;
    TextView date;
    CircleImageView image;
    String username, mail, num, bdate, adds, password, cpassword, gender = "";
    String imagepath = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";


    int y, m, d;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        number = (EditText) findViewById(R.id.number);
        picker = (ImageView) findViewById(R.id.dpicker);
        r1 = (RadioButton) findViewById(R.id.r1);
        r2 = (RadioButton) findViewById(R.id.r2);
        cb = (CheckBox) findViewById(R.id.cb);
        register = (ImageView) findViewById(R.id.register);
        date = (TextView) findViewById(R.id.date);
        address = (EditText) findViewById(R.id.address);
        image = (CircleImageView) findViewById(R.id.image);
        simplePassword = (ShowHidePasswordEditText) findViewById(R.id.simplePassword);
        confirmPassword = (ShowHidePasswordEditText) findViewById(R.id.confirmPassword);

        cb.setChecked(true);
        r1.setChecked(true);

        if (r1.isChecked()) {
            gender = "male";
        } else if (r2.isChecked()) {
            gender = "female";
        }
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    r1.setVisibility(View.VISIBLE);
                } else {
                    r1.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "plz accepet condition", Toast.LENGTH_SHORT).show();
                }
            }
        });

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                y = calendar.get(Calendar.YEAR);
                m = calendar.get(Calendar.MONTH);
                d = calendar.get(Calendar.DAY_OF_MONTH);

                Log.d("calender", "onClick: " + d + "/" + (m + 1) + "/" + y);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        Log.d("calender", "onDateSet: " + d + "/" + (m + 1) + "/" + y);

                        date.setText(d + "/" + (m + 1) + "/" + y);
                    }
                }, y, m, d);

                datePickerDialog.show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=name.getText().toString().trim();
                num = number.getText().toString().trim();
                mail = email.getText().toString().trim();
                bdate = date.getText().toString().trim();
                adds = address.getText().toString().trim();
              password=simplePassword.getText().toString().trim();
                cpassword=confirmPassword.getText().toString().trim();


                Log.d("TAG", "onClick: " + imagepath);
                Log.d("TAG", "onClick: " + name);
                Log.d("TAG", "onClick: " + num);
                Log.d("TAG", "onClick: " + mail);
                Log.d("TAG", "onClick: " + bdate);
//                Log.d("TAG", "onClick: " + city);
                Log.d("TAG", "onClick: " + simplePassword);
                Log.d("TAG", "onClick: " + confirmPassword);
                Log.d("TAG", "onClick: " + adds);
//                Intent i = new Intent(Activity_Ragister.this, Activityshow.class);
//                startActivity(i);
//                 Validation

                if (imagepath.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please select photo", Toast.LENGTH_SHORT).show();
                } else if (username.equals("")) {
                    name.setError("Please Fill name");
                    Toast.makeText(RegisterActivity.this, "Please Fill name", Toast.LENGTH_SHORT).show();
                } else if (num.equals("")) {
                    number.setError("Please Fill number");
                    Toast.makeText(RegisterActivity.this, "Please Fill number", Toast.LENGTH_SHORT).show();
                } else if (mail.equals(emailPattern)) {
                    email.setError("Please Fill email");
                    Toast.makeText(RegisterActivity.this, "Please Fill Email", Toast.LENGTH_SHORT).show();
                } else if (bdate.equals("")) {
                    date.setError("Please Fill Date of birth");
                    Toast.makeText(RegisterActivity.this, "Please Fill Date of birth", Toast.LENGTH_SHORT).show();
//                } else if (city.equals("")) {
//                    Toast.makeText(RegisterActivity.this, "Please Fill City", Toast.LENGTH_SHORT).show();
                } else if (!password.matches(pattern)) {
                    simplePassword.setError("Your password must be at least 8 character and should include a combination of numbers,letters and special character(!@$#)");
                    Toast.makeText(RegisterActivity.this, "Please Fill Password", Toast.LENGTH_SHORT).show();
                } else if (!cpassword.matches(password)) {
                    confirmPassword.setError("Please Fill  Confirm Password");
                    Toast.makeText(RegisterActivity.this, "Please Fill conform Password", Toast.LENGTH_SHORT).show();
                } else if (adds.equals("")) {
                    address.setError("Please Fill Address");
                    Toast.makeText(RegisterActivity.this, "Please Fill Address", Toast.LENGTH_SHORT).show();

                } else {
                    Cursor cursor = Myapp.database.rawQuery("select * from Reg Where mail='" + mail + "'", null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            Toast.makeText(RegisterActivity.this, "Already Exists userid/mail", Toast.LENGTH_SHORT).show();
                        } else {
                            Myapp.database.execSQL("Insert into Reg(imagepath,username,mail,num,bdate,adds,password) values ('" + imagepath + "','" + username + "','" + mail + "','" + num + "','" + bdate + "','" + adds + "','" + password + "'  )");
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            Toast.makeText(RegisterActivity.this, "Registration Successfully !!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                }

            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(RegisterActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Uri Uri = data.getData();
            imagepath = Uri.getPath();
            Log.d("TAG", "onActivityResult: " + imagepath);
            Glide.with(RegisterActivity.this).load(imagepath).into(image);

        } else {
            Toast.makeText(this, "TASK CANCELLED!!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}



