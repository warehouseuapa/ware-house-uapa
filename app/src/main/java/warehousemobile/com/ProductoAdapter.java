package warehousemobile.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductoAdapter extends BaseAdapter {

    Context context;
    JSONArray jsonArray;
    JSONObject jsonObject;

    public ProductoAdapter(Context context, JSONArray jsonArray) {
        super();
        this.context = context;
        this.jsonArray = jsonArray;
    }
    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater ltInflate = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = ltInflate.inflate(R.layout.lista_productos_item, parent, false);
        }
        TextView tCodigo = (TextView) convertView.findViewById(R.id.plCodigo);
        TextView tDescripcion = (TextView) convertView.findViewById(R.id.plDescripcion);
        jsonObject = new JSONObject();
        try {
            jsonObject = jsonArray.getJSONObject(position);
            tCodigo.setText(jsonObject.getString("codigo"));
            tDescripcion.setText(jsonObject.getString("descripcion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
