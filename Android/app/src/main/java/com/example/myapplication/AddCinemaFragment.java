package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentAddCinemaBinding;


public class AddCinemaFragment extends Fragment {


    private FragmentAddCinemaBinding binding;

    private DataBaseManager mydManager;

    boolean[] checkBoxes;

    CustomAdapter adapter;

    ListView list;

    private boolean recInserted;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddCinemaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        mydManager = new DataBaseManager(getActivity().getApplicationContext());
        mydManager.openReadable();


        String[] values = mydManager.retrieveRows().toArray(new String[0]);
        checkBoxes = new boolean[values.length];

        adapter = new CustomAdapter(getContext(), values);
        binding.listView.setAdapter(adapter);





        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                NavHostFragment.findNavController(AddCinemaFragment.this)
                        .navigate(R.id.action_addCinemaFragment_to_cinemaFragment);
            }
        });

        binding.addCinemaButton.setOnClickListener(v -> {

            boolean[] checkboxes = adapter.getCheckBoxState();
            String st = " ";
            for (int i = 0; i < binding.listView.getCount(); i++) {
                if (checkboxes[i] == true)
                    st = st + adapter.getName(i) + " ";

            }
//            Toast.makeText(getContext(), st + "out of " + binding.listView.getCount() + " items! ", Toast.LENGTH_LONG).show();





            recInserted = mydManager.addCinema(
                    binding.id.getText().toString(),
                    binding.cinemaName.getText().toString(),
                    binding.location.getText().toString(),
                    st);


            if (recInserted) {
                binding.response.setText("The row in the products table is inserted");
            }
            else {
                binding.response.setText("Sorry, errors when inserting to DB");
            }
            //clear the form
            binding.id.setText("");
            binding.cinemaName.setText("");
            binding.location.setText("");
        });

        binding.cancelCinemaButton.setOnClickListener(v->{
            binding.id.setText("");
            binding.cinemaName.setText("");
            binding.location.setText("");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}