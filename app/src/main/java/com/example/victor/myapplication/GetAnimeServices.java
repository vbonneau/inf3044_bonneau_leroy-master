package com.example.victor.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import static android.content.ContentValues.TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */






public class GetAnimeServices extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_ANIME = "com.example.victor.myapplication.action.";


    // TODO: Rename parameters

    public GetAnimeServices() {
        super("GetBiersServices");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionAnime(Context context) {
        Intent intent = new Intent(context, GetAnimeServices.class);
        intent.setAction(ACTION_GET_ANIME);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ANIME.equals(action)) {
               handleActionAnime();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionAnime() {
        Log.d(TAG, " Thread service name:" + Thread.currentThread().getName());
        URL url = null;
        String str;
        DataHolder holder = DataHolder.getInstance();
        try {
            int aid= (int) Math.random()*11990;
            url = new URL("http://api.anidb.net:9001/httpapi?client=vbonneau&clientver=42&protover=1&request=anime&aid="+aid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                str = getAnimeFromFile();
                holder.setData(str);
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.BIERS_UPDATE));
                Log.d(TAG, "Biers json downloaded !");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @NotNull
    public static String decompress(byte[] compressed) throws IOException {
        Log.d("it is a tag", "3");
        final int BUFFER_SIZE = 32;
        ByteArrayInputStream is = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
        StringBuilder string = new StringBuilder();
        byte[] data = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = gis.read(data)) != -1) {
            string.append(new String(data, 0, bytesRead));
        }
        gis.close();
        is.close();
        Log.d("it is a tag", "4");
        return string.toString();
    }

    public String getAnimeFromFile(){
        try{
            InputStream is = getResources().openRawResource(R.raw.text);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            Log.d("it is a tag", "2");
            return decompress(buffer);
        }catch (IOException e){
            e.printStackTrace();
            Log.d("it is a tag", "1");
            return new String();
        }
    }


    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
}
