package com.example.pinkpack.ui.customer;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinkpack.R;
import com.example.pinkpack.adapters.CustomerAdapter;
import com.example.pinkpack.database.DatabaseHandler;
import com.example.pinkpack.models.Customer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerViewModel extends ViewModel {
    DatabaseHandler dbHandler = new DatabaseHandler();
    private final MutableLiveData<String> mText;

    public CustomerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is customer fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
    public void getAllCustomers(ArrayList<Customer> listOfCustomer, Context context, ListView listView) {
    }

    public void loadAllCustomers(ArrayList<Customer> listOfCustomer, Context context, RecyclerView recyclerView) {
        dbHandler.loadCustomerListview(new DatabaseHandler.CustomerListCallback() {
            @Override
            public void onCallback(List<Customer> customers) {
                System.out.println("Loaded "+customers.size()+" contacts");
                Log.d("CustomerRepository", "Customers loaded => " + customers.size());
                CustomerAdapter adapter = new CustomerAdapter(context, listOfCustomer);
                recyclerView.setAdapter(adapter);



            }
        },listOfCustomer, context, recyclerView);
    }

    public void deleteCustomer(Customer customer, Context context) {
        dbHandler.deleteCustomer(customer, context);
    }

    public void replaceFragment(Fragment newFragment, Fragment oldFragment, FragmentTransaction transaction) {
        FragmentTransaction newTransaction = transaction;
        newTransaction.replace(oldFragment.getId(), newFragment);
        newTransaction.addToBackStack(null);
        newTransaction.commit();
    }

    public Customer getCustomer(Customer customer) { return customer; }

    public void updateCustomer(Customer customer, Context context) {
        int index = Arrays.asList(context.getResources().getStringArray(R.array.type_array)).indexOf(customer.getType());
        switch(index)
        {
            case 0:
                customer.setNumberOfDaysWeek(5);
                break;
            case 1:
                customer.setNumberOfDaysWeek(3);
                break;
            case 2:
                customer.setNumberOfDaysWeek(2);
                break;
            default:
                customer.setNumberOfDaysWeek(1);
        }
        dbHandler.updateCustomer(customer, context);
    }

    public void addCustomer(Customer customer, Context context) {
        int index = Arrays.asList(context.getResources().getStringArray(R.array.type_array)).indexOf(customer.getType());
        switch(index)
        {
            case 0:
                customer.setNumberOfDaysWeek(5);
                break;
            case 1:
                customer.setNumberOfDaysWeek(3);
                break;
            case 2:
                customer.setNumberOfDaysWeek(2);
                break;
            default:
                customer.setNumberOfDaysWeek(1);
        }
        dbHandler.createCustomer(customer);
    }
    public Boolean removeCustomer(Customer customer) { return true; }
    public Boolean editCustomer(Customer customer) { return true; }
}