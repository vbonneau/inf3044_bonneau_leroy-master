package com.example.victor.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import static com.example.victor.myapplication.MainActivity.BIERS_UPDATE;

public class SecondeActivity extends AppCompatActivity {

    RecyclerView v;
    byte[] byteArray = new byte[640000];
    DataHolder holder = DataHolder.getInstance();
    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);
        IntentFilter filter = new IntentFilter(BIERS_UPDATE);
        Toast.makeText(getApplicationContext(),getString(R.string.dl_text),Toast.LENGTH_LONG).show();
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),filter);
        GetBiersServices.startActionBiers(this);
        v=(RecyclerView) findViewById(R.id.rv_biere);
        v.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        str = holder.getData();
        Pattern regex_title = Pattern.compile("<title xml:lang=\"en\" type=\"main\">([^<])</title>");
        Pattern regex_desc = Pattern.compile("<description>([^<])</description>");
        Pattern regex_episCount = Pattern.compile("<episodecount>([^<])</episodecount>");

        Matcher matcher_title = regex_title.matcher(str);
        String title = matcher_title.group(0);

        Matcher matcher_desc = regex_desc.matcher(str);
        String desc = matcher_desc.group(0);

        Matcher matcher_episCount = regex_episCount.matcher(str);
        String count = matcher_episCount.group(0);
        String[] tab = new String[3];
        tab[0]= title; tab[1]= desc; tab[2] = count;
        ListView list = (ListView) this.findViewById(android.R.id.list);
        ArrayAdapter text = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tab);
        list.setAdapter(text);


    }

    public class BierUpdate extends BroadcastReceiver {

        public static final String BIERS_UPDATE = "com.optip.cours.inf4042_11.BIERS_UPDATE";

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context ,context.getString(R.string.dl_end),Toast.LENGTH_LONG).show();
        }
    }

}



