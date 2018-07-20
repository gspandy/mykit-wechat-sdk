package io.mykit.wechat.mp.http.media;

import io.mykit.wechat.mp.beans.json.mass.tag.image.WxMassTagImageMessage;
import io.mykit.wechat.mp.beans.json.mass.tag.news.WxMassTagNewsMessage;
import io.mykit.wechat.mp.beans.json.mass.tag.text.WxMassTagTextMessage;
import io.mykit.wechat.mp.beans.json.mass.tag.video.WxMassVideoUploadMessage;
import io.mykit.wechat.mp.beans.json.mass.tag.voice.WxMassTagVoiceMessage;
import io.mykit.wechat.mp.beans.json.medis.WxMediaUploadNews;
import io.mykit.wechat.mp.config.LoadProp;
import io.mykit.wechat.mp.http.base.HttpConnectionUtils;
import io.mykit.wechat.mp.http.constants.WxConstants;
import io.mykit.wechat.mp.http.handler.base.BaseHandler;
import io.mykit.wechat.mp.http.handler.token.AccessTokenHandler;
import io.mykit.wechat.mp.http.wx.WxHttpConnectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @Author: liuyazhuang
 * @Date: 2018/7/20 09:23
 * @Description: 微信多媒体处理类
 */
@Slf4j
public class WxMediaHandler extends BaseHandler {

    /**
     * 上传多媒体文件
     * @param appid appid
     * @param secret secret
     * @param fileType 文件类型，支持image/voice/video/thumb
     * @param filePath 文件在本地存储的绝对路径
     * @return 返回结果，成功：{"create_at":123456,"media_id":"123","type":"image"}  失败返回{"errcode":12345, "errmsg":"errmsg"}
     * @throws Exception
     */
     public static String uploadMediaFile(String appid, String secret, String fileType, String filePath) throws Exception{
        String url = LoadProp.getValue(LoadProp.WEIXIN_BASE_UPLOAD) + "?" + WxConstants.ACCESS_TOKEN + "=" + AccessTokenHandler.getAccessToken(appid, secret) + "&type=" +fileType;
        return WxHttpConnectionUtils.uploadFileToWeixinServer(url, filePath, WxHttpConnectionUtils.TYPE_MEDIA);
     }
    /**
     * 上传多媒体文件
     * @param appid appid
     * @param secret secret
     * @param fileType 文件类型，支持image/voice/video/thumb
     * @param filePath 文件在本地存储的绝对路径
     * @return 返回结果，成功：{"create_at":123456,"media_id":"123","type":"image"}  失败返回{"errcode":12345, "errmsg":"errmsg"}
     * @throws Exception
     */
     public static String uploadMediaFile2(String appid, String secret, String fileType, String filePath) throws Exception{
        String url = LoadProp.getValue(LoadProp.WEIXIN_BASE_UPLOAD_FILE) + "?" + WxConstants.ACCESS_TOKEN + "=" + AccessTokenHandler.getAccessToken(appid, secret) + "&type=" +fileType;
        return WxHttpConnectionUtils.uploadFileToWeixinServer(url, filePath, WxHttpConnectionUtils.TYPE_MEDIA);
     }

    /**
     * 下载多媒体文件
     * @param filePath 存储到本地磁盘上的绝对路径
     * @param mediaId 微信服务器上多媒体文件的id
     * @return File对象
     * @throws Exception
     */
     public static File downloadMediaFile(String appid, String secret, String filePath, String mediaId) throws Exception{
        return WxHttpConnectionUtils.downloadMedia(LoadProp.getValue(LoadProp.WEIXIN_BASE_DOWNLOAD_FILE), filePath, AccessTokenHandler.getAccessToken(appid, secret), mediaId);
     }


