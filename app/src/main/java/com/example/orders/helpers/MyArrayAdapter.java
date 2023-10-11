package com.example.orders.helpers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orders.ItemActivity;
import com.example.orders.R;
import com.example.orders.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter {

    private LayoutInflater layoutInflater;
    private List<Category> categories;
    private int layout;
    private Context context;

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        categories = objects;
        layout = resource;
    }


    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = layoutInflater.inflate(layout, null);

        Category category = categories.get(position);
        if (category != null) {
            TextView itemName = convertView.findViewById(R.id.item_title);
            ImageView img = convertView.findViewById(R.id.item_img);

            if (itemName != null) {
                itemName.setText(category.getTitle());
            }
            if (img != null) {
                int id = getContext().getResources().getIdentifier("drawable/" + category.getImg(), null, getContext().getPackageName());
                img.setImageResource(id);

                img.setOnClickListener(View -> {
                    ItemActivity.category_id = category.getId();
                    assert itemName != null;
                    context.startActivity(new Intent(context, ItemActivity.class));
                });


                img.setOnLongClickListener(View -> {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://orders-7c144-default-rtdb.europe-west1.firebasedatabase.app/");
                    final DatabaseReference TABLE = firebaseDatabase.getReference("Category");

                    @SuppressLint("ResourceType") AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));
                    builder.setTitle("Вы действительно хотите удалить элемент?")
                            .setPositiveButton("да", (dialogInterface, i) -> {
                                TABLE.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        TABLE.child(String.valueOf(category.getId())).removeValue();
                                        Toast.makeText(context, "Категория удалена", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(context, "Нет соединения", Toast.LENGTH_LONG).show();
                                    }
                                });
                            })
                            .setNegativeButton("Отмена", null)
                            .create()
                            .show();
                    return true;
                });
            }
        }
        return convertView;
    }
}
