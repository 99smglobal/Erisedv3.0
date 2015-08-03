package in.erised.android.erised.webservice;

/**
 *@author Innoppl
 *@category Interface
 *@description Custom listener interface
 */
public interface RequestListener {
    void onSuccessResponse(String tag, int responseCode, String responseMsg);
    void onErrorResponse(String tag, int responseCode, String responseMsg);
}
