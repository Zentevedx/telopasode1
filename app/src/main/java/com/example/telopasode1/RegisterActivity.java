package com.example.telopasode1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    EditText nombre, apellidos, email, password, fechaNacimiento;
    Button btnRegistrarse;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar vistas
        apellidos = findViewById(R.id.edtApellidos);
        nombre = findViewById(R.id.edtNombre);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        fechaNacimiento = findViewById(R.id.edtFechaNacimiento);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        // Configurar el DatePicker para el EditText de fecha de nacimiento
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validar los campos y edad
                if (nombre.getText().toString().trim().isEmpty() ||
                        apellidos.getText().toString().trim().isEmpty() ||
                        email.getText().toString().trim().isEmpty() ||
                        password.getText().toString().trim().isEmpty() ||
                        fechaNacimiento.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar si el usuario es mayor de edad
                if (!esMayorDeEdad(fechaNacimiento.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "Debes ser mayor de 18 años para registrarte.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Si todo está correcto, registrar el usuario
                registrarUsuario("http://192.168.0.8:80/proyectofinal2024/register.php");
            }
        });
    }

    // Método para validar si el usuario es mayor de edad
    private boolean esMayorDeEdad(String fechaNacimientoStr) {
        try {
            Date fechaNacimiento = dateFormat.parse(fechaNacimientoStr); // Convertir la fecha de nacimiento
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(fechaNacimiento);

            Calendar today = Calendar.getInstance(); // Fecha actual
            int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR); // Calcular años

            if (today.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) ||
                    (today.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) &&
                            today.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
                age--; // Ajustar si aún no ha pasado el cumpleaños este año
            }

            return age >= 18; // Verificar si es mayor o igual a 18
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void registrarUsuario(String URL) {
        // Crear el objeto JSON con los datos del usuario
        JSONObject jsonBody = new JSONObject();
        try {
            String completo = nombre.getText().toString().trim() + " " + apellidos.getText().toString().trim();
            jsonBody.put("nombre", completo);
            jsonBody.put("email", email.getText().toString().trim());
            jsonBody.put("password", password.getText().toString().trim());
            jsonBody.put("fecha_nacimiento", fechaNacimiento.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Error al crear los datos de registro.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear la solicitud JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");

                            if (success) {
                                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String message = response.has("message") ? response.getString("message") : "Registro fallido.";
                                Toast.makeText(RegisterActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Error al procesar la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RegisterActivity", "Error: " + error.toString());
                        Toast.makeText(RegisterActivity.this, "Error en la conexión.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    fechaNacimiento.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }

    public void registro(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
