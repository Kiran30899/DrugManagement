package com.project.drugmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.project.drugmanagement.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {

    ActivityRegistrationBinding registerationBinding;
    String selectedRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerationBinding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(registerationBinding.getRoot());

        registerationBinding.editTextQualification.setEnabled(false);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.drop_down_item, getResources().getStringArray(R.array.roles));
        registerationBinding.autoCompleteTextViewRole.setAdapter(arrayAdapter);

        // get Selected role from autoCompleteTextView
        registerationBinding.autoCompleteTextViewRole.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRole = adapterView.getItemAtPosition(i).toString();

                    try {
                        if (selectedRole.equals("Doctor")) {
                            registerationBinding.editTextQualification.setEnabled(true);
                        }
                    }
                    catch(Exception E)
                    {
                        Toast.makeText(RegistrationActivity.this, "Null pointer Exception"+E , Toast.LENGTH_SHORT).show();
                    }


            }
        });

        // validate entered value
        registerationBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = registerationBinding.editTextEmail.getText().toString();
                String textPwd = registerationBinding.editTextPassword.getText().toString();
                String textConfirmPwd = registerationBinding.editTextConfirmPassword.getText().toString();
                String textName = registerationBinding.editTextName.getText().toString();
                String textDlno = registerationBinding.editTextDlno1.getText().toString();
                String textAddress = registerationBinding.editTextAddress.getText().toString();
                String textContact = registerationBinding.editTextContact.getText().toString();
                String textQualification = registerationBinding.editTextQualification.getText().toString();

                if (TextUtils.isEmpty(selectedRole)) {
                    Toast.makeText(RegistrationActivity.this, "First Select Your role !", Toast.LENGTH_SHORT).show();
                    registerationBinding.autoCompleteTextViewRole.setError("Role is Required");
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter your Email !", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextEmail.setError("Email is required");
                    registerationBinding.editTextEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegistrationActivity.this, "Please re-enter your Email !", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextEmail.setError("Valid email is required");
                    registerationBinding.editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter your password !", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextPassword.setError("password is required");
                    registerationBinding.editTextPassword.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(RegistrationActivity.this, "Password should be at least 6 digits!", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextPassword.setError("password too weak");
                    registerationBinding.editTextPassword.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter your password !", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextConfirmPassword.setError("password is required");
                    registerationBinding.editTextConfirmPassword.requestFocus();
                } else if (!textPwd.equals(textConfirmPwd)) {
                    Toast.makeText(RegistrationActivity.this, "Please save same password !", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextConfirmPassword.setError("password confirmation is required");
                    registerationBinding.editTextConfirmPassword.requestFocus();
                    // clear all editText fields
                    registerationBinding.editTextConfirmPassword.clearComposingText();
                    registerationBinding.editTextConfirmPassword.clearComposingText();
                } else if (TextUtils.isEmpty(textName)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter your name !", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextName.setError("Name is required");
                    registerationBinding.editTextName.requestFocus();
                } else if (TextUtils.isEmpty(textAddress)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter city/villege name !", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextAddress.setError("City/Villege name is required");
                    registerationBinding.editTextAddress.requestFocus();
                } else if (TextUtils.isEmpty(textContact)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter contact no. !", Toast.LENGTH_SHORT).show();
                    registerationBinding.editTextContact.setError("Contact no. is required");
                    registerationBinding.editTextContact.requestFocus();
                } else if (selectedRole.equals("Doctor")) {
                    if (TextUtils.isEmpty(textQualification))
                    {
                        Toast.makeText(RegistrationActivity.this, "Please enter Qualification details. !", Toast.LENGTH_SHORT).show();
                        registerationBinding.editTextContact.setError("Qualification is required");
                        registerationBinding.editTextContact.requestFocus();
                    }
                }  else {
                    registerationBinding.progressBar.setVisibility(View.VISIBLE);
                    registerUser(textEmail, textPwd , textName ,textDlno ,textAddress ,textContact ,textQualification , selectedRole);
                }
            }
        });
    }

    private void registerUser(String email, String pwd, String textName, String textDlno, String textAddress, String textContact, String textQualification , String role) {
        
        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        //create user with provided credentials
        authProfile.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if user registration is successfull
                if (task.isSuccessful())
                {
                    Toast.makeText(RegistrationActivity.this, "User Registered Successfully...!", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //Enter UserData into Firebase realtime database

                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(email,textName,textDlno,textAddress,textContact);
                    registerationBinding.progressBar.setVisibility(View.GONE);
                }
                // if user registration is unsuccessfull
                else {
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthUserCollisionException e) {
                        Toast.makeText(RegistrationActivity.this,"User Already Exists.. , Use another Email",Toast.LENGTH_LONG).show();
                    }
                    catch (FirebaseAuthInvalidUserException e) {
                        Toast.makeText(RegistrationActivity.this,"the email is invalid kindly re-enter ",Toast.LENGTH_LONG).show();
                    }catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    registerationBinding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}