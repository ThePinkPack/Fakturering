package com.example.pinkpack.ui.invoice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InvoiceViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InvoiceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is invoice fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public boolean sendEmail(Context context) {
        Log.i("Send email", "");
        String[] TO = {"henrik.nilsson92@live.se"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //context.startService(Intent.createChooser(emailIntent, "Send mail..."));
            //context.stopService(emailIntent);
            //context.finish();

            Log.i("Finished sending email.", "");
            return true;
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}