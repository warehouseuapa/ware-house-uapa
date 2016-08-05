package warehousemobile.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import warehousemobile.com.modelos.Productos;

public class ProductoPorCodigo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_por_codigo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        System.out.println("Entro al oncreate========================================================");

        TextView codigo = (TextView) findViewById(R.id.pCodigo);
        TextView descripcion = (TextView) findViewById(R.id.pDescripcion);
        TextView cantidad = (TextView) findViewById(R.id.pCantidad);
        TextView precio = (TextView) findViewById(R.id.pPrecio);
        TextView localizacion = (TextView) findViewById(R.id.pLocalizacion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Producto");
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(value);
                JSONObject object = jsonArray.getJSONObject(0);
                codigo.setText(object.getString("codigo"));
                descripcion.setText(object.getString("descripcion"));
                cantidad.setText(object.getString("cantidad"));
                precio.setText(object.getString("precio"));
                localizacion.setText(object.getString("localizacion"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        Productos productos = new Productos();
//
//        codigo.setText(productos.getCodigo());
//        descripcion.setText(productos.getDescripcion());
//        cantidad.setText(String.valueOf(productos.getCantidad()));
//        precio.setText(String.valueOf(productos.getPrecio()));
//        localizacion.setText(productos.getLocalizacion());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
