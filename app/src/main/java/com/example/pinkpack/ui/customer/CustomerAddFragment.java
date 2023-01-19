package com.example.pinkpack.ui.customer;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.pinkpack.R;
import com.example.pinkpack.databinding.FragmentCustomerAddBinding;
import com.example.pinkpack.databinding.FragmentCustomerBinding;
import com.example.pinkpack.models.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;


public class CustomerAddFragment extends Fragment implements View.OnClickListener {
    EditText firstName, surName, petName, eMail, price, invoiceNr;
    Customer newCustomer;
    Button buttonAdd, buttonCancel;
    private FragmentCustomerAddBinding binding;
    CustomerViewModel customerViewModel;
    Spinner spinner;
    View root;
    String choosenType;
    ArrayList<EditText> listOfFields;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerViewModel =
                new ViewModelProvider(this).get(CustomerViewModel.class);

        binding = FragmentCustomerAddBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setBindings();
        initiateSpinner();
        buttonAdd.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        return root;
    }

    private void setBindings() {
        firstName = binding.firstName;
        surName = binding.surName;
        petName = binding.petName;
        eMail = binding.email;
        price = binding.editPrice;
        invoiceNr = binding.editInvoiceNr;
        buttonAdd = binding.btnAdd;
        buttonCancel = binding.btnCancel;
        spinner = (Spinner) binding.spinner;
    }

    private void initiateSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                // parent.getItemAtPosition(pos)
                choosenType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        Boolean isFieldEmpty = false;
        switch (view.getId()) {
            case R.id.btnCancel:
                fragment = new CustomerFragment();
                replaceFragment(fragment);
                break;
            case R.id.btnAdd:
                listOfFields = new ArrayList<>();
                listOfFields.add(firstName);
                listOfFields.add(surName);
                listOfFields.add(petName);
                listOfFields.add(eMail);
                listOfFields.add(price);
                listOfFields.add(invoiceNr);
                if (validateTextFromInput(listOfFields));
                {
                    newCustomer = new Customer();
                    newCustomer.setFirstName(firstName.getText().toString());
                    newCustomer.setSurName(surName.getText().toString());
                    newCustomer.setDogName(petName.getText().toString());
                    newCustomer.setEmail(eMail.getText().toString());
                    newCustomer.setPrice(Double.parseDouble(price.getText().toString()));
                    newCustomer.setType(choosenType);

                    customerViewModel.addCustomer(newCustomer, getContext());
                }
                fragment = new CustomerFragment();
                replaceFragment(fragment);
                break;
        }
    }

    private Boolean validateTextFromInput(ArrayList<EditText> editTextArray) {
        for (EditText editText : editTextArray)
        {
            if(editText.getText() == null || TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError("Your message");
                return false;
            }
        }
        return true;
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