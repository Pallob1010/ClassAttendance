package spark.loop.classattendance;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Databases.SharedPreference;
import Model.LoginResult;
import Model.RegistrationResult;
import Networking.ApiClient;
import Networking.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogReg extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    SharedPreference preference;
    Dialog dialog;
    EditText regName, regNumber, regPass, regConPass, logNumber, logPass;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    String designation[]={"Lecturer","Assistant Professor","Associate Professor","Professor"};
    String teacherdesignation="Lecturer";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        preference = new SharedPreference(this);
        TextView NotRegis = findViewById(R.id.notRegistered);
        TextView forgetPass = findViewById(R.id.forgotpassword);
        logNumber = findViewById(R.id.edittextphoneNumber);
        logPass = findViewById(R.id.edittextpassword);
        Button Submit = findViewById(R.id.Submitt);
        NotRegis.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
        Submit.setOnClickListener(this);

    }

    public void RegistrationWindow() {
        dialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar);
        dialog.getWindow().getAttributes().windowAnimations = R.style.ActivityTheme;
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.registrationwindow);
        regName = dialog.findViewById(R.id.regName);
        regNumber = dialog.findViewById(R.id.regNumber);
        regPass = dialog.findViewById(R.id.regPass);
        regConPass = dialog.findViewById(R.id.regConfirmPassword);
        spinner=dialog.findViewById(R.id.designationSpinner);

        adapter=new ArrayAdapter<>(LogReg.this, android.R.layout.simple_spinner_item, designation);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Button reg = dialog.findViewById(R.id.regSubmit);
        reg.setOnClickListener(this);
        Button regcancel = dialog.findViewById(R.id.regCancel);
        regcancel.setOnClickListener(this);
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Submitt:
                String s5, s6;
                s5 = logNumber.getText().toString().trim();
                s6 = logPass.getText().toString().trim();

                if (!s5.isEmpty()) {
                    if (!s6.isEmpty()) {
                        login(s5, s6);
                    } else {
                        Toast.makeText(this, "Input Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Input Number", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.notRegistered:
                RegistrationWindow();
                break;
            case R.id.forgotpassword:
                Toast.makeText(this, "Processing", Toast.LENGTH_SHORT).show();
                break;
            case R.id.regSubmit:
                String s1, s2, s3, s4;
                s1 = regName.getText().toString().trim();
                s2 = regNumber.getText().toString().trim();
                s3 = regPass.getText().toString().trim();
                s4 = regConPass.getText().toString().trim();
                if (!s1.isEmpty()) {
                    if (!s2.isEmpty()) {
                        if (!s3.isEmpty()) {
                            if (!s4.isEmpty()) {
                                if (s3.equals(s4)) {
                                    register(s1, s2, s3,teacherdesignation);
                                } else {
                                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(this, "Type Password Again ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Input Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Input Number", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Input Name", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.regCancel:
                dialog.dismiss();
                break;
        }

    }

    public void register(String name, String number, String password,String desg) {

        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<RegistrationResult> call = service.postdata(name, number, password,desg);
        call.enqueue(new Callback<RegistrationResult>() {
            @Override
            public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("success")) {
                        Toast.makeText(LogReg.this, "Successfuly Registered", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else if (response.body().getResponse().equals("exist")) {
                        Toast.makeText(LogReg.this, "Number Already Exist!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LogReg.this, "Failed to Register!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<RegistrationResult> call, Throwable t) {
                Toast.makeText(LogReg.this, "Somthing went wrong !! Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login( String number, String password) {

            if (Offline(number)){
                    if(preference.getPassword().equals(password)){
                        startActivity(new Intent(LogReg.this,MainActivity.class));
                        finish();
                    }else {
                        Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
            }else {
                if (hasConnection()){
                    Online(number,password);

                }else {
                    Toast.makeText(this, "First time login requires internet connection", Toast.LENGTH_SHORT).show();
                }

            }


    }

    public boolean Offline(String number){
        if(!preference.getNumber().isEmpty() & !preference.getPassword().isEmpty() & preference.getNumber().equals(number)){
            return true;
        }else {
            return false;
        }
    }

    public void Online(final String number,final String password){
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<LoginResult>>call=service.postdata(number);
        call.enqueue(new Callback<List<LoginResult>>() {
            @Override
            public void onResponse(Call<List<LoginResult>> call, Response<List<LoginResult>> response) {
                if(response.isSuccessful()){
                    String name=response.body().get(0).getName();
                    String pass=response.body().get(0).getPassword();
                    String desg=response.body().get(0).getDesignation();

                    if(password.equals(pass)){
                        preference.saveData(name,number,password,desg);
                        startActivity(new Intent(LogReg.this,MainActivity.class));
                        finish();

                    }else {
                        Toast.makeText(LogReg.this, "Password doesn't matched!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<LoginResult>> call, Throwable t) {
                Toast.makeText(LogReg.this, "Number Doesn't exist!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean hasConnection(){
            NetworkInfo activeNetworkInfo = ((ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        teacherdesignation=designation[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
