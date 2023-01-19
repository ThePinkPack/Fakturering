package com.example.pinkpack.database;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinkpack.models.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    private final String TAG = "DatabaseHandler";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String CUSTOMER_COLLECTION = "customers";

    //CREATE
    public void createCustomer(Customer customer) {
            db.collection(CUSTOMER_COLLECTION)
                    .add(customer)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    //READ
    public void getCustomer(String firstName, final CustomerCallback myCallback) {
        DocumentReference docRef = db.collection(CUSTOMER_COLLECTION).document(firstName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Customer customer = documentSnapshot.toObject(Customer.class);
                customer.setId(docRef.getId());
                myCallback.onCallback(customer);
            }
        });
    }

    public void loadCustomerListview(final CustomerListCallback myCallback, ArrayList<Customer> listOfCustomer, Context context, RecyclerView listView) {
        db.collection(CUSTOMER_COLLECTION)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Customer customer = d.toObject(Customer.class);
                                customer.setId(d.getId());
                                listOfCustomer.add(customer);
                            }
                            myCallback.onCallback(listOfCustomer);
                        } else {
                            Toast.makeText(context, "No data found in Database", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Fail to load data..", Toast.LENGTH_LONG).show();
                    }
                });
    }
    //UPDATE
    public void updateCustomer(Customer customer, Context context) {
        System.out.println("customerId: " + customer.getId());
        db.collection(CUSTOMER_COLLECTION)
            .document(customer.getId())
                .set(customer)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Customer has been updated in Database.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Fail to update the customer. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //DELETE
    public void deleteCustomer(Customer customer, Context context) {
        db.collection(CUSTOMER_COLLECTION).
                        document(customer.getId()).
                        delete().
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Customer has been deleted from Database.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Fail to delete the customer. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public interface CustomerListCallback {
        void onCallback(List<Customer> customers);
    }

    public interface CustomerCallback {
        void onCallback(Customer customer);
    }
}
