package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentListMovieBinding;

import java.util.ArrayList;


public class ListMovieFragment extends Fragment {


    private FragmentListMovieBinding binding;
    private DataBaseManager mydManger;
    private String[] resultArray;




    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //retrieve data from the database and display
        mydManger = new DataBaseManager(getActivity().getApplicationContext());
        mydManger.openReadable();
        ArrayList<String> tableContent = mydManger.retrieveRows();
        binding.disResponse.setText("Your Movies: \n");
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, tableContent);
        binding.prodrec.setAdapter(arrayAdpt);


        // List to View/Edit Movie records button
        binding.prodrec.setOnItemClickListener((parent, v, position, id) -> {

            SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            String item = (String) binding.prodrec.getAdapter().getItem(position);
            String[] dataArray = splitStringToArray(item);
            sharedViewModel.setDataArray(dataArray);

            Toast.makeText(getContext(), item + " selected", Toast.LENGTH_LONG).show();

            NavHostFragment.findNavController(ListMovieFragment.this)
                        .navigate(R.id.action_listMovieFragment_to_viewMovieList);

        });


        // Go back button
        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ListMovieFragment.this)
                        .navigate(R.id.action_listMovieFragment_to_movieFragment);
            }

        });


        //CLear records of Movie table button
        binding.button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeRecs();
            }

        });

    }



    // Function that convert string to Array
    public String[] splitStringToArray(String inputString) {
        // Split the input string using a comma as the delimiter
        resultArray = inputString.split(",");

        // Trim leading and trailing spaces from each element in the array
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = resultArray[i].trim();
        }
        return resultArray;
    }


    // Function to clear Records button
    public boolean removeRecs() {
        mydManger.clearRecords();
        Toast.makeText(getContext()," All Records are removed", Toast.LENGTH_LONG).show();
        binding.prodrec.setAdapter(null);
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}