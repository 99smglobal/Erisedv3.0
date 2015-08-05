package in.erised.android.erised;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import in.erised.android.erised.webservice.RequestListener;
import in.erised.android.erised.webservice.WebService;

/**
 * Created by tamilselvankalimuthu on 05/08/15.
 */
public class PlacesSearchActivity extends AppCompatActivity implements RequestListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebService.getInstance(this).doRequestwithGET("https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Vict&types=(cities)&key=AIzaSyDApxbCNQYG0aicl2WzgtWgYDVCm1rssMw", "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Vict&types=(cities)&key=AIzaSyDApxbCNQYG0aicl2WzgtWgYDVCm1rssMw", this, true);

    }

    @Override
    public void onSuccessResponse(String tag, int responseCode, String responseMsg) {
        Toast.makeText(this, responseMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(String tag, int responseCode, String responseMsg) {

    }
}
