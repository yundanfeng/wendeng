package cn.ipanel.wendeng.service.global;

import cn.ipanel.wendeng.service.annotation.MyInterfaceRequestParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author luzh
 */
@Slf4j
public class Globals {

    public static final String API_TV = "TV接口";
    public static final String API_BACK = "后台接口";

    public static final String COMMON_SEPARATOR = ",";

    // 设置登录session过期时间，单位为秒
    public static final int GLOBAL_LOGIN_SESSION_EXPIRE = 60 * 30;

    public static ZoneOffset getSystemDefaultZoneOffSet() {
        return Clock.systemDefaultZone().getZone().getRules().getOffset(Instant.now());
    }

    public static final DateTimeFormatter COMMON_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter COMMON_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter COMMON_DATE_FORMATTER_ZH = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

    public static Long convertTime(String time) throws Exception {
        if (isBlank(time)) {
            return 0L;
        }
        if (time.length() == 16) {
            time += ":00";
        } else if (time.length() == 10) {
            time += " 00:00:00";
        } else if (time.length() != 19) {
            throw new Exception("错误的日期格式-" + time);
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(time, COMMON_FORMATTER);
            return dateTime.toInstant(ZonedDateTime.now().getOffset()).toEpochMilli();
        } catch (Exception e) {
            throw new Exception("转换日期错误-" + time);
        }
    }

