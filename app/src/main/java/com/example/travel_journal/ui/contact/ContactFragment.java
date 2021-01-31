package com.example.travel_journal.ui.contact;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.travel_journal.MainActivity;
import com.example.travel_journal.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class ContactFragment extends Fragment {

    private static final String TAG = "ContactFragment";

    private TextInputLayout contactName;
    private TextInputLayout contactEmail;
    private TextInputLayout contactMessage;

    private EditText contactEditName;
    private EditText contactEditEmail;
    private EditText contactEditMessage;

    private Button submitButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        initViews(root);
        submitButton.setOnClickListener(view->{
            if(validate()){
                handleSuccess(root);
            } else {
                Log.d(TAG,":submit: data is not valid");
            }
        });
        return root;
    }

    private void clearInputs() {
        contactEditName.setText("");
        contactEditEmail.setText("");
        contactEditMessage.setText("");
    }

    private void handleSuccess(View root) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        String subject = "[Travel_Journal][";
        subject += contactEditEmail.getText().toString();
        subject+="][";
        subject+= contactEditName.getText().toString();
        subject+="]";
        String mail = "mailto:smadici.denis@gmail.com"
                + "?cc=" + contactEditEmail.getText().toString()
                + "&subject=" + subject
                + "&body=" + Uri.encode(contactEditMessage.getText().toString());
        emailIntent.setData(Uri.parse(mail));
        try {
            startActivity(Intent.createChooser(emailIntent, "Sending email"));
            clearInputs();
            Log.i(TAG, ":email:sending email");
        } catch (android.content.ActivityNotFoundException ex) {
            Log.w(TAG, ":email:no email client");
            Snackbar.make(root, "There is no email client installed.",Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean validate() {
        boolean isValid = true;
        if(contactEditName.getText().toString().isEmpty()){
            contactName.setError(getString(R.string.contact_error_name_empty));
            isValid = false;
        }
        else
            clearError(contactName);
        if(contactEditEmail.getText().toString().isEmpty()){
            contactEmail.setError(getString(R.string.contact_error_email_empty));
            isValid = false;
        }
        else if(!isValidEmailAddress()){
            contactEditEmail.setError(getString(R.string.contact_error_email_valid));
            isValid = false;
        }
        else
            clearError(contactEmail);
        return isValid;
    }

    private boolean isValidEmailAddress() {
        return Patterns.EMAIL_ADDRESS.matcher(contactEditEmail.getText()).matches();
    }

    private void clearError(TextInputLayout layout) {
        if (layout != null) {
            layout.setError(null);
        }
    }

    private void initViews(View root) {
       contactName = (TextInputLayout) root.findViewById(R.id.contact_input_layout_name);
       contactEmail = (TextInputLayout) root.findViewById(R.id.contact_input_layout_email);
       contactMessage = (TextInputLayout) root.findViewById(R.id.contact_input_layout_message);
       contactEditName = (TextInputEditText) root.findViewById(R.id.contact_input_text_name);
       contactEditEmail = (TextInputEditText) root.findViewById(R.id.contact_input_text_email);
       contactEditMessage = (TextInputEditText) root.findViewById(R.id.contact_input_text_message);
       submitButton = (Button) root.findViewById(R.id.contact_button_submit);
    }
}