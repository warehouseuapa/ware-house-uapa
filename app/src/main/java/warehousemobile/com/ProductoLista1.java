package warehousemobile.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ProductoLista1 extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_lista1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Lista de productos");

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            final String value = extras.getString("Productos");
        } else {
            cargarTodosLosProductos();
        }

    }

    public void cargarTodosLosProductos(){
        progressBar = (ProgressBar) findViewById(R.id.listaProgress);
        progressBar.setVisibility(View.VISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView listaProductos = (ListView) findViewById(R.id.listViewProductos);

        RequestQueue queue = Volley.newRequestQueue(ProductoLista1.this);
        String url ="http://warehousedev.azurewebsites.net/api/Productos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ProductoAdapter productoAdapter = new ProductoAdapter(ProductoLista1.this, jsonArray);
                            progressBar.setVisibility(View.INVISIBLE);
                            listaProductos.setVisibility(View.VISIBLE);
                            listaProductos.setAdapter(productoAdapter);
                        } catch (Exception e) {
                            Toast.makeText(ProductoLista1.this, e.toString(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Hubo un error, intente de nuevo");
                startActivity(new Intent(ProductoLista1.this, MainActivity.class));
            }
        });
        queue.add(stringRequest);
    }
}

