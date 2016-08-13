package warehousemobile.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Localizaciones extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizaciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Localizaciones");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.loadingLocalizaciones);
        final RelativeLayout mainLayout=(RelativeLayout)this.findViewById(R.id.layoutLocalizaciones);

        final EditText etLocalizacion = (EditText) findViewById(R.id.editTextLocalizacion);
        final ListView listaProductos = (ListView) findViewById(R.id.listViewLocalizacion);
        Button buscar = (Button) findViewById(R.id.button);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String localizacion = etLocalizacion.getText().toString();

            if(TextUtils.isEmpty(localizacion)){
                Toast.makeText(Localizaciones.this, "Debe llenar los dos campos", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.GONE);
                RequestQueue queue = Volley.newRequestQueue(Localizaciones.this);
                String url ="http://warehousedev.azurewebsites.net/api/getProductByLocation?location=" + localizacion;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                if (jsonArray.length() < 1) {
                                    Toast.makeText(Localizaciones.this, "No se encontraron Productos en esa localizacion", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    mainLayout.setVisibility(View.VISIBLE);
                                } else {
                                    ProductoAdapter productoAdapter = new ProductoAdapter(Localizaciones.this, jsonArray);
                                    listaProductos.setAdapter(productoAdapter);
                                    listaProductos.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    mainLayout.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(Localizaciones.this, "Error interno", Toast.LENGTH_SHORT);
                                e.printStackTrace();
                                progressBar.setVisibility(View.GONE);
                                mainLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Localizaciones.this, "Error, intente de nuevo.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                    }
                });
                queue.add(stringRequest);
            }
            }
        });
    }
}
