package sewar_1170001.android.groupassignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity2 extends AppCompatActivity {

    private EditText edtID;
    private EditText edtenterName;
    private EditText edtenterlastname;
    private EditText edtEnterlocation;
    private EditText edtEnterDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setUpViews();
    }

    private void setUpViews() {
        edtID = findViewById(R.id.edtID);
        edtenterName = findViewById(R.id.edtenterName);
        edtenterlastname = findViewById(R.id.edtenterlastname);
        edtEnterlocation = findViewById(R.id.edtEnterlocation);
        edtEnterDOB = findViewById(R.id.edtEnterDOB);
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String fname = edtenterName.getText().toString();
        String lname = edtenterlastname.getText().toString();
        String loc = edtEnterlocation.getText().toString();
        String dob = edtEnterDOB.getText().toString();
        String id = edtID.getText().toString();

        String data = URLEncoder.encode("fName", "UTF-8")
                + "=" + URLEncoder.encode(fname, "UTF-8");

        data += "&" + URLEncoder.encode("lName", "UTF-8") + "="
                + URLEncoder.encode(lname, "UTF-8");

        data += "&" + URLEncoder.encode("location", "UTF-8")
                + "=" + URLEncoder.encode(loc, "UTF-8");

        data += "&" + URLEncoder.encode("DOB", "UTF-8")
                + "=" + URLEncoder.encode(dob, "UTF-8");

        data += "&" + URLEncoder.encode("IdNum", "UTF-8")
                + "=" + URLEncoder.encode(id, "UTF-8");

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

            Toast.makeText(MainActivity2.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToThirdActivity(View view) {
        Intent intent = new Intent(this, MainActivity3.class);
        String ID = edtID.getText().toString();
        String Name = edtenterName.getText().toString();
        String lName = edtenterlastname.getText().toString();
        String loc = edtEnterlocation.getText().toString();
        String dob = edtEnterDOB.getText().toString();
        if (ID.isEmpty() == false && Name.isEmpty() == false&& lName.isEmpty() == false&& loc.isEmpty() == false&& dob.isEmpty() == false) {// Data Validation
            Toast.makeText(this, "Data Updated", Toast.LENGTH_LONG).show();
            String restUrl = "http://10.0.2.2/proj/updatestudentinfo.php";
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