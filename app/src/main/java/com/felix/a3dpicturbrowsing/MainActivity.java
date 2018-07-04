package com.felix.a3dpicturbrowsing;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.felix.a3dpicturbrowsing.adapter.GalleryAdapter;
import com.felix.a3dpicturbrowsing.view.GalleryFlow;


/**
 * 3D浏览图片
 * Created by Felix.Zhong on 2015/2/9
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GalleryFlow galleryFlow = findViewById(R.id.gallery_flow);
        int[] imageIds = new int[]{R.drawable.a1, R.drawable.a2, R.drawable.a3,
                R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7,
                R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11};
        GalleryAdapter adapter = new GalleryAdapter(this, imageIds);

        // 创建倒影图片
        adapter.createRefectedBitmap();

        galleryFlow.setAdapter(adapter);
        galleryFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("aaaaaaaaaaaaaaaaaa", "i = " + i);
            }
        });
    }
}
