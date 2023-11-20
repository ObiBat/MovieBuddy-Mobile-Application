package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentAddMovieBinding;


public class AddMovieFragment extends Fragment {

    private FragmentAddMovieBinding binding;

    public DataBaseManager mydManager;

    private boolean recInserted;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentAddMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mydManager = new DataBaseManager(getActivity().getApplicationContext());
        mydManager.openReadable();
        //when add button on the form is clicked

        binding.addButton.setOnClickListener(v -> {
            //insert input taken from the form the database
            recInserted = mydManager.addRow(
                    binding.id.getText().toString(),
                    binding.title.getText().toString(),
                    binding.directors.getText().toString(),
                    binding.casts.getText().toString(),
                    binding.releaseDate.getText().toString(),
                    binding.poster.getText().toString());


            if (recInserted) {
                binding.response.setText("The row in the products table is inserted");
            }
            else {
                binding.response.setText("Sorry, errors when inserting to DB");
            }
            //clear the form
            binding.id.setText("");
            binding.title.setText("");
            binding.directors.setText("");
            binding.casts.setText("");
            binding.releaseDate.setText("");
            binding.poster.setText("");
        });

        //when cancel button is clicked, clear the form.

        binding.cancelButton.setOnClickListener(v->{
            binding.id.setText("");
            binding.title.setText("");
            binding.directors.setText("");
            binding.casts.setText("");
            binding.releaseDate.setText("");
            binding.poster.setText("");
        });


        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddMovieFragment.this)
                        .navigate(R.id.action_addMovieFragment_to_movieFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

