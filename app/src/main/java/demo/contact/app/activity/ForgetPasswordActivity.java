package demo.contact.app.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import demo.contact.app.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText emailid;
    TextView send;

    String emailid1;
    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiity_forgotpassword);

        emailid=findViewById(R.id.emailid);
        send=findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailid1=emailid.getText().toString();
                Log.d("fog", emailid1);

                if (emailid1.equals("")){
                    Toast.makeText(ForgetPasswordActivity.this, "Enter email.id", Toast.LENGTH_SHORT).show();

                }else  if (!emailid1.matches(emailPattern)){
                    Toast.makeText(ForgetPasswordActivity.this, "Type valid email.id", Toast.LENGTH_SHORT).show();

                }

            }


//                Intent intent=new Intent(Activity_ForgotPassword.this,Activity_Menu.class);
//                Log.d("data", "onClick: ");
//                startActivity(intent);


        });
    }
}
