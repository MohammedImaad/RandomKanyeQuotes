 package com.example.android.randomkanyequotes.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.randomkanyequotes.R;
import com.example.android.randomkanyequotes.Retrofit.GetDataService;
import com.example.android.randomkanyequotes.Retrofit.KanyeQuote;
import com.example.android.randomkanyequotes.Retrofit.RetrofitClientInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class MainActivity extends AppCompatActivity {
    @BindView(R.id.quoteView)
    TextView quoteView;
    public void twitterButtonClicked(View view)
    {
        if(!quoteView.getText().toString().equals("")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text="+quoteView.getText().toString()+"&hashtags=RandomShizKanyeSays"));
            startActivity(browserIntent);
        }else{
            Toast.makeText(this, "Press the generate button to begin", Toast.LENGTH_SHORT).show();
        }
    }
    public void clipboardButtonClicked(View view)
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = android.content.ClipData.newPlainText("Copied Text", quoteView.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }
    public void buttonClicked(View view)
    {
        boolean isConnected=checkingForConnectivity();
        if(isConnected) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<KanyeQuote> call = service.getQuote();
            call.enqueue(new Callback<KanyeQuote>() {
                @Override
                public void onResponse(Call<KanyeQuote> call, Response<KanyeQuote> response) {
                    quoteView.setText(response.body().getQuote());
                }

                @Override
                public void onFailure(Call<KanyeQuote> call, Throwable t) {
                    Log.i("Failure", t.getMessage());
                }
            });
        }else{
            quoteView.setText("You ain't got the internet!");
        }

    }
    public boolean checkingForConnectivity()
    {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
