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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pinkpack.R;
import com.example.pinkpack.databinding.FragmentCustomerAddBinding;
import com.example.pinkpack.databinding.FragmentCustomerEditBinding;
import com.example.pinkpack.models.Customer;

import java.util.ArrayList;
import java.util.Arrays;


public class CustomerEditFragment extends Fragment implements View.OnClickListener {
    EditText firstNameEdit, surNameEdit, petNameEdit, eMailEdit, priceEdit, invoiceNrEdit;
    Customer newCustomer;
    Button buttonAdd, buttonCancel;
    private FragmentCustomerEditBinding binding;
    CustomerViewModel customerViewModel;
    Spinner spinner;
    View root;
    String choosenType;
    ArrayList<EditText> listOfFields;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerViewModel =
                new ViewModelProvider(this).get(CustomerViewModel.class);

        binding = FragmentCustomerEditBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setBindings();
        initiateSpinner();
        buttonAdd.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        return root;
    }

    private void setBindings() {
        firstNameEdit = binding.firstNameEdit;
        surNameEdit = binding.surNameEdit;
        petNameEdit = binding.petNameEdit;
        eMailEdit = binding.emailEdit;
        priceEdit = binding.editPriceEdit;
        invoiceNrEdit = binding.editInvoiceNrEdit;
        buttonAdd = binding.btnAddChange;
        buttonCancel = binding.btnCancelChange;
        spinner = (Spinner) binding.spinnerEdit;
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
            case R.id.btnCancelChange:
                fragment = new CustomerFragment();
                replaceFragment(fragment);
                break;
            case R.id.btnAddChange:

                listOfFields = new ArrayList<>();
                listOfFields.add(firstNameEdit);
                listOfFields.add(surNameEdit);
                listOfFields.add(petNameEdit);
                listOfFields.add(eMailEdit);
                listOfFields.add(priceEdit);
                listOfFields.add(invoiceNrEdit);
                if (validateTextFromInput(listOfFields));
            {
                newCustomer = new Customer();
                newCustomer.setFirstName(firstNameEdit.getText().toString());
                newCustomer.setSurName(surNameEdit.getText().toString());
                newCustomer.setDogName(petNameEdit.getText().toString());
                newCustomer.setEmail(eMailEdit.getText().toString());
                newCustomer.setPrice(Double.parseDouble(priceEdit.getText().toString()));
                newCustomer.setType(choosenType);

                int index= Arrays.asList(getResources().getStringArray(R.array.type_array)).indexOf(choosenType);
                switch(index)
                {
                    case 0:
                        newCustomer.setNumberOfDaysWeek(5);
                        break;
                    case 1:
                        newCustomer.setNumberOfDaysWeek(3);
                        break;
                    case 2:
                        newCustomer.setNumberOfDaysWeek(2);
                        break;
                    default:
                        newCustomer.setNumberOfDaysWeek(1);
                }
                customerViewModel.addCustomer(newCustomer, getContext());
            }
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