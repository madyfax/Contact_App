package demo.contact.app.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import demo.contact.app.R;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.set> {

    ArrayList<Contact_list> contact_lists;
    ArrayList<Contact_list> temp_contact_lists;
    Context context;
    Myclick myclick;

    public ContactAdapter(ArrayList<Contact_list> contact_lists, Context context, Myclick myclick) {
        this.contact_lists = contact_lists;
        this.context = context;
        this.myclick = myclick;
        temp_contact_lists = new ArrayList<>();
        temp_contact_lists.addAll(contact_lists);
    }

    @NonNull
    @Override
    public set onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new set(view1);

    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull set holder, int position) {
        holder.tv.setText(contact_lists.get(position).getCname1());
        holder.tv1.setText(contact_lists.get(position).getCnumber1());
        Glide.with(context).load(contact_lists.get(position).getImagepath()).into(holder.ig);
        holder.ig2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myclick.getmore(position, view);

            }
        });
        holder.ig1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myclick.getcall(position);

            }
        });

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myclick.name(position);
            }
        });

        holder.ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myclick.getphoto(position);
            }
        });

        holder.favv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myclick.name(position);
            }

        });

    }

    @Override
    public int getItemCount() {
        return contact_lists.size();
    }

    public class set extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tv1;
        ImageView ig2;
        ImageView ig1;
        ImageView ig;
        LinearLayout favv;




        public set(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            tv1 = itemView.findViewById(R.id.tv1);
            ig1 = itemView.findViewById(R.id.ig1);
            ig = itemView.findViewById(R.id.ig);
            ig2 = itemView.findViewById(R.id.ig2);
            favv=itemView.findViewById(R.id.favv);
        }
    }


    public interface Myclick {

        public void getpos(int pos);

        public void getcall(int pos);

        public void getphoto(int pos);

        public void getmore(int pos, View view);

        public void name(int pos);

    }


    public void filter(String query) {

        query = query.toLowerCase();

        contact_lists.clear();

        if (query.length() == 0) {

            contact_lists.addAll(temp_contact_lists);

        } else {


            for (Contact_list temp_contact_list : temp_contact_lists) {


                if (temp_contact_list.getCname1().toLowerCase().contains(query)) {
                    contact_lists.add(temp_contact_list);
                } else if (temp_contact_list.getCnumber1().toLowerCase().contains(query)) {
                    contact_lists.add(temp_contact_list);
                } else if (temp_contact_list.getCaddress1().toLowerCase().contains(query)) {
                    contact_lists.add(temp_contact_list);
                }

            }


        }

        notifyDataSetChanged();


    }




}

