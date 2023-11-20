package com.example.myapplication;

import static androidx.navigation.ActivityKt.findNavController;
import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle(R.string.MovieBuddy);
        super.onCreate(savedInstanceState);


        // Approach that called SharedViewModel used for passing data between transition from list to view/edit screen shows current data in type box.

        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class); // Initialize the ViewModel
        String[] dataArray = new String[] { "a", "a", "a", "a", "a", "a"};
        sharedViewModel.setDataArray(dataArray);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        navController = findNavController(this, R.id.nav_host_fragment_content_main);

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
        return onNavDestinationSelected(item,navController) || super.onOptionsItemSelected(item);
    }



}

