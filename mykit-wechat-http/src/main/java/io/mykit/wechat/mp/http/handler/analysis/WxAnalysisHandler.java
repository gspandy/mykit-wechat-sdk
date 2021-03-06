package io.mykit.wechat.mp.http.handler.analysis;

import io.mykit.wechat.mp.beans.json.analysis.resp.api.WxAPISummaryAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.api.WxAPISummaryHourAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.msg.upstream.WxMsgUpstreamAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.msg.upstream.dist.WxMsgUpstreamDistAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.msg.upstream.hour.WxMsgUpstreamHourAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.msg.upstream.month.WxMsgUpstreamMonthAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.msg.upstream.week.WxMsgUpstreamWeekAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.article.summary.WxArticleSummaryAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.article.total.WxNewsArticleTotalAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.article.user.read.WxNewsUserReadAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.article.user.read.hour.WxNewsUserReadHourAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.article.user.share.WxNewsUserShareAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.news.article.user.share.hour.WxNewsUserShareHourAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.req.WxAnalysisDate;
import io.mykit.wechat.mp.beans.json.analysis.resp.user.WxUserCumulateAnalysisResp;
import io.mykit.wechat.mp.beans.json.analysis.resp.user.WxUserSummaryAnalysisResp;
import io.mykit.wechat.mp.config.LoadProp;
import io.mykit.wechat.mp.http.base.HttpConnectionUtils;
import io.mykit.wechat.mp.http.handler.base.BaseHandler;
import io.mykit.wechat.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: liuyazhuang
 * @Date: 2018/8/1 14:57
 * @Description: 数据分析能力
 */
@Slf4j
public class WxAnalysisHandler extends BaseHandler {


