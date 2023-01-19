package com.example.pinkpack.ui.invoice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pinkpack.R;
import com.example.pinkpack.databinding.FragmentInvoiceBinding;
import com.example.pinkpack.ui.customer.CustomerAddFragment;
import com.example.pinkpack.ui.customer.CustomerViewModel;

public class InvoiceFragment extends Fragment implements View.OnClickListener {
    InvoiceViewModel invoiceViewModel;
    private FragmentInvoiceBinding binding;
    Button btnSendEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        invoiceViewModel =
                new ViewModelProvider(this).get(InvoiceViewModel.class);

        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btnSendEmail = binding.btnSendEmail;
        btnSendEmail.setOnClickListener(this);
        final TextView textView = binding.textInvoice;
        invoiceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSendEmail:
                boolean state = invoiceViewModel.sendEmail(this.getContext());

                break;
        }
    }
}