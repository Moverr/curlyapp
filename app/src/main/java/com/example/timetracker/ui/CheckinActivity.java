package com.example.timetracker.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timetracker.R;
import com.example.timetracker.adapters.BranchAdapter;
import com.example.timetracker.data.ApiResponse;
import com.example.timetracker.data.Branch;
import com.example.timetracker.data.Department;
import com.example.timetracker.data.Employee;
import com.example.timetracker.data.TimeTrackerRequest;
import com.example.timetracker.data.model.AuthResponseData;
import com.example.timetracker.data.requests.LoginRequest;
import com.example.timetracker.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.widget.ArrayAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class CheckinActivity extends AppCompatActivity {
    private Spinner branchSpinner, departmentSpinner, employeeSpinner;

    private OkHttpClient client;
    private Gson gson;


    private BranchAdapter branchAdapter;
    private List<Branch> branchList;
    private List<Branch> branches;
    private List<Department> departments;

    private List<Employee> employees;


    private Branch selectedBranch = null;
    private Department selectedDepartment = null;

    private Employee selectedEmployee = null;


    private Button checkinbtn;

    private DatePicker dateInPicker;
    private TimePicker timeInPicker;

    //checkinbtn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkin);

        branchSpinner = findViewById(R.id.spinner);
        departmentSpinner = findViewById(R.id.spinner2);
        employeeSpinner = findViewById(R.id.spinner3);


        client = new OkHttpClient();
        gson = new Gson();

        branchAdapter = new BranchAdapter(this, branchList);

        dateInPicker = findViewById(R.id.datePicker);
        timeInPicker = findViewById(R.id.timePicker);

        fetchBranches();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedBranchName =  parent.getItemAtPosition(position).toString();
                //branchSpinner.getSelectedItem().toString();


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    selectedBranch = branches.stream().filter(x -> x.getName().equals(selectedBranchName)).findFirst().get();
                }

                if (selectedBranch != null) {
                    Log.e("BRANCH_SS", "" + selectedBranch.getId());
                    fetchDepartments(selectedBranch.getId());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selecdtedDeparment =  parent.getItemAtPosition(position).toString();
                        //departmentSpinner.getSelectedItem().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    selectedDepartment = departments.stream().filter(x -> x.getName().equals(selecdtedDeparment)).findFirst().get();
                    fetchEmployees(selectedDepartment.getId());
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedEmployeeName =  parent.getItemAtPosition(position).toString();


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    selectedEmployee = employees.stream().filter(x -> (x.getFirst_name() + " " + x.getLast_name()).equals(selectedEmployeeName)).findFirst().get();
                }




            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        checkinbtn = (Button) findViewById(R.id.checkinbtn);

        checkinbtn.setOnClickListener(v -> {
            //

            //todo: get the checking time information
            Toast.makeText(getApplicationContext(),"Checkin",Toast.LENGTH_LONG).show();

            String dateIn = getFormattedDateFromDatePicker(dateInPicker);
            String timeIn = getFormattedTimeFromTimePicker(timeInPicker);
            String type = "checkin";
            long employee = selectedEmployee.getId();
            TimeTrackerRequest request = new TimeTrackerRequest(timeIn,dateIn,type,employee);

            timeTracker(request);
           // dateInPicker = findViewById(R.id.datePicker);
          //  timeInPicker = findViewById(R.id.timePicker);

            //{"time":"17:05:59","date":"2024-07-10","type":"checkin","employee":11,"status":"approved"}
        });


    }


    private String getFormattedTimeFromTimePicker(TimePicker timePicker) {
        int hour = timePicker.getCurrentHour(); // For API level < 23
        int minute = timePicker.getCurrentMinute(); // For API level < 23
        // Use timePicker.getHour() and timePicker.getMinute() for API level >= 23

        // To get seconds, you can set them to 0 if not needed.
        int second = 0;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    private String getFormattedDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(calendar.getTime());
    }


    private void timeTracker(TimeTrackerRequest timeRequest) {

        // Create an ExecutorService to run the network task
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Create a logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Build the OkHttpClient with the logging interceptor
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();


            Gson gson = new Gson();
            String  payload = gson.toJson(timeRequest);



            // Define the media type and request body
            MediaType mediaType = MediaType.Companion.parse("application/json");
            RequestBody body = RequestBody.Companion.create(mediaType, "{\"time\":\""+timeRequest.getTime()+"\",\"date\":\""+timeRequest.getDate()+"\",\"type\":\"checkin\",\"employee\":"+timeRequest.getEmployee()+",\"status\":\"approved\"}\n");

            // Build the request
            Request request = new Request.Builder()
                    .url("http://10.0.2.2/dictus/public/hrsystem/items/time_tracking")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();


            try (Response response = client.newCall(request).execute()) {

                Log.e("API_CALL",  response.body().string());


                runOnUiThread(() -> {

                    // showSnackbar(null,"Login Succesful",10);
                    Toast.makeText(getApplicationContext(),"FInished Uploading",Toast.LENGTH_LONG);

                    Intent mainIntent = new Intent(CheckinActivity.this, DashboardActivity.class);
                    startActivity(mainIntent);
                    finish();
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("API_CALL",  e.getMessage());
                        Toast.makeText(CheckinActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                        showSnackbar(null,"Login Not succesful",10);

                    }


                });
            }
        });
    }



    private   ArrayAdapter<String> employeeAdapter;


    private void fetchEmployees(int departmentId) {

        Request request = new Request.Builder()
                .url("http://10.0.2.2/dictus/public/hrsystem/items/employees?offset=0&limit=500&sort=sort&meta=total_count,result_count,filter_count&filter[department.id][eq]=" + departmentId)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                            Toast.makeText(CheckinActivity.this, "Failed to load departments", Toast.LENGTH_SHORT).show();

                            Log.e("API_EMPLOYEE", e.getMessage());
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Log.i("API_EMPLOYEE", jsonResponse);
                    Type listType = new TypeToken<ApiResponse<Employee>>() {}.getType();
                    ApiResponse<Employee> apiResponse = gson.fromJson(jsonResponse, listType);
                    employees = apiResponse.getData();

                    List<String> employeeList = new ArrayList<>();
                    for (Employee employee : employees) {
                        employeeList.add(employee.getFirst_name() + " " + employee.getLast_name());
                    }

                    runOnUiThread(() -> {



                    employeeAdapter = new ArrayAdapter<>(CheckinActivity.this,   android.R.layout.simple_spinner_dropdown_item, employeeList);
                       employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       employeeSpinner.setAdapter(employeeAdapter);

//                        departmentAdapter = new ArrayAdapter<>(CheckinActivity.this,
//                                android.R.layout.simple_spinner_dropdown_item, departmentLIst);
//                        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        departmentSpinner.setAdapter(departmentAdapter);
/*
                        employeeAdapter = new ArrayAdapter<>(CheckinActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, employeeAdapter);
                        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        employeeSpinner.setAdapter(employeeAdapter);
                        employeeAdapter.notifyDataSetChanged();*/
                    });
                }
            }
        });

    }
   private  ArrayAdapter<String> departmentAdapter;
    private void fetchDepartments(int branchId) {

        Request request = new Request.Builder()
                .url("http://10.0.2.2/dictus/public/hrsystem/items/departments?offset=0&limit=500&sort=sort&meta=total_count,result_count,filter_count&filter[branch.id][eq]=" + branchId)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                runOnUiThread(() -> {
                            Toast.makeText(CheckinActivity.this, "Failed to load departments", Toast.LENGTH_SHORT).show();

                            Log.e("API_DEPARTMENTS", Objects.requireNonNull(e.getMessage()));
                        }
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Type listType = new TypeToken<ApiResponse<Department>>() {
                    }.getType();
                    ApiResponse<Department> apiResponse = gson.fromJson(jsonResponse, listType);
                    departments = apiResponse.getData();

                    List<String> departmentLIst = new ArrayList<>();
                    for (Department department : departments) {
                        departmentLIst.add(department.getName());
                    }

                    runOnUiThread(() -> {


                         departmentAdapter = new ArrayAdapter<>(CheckinActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, departmentLIst);
                        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        departmentSpinner.setAdapter(departmentAdapter);
                    });
                }
            }
        });

    }

    private void fetchBranches() {
        Request request = new Request.Builder()
                .url("http://10.0.2.2/dictus/public/hrsystem/items/branches")
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                            Toast.makeText(CheckinActivity.this, "Failed to load branches", Toast.LENGTH_SHORT).show();

                            Log.e("API_DEPARTMENTS", e.getMessage());
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Type listType = new TypeToken<ApiResponse<Branch>>() {
                    }.getType();
                    ApiResponse<Branch> apiResponse = gson.fromJson(jsonResponse, listType);
                    branches = apiResponse.getData();

                    List<String> branchList = new ArrayList<>();
                    for (Branch branch : branches) {
                        branchList.add(branch.getName());
                    }

                    runOnUiThread(() -> {
                        ArrayAdapter<String> branchAdapter = new ArrayAdapter<>(CheckinActivity.this,
                                android.R.layout.simple_spinner_item, branchList);
                        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        branchSpinner.setAdapter(branchAdapter);
                    });
                }
            }
        });
    }


}