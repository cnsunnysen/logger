package com.mysteelsoft.logger.vo;

import com.esteel.common.vo.AbstractBaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author shanyesen
 */
@ApiModel("日志查询")
public class LoggingQueryVo extends AbstractBaseQuery {

    @ApiModelProperty("日志id")
    private String id;

    @ApiModelProperty("时间范围")
    private String timeRange;

    /**
     * 日期时间开始
     */
    private Date timeBegin;

    /**
     * 日期时间结束
     */
    private Date timeEnd;

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
        List<Date> dates = splitDataStr(timeRange);
        if (dates != null) {
            this.timeBegin = dates.get(0);
            this.timeEnd = getDateAfter(dates.get(1), 1);
        }
        this.timeRange = timeRange;
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    /**
     * 字符串日期范围转换
     *
     * @param dateStr
     * @return
     */
    public static List<Date> splitDataStr(String dateStr) {
        List<Date> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        } else {
            try {
                String[] split = dateStr.split("~");
                for (String string : split) {
                    Date parse = sdf.parse(string.trim());
                    list.add(parse);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return list;
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
