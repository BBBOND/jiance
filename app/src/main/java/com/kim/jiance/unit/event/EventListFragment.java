package com.kim.jiance.unit.event;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.kim.jiance.R;
import com.kim.jiance.content.App;
import com.kim.jiance.content.MyURL;
import com.kim.jiance.model.basicdata.EventInfo;
import com.kim.jiance.utils.HttpUtil;
import com.kim.jiance.view.LoadListView;
import com.kim.jiance.view.MyListView;

import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 伟阳 on 2015/11/18.
 */
public class EventListFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, LoadListView.LoadListener {

    @Bind(R.id.list)
    LoadListView list;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    List<EventInfo> eventList = new ArrayList<>();
    private EventAdapter adapter;
    private String unitId;
    private int currentPage = 0;
    private static final int PAGESIZE = 15;

    Handler getEventListHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String eventListStr = (String) msg.obj;
            if (!eventListStr.equals("null") && eventListStr != null) {
                eventList = JSON.parseArray(eventListStr, EventInfo.class);
                adapter = new EventAdapter(getContext(), R.layout.item_common, eventList);
                list.setAdapter(adapter);
                currentPage = 0;
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(list, "数据获取失败,请稍后重试!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                return false;
            }
        }
    });

    Handler loadMoreHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String eventListStr = (String) msg.obj;
            if (!eventListStr.equals("null") && eventListStr != null) {
                List<EventInfo> newList = JSON.parseArray(eventListStr, EventInfo.class);
                if (newList.size() < PAGESIZE)
                    currentPage--;
                eventList.removeAll(newList);
                eventList.addAll(newList);
                adapter.notifyDataSetChanged();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, null);
        ButterKnife.bind(this, view);

        unitId = App.getUnitID();

        list.setOnItemClickListener(this);
        list.setLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.BLUE);

        refreshLayout.setRefreshing(true);
        refresh();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventInfo eventInfo = adapter.getItem(position);
        Intent intent = new Intent(getContext(), EventInfoActivity.class);
        intent.putExtra("eventinfo", JSON.toJSONString(eventInfo));
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
                map.put("unitid", unitId);
                map.put("pageNum", 0);
                map.put("pageSize", PAGESIZE);
                Log.d("EventListFragment", JSON.toJSONString(map));
                try {
                    String eventListStr = HttpUtil.get(getContext(), MyURL.GETEVENTINFOSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = eventListStr;
                    getEventListHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("EventListFragment", "获取事件列表连接失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getEventListHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("EventListFragment", "获取事件列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getEventListHandler.sendMessage(message);
                }
            }
        }).start();
    }

    public void loadMore(final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("unitid", unitId);
                map.put("pageNum", page);
                map.put("pageSize", PAGESIZE);
                Log.d("EventListFragment", JSON.toJSONString(map));
                try {
                    String eventListStr = HttpUtil.get(getContext(), MyURL.GETEVENTINFOSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = eventListStr;
                    loadMoreHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("EventListFragment", "获取事件列表连接失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    loadMoreHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("EventListFragment", "获取事件列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    loadMoreHandler.sendMessage(message);
                }
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLoad() {
        loadMore(++currentPage);
    }
}
