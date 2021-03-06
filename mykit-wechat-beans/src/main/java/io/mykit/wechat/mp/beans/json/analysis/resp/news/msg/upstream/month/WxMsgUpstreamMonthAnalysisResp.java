package io.mykit.wechat.mp.beans.json.analysis.resp.news.msg.upstream.month;

import io.mykit.wechat.mp.beans.json.analysis.resp.news.msg.upstream.WxMsgUpstreamAnalysisResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuyazhuang
 * @version 1.0.0
 * @date 2018/8/7 14:46
 * @description 获取消息发送月数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxMsgUpstreamMonthAnalysisResp extends WxMsgUpstreamAnalysisResp {
    private static final long serialVersionUID = -379556154713007241L;
}
