package com.pig.utils;

/**
 * @author Arthas
 * @create 2018/11/1
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Json转换工具类
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 设置默认时间格式
     */
    private static String STANDAR_FOMAT = "yyyy-MM-dd hh:mm:ss";
    /**
     * 静态块配置
     */
    static {
        // 设置对象字段的列入规则
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 设置序列化空bean不报错
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 设置不将date类型转化成timestamps
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 设置日期格式
        objectMapper.setDateFormat(new SimpleDateFormat(STANDAR_FOMAT));
        // 设置如果对象中不存在对应参数，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    /**
     * 对象转Json
     */
    public static <T> String objToStr(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? ((String) obj) : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.warn("Parse Obj to String error", e);
            return null;
        }
    }
    /**
     * 对象转Json（格式化输出）
     */
    public static <T> String objToStrPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? ((String) obj) : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            logger.warn("Parse Obj to String error", e);
            return null;
        }
    }
    /**
     * Json转对象
     */
    public static <T> T strToObj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : (T) objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            logger.warn("Parse String to Obj error", e);
            return null;
        }
    }
    /**
     * Json转对象
     */
    public static <T> T strToObj(String str, TypeReference typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            logger.warn("Parse String to Obj error", e);
            return null;
        }
    }
    /**
     * Json转对象
     */
    public static <T> T strToObj(String str, Class<?> collectionCLass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionCLass, elementClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            logger.warn("Parse String to Obj error", e);
            return null;
        }
    }
}