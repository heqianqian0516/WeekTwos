package model;

import com.google.gson.Gson;

import java.util.Map;

import api.RetrofitManger;
import callBack.MyCallBack;

public class ModelImp implements IModel {
    @Override
    public void onRequestGet(String url, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManger.getInstance().get(url, new RetrofitManger.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o=new Gson().fromJson(data,clazz);
                    if (myCallBack!=null){
                        myCallBack.onSuccess(o);
                    }
                }catch (Exception e){
                    if (myCallBack!=null){
                        myCallBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                 if (myCallBack!=null){
                     myCallBack.onFail(error);
                 }
            }
        });
    }

    @Override
    public void onRequestPost(String url, Map map, Class clazz, MyCallBack myCallBack) {

    }
}
