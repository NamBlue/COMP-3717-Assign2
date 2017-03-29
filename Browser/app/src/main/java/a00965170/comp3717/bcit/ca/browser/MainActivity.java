package a00965170.comp3717.bcit.ca.browser;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.myText);

        DelayDisplayTask ddt = new DelayDisplayTask();

        ddt.execute("works", "like", "a", "charm");




    }

    private class DelayDisplayTask extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(final String... params)
        {
            for(int i = 0; i < params.length; i++){
                final int j = i;
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        textView.setText(params[j]);
                    }
                });
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
