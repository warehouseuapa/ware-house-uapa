package warehousemobile.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import warehousemobile.com.scanner.IntentIntegrator;
import warehousemobile.com.scanner.IntentResult;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               llamarScanner();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nuevo_item) {
            showMessage("nuevo item");
            startActivity(new Intent(MainActivity.this, ProductoNuevo.class));
        } else if (id == R.id.localizaciones) {
            showMessage("localizaciones");
        } else if (id == R.id.inventario) {
            showMessage("inventario");
        } else if (id == R.id.scanAdd) {
            showMessage("scanadd");
            llamarScanner();
        } else if (id == R.id.buscar) {
            showMessage("buscar");
        } else if (id == R.id.salir) {
            showMessage("salir");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void llamarScanner() {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator.initiateScan();
    }

    public void showMessage(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url ="http://warehousedev.azurewebsites.net/api/Productos?codigo=" + scanResult.getContents();
            System.out.println(url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            try {
                                Intent i = new Intent(MainActivity.this, ProductoPorCodigo.class);
                                i.putExtra("Producto", response);
                                startActivity(i);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showMessage("Hubo un error, intente de nuevo");
                }
            });
            queue.add(stringRequest);
        }else {
            showMessage(scanResult.getErrorCorrectionLevel());
        }
    }
}
