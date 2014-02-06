package jp.co.webtech.android.pngopttestapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class ViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);

        Intent intent = getIntent();

        String file = intent.getStringExtra("file");
        if(file != null) {
            Bitmap bitmap = Util.loadBitmapFromAssets(getResources().getAssets(), file);
            if(bitmap != null) {
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            }
        }

        String title = intent.getStringExtra("title");
        Toast.makeText(ViewActivity.this, (title != null) ? title : "null", Toast.LENGTH_SHORT).show();
    }
}