    /**
     * 获取用户增减数据,	最大时间跨度: 7
     * @param appid appid
     * @param secret secret
     * @param wxUserAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *     "list": [
     *         {
     *             "ref_date": "2014-12-07",
     *             "user_source": 0,
     *             "new_user": 0,
     *             "cancel_user": 0
     *         }//后续还有ref_date在begin_date和end_date之间的数据
     *     ]
     * }
     * @throws Exception
     */
    public static WxUserSummaryAnalysisResp getUserSummary(String appid, String secret, WxAnalysisDate wxUserAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_DATACUBE_GETUSERSUMMARY), wxUserAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret) ,null, HttpConnectionUtils.TYPE_STREAM), WxUserSummaryAnalysisResp.class);
    }

    /**
     * 获取累计用户数据, 最大时间跨度7
     * @param appid appid
     * @param secret secret
     * @param wxUserAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     *
     * {
     *     "list": [
     *         {
     *             "ref_date": "2014-12-07",
     *             "cumulate_user": 1217056
     *         }, //后续还有ref_date在begin_date和end_date之间的数据
     *     ]
     * }
     * @throws Exception
     */
    public static WxUserCumulateAnalysisResp getUserCumulate(String appid, String secret, WxAnalysisDate wxUserAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_DATACUBE_GETUSERCUMULATE), wxUserAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret) ,null, HttpConnectionUtils.TYPE_STREAM), WxUserCumulateAnalysisResp.class);
    }


    /**
     * 获取图文群发每日数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *     "list": [
     *         {
     *             "ref_date": "2014-12-08",
     *             "msgid": "10000050_1",
     *             "title": "12月27日 DiLi日报",
     *             "int_page_read_user": 23676,
     *             "int_page_read_count": 25615,
     *             "ori_page_read_user": 29,
     *             "ori_page_read_count": 34,
     *             "share_user": 122,
     *             "share_count": 994,
     *             "add_to_fav_user": 1,
     *             "add_to_fav_count": 3
     *         }
     *       //后续会列出该日期内所有被阅读过的文章（仅包括群发的文章）在当天的阅读次数等数据
     *     ]
     * }
     * @throws Exception
     */
    public static WxArticleSummaryAnalysisResp getArticleSummary(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_NEWS_ARTICLE_SUMMARY), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxArticleSummaryAnalysisResp.class);
    }

    /**
     * 获取图文群发总数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     *
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-14",
     *            "msgid": "202457380_1",
     *            "title": "马航丢画记",
     *            "details": [
     *                {
     *                    "stat_date": "2014-12-14",
     *                    "target_user": 261917,
     *                    "int_page_read_user": 23676,
     *                    "int_page_read_count": 25615,
     *                    "ori_page_read_user": 29,
     *                    "ori_page_read_count": 34,
     *                    "share_user": 122,
     *                    "share_count": 994,
     *                    "add_to_fav_user": 1,
     *                    "add_to_fav_count": 3,
     *                     "int_page_from_session_read_user": 657283,
     *                     "int_page_from_session_read_count": 753486,
     *                     "int_page_from_hist_msg_read_user": 1669,
     *                     "int_page_from_hist_msg_read_count": 1920,
     *                     "int_page_from_feed_read_user": 367308,
     *                     "int_page_from_feed_read_count": 433422,
     *                     "int_page_from_friends_read_user": 15428,
     *                     "int_page_from_friends_read_count": 19645,
     *                     "int_page_from_other_read_user": 477,
     *                     "int_page_from_other_read_count": 703,
     *                     "feed_share_from_session_user": 63925,
     *                     "feed_share_from_session_cnt": 66489,
     *                     "feed_share_from_feed_user": 18249,
     *                     "feed_share_from_feed_cnt": 19319,
     *                     "feed_share_from_other_user": 731,
     *                     "feed_share_from_other_cnt": 775
     *                }, //后续还会列出所有stat_date符合“ref_date（群发的日期）到接口调用日期”（但最多只统计7天）的数据
     *            ]
     *        },//后续还有ref_date（群发的日期）在begin_date和end_date之间的群发文章的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxNewsArticleTotalAnalysisResp getArticleTotal(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_NEWS_ARTICLE_TOTAL), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxNewsArticleTotalAnalysisResp.class);
    }


    /**
     * 获取图文统计数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "int_page_read_user": 45524,
     *            "int_page_read_count": 48796,
     *            "ori_page_read_user": 11,
     *            "ori_page_read_count": 35,
     *            "share_user": 11,
     *            "share_count": 276,
     *            "add_to_fav_user": 5,
     *            "add_to_fav_count": 15
     *        }, //后续还有ref_date在begin_date和end_date之间的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxNewsUserReadAnalysisResp getUserRead(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_NEWS_USER_READ), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxNewsUserReadAnalysisResp.class);
    }

    /**
     * 获取图文统计分时数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     *{
     *    {
     *    "list": [
     *        {
     *            "ref_date": "2015-07-14",
     *            "ref_hour": 0,
     *            "user_source": 0,
     *            "int_page_read_user": 6391,
     *            "int_page_read_count": 7836,
     *            "ori_page_read_user": 375,
     *            "ori_page_read_count": 440,
     *            "share_user": 2,
     *            "share_count": 2,
     *            "add_to_fav_user": 0,
     *            "add_to_fav_count": 0
     *        },
     *        {
     *            "ref_date": "2015-07-14",
     *            "ref_hour": 0,
     *            "user_source": 1,
     *            "int_page_read_user": 1,
     *            "int_page_read_count": 1,
     *            "ori_page_read_user": 0,
     *            "ori_page_read_count": 0,
     *            "share_user": 0,
     *            "share_count": 0,
     *            "add_to_fav_user": 0,
     *            "add_to_fav_count": 0
     *        },
     *        {
     *            "ref_date": "2015-07-14",
     *            "ref_hour": 0,
     *            "user_source": 2,
     *            "int_page_read_user": 3,
     *            "int_page_read_count": 3,
     *            "ori_page_read_user": 0,
     *            "ori_page_read_count": 0,
     *            "share_user": 0,
     *            "share_count": 0,
     *            "add_to_fav_user": 0,
     *            "add_to_fav_count": 0
     *        },
     *        {
     *            "ref_date": "2015-07-14",
     *            "ref_hour": 0,
     *            "user_source": 4,
     *            "int_page_read_user": 42,
     *            "int_page_read_count": 100,
     *            "ori_page_read_user": 0,
     *            "ori_page_read_count": 0,
     *            "share_user": 0,
     *            "share_count": 0,
     *            "add_to_fav_user": 0,
     *            "add_to_fav_count": 0
     *        }
     *      //后续还有ref_hour逐渐增大,以列举1天24小时的数据
     *    ]
     * }
     *
     * @throws Exception
     */
    public static WxNewsUserReadHourAnalysisResp getUserReadHour(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_NEWS_USER_READ_HOUR), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxNewsUserReadHourAnalysisResp.class);
    }


    /**
     * 获取图文分享转发数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "share_scene": 1,
     *            "share_count": 207,
     *            "share_user": 11
     *        },
     *        {
     *            "ref_date": "2014-12-07",
     *            "share_scene": 5,
     *            "share_count": 23,
     *            "share_user": 11
     *        }//后续还有不同share_scene（分享场景）的数据，以及ref_date在begin_date和end_date之间的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxNewsUserShareAnalysisResp getUserShare(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_NEWS_USER_SHARE), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret),null, HttpConnectionUtils.TYPE_STREAM), WxNewsUserShareAnalysisResp.class);
    }

    /**
     * 获取图文分享转发分时数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "ref_hour": 1200,
     *            "share_scene": 1,
     *            "share_count": 72,
     *            "share_user": 4
     *        }//后续还有不同share_scene的数据，以及ref_hour逐渐增大的数据。由于最大时间跨度为1，所以ref_date此处固定
     *    ]
     * }
     * @throws Exception
     */
    public static WxNewsUserShareHourAnalysisResp getUserShareHour(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_NEWS_USER_SHARE_HOUR), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxNewsUserShareHourAnalysisResp.class);
    }

    /**
     * 获取消息发送概况数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     *   {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "msg_type": 1,
     *            "msg_user": 282,
     *            "msg_count": 817
     *        }//后续还有同一ref_date的不同msg_type的数据，以及不同ref_date（在时间范围内）的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxMsgUpstreamAnalysisResp getUpstreamMsg(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MSG_USTREAM_MSG), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxMsgUpstreamAnalysisResp.class);
    }

    /**
     * 获取消息分送分时数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "ref_hour": 0,
     *            "msg_type": 1,
     *            "msg_user": 9,
     *            "msg_count": 10
     *        }//后续还有同一ref_hour的不同msg_type的数据，以及不同ref_hour的数据，ref_date固定，因为最大时间跨度为1
     *    ]
     * }
     * @throws Exception
     */
    public static WxMsgUpstreamHourAnalysisResp getUpstreamMsgHour(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MSG_USTREAM_MSG_HOUR), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxMsgUpstreamHourAnalysisResp.class);
    }


    /**
     * 获取消息发送周数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-08",
     *            "msg_type": 1,
     *            "msg_user": 16,
     *            "msg_count": 27
     *        }    //后续还有同一ref_date下不同msg_type的数据，及不同ref_date的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxMsgUpstreamWeekAnalysisResp getUpStreamMsgWeek(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MSG_UPSTREAM_MSG_WEEK), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxMsgUpstreamWeekAnalysisResp.class);
    }

    /**
     * 获取消息发送月数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-11-01",
     *            "msg_type": 1,
     *            "msg_user": 7989,
     *            "msg_count": 42206
     *        }//后续还有同一ref_date下不同msg_type的数据，及不同ref_date的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxMsgUpstreamMonthAnalysisResp getUpstreamMsgMonth(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MSG_UPSTREAM_MSG_MONTH), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxMsgUpstreamMonthAnalysisResp.class);
    }


    /**
     * 获取消息发送分布数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "count_interval": 1,
     *            "msg_user": 246
     *        }//后续还有同一ref_date下不同count_interval的数据，及不同ref_date的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxMsgUpstreamDistAnalysisResp getUpstreamMsgDist(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MSG_UPSTREAM_DIST), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxMsgUpstreamDistAnalysisResp.class);
    }

    /**
     * 获取消息发送分布周数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "count_interval": 1,
     *            "msg_user": 246
     *        }//后续还有同一ref_date下不同count_interval的数据，及不同ref_date的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxMsgUpstreamDistAnalysisResp getUpstreamMsgDistWeek(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MSG_UPSTREAM_DIST_WEEK), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxMsgUpstreamDistAnalysisResp.class);
    }

    /**
     * 获取消息发送分布月数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "count_interval": 1,
     *            "msg_user": 246
     *        }//后续还有同一ref_date下不同count_interval的数据，及不同ref_date的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxMsgUpstreamDistAnalysisResp getUpstreamMsgDistMonth(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_MSG_UPSTREAM_DIST_MONTH), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxMsgUpstreamDistAnalysisResp.class);
    }


    /**
     * 获取接口分析数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-07",
     *            "callback_count": 36974,
     *            "fail_count": 67,
     *            "total_time_cost": 14994291,
     *            "max_time_cost": 5044
     *        }//后续还有不同ref_date（在begin_date和end_date之间）的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxAPISummaryAnalysisResp getInterfaceSummary(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEXIN_API_ANALYSIS_SUMMARY), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxAPISummaryAnalysisResp.class);
    }


    /**
     * 获取接口分析分时数据
     * @param appid appid
     * @param secret secret
     * @param wxAnalysisDate
     * {
     *     "begin_date": "2014-12-02",
     *     "end_date": "2014-12-07"
     * }
     * @return
     * {
     *    "list": [
     *        {
     *            "ref_date": "2014-12-01",
     *            "ref_hour": 0,
     *            "callback_count": 331,
     *            "fail_count": 18,
     *            "total_time_cost": 167870,
     *            "max_time_cost": 5042
     *        }//后续还有不同ref_hour的数据
     *    ]
     * }
     * @throws Exception
     */
    public static WxAPISummaryHourAnalysisResp getInterfaceSummaryHour(String appid, String secret, WxAnalysisDate wxAnalysisDate) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEXIN_API_ANALYSIS_SUMMARY_HOUR), wxAnalysisDate.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxAPISummaryHourAnalysisResp.class);
    }
}
