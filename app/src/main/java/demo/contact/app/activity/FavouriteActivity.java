package demo.contact.app.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import demo.contact.app.R;

public class FavouriteActivity extends AppCompatActivity implements ContactAdapter.Myclick {

    RecyclerView rc;
    ArrayList<Contact_list>contact_lists=new ArrayList<>();
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        rc=findViewById(R.id.rc);
        dataload();
    }


    private void dataload(){
        contact_lists.clear();
        Cursor cursor=Myapp.database.rawQuery("select * from favo where uid='"+Myapp.GETUID()+"'",null);
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                String cid= cursor.getString(1);
                if (getContactItem(cid) != null) {
                    contact_lists.add(getContactItem(cid));
                }

            }

            Log.d("TAG", "dataload: "+   contact_lists.size());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rc.setLayoutManager(linearLayoutManager);

            contactAdapter=new ContactAdapter(contact_lists,FavouriteActivity.this,FavouriteActivity.this);
            rc.setAdapter(contactAdapter);

        }
    }

    public Contact_list getContactItem(String cid){
        Contact_list contact_list=null;
        Cursor cursor = Myapp.database.rawQuery("select * from Contact where id='" + cid + "'", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {

                String id = cursor.getString(0);
                String imagepath = cursor.getString(1);
                String cname1 = cursor.getString(2);
                String cnumber1 = cursor.getString(3);
                String caddress1 = cursor.getString(4);
                String uid = cursor.getString(5);
              return new Contact_list(id, imagepath,cname1,cnumber1,caddress1,uid);
            }

        }else {
            return contact_list;
        }
        return contact_list;
    }


    @Override
    public void getmore(int pos, View view) {

        PopupMenu popupMenu = new PopupMenu(FavouriteActivity.this, view);

        popupMenu.getMenuInflater().inflate(R.menu.contact_option, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.et) {

                    Myapp.setCID(contact_lists.get(pos).getId());
                    Toast.makeText(FavouriteActivity.this, "EDit", Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(FavouriteActivity.this, EditContactActivity.class);
                    startActivity(j);

                }
                if (menuItem.getItemId() == R.id.share) {
                    String data = contact_lists.get(pos).getCname1() + "\n" + contact_lists.get(pos).getCnumber1();

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, data);
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                    Toast.makeText(FavouriteActivity.this, "share", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.delete) {
                    Myapp.setCID(contact_lists.get(pos).getId());
                    Myapp.database.execSQL("delete from Contact where id='" + Myapp.getCID() + "'");
                    dataload();
                    Toast.makeText(FavouriteActivity.this, "delete", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.copy) {
                    String data = contact_lists.get(pos).getCname1() + "\n" + contact_lists.get(pos).getCnumber1();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", data);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(FavouriteActivity.this, "copy", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.call) {
                    String phone = contact_lists.get(pos).getCnumber1();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(callIntent);
                    Toast.makeText(FavouriteActivity.this, "call", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        popupMenu.show();
    }


    @Override
    public void getpos(int pos) {

    }

    @Override
    public void getcall(int pos) {

    }

    @Override
    public void getphoto(int pos) {

    }


    @Override
    public void name(int pos) {

    }
}
