package inline.android_reactive_adapter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import inline.android_reactive_adapter.adapter.ItemAdapter;
import inline.android_reactive_adapter.databinding.ActivityMainBinding;
import inline.android_reactive_adapter.model.User;
import inline.android_reactive_adapter.util.DataManager;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemAddListener{

    ActivityMainBinding binding;

    ItemAdapter itemAdapter;
    public DataManager dataManager;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
        initialize();
        fillData();
    }

    private void initialize() {
        users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.fname = "First name "+i;
            user.lname = "Last name "+i;
            users.add(user);
        }
        dataManager = new DataManager(users);
        itemAdapter = new ItemAdapter(this,this);
        /*To bind widget from include layout we need to use include layout id with ActivityMainBinding*/
        binding.recycle.recycleList.setLayoutManager(new LinearLayoutManager(this));
        binding.recycle.recycleList.setAdapter(this.itemAdapter);
    }

    private void fillData() {
        dataManager.userDetails().subscribe(itemAdapter);
    }

    private void addItem(){
        dataManager.addUser().subscribe(itemAdapter);
    }


    @Override
    public void onItemAdded() {
        binding.recycle.recycleList.smoothScrollToPosition(itemAdapter.getItemCount());
    }
}
