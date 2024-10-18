package com.example.telopasode1;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class AdListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAnuncios;
    private AnuncioAdapter anuncioAdapter;
    private ArrayList<Anuncio> anunciosList;
    private TextView tvCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_list);

        // Inicializar vistas
        recyclerViewAnuncios = findViewById(R.id.recyclerViewAnuncios);
        tvCategoria = findViewById(R.id.tv_categoria);

        // Configurar RecyclerView
        recyclerViewAnuncios.setLayoutManager(new LinearLayoutManager(this));
        anunciosList = new ArrayList<>();
        anuncioAdapter = new AnuncioAdapter(anunciosList, this);
        recyclerViewAnuncios.setAdapter(anuncioAdapter);

        // Obtener anuncios
        obtenerAnuncios();
    }

    private void obtenerAnuncios() {
        Conexion x = new Conexion();
        // Construir la URL para obtener todos los anuncios
        String URL = x.getIp() + "getAnuncios.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null, // No hay cuerpo en una solicitud GET
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Procesar el array de anuncios desde la respuesta JSON
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject anuncioObj = response.getJSONObject(i);

                                // Obtener las imágenes (puede ser un array vacío si no hay imágenes)
                                JSONArray imagenesArray = anuncioObj.getJSONArray("imagenes");
                                ArrayList<String> imagenes = new ArrayList<>();
                                for (int j = 0; j < imagenesArray.length(); j++) {
                                    imagenes.add(imagenesArray.getString(j));
                                }

                                // Crear el objeto Anuncio con los datos del JSON
                                Anuncio anuncio = new Anuncio(
                                        anuncioObj.getString("categoria"),
                                        anuncioObj.getString("titulo"),
                                        anuncioObj.getString("descripcion"),
                                        anuncioObj.isNull("precio") ? null : anuncioObj.getDouble("precio"),
                                        anuncioObj.getString("fecha_actualizacion"),
                                        imagenes
                                );

                                // Agregar el anuncio a la lista
                                anunciosList.add(anuncio);
                            }
                            // Notificar al adapter que los datos han cambiado
                            anuncioAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdListActivity.this, "Error al procesar la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AdListActivity", "Error: " + error.toString());
                        Toast.makeText(AdListActivity.this, "Error al obtener anuncios.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Agregar la solicitud a la cola de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
