package warehousemobile.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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
                JSONObject object = new JSONObject(value);
                codigo.setText(object.getString("codigo"));
                descripcion.setText(object.getString("descripcion"));
                cantidad.setText(object.getString("cantidad"));
                precio.setText(object.getString("precio"));
                localizacion.setText(object.getString("localizacion"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
