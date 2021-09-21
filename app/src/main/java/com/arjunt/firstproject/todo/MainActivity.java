package com.arjunt.firstproject.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arjunt.firstproject.todo.DataModel.ToDoItem;
import com.arjunt.firstproject.todo.Database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arjunt.firstproject.todo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static  View.OnClickListener deleteOnClickListener;
    private static RecyclerView recyclerView;
    private static ArrayList<ToDoItem> toDoItems;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        deleteOnClickListener = new MainActivity.DeleteOnClickListener(MainActivity.this);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshPage();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddToDoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshPage()
    {

        toDoItems = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        toDoItems = databaseHelper.getAllToDoItems();

        adapter = new CustomAdapter(toDoItems);

        recyclerView.setAdapter(adapter);

    }

    private static class DeleteOnClickListener implements View.OnClickListener
    {
        private Context context;

        public DeleteOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View view) {

            String selectedId="";

            ViewGroup parentView =(ViewGroup) view.getParent();

            TextView id = parentView.findViewById(R.id.id);

            selectedId = id.getText().toString();

            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.deleteToDoItem(Integer.parseInt(selectedId));

            toDoItems = databaseHelper.getAllToDoItems();

            adapter = new CustomAdapter(toDoItems);

            recyclerView.setAdapter(adapter);




            Toast.makeText(context,"Deleted Success",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPage();
    }
}