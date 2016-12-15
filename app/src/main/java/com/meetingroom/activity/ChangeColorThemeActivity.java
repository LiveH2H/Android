package com.meetingroom.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.itutorgroup.h2hconference.H2HConference;
import com.meetingroom.adapter.ChooseColorAdapter;
import com.meetingroom.bean.SettingConfig;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.List;

import itutorgroup.h2h.utils.ViewUtil;
import itutorgroup.h2h.R;

public class ChangeColorThemeActivity extends MeetingRoomBaseActivity {
    private ImageView ivBack;
    private GridView gv;
    private ChooseColorAdapter adapter;
    private List<String> colors;
    @Override
    protected void initDatas() {

    }

    @Override
    protected int setContent() {
        return R.layout.activity_change_color_theme;
    }

    @Override
    protected void initView() {
        gv = ViewUtil.findViewById(this,R.id.gv_color);
        colors = Arrays.asList(getResources().getStringArray(R.array.theme_colors));
        adapter = new ChooseColorAdapter(context,colors,R.layout.view_color_select,0);
        gv.setAdapter(adapter);
        ivBack = ViewUtil.findViewById(this,R.id.iv_back);
    }

    @Override
    protected void addListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void ok(View view){
        List<SettingConfig> configs = DataSupport.findAll(SettingConfig.class);
        for(SettingConfig config : configs){
            if(TextUtils.equals(config.getMeetingId(), H2HConference.getInstance().getMeetingId())&&
                    TextUtils.equals(config.getUserId(), H2HConference.getInstance().getLocalUserName())){
                config.setThemeColor(colors.get(adapter.position));
                config.save();
                findViewById(R.id.rl_title).setBackgroundColor(Color.parseColor(colors.get(adapter.position)));
                break;
            }
        }
    }

}
