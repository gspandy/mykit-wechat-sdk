package io.mykit.wechat.mp.beans.json.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.mykit.wechat.mp.beans.base.BaseBean;

/**
 * @Author: liuyazhuang
 * @Date: 2018/7/18 14:37
 * @Description: 从基础的JsonBean
 */

public class BaseJsonBean extends BaseBean {

    private static final long serialVersionUID = -1446274398967377397L;

    public String toString(Object obj){
        return JSONObject.toJSONString(obj);
    }

    public String toJsonArrayString(Object obj){
        return JSONArray.toJSONString(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
