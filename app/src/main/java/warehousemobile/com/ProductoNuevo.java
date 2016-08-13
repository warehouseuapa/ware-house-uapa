package warehousemobile.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import warehousemobile.com.scanner.IntentIntegrator;
import warehousemobile.com.scanner.IntentResult;

public class ProductoNuevo extends AppCompatActivity {

    public EditText codigo;
    public EditText localizacion;
    public EditText cantidad;
    public EditText descripcion;
    public EditText precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_nuevo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Nuevo producto");

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(ProductoNuevo.this);
                integrator.initiateScan();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setVisibility(View.INVISIBLE);

        codigo = (EditText) findViewById(R.id.nCodigo);
        descripcion = (EditText) findViewById(R.id.nDescripcion);
        precio = (EditText) findViewById(R.id.nPrecio);
        cantidad = (EditText) findViewById(R.id.nCantidad);
        localizacion = (EditText) findViewById(R.id.nLocalizacion);

        Button btnGuardar = (Button) findViewById(R.id.btn_guardar_nuevo);

        codigo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    fab.setVisibility(View.VISIBLE);
                    descripcion.setEnabled(true);
                    precio.setEnabled(true);
                } else {
                    fab.setVisibility(View.INVISIBLE);
                    final String value = codigo.getText().toString();
                    RequestQueue queue = Volley.newRequestQueue(ProductoNuevo.this);
                    String url ="http://warehousedev.azurewebsites.net/api/Productos?codigo=" + value;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject object = new JSONObject(response);
                                        if (object != null) {
                                            descripcion.setText(object.getString("descripcion"));
                                            precio.setText(object.getString("precio"));
                                            descripcion.setEnabled(false);
                                            precio.setEnabled(false);
                                            cantidad.requestFocus();
                                        } else {

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Errorrrrr: " + error.toString());
                        }
                    });
                    queue.add(stringRequest);
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textCodigo = codigo.getText().toString();
                String textDescripcion = descripcion.getText().toString();
                String textPrecio = precio.getText().toString();
                String textCantidad = cantidad.getText().toString();
                String textLocallizacion = localizacion.getText().toString();
                if(validarEditText(textCodigo, textDescripcion, textPrecio, textCantidad, textLocallizacion)) {
                    makeRequest(textCodigo, textDescripcion, textPrecio, textCantidad, textLocallizacion);
                } else {
                    Toast.makeText(ProductoNuevo.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validarEditText(String textCodigo, String textDescripcion, String textPrecio,
                                    String textCantidad, String textLocallizacion) {
        boolean esValido = true;

        if(TextUtils.isEmpty(textCodigo) || TextUtils.isEmpty(textDescripcion) ||
                TextUtils.isEmpty(textPrecio) || TextUtils.isEmpty(textCantidad) ||
                TextUtils.isEmpty(textLocallizacion)){
            esValido = false;
        }

        return esValido;
    }

    public void makeRequest(final String mCodigo, final String mDescripcion, final String mPrecio,
                            final String mCantidad, final String mLocalizacion) {
        RequestQueue queue = Volley.newRequestQueue(ProductoNuevo.this);
        String url = "http://warehousedev.azurewebsites.net/api/Productos";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.has("errorCode")){
                                Toast.makeText(ProductoNuevo.this, object.getString("message"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ProductoNuevo.this, "Guardado con exito", Toast.LENGTH_LONG).show();
                                Log.d("Response", response);
                                codigo.setText("");
                                descripcion.setText("");
                                precio.setText("");
                                cantidad.setText("");
                                localizacion.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("codigo", mCodigo);
                params.put("descripcion", mDescripcion);
                params.put("cantidad", mCantidad);
                params.put("precio", mPrecio);
                params.put("localizacion", mLocalizacion);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            codigo.setText(scanResult.getContents());
        }else {

        }
    }

}
