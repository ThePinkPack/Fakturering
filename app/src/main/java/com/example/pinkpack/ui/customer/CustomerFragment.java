package com.example.pinkpack.ui.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinkpack.R;
import com.example.pinkpack.databinding.FragmentCustomerBinding;
import com.example.pinkpack.models.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class CustomerFragment extends Fragment implements View.OnClickListener {
    ArrayList<Customer> customerArrayList;
    RecyclerView recyclerView;
    FloatingActionButton buttonAdd;
    private FragmentCustomerBinding binding;
    CustomerViewModel customerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerViewModel =
                new ViewModelProvider(this).get(CustomerViewModel.class);

        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        buttonAdd = binding.addButton;

        customerArrayList = new ArrayList<>();
        recyclerView = binding.recyclerView;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        customerViewModel.loadAllCustomers(customerArrayList, root.getContext(), recyclerView);

        buttonAdd.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.add_button:
                fragment = new CustomerAddFragment();
                replaceFragment(fragment);
                break;
        }
    }
    public void replaceFragment(Fragment someFragment) {

        customerViewModel.replaceFragment(someFragment, this,getFragmentManager().beginTransaction());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}