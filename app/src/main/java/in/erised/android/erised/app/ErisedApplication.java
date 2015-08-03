package in.erised.android.erised.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import in.erised.android.erised.util.LruBitmapCache;

/**
 * Created by tamilselvankalimuthu on 03/08/15.
 */
public class ErisedApplication extends Application {

    private static ErisedApplication mInstance;
    private RequestQueue mRequestQueue;
    private com.android.volley.toolbox.ImageLoader mImageLoader;
    private static final String TAG = ErisedApplication.class
            .getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public com.android.volley.toolbox.ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new com.android.volley.toolbox.ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void clearCache() {
        mRequestQueue.getCache().clear();
    }

    /*
    Volley Initialization
     */
    public static synchronized ErisedApplication getInstance() {
        return mInstance;
    }

}
