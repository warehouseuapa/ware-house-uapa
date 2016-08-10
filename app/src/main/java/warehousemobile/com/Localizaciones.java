package warehousemobile.com;

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

                }
            }
        });
    }
}
