package com.cst2335.tran0450;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar progressBar;
    ImageView imageView;
    TextView currentTempView;
    TextView maxTempView;
    TextView minTempView;
    TextView uvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = (ProgressBar) findViewById(R.id.processBar);
        progressBar.setVisibility(View.VISIBLE);
        imageView = (ImageView) findViewById(R.id.currentWeather);
        currentTempView =(TextView) findViewById(R.id.currentTem);
        maxTempView = (TextView) findViewById(R.id.maxTem);
        minTempView = (TextView) findViewById(R.id.minTem);
        uvView = (TextView) findViewById(R.id.uvRating);

        ForecastQuery query = new ForecastQuery();
        query.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

        UVQuery query1 = new UVQuery();
        query1.execute("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

    }


//     "http://openweathermap.org/img/w/"+query.iconName+".png",
    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String currentTemp;
        String minTemp;
        String maxTemp;
        String iconName;
        Bitmap weatherImage = null;
        @Override
        protected String doInBackground(String... args) {
            try {

                //create a URL object of what server to contact:
                URL weatherUrl = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) weatherUrl.openConnection();
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            currentTemp = xpp.getAttributeValue(null,"value");
                            publishProgress(25,50,75);
                            minTemp = xpp.getAttributeValue(null,"min");
                            publishProgress(25,50,75);
                            maxTemp = xpp.getAttributeValue(null,"max");
                            publishProgress(25,50,75);
                        }

                        else if(xpp.getName().equals("weather"))
                        {
                            iconName = xpp.getAttributeValue(null, "icon")+".png"; // this will run for <AMessage message="parameter" >
                            File file = getBaseContext().getFileStreamPath(iconName);
                            if (!file.exists()) {
                                saveImage(iconName);
                            }else {
                                Log.i("WeatherForecast", "Saved icon, " + iconName + " is displayed.");
                                try {
                                    FileInputStream in = new FileInputStream(file);
                                    weatherImage = BitmapFactory.decodeStream(in);
                                } catch (FileNotFoundException e) {
                                    Log.i("WeatherForecast", "Saved icon, " + iconName + " is not found.");
                                }
                            }
                            publishProgress(100);
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

            }
            catch (Exception e)
            {

            }

            return "Done";
        }


        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            progressBar = (ProgressBar) findViewById(R.id.processBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar = (ProgressBar) findViewById(R.id.processBar);
            progressBar.setVisibility(View.VISIBLE);
            currentTempView =(TextView) findViewById(R.id.currentTem);
            maxTempView = (TextView) findViewById(R.id.maxTem);
            minTempView = (TextView) findViewById(R.id.minTem);
            imageView = (ImageView) findViewById(R.id.currentWeather);
            imageView.setImageBitmap(weatherImage);


            currentTempView.setText("Current Temp:\n"+currentTemp);
            maxTempView.setText("maxTemp:\n"+maxTemp);
            minTempView.setText("Min Temp:\n"+minTemp);


        }
    private void saveImage(String fname) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://openweathermap.org/img/w/" + fname);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                weatherImage = BitmapFactory.decodeStream(connection.getInputStream());
                FileOutputStream outputStream = openFileOutput(fname, Context.MODE_PRIVATE);
                weatherImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                Log.i("WeatherForecast", "Weather icon, " + fname + " is downloaded and displayed.");
            } else
                Log.i("WeatherForecast", "Can't connect to the weather icon for downloading.");
        } catch (Exception e) {
            Log.i("WeatherForecast", "weather icon download error: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    }

    public class UVQuery extends AsyncTask<String, Integer, String> {
        double uV;

        @Override
        protected String doInBackground(String... args) {
            try {
                             //UV
                URL uvUrl = new URL(args[0]);
                HttpURLConnection uvConnection = (HttpURLConnection) uvUrl.openConnection();
                InputStream uvResponse = uvConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(uvResponse, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                    Log.d("Response: ", "> " + line);
                }
                String result = sb.toString();

                JSONObject uvReport = new JSONObject(result);
                uV = uvReport.getDouble("value");



            }
            catch (Exception e)
            {

            }

            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            progressBar = (ProgressBar) findViewById(R.id.processBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar = (ProgressBar) findViewById(R.id.processBar);
            progressBar.setVisibility(View.VISIBLE);
            uvView = (TextView) findViewById(R.id.uvRating);
            uvView.setText("UVRating:\n"+String.valueOf(uV));

        }
    }




}