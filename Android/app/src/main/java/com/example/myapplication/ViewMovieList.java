package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentViewMovieListBinding;


public class ViewMovieList extends Fragment {


    private FragmentViewMovieListBinding binding;

    private DataBaseManager mydManager;

    private boolean recUpdated;

    private boolean recDeleted;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  Retrieve data from the Database and Display
        mydManager = new DataBaseManager(getActivity().getApplicationContext());

        binding = FragmentViewMovieListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieving data being passed from previous FRAGMENT
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        String[] dataArray1 = sharedViewModel.getDataArray(); // Retrieve the string array


        // Setting text box as current state of the DATA
        if (dataArray1 != null) {
            binding.title.setText(dataArray1[1]);
            binding.directors.setText(dataArray1[2]);
            binding.casts.setText(dataArray1[3]);
            binding.releaseDate.setText(dataArray1[4]);
            binding.poster.setText(dataArray1[5]);
        }



        // Save Button to update the TABLE with data entry
        binding.button11.setOnClickListener(v -> {

            // Updating ROW
            String id = dataArray1[0];
            recUpdated = mydManager.UpdateRows(
                    id,
                    binding.title.getText().toString(),
                    binding.directors.getText().toString(),
                    binding.casts.getText().toString(),
                    binding.releaseDate.getText().toString(),
                    binding.poster.getText().toString());
            if (recUpdated) {
                binding.response1.setText("Movie record is updated successfully");
            }
            else {
                binding.response1.setText("Sorry, errors when updating to DB");
            }
        });


        // Go Back BUTTON
        binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ViewMovieList.this)
                        .navigate(R.id.action_viewMovieList_to_listMovieFragment);
            }
        });


        // Delete Row BUTTON
        binding.button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = dataArray1[0];
                recDeleted = mydManager.deleteRow(id);
                if (recDeleted) {
                    Toast.makeText(getContext(),   " Row Successfully Deleted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(),   " Error Deleting the Row", Toast.LENGTH_LONG).show();
                }

                NavHostFragment.findNavController(ViewMovieList.this)
                        .navigate(R.id.action_viewMovieList_to_listMovieFragment);

            }
        });
    }
}