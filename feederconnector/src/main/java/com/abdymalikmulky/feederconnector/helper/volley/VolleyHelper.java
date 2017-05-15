package com.abdymalikmulky.feederconnector.helper.volley;

import android.content.Context;

import com.abdymalikmulky.feederconnector.util.ConnectionUtil;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by 488 on 21/09/2016.
 */
public class VolleyHelper {
    private Context mContext;

    public VolleyHelper(Context context) {
        mContext = context;
    }

    public void getResponseFeeder(final int method, final String url, final String token, final Map<String, String> postParam, final VolleyCallback callback, final boolean withWait) {
        Timber.d("url %s", url);
        Timber.d("token %s", token);

        RequestQueue queue = SingletonRequestQueue.getInstance(mContext).getRequestQueue();
        StringRequest strreq = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Timber.d("Response", result);

                JSONObject response = null;
                String id = "";
                if (withWait) {
                    try {
                        response = new JSONObject(result);
                        id = response.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        String urlWait = url + "?id=" + id;
                        getResponseWaitFeeder(urlWait, token, callback);
                    }
                } else {
                    callback.onSuccessResponse(result);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                callback.onFailedResponse(e.toString());

                //disconnect
                ConnectionUtil.disconnect(mContext);
            }
        }) {
            // set headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Token", token);

                return params;
            }

            //set param
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pars = new HashMap<String, String>();
                pars = postParam;
                Timber.d("Param : %s", pars.toString());
                return pars;
            }
        };
        SingletonRequestQueue.getInstance(mContext).addToRequestQueue(strreq);
    }

    public void getResponseWaitFeeder(String url, final String token, final VolleyCallback callback) {
        RequestQueue queue = SingletonRequestQueue.getInstance(mContext).getRequestQueue();
        Timber.d("urlWait : %s", url);
        StringRequest strreq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String Response) {
                Timber.d("Res : %s", Response);
                callback.onSuccessResponse(Response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Timber.e("ERROR %s", e.toString());

                callback.onFailedResponse(e.toString());

                //disconnect
                ConnectionUtil.disconnect(mContext);
            }
        }) {
            // set headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Token", token);

                return params;
            }
        };
        SingletonRequestQueue.getInstance(mContext).addToRequestQueue(strreq);
    }

}
