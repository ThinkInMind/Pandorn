package cn.d1m.pandora.service.impl;

import cn.d1m.pandora.domain.entity.ScheduleHistory;
import cn.d1m.pandora.domain.mybatis.BaseMapper;
import cn.d1m.pandora.domain.mybatis.tkmapper.ScheduleHistoryTKMapper;
import cn.d1m.pandora.service.ScheduleHistoryTKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jone.wang on 2018/9/21.
 * Description:
 */
@Service
public class ScheduleHistoryTKServiceImpl implements ScheduleHistoryTKService {

    @Autowired
    private ScheduleHistoryTKMapper scheduleHistoryTKMapper;

    @Override
    public BaseMapper<ScheduleHistory> getMapper() {
        return this.scheduleHistoryTKMapper;
    }
}
