package com.kondenko.yamblzweather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 22.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

    @Mock
    Context context;

    @Mock
    ConnectivityManager connectivityManager;


    @Mock
    NetworkInfo networkInfo;

    @Before
    public void setUp(){
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
    }

    @Test
    public void mustFetchConnectivity() throws Exception {
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(null);
        assertEquals(Utils.isOnline(context), false);

        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(false);
        assertEquals(Utils.isOnline(context), false);

        when(networkInfo.isConnected()).thenReturn(true);
        assertEquals(Utils.isOnline(context), true);
    }
    

}