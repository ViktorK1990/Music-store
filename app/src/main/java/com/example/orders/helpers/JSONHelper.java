package com.example.orders.helpers;

import android.content.Context;

import com.example.orders.models.Cart;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    private static final String FILE_NAME = "shop_cartList.json";

    public static boolean exportToJSON(Context context, List<Cart> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setList(dataList);
        String jsonString = gson.toJson(dataItems);

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static List<Cart> importFromJSON(Context context) {
        Gson gson = new Gson();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        try {
            fis = context.openFileInput(FILE_NAME);
             isr = new InputStreamReader(fis);
            DataItems dataItems = gson.fromJson(isr, DataItems.class);
            return dataItems.getList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private static class DataItems {
        private List<Cart> list;

        public List<Cart> getList() {
            return list;
        }

        public void setList(List<Cart> list) {
            this.list = list;
        }
    }
}
