package warehousemobile.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Localizaciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizaciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText etItem = (EditText) findViewById(R.id.editTextCodigo);
        final EditText etLocalizacion = (EditText) findViewById(R.id.editTextLocalizacion);
        Button buscar = (Button) findViewById(R.id.button);

        Toast.makeText(Localizaciones.this, "Debe llenar los dos campos", Toast.LENGTH_SHORT);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = etItem.getText().toString();
                String localizacion = etLocalizacion.getText().toString();

                if(TextUtils.isEmpty(item) || TextUtils.isEmpty(localizacion)){
                    Toast.makeText(Localizaciones.this, "Debe llenar los dos campos", Toast.LENGTH_SHORT);
                } else {
                    RequestQueue queue = Volley.newRequestQueue(Localizaciones.this);
                    String url ="http://warehousedev.azurewebsites.net/api/getProductByItemAndLocation?item=" +
                            item + "&location=" + localizacion;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println(response);
                                    try {
                                        JSONObject object = new JSONObject(response);
                                        if (object.getString("errorCode") != null) {
                                            Toast.makeText(Localizaciones.this, object.getString("mensaje"), Toast.LENGTH_SHORT);
                                        } else {
                                            Intent i = new Intent(Localizaciones.this, ProductoPorCodigo.class);
                                            i.putExtra("Producto", object.getString("codigo"));
                                            startActivity(i);
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(Localizaciones.this, "Error interno", Toast.LENGTH_SHORT);
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("EERROORR: Hubo un error, intente de nuevo");
                        }
                    });
                    queue.add(stringRequest);
                }
            }
        });
    }
}
