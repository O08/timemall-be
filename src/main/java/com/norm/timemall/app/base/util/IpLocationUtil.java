package com.norm.timemall.app.base.util;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;


@Slf4j
public class IpLocationUtil {
    private static final String DBPATH = "xdb/ip2region.xdb";
    private static byte[] cBuff;

    private static Searcher searcher;

    /**
     * 本地环回地址
     */
    private static final String LOCAL_IP = "127.0.0.1";

    /**
     * 未知
     */
    private static final String UNKNOWN = "unknown";

    static {
        // 1、从 DBPATH 加载整个 xdb 到内存。

        try {
            InputStream is = new PathMatchingResourcePatternResolver().getResources(DBPATH)[0].getInputStream();
            cBuff = IOUtils.toByteArray(is);
            is.close();

        } catch (Exception e) {
            log.error("failed to load content from " + DBPATH + "  \n" + e);
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。

        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.error("failed to load content from  cached searcher+" + e);
        }
    }

    public static String getIpAddress(HttpServletRequest request) {

        if (request == null) {
            return UNKNOWN;
        }

        String ip = request.getHeader("x-forwarded-for");
        if (CharSequenceUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (CharSequenceUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (CharSequenceUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (CharSequenceUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (CharSequenceUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? LOCAL_IP : ip;
    }

    public static String getCityInfo(String ip) {
        try {
            String region = searcher.search(ip);
            if (!region.isEmpty()) {
                region = region.replace("|0", "");
                region = region.replace("0|", "");
            }
            return region;
        } catch (Exception e) {
            log.error("failed to search Ip location: " + ip + "\n reason:" + e);
        }
        return "";
    }

    /**
     * 获取IP属地
     *
     * @param ip
     * @return
     */
    public static String getIpPossession(String ip) {
        String cityInfo = getCityInfo(ip);
        if (!cityInfo.isEmpty()) {
            cityInfo = cityInfo.replace("|", " ");
            String[] cityList = cityInfo.split(" ");
            if (cityList.length > 0) {
                // 国内的显示到具体的市
                if ("中国".equals(cityList[0])) {
                    if (cityList.length > 2) {
                        return cityList[2];
                    }
                    if (cityList.length > 1) {
                        return cityList[1];
                    }
                }
                // 国外显示到国家
                return cityList[0];
            }
        }
        return "未知";
    }

    public static String getIpLocationFromHeader(HttpServletRequest request) {

        String ipAddress = getIpAddress(request);
        return getCityInfo(ipAddress);

    }
}
