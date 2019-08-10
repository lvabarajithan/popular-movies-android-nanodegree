package com.popularmovies.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.util.Consumer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Abarajithan
 */
public class InternetCheck extends AsyncTask<Void, Void, Boolean> {

    private ConnectivityManager manager;
    private Consumer<Boolean> consumer;

    public InternetCheck(ConnectivityManager manager, Consumer<Boolean> consumer) {
        this.manager = manager;
        this.consumer = consumer;
    }

    @Override
    protected void onPreExecute() {
        if (manager == null) {
            cancel(true);
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            cancel(true);
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            socket.close();
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        this.consumer.accept(result);
    }

}
