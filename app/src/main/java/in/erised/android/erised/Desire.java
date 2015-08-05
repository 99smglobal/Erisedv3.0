package in.erised.android.erised;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import in.erised.android.erised.adapters.CategoryAdapter;
import in.erised.android.erised.pojo.ListItemPOJO;
import in.erised.android.erised.services.Constants;
import in.erised.android.erised.services.FetchAddressIntentService;


public class Desire extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //int w, h;
    private int widthBox, widthBox2;
    private Toolbar toolbar;
    private RecyclerView itemRecyclerView;
    private List<ListItemPOJO> listItems = new ArrayList<>();
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    /**
     * The formatted location address.
     */
    protected String mAddressOutput;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desire);
        buildGoogleApiClient();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        itemRecyclerView = (RecyclerView) findViewById(R.id.itemListView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mResultReceiver = new AddressResultReceiver(new Handler());
        GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(this, 2);
        staggeredGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
        itemRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        populateListItem();
        ((TextView) toolbar.findViewById(R.id.currentLocation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Desire.this,PlacesSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateListItem() {
        listItems = new ArrayList<>();
        String[] tagItem = {"Shirts-Men", "Suits-Men", "Shirts-Women", "Suits-Men", "Sarees", "Kurtis",
                "OnePiece-Women", "Shoes-Kids", "Heels", "Flippers", "T-Shirts Women", "Watches", "Watches-Kids", "EthnicWear-Men",
                "DesignerWear-Men", "DesignerWear-Women", "Purse", "Wallets", "Tie & Belts", "Salvaars", "Handbags"};
        String[] tagItem2 = {"Jeans-Men", "Pants-Men", "Jeans-Women", "Pants-Women", "Nightwear-Women", "Nightwear-Kids", "Shorts-Guys",
                "CasualShoes-Men", "SportsShoes-Men", "T-Shirts Men", "Floaters", "T-Shirts Kids", "Bracelets", "EthnicWear-Women",
                "Artificial Jewellery", "Sportswear", "Bags", "Sunglasses", "Cosmetics", "Undergarments-Men", "Undergarments-Women"};
        ListItemPOJO pojo;
        for (int i = 0; i < tagItem.length; i++) {
            pojo = new ListItemPOJO(i, tagItem[i], false);
            listItems.add(pojo);
            pojo = new ListItemPOJO(i, tagItem2[i], false);
            listItems.add(pojo);
        }
        CategoryAdapter adapter = new CategoryAdapter(this, listItems);
        itemRecyclerView.setAdapter(adapter);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            startIntentService();
        }
    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i("TAG", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i("TAG", "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            ((TextView) toolbar.findViewById(R.id.currentLocation)).setText(mAddressOutput);
            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                showToast(getString(R.string.address_found));
            }

        }
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
