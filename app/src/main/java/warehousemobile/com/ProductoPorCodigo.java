package warehousemobile.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

    TextView codigo;
    TextView descripcion;
    TextView cantidad;
    TextView precio;
    TextView localizacion;
    ListView listView;
    JSONArray jsonArray;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_por_codigo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Producto por codigo");

        codigo = (TextView) findViewById(R.id.pCodigo);
        descripcion = (TextView) findViewById(R.id.pDescripcion);
        cantidad = (TextView) findViewById(R.id.pCantidad);
        precio = (TextView) findViewById(R.id.pPrecio);
        localizacion = (TextView) findViewById(R.id.pLocalizacion);
        listView = (ListView) findViewById(R.id.listViewP);
        progressBar = (ProgressBar) findViewById(R.id.loadingP);

        final RelativeLayout mainLayout=(RelativeLayout)this.findViewById(R.id.porcodigo);
        mainLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.INVISIBLE);

//para manejar el click del listview
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    JSONObject object = jsonArray.getJSONObject(position);
//                    asignarText(object);
//                    listView.setVisibility(View.GONE);
//                    mainLayout.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        mainLayout.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productoPorLocation = extras.getString("ProductoPorLocation");
            if (productoPorLocation != null) {
                try {
                    JSONObject object = new JSONObject(productoPorLocation);
                    asignarText(object);
                    listView.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                final String value = extras.getString("Producto");
                RequestQueue queue = Volley.newRequestQueue(ProductoPorCodigo.this);
                String url ="http://warehousedev.azurewebsites.net/api/getProductsByCodigo?codigo=" + value;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    jsonArray = new JSONArray(response);
                                    if (jsonArray.length() == 1) {
                                        JSONObject object = jsonArray.getJSONObject(0);
                                        asignarText(object);
                                        listView.setVisibility(View.GONE);
                                        mainLayout.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                    } else if (jsonArray.length() == 0) {
                                        Toast.makeText(ProductoPorCodigo.this, "No se encontraron Resultados", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProductoPorCodigo.this, MainActivity.class));
                                    } else {
                                        ProductoAdapterPorCodigo productoAdapterPorCodigo = new
                                                ProductoAdapterPorCodigo(ProductoPorCodigo.this, jsonArray);
                                        mainLayout.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                        listView.setAdapter(productoAdapterPorCodigo);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductoPorCodigo.this, "Hubo un error, intente de nuevo", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProductoPorCodigo.this, MainActivity.class));
                    }
                });
                queue.add(stringRequest);
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void asignarText(JSONObject object){
        try {
            codigo.setText(object.getString("codigo"));
            descripcion.setText(object.getString("descripcion"));
            cantidad.setText(object.getString("cantidad"));
            precio.setText(object.getString("precio"));
            localizacion.setText(object.getString("localizacion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProductoPorCodigo.this, MainActivity.class));
    }
}
