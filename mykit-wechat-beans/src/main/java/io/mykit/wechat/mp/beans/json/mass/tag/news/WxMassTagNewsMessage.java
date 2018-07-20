package io.mykit.wechat.mp.beans.json.mass.tag.news;

import io.mykit.wechat.mp.beans.json.base.BaseJsonBean;
import io.mykit.wechat.mp.beans.json.mass.tag.WxMassTagFilter;
import io.mykit.wechat.mp.beans.json.mass.tag.WxMassTagMediaId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: liuyazhuang
 * @Date: 2018/7/20 10:22
 * @Description: 图文消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxMassTagNewsMessage extends BaseJsonBean {
    private static final long serialVersionUID = 8774698191192028855L;
    private WxMassTagFilter filter;
    private WxMassTagMediaId mpnews;
    //群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
    private String msgtype;
    //图文消息被判定为转载时，是否继续群发。 1为继续群发（转载），0为停止群发。 该参数默认为0。
    private Integer send_ignore_reprint = 0;

    @Override
    public String toString() {
        return super.toString(this);
    }
}
