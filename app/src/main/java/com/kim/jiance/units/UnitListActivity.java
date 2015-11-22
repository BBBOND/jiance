package com.kim.jiance.units;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.kim.jiance.R;
import com.kim.jiance.content.App;
import com.kim.jiance.content.MyURL;
import com.kim.jiance.login.LoginActivity;
import com.kim.jiance.unit.UnitActivity;
import com.kim.jiance.unit.machine.MachineListFragment;
import com.kim.jiance.model.more.SimpleFireControlMess;
import com.kim.jiance.utils.HttpUtil;

import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 伟阳 on 2015/11/17.
 */
public class UnitListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    private int backKeyPressedTime = 0;
    private UnitAdapter adapter;

    Handler getUnitListHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String unitListStr = (String) msg.obj;
            if (!unitListStr.equals("null") && unitListStr != null) {
                List<SimpleFireControlMess> messList = JSON.parseArray(unitListStr, SimpleFireControlMess.class);
                adapter = new UnitAdapter(UnitListActivity.this, R.layout.item_common, messList);
                list.setAdapter(adapter);
                swiperefreshlayout.setRefreshing(false);
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(toolbar, "数据获取失败,请稍后重试!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                swiperefreshlayout.setRefreshing(false);
                return false;
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        swiperefreshlayout.setColorSchemeColors(R.color.snow_white);
        swiperefreshlayout.setOnRefreshListener(this);
        list.setOnItemClickListener(this);

        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SimpleFireControlMess simpleFireControlMess = adapter.getItem(position);
        Intent intent = new Intent(UnitListActivity.this, UnitActivity.class);
        App.setUnitID(simpleFireControlMess.getUnitid());
        intent.putExtra("unitcode", simpleFireControlMess.getUnitcode());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userid", App.getUserID());
                Log.d("UnitListActivity", JSON.toJSONString(map));
                try {
                    String unitListStr = HttpUtil.get(UnitListActivity.this, MyURL.MESSAGEOFEQUIPSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = unitListStr;
                    getUnitListHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("UnitListActivity", "获取单位列表连接失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getUnitListHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("UnitListActivity", "获取单位列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getUnitListHandler.sendMessage(message);
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && backKeyPressedTime == 0) {
            Snackbar snackbar = Snackbar.make(toolbar, "再按一次返回键退出程序,或注销", Snackbar.LENGTH_SHORT);
            String str = "<font color='#FFF'>注销</font>";
            CharSequence charSequence = Html.fromHtml(str);
            snackbar.setAction(charSequence, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.setUnitID("");
                    App.setUserID("");
                    App.setRoleName("");
                    Intent intent = new Intent(UnitListActivity.this, LoginActivity.class);
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
        return true;
    }
}
