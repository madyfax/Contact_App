package demo.contact.app.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import java.util.List;

import demo.contact.app.R;

public class LoginActivity extends AppCompatActivity {

    EditText user;
    ShowHidePasswordEditText pc;
    ImageView login;
    String userid;
    String pass;
    TextView fp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.user);
        pc = (ShowHidePasswordEditText) findViewById(R.id.pc);
        login = (ImageView) findViewById(R.id.login);
        fp=findViewById(R.id.fp);
        user.setText(Myapp.getusername());
        pc.setText(Myapp.getpass());

        allowpermission();

        if (!Myapp.GETUID().equalsIgnoreCase("")) {
            Intent intent = new Intent(LoginActivity.this, MainContactActivity.class);
            startActivity(intent);
            finish();
        }

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          showcustomdialog();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userid = user.getText() +"".trim();
                pass = pc.getText()+"".trim();
                Log.d("TAG", "" + userid);
                Log.d("TAG", "" + pass);


                if (userid.equals("")) {
                    user.setError("Enter Username");
                }
                else if (pass.equals("")) {
                    pc.setError("Enter Password");
                }
                else {
                    Cursor cursor = Myapp.database.rawQuery("select * from Reg Where mail='" + userid + "' and password='" + pass + "'", null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            Toast.makeText(LoginActivity.this, "Done!!", Toast.LENGTH_SHORT).show();
                            String id=  cursor.getString(0);
                            Myapp.SETUID(id);
                            Myapp.setpass(cursor.getString(7));
                            Intent intent = new Intent(LoginActivity.this, MainContactActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "INVALIED!!", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });

    }


    public void showcustomdialog(){

//        Dialog dialog=new Dialog(Activity_Login.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        Dialog dialog=new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.dialog_forgotpassword);
        dialog.setTitle("Forgot password???");
        dialog.setCancelable(false);
        EditText et=dialog.findViewById(R.id.et);
        Button bt=dialog.findViewById(R.id.bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=et.getText().toString().trim();

                    Cursor cursor=Myapp.database.rawQuery("select * from Reg Where mail='"+mail+"'",null);
                    if (cursor!=null) {
                        if (cursor.moveToNext()) {
                            String mobile= cursor.getString(4);
                            String pass= cursor.getString(7);
                            String msg="YOur Pass: "+pass;

                            Toast.makeText(LoginActivity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "worng username/email", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        });

        dialog.show();
    }

    public void Register(View view) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    public void exitdialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
        builder1.setMessage("Are you sure u aent exit app.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    @Override
    public void onBackPressed() {
        exitdialog();
    }

    public void allowpermission() {
        PermissionX.init(LoginActivity.this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {

                    }
                });

    }
}



