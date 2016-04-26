package com.kim.jiance.welcome;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.kim.jiance.R;
import com.kim.jiance.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 伟阳 on 2015/11/15.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Bind(R.id.welcome_image)
    ImageView welcomeImage;
    @Bind(R.id.welcome_text)
    TextView welcomeText;
    @Bind(R.id.welcome_version)
    TextView welcomeVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        init();
    }

    public void init() {
        welcomeVersion.setText(getVersion());
        new Handler().postAtTime(new toLogin(), SystemClock.uptimeMillis() + 2000);
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    private String getVersion() {
        String version = "Version";
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            version += info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    class toLogin implements Runnable {

        @Override
        public void run() {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
