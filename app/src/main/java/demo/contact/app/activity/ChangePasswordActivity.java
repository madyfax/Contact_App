package demo.contact.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import demo.contact.app.R;

public class ChangePasswordActivity extends AppCompatActivity {

    TextView save;
    EditText currentPassword, newPassword, confirmPassword;
    String cp, np, re;
    String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        currentPassword = findViewById(R.id.currentPassword);
        newPassword =  findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        save=findViewById(R.id.psave);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "Done PASSWORD CHANGE!!");
//                Intent i = new Intent(Activity_Changepassword.this, Activity_Splash.class);
//                startActivity(i);

                cp = currentPassword.getText() + "";
                np = newPassword.getText() + "";
                re = confirmPassword.getText() + "";
                Log.d("TAG", "onClick: "+cp);
                Log.d("TAG", "onClick: "+np);
                Log.d("TAG", "onClick: "+re);

                if (!cp.matches(pattern)) {
                    Toast.makeText(ChangePasswordActivity.this, "Fill Password", Toast.LENGTH_SHORT).show();
                    currentPassword.setError("CurrentPassword");
                }
                else if (!np.matches(pattern)) {
                    Toast.makeText(ChangePasswordActivity.this, "Fill Password", Toast.LENGTH_SHORT).show();
                    newPassword.setError("Type new password");
                } else if (!re.equals(np)) {
                    Toast.makeText(ChangePasswordActivity.this, "Fill Password", Toast.LENGTH_SHORT).show();
                    confirmPassword.setError("Confirm password");
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this, "Done!!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ChangePasswordActivity.this,MainContactActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}