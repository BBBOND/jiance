package com.kim.jiance.units;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.kim.jiance.R;
import com.kim.jiance.content.App;
import com.kim.jiance.content.MyURL;
import com.kim.jiance.login.LoginActivity;
import com.kim.jiance.model.more.SimpleFireControlMess;
import com.kim.jiance.unit.UnitActivity;
import com.kim.jiance.utils.HttpUtil;
import com.kim.jiance.view.LoadListView;

import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 单位列表
 * Created by 伟阳 on 2015/11/17.
 */
public class UnitListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, LoadListView.LoadListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.image_search_back)
    ImageView imageSearchBack;
    @Bind(R.id.edit_text_search)
    EditText editTextSearch;
    @Bind(R.id.image_clear_search)
    ImageView imageClearSearch;
    @Bind(R.id.card_search)
    RelativeLayout cardSearch;
    @Bind(R.id.list)
    LoadListView list;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private int backKeyPressedTime = 0;
    private UnitAdapter adapter;
    private List<SimpleFireControlMess> messList = null;
    private List<SimpleFireControlMess> searchList = null;
    private int currentPage = 0;
    private static final int PAGESIZE = 15;

    Handler getUnitListHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String unitListStr = (String) msg.obj;
            if (!unitListStr.equals("null") && unitListStr != null) {
                messList = JSON.parseArray(unitListStr, SimpleFireControlMess.class);
                adapter = new UnitAdapter(UnitListActivity.this, R.layout.item_common, messList);
                list.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
                currentPage = 0;
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(toolbar, "数据获取失败,请稍后重试!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                refreshLayout.setRefreshing(false);
                return false;
            }
        }
    });

    Handler loadMoreHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String unitListStr = (String) msg.obj;
            if (!unitListStr.equals("null") && unitListStr != null) {
                List<SimpleFireControlMess> newList = JSON.parseArray(unitListStr, SimpleFireControlMess.class);
                if (newList.size() < PAGESIZE)
                    currentPage--;
                messList.removeAll(newList);
                messList.addAll(newList);
                adapter.notifyDataSetChanged();
                currentPage = 0;
                list.loadComplete();
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(list, "没有更多数据了！", Snackbar.LENGTH_SHORT);
                snackbar.show();
                list.loadComplete();
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

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.BLUE);
        list.setOnItemClickListener(this);
        list.setLoadListener(this);

        imageSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintSearch();
                editTextSearch.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        imageClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSearch.setText("");
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (messList != null) {
                    searchList = new ArrayList<SimpleFireControlMess>();
                    for (SimpleFireControlMess simpleFireControlMess : messList) {
                        if (simpleFireControlMess.getUnitname().contains(s)) {
                            searchList.add(simpleFireControlMess);
                        }
                    }
                    adapter = new UnitAdapter(UnitListActivity.this, R.layout.item_common, searchList);
                    list.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        refreshLayout.setRefreshing(true);
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
                showSearch();
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
                map.put("pageNum", 0);
                map.put("pageSize", PAGESIZE);
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

    public void loadMore(final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("pageNum", page);
                map.put("pageSize", PAGESIZE);
                Log.d("UnitListActivity", " loadMore:" + JSON.toJSONString(map));
                try {
                    String unitListStr = HttpUtil.get(UnitListActivity.this, MyURL.MESSAGEOFEQUIPSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = unitListStr;
                    loadMoreHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("UnitListActivity", "获取单位列表连接失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    loadMoreHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("UnitListActivity", "获取单位列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    loadMoreHandler.sendMessage(message);
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !toolbar.isShown() && cardSearch.isShown()) {
            hintSearch();
        } else if (keyCode == KeyEvent.KEYCODE_BACK && backKeyPressedTime == 0) {
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

    public void showSearch() {
        if (toolbar.isShown() && !cardSearch.isShown()) {
            toolbar.setVisibility(View.INVISIBLE);
            cardSearch.setVisibility(View.VISIBLE);
            editTextSearch.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void hintSearch() {
        if (!toolbar.isShown() && cardSearch.isShown()) {
            cardSearch.setVisibility(View.INVISIBLE);
            toolbar.setVisibility(View.VISIBLE);
            if (messList != null) {
                adapter = new UnitAdapter(UnitListActivity.this, R.layout.item_common, messList);
                list.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onLoad() {
        loadMore(++currentPage);
    }
}
