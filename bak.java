/**
 * @project: cvs-cloud-core
 * Copyright 2017-2027 &copy;西安库连网络科技有限公司 All Rights Reserved 
 *
 */
package kulink.cvscloud.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import kulink.cvscloud.core.utils.StatisticsService.DataType;

/**
 * <p>
 * 拼装字符串统计用
 * </p>
 *
 * @author 太隐于世(348133476@qq.com)
 * @since 1.0
 */
public class LuaRedisUtils {

	static final Log				logger			= LogFactory.getLog(StatisticsServiceImpl.class);
	static final Long				DAY_MILLIS		= 86400000L;
	static final int				DAY_SECONDS		= 86400;
	static Long						days			= 30L;
	static final Long				TIME_ZONE_SPAN	= -28800000L;

	static Calendar					calendar		= Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

	static final SimpleDateFormat	sdf				= new SimpleDateFormat("yyyyMMdd");

	static final String[]			daysHours		= new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
	                                                                 "21", "22", "23", "24" };

	static final String[]			daysCouple		= new String[] { "2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24" };

	static String					hours			= "";
	static String					today			= "";

	static {
		sdf.setCalendar(calendar);
		hours = (calendar.get(Calendar.HOUR_OF_DAY) + 1) + "";
		today = sdf.format(new Date(System.currentTimeMillis()));
		System.out.println("===============");
	}

	public static void setMaxAvg(StringBuffer sb, String maxAvgKey, Long baseTime) {
		setMaxGeneric(sb, DataType.AVG, baseTime);
	}

	public static void setMaxFlowrate(StringBuffer sb, String maxFlowrateKey, Long baseTime) {
		setMaxGeneric(sb, DataType.RATE, baseTime);
	}

	public static void setMaxOrders(StringBuffer sb, String maxOrdersKey, Long baseTime) {
		setMaxGeneric(sb, DataType.ORDERS, baseTime);
	}

	public static void setMaxTurnovers(StringBuffer sb, String maxTurnoversKey, Long baseTime) {
		setMaxGeneric(sb, DataType.TURNOVERS, baseTime);
	}

	public static void setMaxSales(StringBuffer sb, String maxSalesKey, Long baseTime) {
		setMaxGeneric(sb, DataType.SALES, baseTime);
	}

	public static void setMaxVisitors(StringBuffer sb, String maxVisitorsKey, Long baseTime) {
		setMaxGeneric(sb, DataType.VISITORS, baseTime);
	}

	public static void setMaxGeneric(StringBuffer sb, DataType type, Long baseTime) {
		String name = type.name().toLowerCase(), nameUp = name.substring(0, 1).toUpperCase().concat(name.substring(1)), key = "max" + nameUp + "Key";
		sb.append(" local ex_" + name + " = redis.call('EXISTS','" + key + "') \n");
		sb.append(" if ex_" + name + " == 0 then");
		sb.append(" redis.call('HSET','" + key + "','max',0)");
		sb.append(" redis.call('HSET','" + key + "','maxtime'," + baseTime + ")");
		sb.append(" redis.call('HSET','" + key + "','day',d" + name + ")");
		sb.append(" redis.call('HSET','" + key + "','daytime'," + baseTime + ")");
		sb.append(" else ");
		sb.append(" local max_" + name + " =  redis.call('HGET','" + key + "','max')");
		sb.append(" local max_" + name + "_time = redis.call('HGET','" + key + "','maxtime')");
		sb.append(" local day_" + name + " =  redis.call('HGET','" + key + "','day')");
		sb.append(" local day_" + name + "_time = redis.call('HGET','" + key + "','daytime')");
		sb.append(" 	if tonumber(day_" + name + "_time) ~= " + baseTime + " and  tonumber(max_" + name + ") < tonumber(day_" + name + ") then ");
		sb.append(" 		redis.call('HSET','" + key + "','max',day_" + name + ")");
		sb.append(" 		redis.call('HSET','" + key + "','maxtime',day_" + name + "_time)");
		sb.append(" 	end ");
		sb.append(" end ");
		sb.append(" redis.call('HSET','" + key + "','day',d" + name + ")");
		sb.append(" redis.call('HSET','" + key + "','daytime'," + baseTime + ")");
	}

	public static void syncAvgWithKeys(StringBuffer sb, String dayAvgKey, String dailyAvgKey, String totalAvgKey) {

		sb.append(" local havg = 0 local davg = 0 local tavg = 0 ");
		sb.append(
		        " horders = tonumber(horders)  dorders = tonumber(dorders)  hturnovers = tonumber(hturnovers)  dturnovers = tonumber(dturnovers) tturnovers = tonumber(tturnovers) torders = tonumber(torders) ");
		sb.append(" if type(horders) == 'number' and horders > 0 and type(hturnovers) == 'number' and hturnovers > 0 then havg = hturnovers / horders end ");
		sb.append(" if type(dorders) == 'number' and dorders > 0 and type(dturnovers) == 'number' and dturnovers > 0 then davg = dturnovers / dorders end ");
		sb.append(" if type(torders) == 'number' and torders > 0 and type(tturnovers) == 'number' and tturnovers > 0 then tavg = tturnovers / torders end ");
		sb.append(" redis.call('HSET','" + dayAvgKey + "','" + hours + "',havg); ");
		sb.append(" redis.call('EXPIRE','" + dayAvgKey + "'," + 2 * DAY_SECONDS + "); ");
		sb.append(" redis.call('HSET','" + dayAvgKey + "','total', davg); ");
		sb.append(" redis.call('SET','" + dailyAvgKey + "',davg); ");
		sb.append(" redis.call('SET','" + totalAvgKey + "',tavg); ");
		sb.append(" redis.call('EXPIRE','" + dailyAvgKey + "'," + days * DAY_SECONDS + "); ");

	}

	public static void syncOrdersWithKeys(StringBuffer sb, String dayOrdersKey, String dailyOrdersKey, String totalOrdersKey, Object... value) {
		sb.append(" redis.call('HINCRBY','" + dayOrdersKey + "','" + hours + "'," + value[0] + "); ");
		sb.append(" redis.call('HINCRBY','" + dayOrdersKey + "','total'," + value[0] + "); ");
		sb.append(" redis.call('EXPIRE','" + dayOrdersKey + "'," + 2 * DAY_SECONDS + "); ");
		sb.append(" redis.call('INCRBY','" + dailyOrdersKey + "'," + value[0] + "); ");
		sb.append(" redis.call('INCRBY','" + totalOrdersKey + "'," + value[0] + "); ");
		sb.append(" redis.call('EXPIRE','" + dailyOrdersKey + "'," + days * DAY_SECONDS + "); ");
		sb.append(" local horders =  redis.call('HGET','" + dayOrdersKey + "','" + hours + "') ");
		sb.append(" local dorders =  redis.call('GET','" + dailyOrdersKey + "') ");
		sb.append(" local torders = redis.call('GET','" + totalOrdersKey + "') ");
	}

	public static void syncRateWithKeys(StringBuffer sb, String dayOrdersKey, String dailyOrdersKey, String totalOrdersKey) {

	}

	public static void syncSalesWithKeys(StringBuffer sb, String daySalesKey, String dailySalesKey, String totalSalesKey) {
		sb.append("	local ls = KEYS  local rs = {} local t ");
		sb.append(" for i = 1, #ls do ");
		sb.append(" 	local ret = {}  string.gsub(ls[i],'(%d+)%=(%d+)',function (c,b) ret[c]=b end) ");
		sb.append(" 	t = ret ");
		sb.append(" 	for m,n in pairs(ret) do ");
		sb.append("      	rs[m]=n ");
		sb.append("      	redis.call('HINCRBY','" + daySalesKey + "',m,n) ");
		sb.append("      	redis.call('HINCRBY','" + daySalesKey + "','total',n) ");
		sb.append("      	redis.call('HINCRBY','" + dailySalesKey + "',m,n) ");
		sb.append("      	redis.call('HINCRBY','" + dailySalesKey + "','total',n) ");
		sb.append("      	redis.call('HINCRBY','" + totalSalesKey + "',m,n) ");
		sb.append("      	redis.call('HINCRBY','" + totalSalesKey + "','total',n) ");
		sb.append(" 	end ");
		sb.append(" end ");
		sb.append(" redis.call('EXPIRE','" + daySalesKey + ":" + hours + "'," + DAY_SECONDS + "); ");
		sb.append(" redis.call('EXPIRE','" + dailySalesKey + "'," + days * DAY_SECONDS + "); ");
		sb.append(" local dsales = redis.call('HGET','" + dailySalesKey + "','total')");

	}

	public static void syncTurnoversWithKeys(StringBuffer sb, String dayTurnoversKey, String dailyTurnoversKey, String totalTurnoversKey, Object... value) {
		sb.append(" redis.call('HINCRBY','" + dayTurnoversKey + "','" + hours + "'," + value[1] + "); ");
		sb.append(" redis.call('HINCRBY','" + dayTurnoversKey + "','total'," + value[1] + "); ");
		sb.append(" redis.call('EXPIRE','" + dayTurnoversKey + "'," + 2 * DAY_SECONDS + "); ");
		sb.append(" redis.call('INCRBY','" + dailyTurnoversKey + "'," + value[1] + "); ");
		sb.append(" redis.call('INCRBY','" + totalTurnoversKey + "'," + value[1] + "); ");
		sb.append(" redis.call('EXPIRE','" + dailyTurnoversKey + "'," + days * DAY_SECONDS + "); ");
		sb.append(" local hturnovers =  redis.call('HGET','" + dayTurnoversKey + "','" + hours + "') ");
		sb.append(" local dturnovers =  redis.call('GET','" + dailyTurnoversKey + "') ");
		sb.append(" local tturnovers =  redis.call('GET','" + totalTurnoversKey + "') ");

	}

	public static void syncVisitorsWithKeys(StringBuffer sb, String dayVisitorsKey, String dailyVisitorsKey, String totalVisitorsKey) {

		sb.append(" redis.call('HINCRBY','" + dayVisitorsKey + "','" + hours + "',1); ");
		sb.append(" redis.call('HINCRBY','" + dayVisitorsKey + "','total',1); ");
		sb.append(" redis.call('EXPIRE','" + dayVisitorsKey + "'," + 2 * DAY_SECONDS + "); ");
		sb.append(" redis.call('INCRBY','" + dailyVisitorsKey + "',1); ");
		sb.append(" redis.call('INCRBY','" + totalVisitorsKey + "',1); ");
		sb.append(" redis.call('EXPIRE','" + dailyVisitorsKey + "'," + days * DAY_SECONDS + "); ");

	}

	public static void syncDataRef(StringBuffer sb, String prefix, String key, DataType... keys) {

		String keyHolder = "store".equals(prefix) ? ":" + key : "";
		Arrays.asList(keys).forEach(v -> {
			String name = v.name().toLowerCase();
			String dayKey = prefix + "." + today + "." + DataType.AVG.name().toLowerCase() + keyHolder,
			        dailyKey = prefix + ".daily." + DataType.AVG.name().toLowerCase() + keyHolder + ":" + today,
			        totalKey = prefix + ".total." + DataType.AVG.name().toLowerCase() + keyHolder;
			sb.append(" ");
		});

	}

	public static void main(String[] args) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long baseTime = sdf.parse(sdf.format(new Date(System.currentTimeMillis()))).getTime();

		System.out.println(LuaRedisUtils.hours);

	}

}
