package app;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco的初始化
        Fresco.initialize(this);
        //磁盘缓存的配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(getCacheDir())
                .setMaxCacheSize(8*1024*1024)
                .build();
        //把磁盘缓存的设置，设置到三级缓存中
        ImagePipelineConfig pipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this,pipelineConfig);

    }
}
