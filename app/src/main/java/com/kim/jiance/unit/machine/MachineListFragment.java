package com.kim.jiance.unit.machine;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.kim.jiance.R;
import com.kim.jiance.content.App;
import com.kim.jiance.content.MyURL;
import com.kim.jiance.model.basicdata.MachineInfo;
import com.kim.jiance.utils.HttpUtil;

import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 伟阳 on 2015/11/17.
 */
public class MachineListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnClickListener {
    ListView list;
    SwipeRefreshLayout swiperefreshlayout;

    private List<MachineInfo> messList = new ArrayList<>();
    private MachineAdapter adapter;
    private String unitId;
    private int currentPage = 0;
    private static final int PAGESIZE = 10;

    Handler getMachineListHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String machineListStr = (String) msg.obj;
            if (!machineListStr.equals("null") && machineListStr != null) {
                messList = JSON.parseArray(machineListStr, MachineInfo.class);
                adapter = new MachineAdapter(getContext(), R.layout.item_common, messList);
                list.setAdapter(adapter);
                swiperefreshlayout.setRefreshing(false);
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(list, "数据获取失败,请稍后重试!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                swiperefreshlayout.setRefreshing(false);
                return false;
            }
        }
    });

    Handler loadMoreHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String eventListStr = (String) msg.obj;
            if (!eventListStr.equals("null") && eventListStr != null) {
                List<MachineInfo> newList = JSON.parseArray(eventListStr, MachineInfo.class);
                if (newList.size() < PAGESIZE)
                    currentPage--;
                messList.addAll(newList);
                adapter.notifyDataSetChanged();
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(list, "没有更多数据了！", Snackbar.LENGTH_SHORT);
                snackbar.show();
                return false;
            }
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine_list, null);
        list = (ListView) view.findViewById(R.id.list);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);

        unitId = App.getUnitID();
        swiperefreshlayout.setColorSchemeColors(R.color.snow_white);
        swiperefreshlayout.setOnRefreshListener(this);
        list.setOnItemClickListener(this);

        refresh();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MachineInfo machineInfo = adapter.getItem(position);
        Intent intent = new Intent(getContext(), MachineInfoActivity.class);
        intent.putExtra("machineinfo", JSON.toJSONString(machineInfo));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loadMoreBtn:
                loadMore(++currentPage);
                break;
        }
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
                Log.d("MachineListFragment", JSON.toJSONString(map));
                try {
                    String machineListStr = HttpUtil.get(getContext(), MyURL.GETMACHINEINFOSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = machineListStr;
                    getMachineListHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("MachineListFragment", "获取设备列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getMachineListHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("MachineListFragment", "获取设备列表连接失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    getMachineListHandler.sendMessage(message);
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
                Log.d("MachineListFragment", JSON.toJSONString(map));
                try {
                    String machineListStr = HttpUtil.get(getContext(), MyURL.GETMACHINEINFOSIMPL, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = machineListStr;
                    loadMoreHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("MachineListFragment", "获取设备列表失败");
                    Message message = Message.obtain();
                    message.obj = "null";
                    loadMoreHandler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("MachineListFragment", "获取设备列表连接失败");
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
}
