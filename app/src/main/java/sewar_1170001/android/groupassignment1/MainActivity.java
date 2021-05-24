package sewar_1170001.android.groupassignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtLocation;
    private EditText edtIdNumber;
    private EditText edtDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
    }
    private void setUpViews() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtLocation = findViewById(R.id.edtLocation);
        edtIdNumber = findViewById(R.id.edtIdNumber);
        edtDOB = findViewById(R.id.edtDOB);
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String fname = edtFirstName.getText().toString();
        String lname = edtLastName.getText().toString();
        String loc = edtLocation.getText().toString();
        String id = edtIdNumber.getText().toString();
        String dob = edtDOB.getText().toString();

        String data = URLEncoder.encode("fName", "UTF-8")
                + "=" + URLEncoder.encode(fname, "UTF-8");

        data += "&" + URLEncoder.encode("lName", "UTF-8") + "="
                + URLEncoder.encode(lname, "UTF-8");

        data += "&" + URLEncoder.encode("location", "UTF-8")
                + "=" + URLEncoder.encode(loc, "UTF-8");

        data += "&" + URLEncoder.encode("IdNum", "UTF-8")
                + "=" + URLEncoder.encode(id, "UTF-8");

        data += "&" + URLEncoder.encode("DOB", "UTF-8")
                + "=" + URLEncoder.encode(dob, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }

            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        // Show response on activity
        return text;
    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }


    public void GoToSecondActivity(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        String fName = edtFirstName.getText().toString();
        String lName = edtLastName.getText().toString();
        String loc = edtLocation.getText().toString();
        String idNum = edtIdNumber.getText().toString();
        String dob = edtDOB.getText().toString();

        String restUrl = "http://10.0.2.2/proj/addstudent.php";


        if (fName.isEmpty() == false && lName.isEmpty() == false && loc.isEmpty()== false && idNum.isEmpty() == false && dob.isEmpty() == false ) {// Data Validation
            Toast.makeText(this, "Data Added", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        123);

            } else{
                SendPostRequest runner = new SendPostRequest();
                runner.execute(restUrl);
            }
            startActivity(intent);
        }else{
            Toast.makeText(this, "All Fields Are Required", Toast.LENGTH_LONG).show();
        }
    }
}