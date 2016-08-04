package warehousemobile.com;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Samuel on 03/08/2016.
 */
public class ApiLlamada {
    public static void getHttp(String url1){
        String dataUrl = url1;
        String dataUrlParameters = "email="+"pp@gmail.com"+"&name="+"priyabrat";
        URL url;
        HttpURLConnection connection = null;
        try {
// Create connection
            url = new URL(dataUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json; charset=utf-8");
            //connection.setRequestProperty("Content-Length","" + Integer.toString(dataUrlParameters.getBytes().length));
            //connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
// Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(dataUrlParameters);
            wr.flush();
            wr.close();
// Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            String responseStr = response.toString();
            Log.d("Server response", responseStr);
        } catch (Exception e) {
            Log.d("error:", e.toString());
            e.printStackTrace();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
