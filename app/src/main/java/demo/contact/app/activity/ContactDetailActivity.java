package demo.contact.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import demo.contact.app.R;

public class ContactDetailActivity extends AppCompatActivity {

    ImageView call,msg,photo,favo,edit;
    TextView name,callno,del;
    String nam,num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_contactdetails);
        call=findViewById(R.id.call);
        favo=findViewById(R.id.favo);
        edit=findViewById(R.id.edit);
        del=findViewById(R.id.del);
        msg=findViewById(R.id.msg);
        name=findViewById(R.id.name);
        callno=findViewById(R.id.callno);
        photo=findViewById(R.id.photo);

        name.setText(Myapp.getname());
        callno.setText(Myapp.getnumber());
        String img=Myapp.getimage();
        Glide.with(this).load(img).into(photo);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mb=callno.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mb));
                startActivity(callIntent);
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        favo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor=Myapp.database.rawQuery("select * from favo where cid='"+Myapp.getCID()+"'",null);
                if (cursor!=null) {
                    if (cursor.moveToNext()) {
                        Myapp.database.execSQL("delete from favo where cid='"+Myapp.getCID()+"'");
                        favo.setImageDrawable(getResources().getDrawable(R.drawable.heart, getApplicationContext().getTheme()));
                    }else{
                        favo.setImageDrawable(getResources().getDrawable(R.drawable.favourite, getApplicationContext().getTheme()));
                        Myapp.database.execSQL("insert into favo(cid,uid) values ('"+Myapp.getCID()+"','"+Myapp.GETUID()+"')");
                    }
                }

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactDetailActivity.this, EditContactActivity.class);
                startActivity(i);
            }
        });

        favload();
    }


    public void favload(){
        Cursor cursor=Myapp.database.rawQuery("select * from favo where cid='"+Myapp.getCID()+"'",null);
        if (cursor!=null) {
            if (cursor.moveToNext()) {
                favo.setImageDrawable(getResources().getDrawable(R.drawable.favourite, getApplicationContext().getTheme()));
            }else{
                favo.setImageDrawable(getResources().getDrawable(R.drawable.heart, getApplicationContext().getTheme()));

            }
        }


    }


    public void back(View view) {
        onBackPressed();
    }


    }