    /**
     * 上传图文消息素材
     * @param appid appid
     * @param secret secret
     * @param wxMediaUploadNews 封装的接口请求体
     *                          post数据格式如下：
     *                          {
                             *     "articles": [
                             *         {
                             *             "thumb_media_id": "qI6_Ze_6PtV7svjolgs-rN6stStuHIjs9_DidOHaj0Q-mwvBelOXCFZiq2OsIU-p",
                             *             "author": "xxx",
                             *             "title": "Happy Day",
                             *             "content_source_url": "www.qq.com",
                             *             "content": "content",
                             *             "digest": "digest",
                             *             "show_cover_pic": 1
                             *         },
                             *         {
                             *             "thumb_media_id": "qI6_Ze_6PtV7svjolgs-rN6stStuHIjs9_DidOHaj0Q-mwvBelOXCFZiq2OsIU-p",
                             *             "author": "xxx",
                             *             "title": "Happy Day",
                             *             "content_source_url": "www.qq.com",
                             *             "content": "content",
                             *             "digest": "digest",
                             *             "show_cover_pic": 0
                             *         }
                             *     ]
                            * }
     * @return 成功：：{"create_at":123456,"media_id":"123","type":"image"}  失败返回:{"errcode":12345, "errmsg":"errmsg"}
     * @throws Exception
     */
     public static String uploadMediaNewsFile(String appid, String secret, WxMediaUploadNews wxMediaUploadNews) throws Exception{
        return HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MEDIA_NEWS_UPLOAD), wxMediaUploadNews.toString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM);
     }


    /**
     * 根据标签发送微信图文消息
     * @param appid appid
     * @param secret secret
     * @param wxMassNewsMessage 封装的请求体，格式如下：
     *  {
     *    "filter":{
     *       "is_to_all":false,
     *       "tag_id":2
     *    },
     *    "mpnews":{
     *       "media_id":"123dsdajkasd231jhksad"
     *    },
     *     "msgtype":"mpnews",
     *     "send_ignore_reprint":0
     * }
     * @return 结果数据：
     * {
     *    "errcode":0,
     *    "errmsg":"send job submission success",
     *    "msg_id":34182,
     *    "msg_data_id": 206227730
     * }
     * type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），图文消息为news
     * errcode	错误码
     * errmsg	错误信息
     * msg_id	消息发送任务的ID
     * msg_data_id	消息的数据ID，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。
     * 请注意：在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，仍有可能在后续的发送过程中出现异常情况导致用户未收到消息，如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
     * 错误时微信会返回错误码等信息，请根据错误码查询错误信息
     * @throws Exception
     */
     public static String sendMpnewsMessageByTag(String appid, String secret, WxMassTagNewsMessage wxMassNewsMessage) throws Exception{
        return HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MASS_SENDALL), wxMassNewsMessage.toString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM);
     }

    /**
     * 根据标签发送微信文本消息
     * @param appid appid
     * @param secret secret
     * @param wxMassTagTextMessage 封装的请求体，格式如下：
     *  {
     *    "filter":{
     *       "is_to_all":false,
     *       "tag_id":2
     *    },
     *    "text":{
     *       "content":"CONTENT"
     *    },
     *     "msgtype":"text"
     * }
     * @return 结果数据：
     * {
     *    "errcode":0,
     *    "errmsg":"send job submission success",
     *    "msg_id":34182,
     *    "msg_data_id": 206227730
     * }
     * type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），图文消息为news
     * errcode	错误码
     * errmsg	错误信息
     * msg_id	消息发送任务的ID
     * msg_data_id	消息的数据ID，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。
     * 请注意：在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，仍有可能在后续的发送过程中出现异常情况导致用户未收到消息，如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
     * 错误时微信会返回错误码等信息，请根据错误码查询错误信息
     * @throws Exception
     */
     public static String sendTextMessageByTag(String appid, String secret, WxMassTagTextMessage wxMassTagTextMessage) throws Exception{
        return HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MASS_SENDALL), wxMassTagTextMessage.toString(),getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM);
     }
    /**
     * 语音/音频（注意此处media_id需通过基础支持中的上传下载多媒体文件来得到）
     * @param appid appid
     * @param secret secret
     * @param wxMassTagVoiceMessage 封装的请求体，格式如下：
     *  {
     *    "filter":{
     *       "is_to_all":false,
     *       "tag_id":2
     *    },
     *    "voice":{
     *       "media_id":"123dsdajkasd231jhksad"
     *    },
     *     "msgtype":"voice"
     * }
     * @return 结果数据：
     * {
     *    "errcode":0,
     *    "errmsg":"send job submission success",
     *    "msg_id":34182,
     *    "msg_data_id": 206227730
     * }
     * type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），图文消息为news
     * errcode	错误码
     * errmsg	错误信息
     * msg_id	消息发送任务的ID
     * msg_data_id	消息的数据ID，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。
     * 请注意：在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，仍有可能在后续的发送过程中出现异常情况导致用户未收到消息，如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
     * 错误时微信会返回错误码等信息，请根据错误码查询错误信息
     * @throws Exception
     */
     public static String sendVoiceMessageByTag(String appid, String secret, WxMassTagVoiceMessage wxMassTagVoiceMessage) throws Exception{
         return HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MASS_SENDALL), wxMassTagVoiceMessage.toString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM);
     }

    /**
     * 图片（注意此处media_id需通过基础支持中的上传下载多媒体文件来得到）
     * @param appid appid
     * @param secret secret
     * @param wxMassTagImageMessage 封装的请求体，格式如下：
     *  {
     *    "filter":{
     *       "is_to_all":false,
     *       "tag_id":2
     *    },
     *    "image":{
     *       "media_id":"123dsdajkasd231jhksad"
     *    },
     *     "msgtype":"image"
     * }
     * @return 结果数据：
     * {
     *    "errcode":0,
     *    "errmsg":"send job submission success",
     *    "msg_id":34182,
     *    "msg_data_id": 206227730
     * }
     * type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），图文消息为news
     * errcode	错误码
     * errmsg	错误信息
     * msg_id	消息发送任务的ID
     * msg_data_id	消息的数据ID，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。
     * 请注意：在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，仍有可能在后续的发送过程中出现异常情况导致用户未收到消息，如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
     * 错误时微信会返回错误码等信息，请根据错误码查询错误信息
     * @throws Exception
     */
     public static String sendImageMessageByTag(String appid, String secret, WxMassTagImageMessage wxMassTagImageMessage) throws Exception{
         return HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MASS_SENDALL), wxMassTagImageMessage.toString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM);
     }

    /**
     * 群发消息上传视频素材
     * @param appid appid
     * @param appsecret secret
     * @param wxMassVideoUploadMessage 封装的请求体，格式如下：
     * {
     *   "media_id": "rF4UdIMfYK3efUfyoddYRMU50zMiRmmt_l0kszupYh_SzrcW5Gaheq05p_lHuOTQ",
     *   "title": "TITLE",
     *   "description": "Description"
     * }
     * @return 格式：
     * {
     *   "type":"video",
     *   "media_id":"IhdaAQXuvJtGzwwc0abfXnzeezfO0NgPK6AQYShD8RQYMTtfzbLdBIQkQziv2XJc",
     *   "created_at":1398848981
     * }
     * @throws Exception
     */
     public static String uploadMediaVideo(String appid, String appsecret, WxMassVideoUploadMessage wxMassVideoUploadMessage) throws Exception{
        return HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MEDIA_VIDEO_UPLOAD), wxMassVideoUploadMessage.toString(), getAccessTokenNameValuePairs(appid, appsecret), null, HttpConnectionUtils.TYPE_STREAM);
     }


}
