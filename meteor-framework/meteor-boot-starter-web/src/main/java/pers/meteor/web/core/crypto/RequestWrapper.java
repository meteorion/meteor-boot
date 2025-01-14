package pers.meteor.web.core.crypto;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.*;

/**
 * AKS加密解密请求封装，注意这个对象每次请求都需要new出来
 * 这个类只是负责封装处理的参数
 *
 * @author 钟宗兵
 * @since 1.0.0
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private static final Logger log = LoggerFactory.getLogger(RequestWrapper.class);

    /**
     * 请求体，用于获取参数
     * -- GETTER --
     *  获取请求体
     *
     */
    @Getter
    private String body;
    /**
     * 保存原始Request对象
     */
    private final HttpServletRequest request;
    /**
     * 额外参数可以加到这个里面
     */
    private final Map<String, String[]> parameterMap = new LinkedHashMap<>();

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
        // 如果是文件上传类请求
        if(request instanceof MultipartRequest){
            this.parseBody(request);
        }else {
            // 普通请求将请求体设置到body中去；这样可以多次使用流方式访问请求体
            try (ByteArrayOutputStream data = new ByteArrayOutputStream()) {
                IOUtils.copy(request.getInputStream(), data);
                body = data.toString();
            } catch (IOException e) {
                log.error("复制请求体失败，原因：{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 如果是MultipartRequest，需要解析参数信息
     */
    private void parseBody(HttpServletRequest request) {
        Map<String,Object> parameterMap = new LinkedHashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            String[] values = request.getParameterValues(name);
            parameterMap.put(name, (values !=null && values.length == 1) ? values[0] : values);
        }
        // 将解析出来的参数，转换成JSON并设置到body中保存
        this.body = JSON.toJSONString(parameterMap);
    }

    /**
     * The default behavior of this method is to return getInputStream() on the
     * wrapped request object.
     */
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


    /**
     * 设置请求体，如果是MultipartRequest请求则需要同步保存到参数Map中去
     * @param body 请求体
     */
    public void setBody(String body){
        this.body = body;
        try {
            if(this.request instanceof MultipartRequest){
                this.setParameterMap(JSON.parseObject(body));
            }
        } catch (Exception e) {
            log.error("转换参数异常，参数：{}，异常",body, e);
        }
    }

    /**
     * The default behavior of this method is to return getParameter(String
     * name) on the wrapped request object.
     *
     * @param name /
     */
    @Override
    public String getParameter(String name) {
        String result =  super.getParameter(name);
        // 如果参数获取不到则尝试从参数Map中获取，并且只返回第一个
        if(StringUtils.isBlank(result) && this.parameterMap.containsKey(name)){
            result = this.parameterMap.get(name)[0];
        }
        return result;
    }

    /**
     * The default behavior of this method is to return getParameterMap() on the
     * wrapped request object.
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        // 需要将原有的参数加上新参数 返回
        Map<String,String[]> map = new HashMap<>(super.getParameterMap());
        for(String key: this.parameterMap.keySet()){
            map.put(key, this.parameterMap.get(key));
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * The default behavior of this method is to return
     * getParameterValues(String name) on the wrapped request object.
     *
     * @param name /
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] result =  super.getParameterValues(name);
        if(result == null && this.parameterMap.containsKey(name)){
            result = this.parameterMap.get(name);
        }
        return result;
    }

    /**
     * The default behavior of this method is to return getParameterNames() on
     * the wrapped request object.
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Enumeration<String> parameterNames = super.getParameterNames();
        Set<String> names = new LinkedHashSet<>();
        if(parameterNames !=null){
            while(parameterNames.hasMoreElements()){
                names.add(parameterNames.nextElement());
            }
        }
        // 添加后期设置的参数Map
        if(!this.parameterMap.isEmpty()){
            names.addAll(this.parameterMap.keySet());
        }
        return Collections.enumeration(names);
    }

    /**
     * 设置参数map
     * @param json2map /
     */
    public void setParameterMap(Map<String, Object> json2map) {
        if(json2map != null && !json2map.isEmpty()) {
            for (String key : json2map.keySet()){
                String value = MapUtils.getString(json2map, key);
                if(this.parameterMap.containsKey(key)){
                    this.parameterMap.put(key, ArrayUtils.add(this.parameterMap.get(key), value));
                }else{
                    this.parameterMap.put(key, new String[]{value});
                }
            }
        }
    }
}
