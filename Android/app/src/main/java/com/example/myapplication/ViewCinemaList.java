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

import com.example.myapplication.databinding.FragmentViewCinemaListBinding;



public class ViewCinemaList extends Fragment {

    private FragmentViewCinemaListBinding binding;

    private DataBaseManager mydManager;

    private boolean recUpdated;

    private boolean recDeleted;

    CustomAdapter adapter;

    boolean[] checkBoxes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentViewCinemaListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  Retrieve data from the Database and Display
        mydManager = new DataBaseManager(getActivity().getApplicationContext());
        String[] values = mydManager.retrieveRows().toArray(new String[0]);
        checkBoxes = new boolean[values.length];
        adapter = new CustomAdapter(getContext(), values);
        binding.listView.setAdapter(adapter);

        // Retrieving data being passed from previous FRAGMENT
        SharedViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        String[] dataArray1 = sharedViewModel1.getDataArray(); // Retrieve the string array

        // Setting text box as current state of the DATA
        if (dataArray1 != null) {
            binding.cinemaName.setText(dataArray1[1]);
            binding.location.setText(dataArray1[2]);
        }

        // Save Button to update the TABLE with data entry
        binding.button16.setOnClickListener(v -> {

            // Grabbing Checkbox DATA
            boolean[] checkboxes = adapter.getCheckBoxState();
            String st = " ";
            for (int i = 0; i < binding.listView.getCount(); i++) {
                if (checkboxes[i] == true)
                    st = st + adapter.getName(i) + " ";
            }

            // Updating ROW
            String id = dataArray1[0];
            recUpdated = mydManager.editCinema(
                    id,
                    binding.cinemaName.getText().toString(),
                    binding.location.getText().toString(),
                    st);

            if (recUpdated) {
                binding.response2.setText("Movie record is updated successfully");
            }
            else {
                binding.response2.setText("Sorry, errors when updating to DB");
            }

        });

        // Go Back BUTTON
        binding.button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ViewCinemaList.this)
                        .navigate(R.id.action_viewCinemaList_to_listCinemaFragment);
            }
        });


        // Delete Row BUTTON
        binding.button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = dataArray1[0];
                recDeleted = mydManager.deleteCinema(id);
                if (recDeleted) {
                    Toast.makeText(getContext(),   " Row Successfully Deleted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(),   " Error Deleting the Row", Toast.LENGTH_LONG).show();
                }

                NavHostFragment.findNavController(ViewCinemaList.this)
                        .navigate(R.id.action_viewCinemaList_to_listCinemaFragment);

            }
        });
    }
}