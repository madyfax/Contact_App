package demo.contact.app.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import demo.contact.app.R;

public class MainContactActivity extends AppCompatActivity implements ContactAdapter.Myclick {

    RecyclerView recycle;
    ArrayList<Contact_list> contact_lists = new ArrayList<>();
    FloatingActionButton add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincontact);
        recycle = findViewById(R.id.rc);
        add = findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainContactActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });

//        contact_lists.add(new Contact_list("Contact 1","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 2","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 3","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 4","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 5","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 6","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 7","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 8","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 9","34632932740","EDHBF","GHD"));
//        contact_lists.add(new Contact_list("Contact 10","34632932740","EDHBF","GHD"));
//
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//        recycle.setLayoutManager(linearLayoutManager);
//
//        ContactAdapter contactAdapter=new ContactAdapter(contact_lists, MainContactActivity.this, MainContactActivity.this);
//        recycle.setAdapter(contactAdapter);


        dataload();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dataload();
    }

    public void dataload() {
        contact_lists.clear();
        Cursor cursor = Myapp.database.rawQuery("select * from Contact where uid='" + Myapp.GETUID() + "'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                String id = cursor.getString(0);
                String imagepath = cursor.getString(1);
                String cname1 = cursor.getString(2);
                String cnumber1 = cursor.getString(3);
                String caddress1 = cursor.getString(4);
                String uid = cursor.getString(5);
                contact_lists.add(new Contact_list(id, imagepath,cname1,cnumber1,caddress1,uid));
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recycle.setLayoutManager(linearLayoutManager);
            contactAdapter = new ContactAdapter(contact_lists, MainContactActivity.this, MainContactActivity.this);
            recycle.setAdapter(contactAdapter);
        }
    }

    ContactAdapter contactAdapter;

    @Override
    public void getpos(int pos) {

//            Toast.makeText(this, ""+contact_lists.get(pos).getMore(), Toast.LENGTH_SHORT).show();
    }

    public void getcall(int call) {

//        Toast.makeText(this, ""+contact_lists.get(call).getCall(), Toast.LENGTH_SHORT).show();
    }

    public void getphoto(int photo) {

//        Toast.makeText(this, ""+contact_lists.get(photo).getPhoto(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getmore(int pos, View view) {

        PopupMenu popupMenu = new PopupMenu(MainContactActivity.this, view);

        popupMenu.getMenuInflater().inflate(R.menu.contact_option, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.et) {

                    Myapp.setCID(contact_lists.get(pos).getId());
                    Toast.makeText(MainContactActivity.this, "EDit", Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(MainContactActivity.this, EditContactActivity.class);
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
                    Toast.makeText(MainContactActivity.this, "share", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.delete) {
                    Myapp.setCID(contact_lists.get(pos).getId());
                    Myapp.database.execSQL("delete from Contact where id='" + Myapp.getCID() + "'");
                    dataload();
                    Toast.makeText(MainContactActivity.this, "delete", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.copy) {
                    String data = contact_lists.get(pos).getCname1() + "\n" + contact_lists.get(pos).getCnumber1();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", data);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(MainContactActivity.this, "copy", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.call) {
                    String phone = contact_lists.get(pos).getCnumber1();
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(callIntent);
                    Toast.makeText(MainContactActivity.this, "call", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        popupMenu.show();
    }


    //for search item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.sc);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                Log.d("TAG", "onQueryTextSubmit: " + query);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.d("TAG", "onQueryTextChange: " + newText);
                contactAdapter.filter(newText);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sc) {
            Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.cp) {
            Intent intent = new Intent(MainContactActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        }

        if (item.getItemId() == R.id.lv) {
            Intent intent = new Intent(MainContactActivity.this, FavouriteActivity.class);
            startActivity(intent);
            finish();
        }

        if (item.getItemId() == R.id.logout) {
            Myapp.SETUID("");
            Intent intent = new Intent(MainContactActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.ep) {
            Intent intent = new Intent(MainContactActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.share) {
            Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


//
//    public void getmore(int more) {
////
////        Toast.makeText(this, ""+contact_lists.get(more).getPhoto(), Toast.LENGTH_SHORT).show();
////
////        BottomsheetDIalog bottomsheetDIalog=new BottomsheetDIalog();
////        bottomsheetDIalog.show(getSupportFragmentManager(),"");
//
//
//
//    }

    @Override
    public void name(int pos) {
        String name = contact_lists.get(pos).getCname1();
        Myapp.setname(name);
        String number = contact_lists.get(pos).getCnumber1();
        Myapp.setnumber(number);
        String img = contact_lists.get(pos).imagepath;
        Myapp.setimage(img);
        Myapp.setCID(contact_lists.get(pos).getId());
        Intent intent = new Intent(MainContactActivity.this, ContactDetailActivity.class);
        startActivity(intent);
    }
}