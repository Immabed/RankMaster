package com.immabed.rankmaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import com.google.gson.JsonParser;
import com.immabed.rankmaster.rankings.RankTable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by Immabed on 2016-04-10.
 */
public abstract class RankTableIO {

    public static void clearTables(Context context) {
        SharedPreferences mPrefs=context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        ed.clear();
        ed.commit();
    }

    public static void removeTable(Context context, RankTable rankTable) {
        SharedPreferences mPrefs=context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        ed.remove(rankTable.getId());
        ed.commit();
    }

    public static void saveTable(Context context, RankTable rankTable) {
        SharedPreferences mPrefs=context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        ed.putString(rankTable.getId(), objectToString(rankTable));
        ed.commit();
    }

    public static RankTable[] getTables(Context context) {
        SharedPreferences mPrefs=context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = mPrefs.getAll();
        RankTable[] rankTables = new RankTable[allEntries.size()];
        int i = 0;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            rankTables[i] = (RankTable)stringToObject(mPrefs.getString(entry.getKey(), null));//gson.fromJson(parser.parse(mPrefs.getString(entry.getKey(), null)), RankTable.class);
            i++;
        }
        return rankTables;
    }

    static public String objectToString(Serializable object) {
        String encoded = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            encoded = new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encoded;
    }

    @SuppressWarnings("unchecked")
    static public Serializable stringToObject(String string){
        byte[] bytes = Base64.decode(string, 0);
        Serializable object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream( new ByteArrayInputStream(bytes) );
            object = (Serializable)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return object;
    }
}
