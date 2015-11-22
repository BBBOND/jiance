package com.kim.jiance.unit.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kim.jiance.R;
import com.kim.jiance.model.basicdata.EventInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 伟阳 on 2015/11/21.
 */
public class EventInfoActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.event_unit_name)
    TextView eventUnitName;
    @Bind(R.id.event_unit_address)
    TextView eventUnitAddress;
    @Bind(R.id.event_unit_phone)
    TextView eventUnitPhone;
    @Bind(R.id.event_machine_code)
    TextView eventMachineCode;
    @Bind(R.id.event_alert_machine)
    TextView eventAlertMachine;
    @Bind(R.id.event_alert_source)
    TextView eventAlertSource;
    @Bind(R.id.event_alert_description)
    TextView eventAlertDescription;
    @Bind(R.id.event_happen_place)
    TextView eventHappenPlace;
    @Bind(R.id.event_event_name)
    TextView eventEventName;
    @Bind(R.id.event_happen_time)
    TextView eventHappenTime;
    @Bind(R.id.event_description)
    TextView eventDescription;
    @Bind(R.id.event_handle_user)
    TextView eventHandleUser;
    @Bind(R.id.event_handle_description)
    TextView eventHandleDescription;
    @Bind(R.id.event_handle_time)
    TextView eventHandleTime;
    @Bind(R.id.event_is_handle)
    TextView eventIsHandle;

    private EventInfo eventInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String eventInfoStr = intent.getStringExtra("eventinfo");
        eventInfo = JSON.parseObject(eventInfoStr, EventInfo.class);
        init();
    }

    public void init() {
        eventUnitName.setText(eventInfo.getUnitName());
        eventUnitAddress.setText(eventInfo.getUnitAddr());
        eventUnitPhone.setText(eventInfo.getContactTel());
        eventUnitPhone.setMovementMethod(LinkMovementMethod.getInstance());
        eventMachineCode.setText(eventInfo.getMachineCode());
        eventAlertMachine.setText(eventInfo.getAlertMachine());
        eventAlertSource.setText(eventInfo.getAlertSource());
        eventAlertDescription.setText(eventInfo.getAlertDescription());
        eventHappenPlace.setText(eventInfo.getHappenPlace());
        eventEventName.setText(eventInfo.getEventName());
        eventHappenTime.setText(eventInfo.getHappenTime());
        eventDescription.setText(eventInfo.getEventDescription());
        eventHandleUser.setText(eventInfo.getHandleUserId());
        eventHandleDescription.setText(eventInfo.getHandleDescription());
        eventHandleTime.setText(eventInfo.getHandleTime());
        String text = "";
        CharSequence charSequence = "";
        if (eventInfo.getIsHandle()) {
            text = "<font color='green'>已处理</font>";
            charSequence = Html.fromHtml(text);
        } else {
            text = "<font color='red'>未处理</font>";
            charSequence = Html.fromHtml(text);
        }
        eventIsHandle.setText(charSequence);
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
}
