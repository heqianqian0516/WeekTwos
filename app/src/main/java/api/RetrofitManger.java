package api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManger {
    private static String BASE_URL="http://172.17.8.100/small/";
    //http://gank.io/api/
    private static String BASE_URLVIEW="http://gank.io/api/";
    private static RetrofitManger manger;
    private final BaseApis baseApis;
    private final BaseApis mbaseApis;

    //单例
    public static RetrofitManger getInstance(){
        if (manger==null){
            synchronized (RetrofitManger.class){
                manger = new RetrofitManger();
            }
        }
        return manger;
    }
    //构造方法
    private RetrofitManger(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.writeTimeout(15,TimeUnit.SECONDS);
        builder.readTimeout(15,TimeUnit.SECONDS);
        builder.connectTimeout(15,TimeUnit.SECONDS);
        builder.addNetworkInterceptor(interceptor);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client=builder.build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        Retrofit retrofitView=new Retrofit.Builder()
                .baseUrl(BASE_URLVIEW)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        mbaseApis = retrofitView.create(BaseApis.class);
        baseApis = retrofit.create(BaseApis.class);
    }
    //get
    public void get(String url, HttpListener httpListener){
        baseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpListener));
        mbaseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpListener));
    }
    private Observer getObserver(final HttpListener httpListener){
        Observer observer= new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                   if (httpListener!=null){
                       httpListener.onFail(e.getMessage());
                   }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data=responseBody.string();
                    if (httpListener!=null){
                        httpListener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (httpListener!=null){
                        httpListener.onFail(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }
    //定义接口
    public interface HttpListener{
        void onSuccess(String data);
        void onFail(String error);
    }
}
