package com.kim.jiance.unit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.kim.jiance.R;
import com.kim.jiance.content.App;
import com.kim.jiance.login.Login;
import com.kim.jiance.login.LoginActivity;
import com.kim.jiance.unit.event.EventListFragment;
import com.kim.jiance.unit.machine.MachineListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 伟阳 on 2015/11/21.
 */
public class UnitActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private int backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        ButterKnife.bind(this);

        List<String> titleList = new ArrayList<>();
        titleList.add("设备");
        titleList.add("事件");
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.addTab(tab.newTab().setText(titleList.get(0)));
        tab.addTab(tab.newTab().setText(titleList.get(1)));
        tab.setOnTabSelectedListener(this);
        Fragment machineFragment = new MachineListFragment();
        Fragment eventFragment = new EventListFragment();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(machineFragment);
        fragmentList.add(eventFragment);
        viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        viewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        tab.setScrollPosition(position, positionOffset, true);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (App.getRoleName().equals("监控用户")) {
            if (keyCode == KeyEvent.KEYCODE_BACK && backKeyPressedTime == 0) {
                Snackbar snackbar = Snackbar.make(tab, "再按一次返回键退出程序,或注销", Snackbar.LENGTH_SHORT);
                String str = "<font color='#FFF'>注销</font>";
                CharSequence charSequence = Html.fromHtml(str);
                snackbar.setAction(charSequence, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        App.setUnitID("");
                        App.setUserID("");
                        App.setRoleName("");
                        Intent intent = new Intent(UnitActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                snackbar.show();
                backKeyPressedTime = 1;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            backKeyPressedTime = 0;
                        }
                    }
                }.start();
                return false;
            } else if (keyCode == KeyEvent.KEYCODE_BACK && backKeyPressedTime == 1) {
                this.finish();
                System.exit(0);
            }
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        App.setUnitID("");
        super.onDestroy();
    }
}
