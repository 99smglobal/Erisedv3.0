package in.erised.android.erised.webservice;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;


public class VolleyJsonRequest extends JsonRequest<String> {

    private final Response.Listener<String> mListener;
    private JSONObject mParams;

    public VolleyJsonRequest(int method, String url, Response.ErrorListener errorListener, Response.Listener<String> listener, JSONObject params) {
        super(method, url, params.toString(), listener, errorListener);
        mListener = listener;
        mParams = params;
    }

    public VolleyJsonRequest(int method, String url, Response.ErrorListener errorListener, Response.Listener<String> listener) {
        super(method, url, null, listener, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            VolleyCustomResponse res = new VolleyCustomResponse(networkResponse);
//            String je = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(res.serialize(), getCacheEntry());
//            JSONObject resss = new JSONObject(je);
//            Cache.Entry resp = HttpHeaderParser.parseCacheHeaders(networkResponse);
//            VolleyCustomResponse res = new VolleyCustomResponse(networkResponse);
//            return Response.success(res.toString(), getCacheEntry());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void deliverResponse(String s) {
        mListener.onResponse(s);
    }
}
