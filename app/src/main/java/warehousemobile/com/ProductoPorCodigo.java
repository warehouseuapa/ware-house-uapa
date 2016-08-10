package warehousemobile.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
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


public class ProductoPorCodigo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_por_codigo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView codigo = (TextView) findViewById(R.id.pCodigo);
        final TextView descripcion = (TextView) findViewById(R.id.pDescripcion);
        final TextView cantidad = (TextView) findViewById(R.id.pCantidad);
        final TextView precio = (TextView) findViewById(R.id.pPrecio);
        final TextView localizacion = (TextView) findViewById(R.id.pLocalizacion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String value = extras.getString("Producto");
            RequestQueue queue = Volley.newRequestQueue(ProductoPorCodigo.this);
            String url ="http://warehousedev.azurewebsites.net/api/Productos?codigo=" + value;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                JSONObject object = new JSONObject(response);
                                codigo.setText(object.getString("codigo"));
                                descripcion.setText(object.getString("descripcion"));
                                cantidad.setText(object.getString("cantidad"));
                                precio.setText(object.getString("precio"));
                                localizacion.setText(object.getString("localizacion"));
                            } catch (JSONException e) {
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
