package com.example.pinkpack.adapters;

import static android.R.layout.simple_spinner_dropdown_item;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.pinkpack.R;
import com.example.pinkpack.models.Customer;
import com.example.pinkpack.ui.customer.CustomerViewModel;

import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    Context _context;
    ArrayList<Customer> _customerArrayList;
    CustomerViewModel customerViewModel = new CustomerViewModel();

    public CustomerAdapter(@NonNull Context context, ArrayList<Customer> customerArrayList) {
        _context = context;
        _customerArrayList = customerArrayList;
    }
    @NonNull
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder holder, int position) {
        holder.bind(_customerArrayList.get(position), holder);
    }

    @Override
    public int getItemCount() {
        return _customerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView customerPetNameHeader, txtFirstName, txtType, txtBillNr, txtPris, txtEmail, txtSurName, txtPetName;
        public EditText customerFirstName, customerSurName, customerPet, customerPrice, customerEmail, customerInvoiceNr;
        public Button btnCancel, btnUpdate;
        public ImageButton btnEdit, btnDelete;
        private Boolean isVisible = false;
        public Spinner customerSpinner;
        private String choosenType;
        private Customer currentCustomer;

        public ViewHolder(View itemView) {
            super(itemView);
            setBindings(itemView);
        }

        private void setBindings(View itemView) {
            customerPetNameHeader = (TextView) itemView.findViewById(R.id.txtPetNameHeader);
            customerFirstName = (EditText) itemView.findViewById(R.id.editCustomerFirstName);
            customerSurName = (EditText) itemView.findViewById(R.id.editCustomerSurname);
            customerPet = (EditText) itemView.findViewById(R.id.editCustomerPet);
            customerPrice = (EditText) itemView.findViewById(R.id.editCustomerPrice);
            customerEmail = (EditText) itemView.findViewById(R.id.editCustomerEmail);
            customerInvoiceNr = (EditText) itemView.findViewById(R.id.editCustomerInvoiceNr);
            btnCancel = (Button) itemView.findViewById(R.id.btnCancel);
            btnUpdate = (Button) itemView.findViewById(R.id.btnUpdate);
            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            customerSpinner = (Spinner) itemView.findViewById(R.id.editSpinner);
            txtFirstName = (TextView) itemView.findViewById(R.id.txtFirstName);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtBillNr = (TextView) itemView.findViewById(R.id.txtBillNr);
            txtPris = (TextView) itemView.findViewById(R.id.txtPris);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtSurName = (TextView) itemView.findViewById(R.id.txtSurName);
            txtPetName = (TextView) itemView.findViewById(R.id.txtPetName);
        }

        public void bind(final Customer customer, CustomerAdapter.ViewHolder holder) {
            holder.customerPetNameHeader.setText(customer.getDogName());
            holder.customerFirstName.setText(customer.getFirstName());
            holder.customerSurName.setText(customer.getSurName());
            holder.customerEmail.setText(customer.getEmail());
            holder.customerPet.setText(customer.getDogName());
            holder.customerPrice.setText(customer.getPrice().toString());
            holder.customerInvoiceNr.setText(String.valueOf(customer.getInvoiceNumber()));
            currentCustomer = customer;
            initiateSpinner(holder, customer);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isVisible) {
                        toggleVisibility(holder, 8);
                    } else {
                        toggleVisibility(holder, 0);
                    }
                 }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customerViewModel.deleteCustomer(customer, _context);
                    _customerArrayList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), _customerArrayList.size());

                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Customer updatedCustomer = updateCustomer(holder);
                    customerViewModel.updateCustomer(updatedCustomer, _context);
                    _customerArrayList.remove(getAdapterPosition());
                    _customerArrayList.add(updatedCustomer);
                    notifyDataSetChanged();
                    notifyItemRangeChanged(getAdapterPosition(), _customerArrayList.size());
                }
            });
        }

        private Customer updateCustomer(CustomerAdapter.ViewHolder holder) {
            Customer customer = new Customer();
            customer.setFirstName(holder.customerFirstName.getText().toString());
            customer.setSurName(holder.customerSurName.getText().toString());
            customer.setDogName(holder.customerPet.getText().toString());
            customer.setType(holder.choosenType);
            customer.setEmail(holder.customerEmail.getText().toString());
            customer.setPrice(Double.parseDouble(holder.customerPrice.getText().toString()));
            customer.setInvoiceNumber(Integer.parseInt(holder.customerInvoiceNr.getText().toString()));
            customer.setId(currentCustomer.getId());
            return customer;
        }

        private void initiateSpinner(CustomerAdapter.ViewHolder holder, Customer customer) {
            holder.customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    choosenType = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(_context,
                    R.array.type_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(simple_spinner_dropdown_item);
            holder.customerSpinner.setAdapter(adapter);
          //  holder.customerSpinner.setSelection(Arrays.asList(R.array.type_array).indexOf(customer.getType()));
        }

        private void toggleVisibility(CustomerAdapter.ViewHolder holder, int visibilty) {
            holder.customerFirstName.setVisibility(visibilty);
            holder.customerSurName.setVisibility(visibilty);
            holder.customerPet.setVisibility(visibilty);
            holder.customerPrice.setVisibility(visibilty);
            holder.customerEmail.setVisibility(visibilty);
            holder.customerInvoiceNr.setVisibility(visibilty);
            holder.btnCancel.setVisibility(visibilty);
            holder.btnUpdate.setVisibility(visibilty);
            holder.customerSpinner.setVisibility(visibilty);
            holder.txtFirstName.setVisibility(visibilty);
            holder.txtType.setVisibility(visibilty);
            holder.txtBillNr.setVisibility(visibilty);
            holder.txtPris.setVisibility(visibilty);
            holder.txtEmail.setVisibility(visibilty);
            holder.txtSurName.setVisibility(visibilty);
            holder.txtPetName.setVisibility(visibilty);
            isVisible = !isVisible;
        }
    }
}



