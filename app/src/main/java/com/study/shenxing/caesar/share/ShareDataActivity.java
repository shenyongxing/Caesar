package com.study.shenxing.caesar.share;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.study.shenxing.caesar.R;

/**
 * share学习
 */
public class ShareDataActivity extends AppCompatActivity {
    private ShareActionProvider mShareActionProvider ;
    private Button mShareButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_data);

        mShareButton = (Button) findViewById(R.id.share_btn);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = Intent.createChooser(getTestShareIntent(), "请选择要分享的应用") ;
                startActivity(shareIntent);
            }
        });

        setShareIntent(getTestShareIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_data_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        return true ;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private Uri getTestPicUri() {
        Resources res = getResources() ;
        Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(R.drawable.technology) + "/"
                + res.getResourceTypeName(R.drawable.technology) + "/"
                + res.getResourceEntryName(R.drawable.technology));

        return uri ;
    }

    private Intent getTestShareIntent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, getTestPicUri());
        shareIntent.setType("image/jpeg");
        return shareIntent ;
    }
}
