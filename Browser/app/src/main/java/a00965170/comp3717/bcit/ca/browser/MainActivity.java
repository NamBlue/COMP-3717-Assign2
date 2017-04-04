package a00965170.comp3717.bcit.ca.browser;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity
{
    private TextView textView;
    private List<String> listValues;
    private List<Link> links;
    private Gson gson;
    private  ArrayAdapter<String> myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.myText);
        listValues = new ArrayList<String>();
        links = new ArrayList<>();
        gson = new Gson();

        myAdapter = new ArrayAdapter<String>(this,
                R.layout.row_layout, R.id.listText, listValues);
        setListAdapter(myAdapter);

        Ion.with(getBaseContext()).load("http://max.bcit.ca/comp.json")
                .asJsonArray().setCallback(new FutureCallback<JsonArray>()
        {
            @Override
            public void onCompleted(Exception e, JsonArray result)
            {
                if (result != null)
                {
                    for (int i = 0; i < result.size(); i++)
                    {
                        Link temp = gson.fromJson(result.get(i), Link.class);
                        if (temp != null)
                        {
                            links.add(temp);
                            myAdapter.add(temp.name);
                        }
                    }
                }
                else if (result == null || result.size() == 0)
                {
                    textView.setText("No json found or empty array");
                }
            }
        });
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        if(isInternetAvailable(this))
        {
            String selectedItem = (String) getListView().getItemAtPosition(position);

            String url;
            url = links.get((int) id).url;
            if(!Patterns.WEB_URL.matcher(url).matches())
            {
                textView.setText("Invalid URL");
                return;
            }
            final Intent intent;
            intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", url);
            startActivityForResult(intent, 1);
        }
        else
        {
            textView.setText("Sorry there is no internet connection!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == 1) {
                    textView.setText(data.getStringExtra("error"));
                }
                break;
            }
        }
    }

    public static boolean isInternetAvailable(Context context)
    {
        NetworkInfo info = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