    /**
     * utc时间毫秒值时间转成格式化日期
     *
     * @param dateTime 时间
     * @return 日期
     */
    private static String longDateTimeFormatter(Long dateTime) {
        try {
            return COMMON_FORMATTER.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), getSystemDefaultZoneOffSet()));
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * utc时间毫秒值时间转成格式化日期
     *
     * @param dateTime 时间
     * @return 日期
     */
    public static String formatterDateTime(Long dateTime) {
        return Optional.ofNullable(dateTime).map(Globals::longDateTimeFormatter).orElse("---");
    }

    /**
     * 检查接口输入
     *
     * @param object req类
     * @throws RequestParamErrorException e
     * @throws IllegalAccessException     e
     */
    public static void checkParams(Object object) throws RequestParamErrorException, IllegalAccessException {
        MyInterfaceRequestParams myInterfaceRequestParams = object.getClass().getAnnotation(MyInterfaceRequestParams.class);
        if (null == myInterfaceRequestParams) {
            return;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ApiModelProperty modelProperty = field.getAnnotation(ApiModelProperty.class);
            if (null == modelProperty) {
                continue;
            }
            if (modelProperty.required()) {
                if (isEmpty(field.get(object))
                        || (field.get(object) instanceof String && isBlank(field.get(object).toString()))
                        || (field.getType().isAssignableFrom(List.class) && ((List) field.get(object)).isEmpty())) {
                    throw new RequestParamErrorException(StringUtils.isEmpty(modelProperty.value()) ? field.getName() : modelProperty.value() + "为必填");
                }
            }
            // 如果字段不为空，且该字段类型为class并且有MyInterfaceRequestParams注解，对该字段进行判断
            if (null != field.get(object)) {
                Type genericType = field.getGenericType();
                // 如果是泛型参数的类型
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    // 先取泛型参数的类型，判断是否有ApiModel注解
                    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                    MyInterfaceRequestParams myInterfaceRequestParams1 = genericClazz.getAnnotation(MyInterfaceRequestParams.class);
                    if (null != myInterfaceRequestParams1) {
                        // 如果是list，遍历判断每个对象的值是否满足要求
                        if (field.getType().isAssignableFrom(List.class)) {
                            List list = (List) field.get(object);
                            for (Object obj : list) {
                                checkParams(obj);
                            }
                        }
                    }
                } else {
                    Class<?> clazz = field.get(object).getClass();
                    MyInterfaceRequestParams myInterfaceRequestParams1 = clazz.getAnnotation(MyInterfaceRequestParams.class);
                    if (null != myInterfaceRequestParams1) {
                        checkParams(field.get(object));
                    }
                }
            }
        }
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Throwable getOriginException(Throwable e) {
        while (e.getCause() != null) {
            e = e.getCause();
        }
        return e;
    }

    public static void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<Predicate> convertSearch(CriteriaBuilder criteriaBuilder, Root<?> root, String searchValue, String searchColumns, List<String> entityColumns, List<Predicate> predicateList) {
        if (!Globals.isBlank(searchValue) && !Globals.isBlank(searchColumns)) {
            List<Predicate> searchList = new ArrayList<>();
            List<String> searchFields = Arrays.asList(searchColumns.split(","));
            searchFields.stream()
                    .filter(entityColumns::contains)
                    .forEach(field -> searchList.add(criteriaBuilder.like(root.get(field), "%" + searchValue + "%")));
            if (!searchList.isEmpty()) {
                predicateList.add(criteriaBuilder.or(searchList.toArray(new Predicate[1])));
            }
        }
        return predicateList;
    }

    /**
     * 检查输入字段
     *
     * @param obj          检查对象
     * @param checkIsEmpty 是否检查为空
     * @param length       长度
     * @param max          最大值
     * @param min          最小值
     * @param paramName    提示名称
     * @throws RequestParamErrorException e
     */
    public static void checkRequestParam(Object obj, Boolean checkIsEmpty, Integer length, Integer max, Integer min,
                                         String paramName) throws RequestParamErrorException {
        if (null == obj) {
            throw new RequestParamErrorException(paramName + "不能为空");
        }
        if (checkIsEmpty && StringUtils.isEmpty(obj)) {
            throw new RequestParamErrorException(paramName + "不能为空");
        }
        if (obj instanceof List<?> && checkIsEmpty) {
            if (((List<?>) obj).isEmpty()) {
                throw new RequestParamErrorException(paramName + "不能为空");
            }
            if (length != null && ((List<?>) obj).size() != length) {
                throw new RequestParamErrorException(paramName + "大小为" + length);
            }
            if (max != null && ((List<?>) obj).size() > max) {
                throw new RequestParamErrorException(paramName + "大小不能超过" + max);
            }
            if (min != null && ((List<?>) obj).size() < min) {
                throw new RequestParamErrorException(paramName + "大小不能小于" + min);
            }
        }
        if (obj instanceof String) {
            if (length != null && !StringUtils.isEmpty(obj) && obj.toString().length() != length) {
                throw new RequestParamErrorException(paramName + "长度为" + length);
            }
            if (max != null && !StringUtils.isEmpty(obj) && obj.toString().length() > max
                    && max > 0) {
                throw new RequestParamErrorException(paramName + "长度不能超过" + max);
            }
            if (min != null && !StringUtils.isEmpty(obj) && obj.toString().length() < min
                    && min > 0) {
                throw new RequestParamErrorException(paramName + "长度不能少于" + min);
            }
        }
        if (obj instanceof Long) {
            if (min != null && (Long) obj < min) {
                throw new RequestParamErrorException(paramName + "不能小于" + min);
            }
            if (max != null && (Long) obj > max) {
                throw new RequestParamErrorException(paramName + "不能大于" + max);
            }
        }
        if (obj instanceof Integer) {
            if (min != null && (Integer) obj < min) {
                throw new RequestParamErrorException(paramName + "不能小于" + min);
            }
            if (max != null && (Integer) obj > max) {
                throw new RequestParamErrorException(paramName + "不能大于" + max);
            }
        }
        if (obj instanceof Double) {
            if (min != null && (Double) obj <= min) {
                throw new RequestParamErrorException(paramName + "不能小于等于" + min);
            }
            if (max != null && ((Double) obj).intValue() > max) {
                throw new RequestParamErrorException(paramName + "不能大于" + max);
            }
        }
    }

    public static String convertDateToChinese(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, Globals.COMMON_DATE_FORMATTER);
            return Globals.COMMON_DATE_FORMATTER_ZH.format(localDate);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public static String timeQuantum() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int hour = localDateTime.getHour();
        if (hour < 1 ) {
            return "子时";
        }
        if (hour < 3) {
            return "丑时";
        }
        if (hour < 5) {
            return "寅时";
        }
        if (hour < 7) {
            return "卯时";
        }
        if (hour < 9) {
            return "辰时";
        }
        if (hour < 11) {
            return "巳时";
        }
        if (hour < 13) {
            return "午时";
        }
        if (hour < 15) {
            return "未时";
        }
        if (hour < 17) {
            return "申时";
        }
        if (hour < 19) {
            return "酉时";
        }
        if (hour < 21) {
            return "戌时";
        }
        if (hour < 23) {
            return "亥时";
        }
        return "子时";
    }

}
