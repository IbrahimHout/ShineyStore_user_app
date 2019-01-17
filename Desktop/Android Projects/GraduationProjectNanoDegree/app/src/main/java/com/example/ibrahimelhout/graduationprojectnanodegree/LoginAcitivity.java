package com.example.ibrahimelhout.graduationprojectnanodegree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ibrahimelhout.graduationprojectnanodegree.Utils.UtilsClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginAcitivity extends AppCompatActivity {


    FirebaseAuth mFirebaseAuth;

    ProgressDialog progressDialog;


    @BindView(R.id.toolbarLogin)
    Toolbar toolbarLogin;
    @BindView(R.id.emailET)
    TextInputEditText emailET;
    @BindView(R.id.passwordET)
    TextInputEditText passwordET;
    @BindView(R.id.signUpButton)
    TextView signUpButton;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.layoutOfsign)
    LinearLayout layoutOfsign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAcitivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    @OnClick(R.id.loginButton)
    public void onViewClicked() {


        mFirebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.logging_in));
        progressDialog.setMessage(getString(R.string.wait_for_a_second));
        progressDialog.setCanceledOnTouchOutside(false);

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        UtilsClass.getInstance().hideKeyboard(this);

        if ((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(password))) {

            progressDialog.show();
            loginUser(email, password);


        } else {
            Snackbar snackbar = Snackbar.make(layoutOfsign, getString(R.string.make_sure_u_filled), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    private void loginUser(String email, String password) {

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.setMessage(getString(R.string.ure_ready_togo));
                    progressDialog.dismiss();
                    Intent main = new Intent(LoginAcitivity.this, MainActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(main);
                    finish();

                } else {

                    progressDialog.dismiss();

                    Snackbar snackbar = Snackbar.make(layoutOfsign, R.string.sth_went_wrong, Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            }
        });


    }
}
