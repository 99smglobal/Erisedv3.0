package in.erised.android.erised.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import in.erised.android.erised.app.ErisedApplication;

/**
 * #author Innoppl
 * #category Class
 * #description It's to handle webservice using volley library.
 */
public class WebService {

    private static final String TAG = WebService.class
            .getSimpleName();
    //Progress Dialog
    private static ProgressDialog pDialog;

    /**
     * #param context is Context object
     * #return instance of this class
     * #description Method to return the instance of this class.
     */
    public static synchronized WebService getInstance(Context context) {
//        if (mInstance == null)
        return new WebService(context);
    }

    /**
     * #param context is context object.
     * #description Initialize progress dialog.
     */
    private WebService(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
    }

    /**
     * #param tag      string
     * #param url      string
     * #param params   Map
     * #param listener custom listener interface
     * #description Method to requesting server with GET method with params,
     * and it will return status code and string response.
     */
    public void doRequestwithGET(final String tag, String url, final JSONObject params, final RequestListener listener, boolean ShowDialog) {
        volleyJsonRequest(tag, Request.Method.GET, url, params, listener, ShowDialog);
    }

    /**
     * #param tag      string
     * #param url      string
     * #param listener custom listener interface
     * #description Method to requesting server with GET Method without params,
     * and it will return status code and string response.
     */
    public void doRequestwithGET(final String tag, String url, final RequestListener listener, boolean ShowDialog) {
        volleyJsonRequest(tag, Request.Method.GET, url, new JSONObject(), listener, ShowDialog);
    }

    /**
     * #param tag      string
     * #param url      string
     * #param params   Map
     * #param listener custom listener interface
     * #descrption Method to requesting server with POST method with params,
     * and it will return status code and string response.
     */
    public void doRequestwithPOST(final String tag, String url, final JSONObject params, final RequestListener listener, boolean ShowDialog) {
//        doObjectRequest(url, tag, params, listener);
        volleyJsonRequest(tag, Request.Method.POST, url, params, listener, ShowDialog);
    }

    /**
     * #param tag      string
     * #param url      string
     * #param obj      Hashmap
     * #param listener custom listener interface
     * #descrption Method to requesting server with POST method with string entity params,
     * and it will return status code and string response.
     */
    public void doMultipartObjectRequestWithPOST(final String tag, String url, HashMap<String, String> obj, final RequestListener listener) {
        volleyMultipartEntityRequest(tag, url, obj, null, null, listener);
    }

    /**
     * #param tag          string
     * #param url          string
     * #param filePartName string
     * #param file         file
     * #param obj          hashmap
     * #param listener     custom listener interface
     * #description Method to requesting server with POST method with upload file and string entity params,
     * and it will return staus code and string response.
     */
    public void doMultipartObjectRequestWithPOST(final String tag, String url, String filePartName, List<File> file, HashMap<String, String> obj, final RequestListener listener) {
        volleyMultipartEntityRequest(tag, url, obj, filePartName, file, listener);
    }

    /**
     * #param tag      string
     * #param method   int
     * #param url      string
     * #param params   hashmap
     * #param listener custom listener interface
     * #desscription It's a common method for executing json server request.
     */
    private void volleyJsonRequest(final String tag, int method, String url, final JSONObject params, final RequestListener listener, boolean showDialog) {
        if (showDialog)
            showpDialog();
        Log.e("Webservice : TAG", tag);
        Log.e("Webservice : URL", url);

        VolleyJsonRequest jsonObjReq = new VolleyJsonRequest(method, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hidepDialog();
                NetworkResponse networkResponse = volleyError.networkResponse;
                if (listener != null) {
                    try {
                        listener.onErrorResponse(tag, networkResponse.statusCode, new String(networkResponse.data));
                    } catch (Exception e) {
                        listener.onErrorResponse(tag, HttpStatus.SC_NO_CONTENT, "");
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                hidepDialog();
                if (listener != null) {
                    VolleyCustomResponse response = VolleyCustomResponse.create(s);
                    Log.e("Webservice : STATUSCODE", "" + response.getStatuscode());
                    Log.e("Webservice : DATA", new String(response.getResponseData()));
                    if (response.getStatuscode() == 200) {
                        try {
                            listener.onSuccessResponse(tag, response.getStatuscode(), new String(response.getResponseData()));
                        } catch (Exception jse) {
                            jse.printStackTrace();
                        }
                    } else {
                        try {
                            listener.onErrorResponse(tag, response.getStatuscode(), new String(response.getResponseData()));
                        } catch (Exception e) {
                            listener.onErrorResponse(tag, HttpStatus.SC_NO_CONTENT, "");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, params);
        jsonObjReq.setShouldCache(false);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ErisedApplication.getInstance().addToRequestQueue(jsonObjReq, tag);
    }

    public void doObjectRequest(String URL, final String Tag, JSONObject params, final RequestListener listener) {
        try {
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(URL, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (listener != null)
                            listener.onSuccessResponse(Tag, 0, response.toString());
                    } catch (JsonSyntaxException jse) {
                        try {
                            listener.onSuccessResponse(Tag, 0, new JSONArray("[]").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

                    , new Response.ErrorListener()

            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (listener != null)
                        try {
                            listener.onErrorResponse(Tag, 0, Integer.toString(networkResponse.statusCode));
                        } catch (Exception e) {
                            listener.onErrorResponse(Tag, 0, Integer.toString(HttpStatus.SC_NO_CONTENT));
                            e.printStackTrace();
                        }
                }
            }

            );
            jsArrayRequest.setShouldCache(false);
            jsArrayRequest.setRetryPolicy(new

                            DefaultRetryPolicy(
                            30000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

            );
            ErisedApplication.getInstance().

                    addToRequestQueue(jsArrayRequest, Tag);

        } catch (IllegalStateException ise) {
            Log.v(TAG, ise.toString());
        }
    }

    /**
     * #param tag          string
     * #param url          string
     * #param obj          hashmap
     * #param filePartname string
     * #param file         file
     * #param listener     custom listener interface
     * #description Method to requesting server with POST method with upload file and string entity params,
     * and it will return staus code and string response.
     */

    private void volleyMultipartEntityRequest(final String tag, String url, HashMap<String, String> obj, final String filePartname, final List<File> file, final RequestListener listener) {
        showpDialog();
        MultiPartEntityRequest request = new MultiPartEntityRequest(url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hidepDialog();
                Log.e(TAG, "Error:" + volleyError.networkResponse);
                NetworkResponse networkResponse = volleyError.networkResponse;
                if (listener != null) {
                    try {
                        listener.onErrorResponse(tag, networkResponse.statusCode, new String(networkResponse.data));
                    } catch (Exception e) {
                        listener.onErrorResponse(tag, HttpStatus.SC_NO_CONTENT, new String(networkResponse.data));
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                hidepDialog();
                if (listener != null) {
                    VolleyCustomResponse response = VolleyCustomResponse.create(s);
                    if (response.getStatuscode() == 200) {

                        try {
                            listener.onSuccessResponse(tag, response.getStatuscode(), new String(response.getResponseData()));
                        } catch (Exception jse) {
                            jse.printStackTrace();
                        }
                    } else {
                        try {
                            listener.onErrorResponse(tag, response.getStatuscode(), new String(response.getResponseData()));
                        } catch (Exception e) {
                            listener.onErrorResponse(tag, HttpStatus.SC_NO_CONTENT, new String(response.getResponseData()));
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, filePartname, file, obj);
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ErisedApplication.getInstance().addToRequestQueue(request, tag);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
