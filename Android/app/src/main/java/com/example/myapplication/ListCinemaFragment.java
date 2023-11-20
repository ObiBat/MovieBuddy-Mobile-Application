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

import com.example.myapplication.databinding.FragmentListCinemaBinding;

import java.util.ArrayList;

public class ListCinemaFragment extends Fragment {
    private FragmentListCinemaBinding binding;

    private DataBaseManager mydManger;


    private String[] resultArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListCinemaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //retrieve data from the database and display

        mydManger = new DataBaseManager(getActivity().getApplicationContext());
        mydManger.openReadable();
        ArrayList<String> tableContent = mydManger.retrieveCinemas();
        binding.disResponse.setText("Your Cinemas: \n");
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, tableContent);
        binding.prodrec.setAdapter(arrayAdpt);


        // Go back button
        binding.button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ListCinemaFragment.this)
                        .navigate(R.id.action_listCinemaFragment_to_cinemaFragment);
            }
        });

        // Clear records button
        binding.button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeRecs();
            }

        });

        //List to View/Edit Fragment button
        binding.prodrec.setOnItemClickListener((parent, v, position, id) -> {


            SharedViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

            String item = (String) binding.prodrec.getAdapter().getItem(position);
            String[] dataArray0 = splitStringToArray(item);
            sharedViewModel1.setDataArray(dataArray0);

            Toast.makeText(getContext(), item + " selected", Toast.LENGTH_LONG).show();

            NavHostFragment.findNavController(ListCinemaFragment.this)
                    .navigate(R.id.action_listCinemaFragment_to_viewCinemaList);

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
        mydManger.clearRecords_cinema();
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