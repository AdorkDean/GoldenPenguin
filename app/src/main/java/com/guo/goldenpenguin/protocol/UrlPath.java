package com.guo.goldenpenguin.protocol;


import com.guo.goldenpenguin.switches.URLParse;

/**
 * 所有网络请求的链接地址
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/5 23:52
 */
public class UrlPath {
 //获取首页tab
 public static final String URL_GET_HOME_TAB= URLParse.getUrlPath()+"/api/MyCenter/HaveTabSkill";
 //获取首页tab视频
 public static final String URL_GET_HOME_TAB_VIDEO=URLParse.getUrlPath()+"/api/Videos/HaveTabVideosList";
 //获取首页轮播图，启动页接口:类型(0:轮播图 1:启动页)
 public static final String URL_GET_HOME_BANNER=URLParse.getUrlPath()+"/api/Accounts/HaveBannerList";
 //获取附近达人数据
 public static  final String URL_GET_NEAR_PEPLE=URLParse.getUrlPath()+"/api/Videos/HaveNearbyList";
 //获取理想达人数据
 public static  final String URL_GET_VEDIO= URLParse.getUrlPath()+"/api/Videos/HaveDreamEredar";
 //个人中心
 public static final String URL_GET_PERSON_HOME=URLParse.getUrlPath()+"/api/MyCenter/HavePersonHome";
 //个人视频列表
 public static final String URL_GET_PERSON_VIDEO=URLParse.getUrlPath()+"/api/Videos/HaveVideosList";
 //获取搜索标签
 public static final String URL_GET_PERSON_LABEL=URLParse.getUrlPath()+"/api/Accounts/HaveSkillTabList";
 //所搜结果列表
 public static final String URL_SEARCH_LIST=URLParse.getUrlPath()+"/api/Videos/HaveSearchList";
 //获取视频播放详情
 public static final String URL_VIDEO_DETAIL=URLParse.getUrlPath()+"/api/Videos/HavePlayDetail";
 //获取视频评论
 public static final String URL_VIDEO_COMMENT=URLParse.getUrlPath()+"/api/Videos/HaveCommentList";
 //视频点赞
 public static final String URL_VIDEO_BOUNS=URLParse.getUrlPath()+"/api/Videos/UserVideoBueno";



 //获取理想商城数据
 public static final String URL_GET_DRARMM_PRODUCT=URLParse.getUrlPath()+"/ProductInterface/GetHandler.ashx";
 //登录
 public static final String URL_LOGIN = URLParse.getUrlPath()+"/api/Accounts/UserLogin";
 //第三方登录
 public static final String URL_LOGIN_THIRD = URLParse.getUrlPath()+"/api/Accounts/OAuthLogin";
 //注册
 public static final String URL_REGIST= URLParse.getUrlPath()+"/api/Accounts/UserRegister";
 //获取我的项目
 public static final String GET_MOUDLE_PRODUCT=URLParse.getUrlPath()+"/ProductInterface/GetHandler.ashx";
 //女友图片
 public static final String GET_GIRL_PICTURE=URLParse.getUrlPath()+"/PicturesInterface/GetHandler.ashx";
 //用户修改密码
 public static final String GET_MODIFY_PASSWORD = URLParse.getUrlPath()+"/api/Accounts/ModifyPassWord";
 //首次注册完善信息接口
 public static final String URL_MODIFY_MATURITYINFO = URLParse.getUrlPath()+"/api/Accounts/ForgetPassword";
 //用户忘记密码
 public static final String GET_FORGET_PASSWORD = URLParse.getUrlPath()+"/api/MyCenter/ModifyMaturityInfo";
 //用户修改昵称接口
 public static final String URL_MODIFY_NICKNAME = URLParse.getUrlPath()+"/api/Accounts/ModifyNickname";
 //用户绑定手机号接口
 public static final String URL_BAND_PHONE = URLParse.getUrlPath()+"/api/Accounts/BindMobile";
 //用户修改头像接口
 public static final String URL_USER_PORTRAIT = URLParse.getUrlPath()+"/api/Accounts/ModifyHeadImg";
 //用户设置邀请码接口
 public final static String URL_INVITATION_CODE = URLParse.getUrlPath()+"/api/Accounts/SetInvite";
 //获取技能标签接口:类型(1:视频技能 2:个人技能 3:搜索标签)
 public final static String URL_SKITLL_LABLES = URLParse.getUrlPath()+"/api/Accounts/HaveSkillTabList";
 //用户关注接口
 public final static String URL_ATTENTION_MODIFY = URLParse.getUrlPath()+"/api/Girlfriend/AttentionModify";
 //获取个人中心信息接口
 public final static String URL_USER_INFO = URLParse.getUrlPath()+"/api/MyCenter/HaveMyCenterDetail";
 //用户修改个性签名接口
 public final static String URL_MODIFY_SIGNATURE = URLParse.getUrlPath()+"/api/MyCenter/ModifySignature";
 //获取谁看过我接口
 public final static String URL_WHOM_VISITED = URLParse.getUrlPath()+"/api/MyCenter/HaveBrowseList";
 //获取我的关注接口
 public final static String URL_MY_ATTENTION = URLParse.getUrlPath()+"/api/MyCenter/HaveMyAttention";
 //获取我的粉丝接口
 public final static String URL_MY_FANS = URLParse.getUrlPath()+"/api/MyCenter/HaveMyFans";
 //用户修改个人标签接口
 public final static String URL_MODIFY_SKILLS_TAG = URLParse.getUrlPath()+"/api/MyCenter/ModifySkillsTag";
 //上传文件接口
 public final static String URL_FILE_UPLOAD = URLParse.getUrlPath()+"/api/Accounts/FileUpload";
 //金山云短视频鉴权接口
 public final static String URL_KSAUTHENTIC = URLParse.getUrlPath()+"/api/Kingsoft/KsAuthentic";
 //用户上传视频接口
 public final static String URL_UPLOAD_VIDEO = URLParse.getUrlPath()+"/api/Videos/UploadAudio";
 //用户修改生日接口
 public final static String URL_MODIFY_BIRTHDAY = URLParse.getUrlPath()+"/api/MyCenter/ModifyBirthday";
 //用户修改家乡接口
 public final static String URL_MODIFY_HOMETOWN = URLParse.getUrlPath()+"/api/MyCenter/ModifyHometown";
 //用户修改性别接口
 public final static String URL_MODIFY_GENDER = URLParse.getUrlPath()+"/api/MyCenter/ModifyGender";
 //用户消费or支付理想币接口
 public final static String URL_CUSTOM_UTOPIACOIN = URLParse.getUrlPath()+"/api/Expense/PlyDreamMoney";


 //获取朋友圈接口
 public final static String URL_MOMENTS_LIST = URLParse.getUrlPath()+"/api/MyCenter/HaveMomentsList";
 //屏蔽好友动态
 public final static String URL_MOMENTS_BLOCK = URLParse.getUrlPath()+"/api/MyCenter/BlockTrends";
 //获取屏蔽好友列表接口
 public final static String URL_MOMENTS_BLOCK_LIST = URLParse.getUrlPath()+"/api/MyCenter/HaveBlockTrendsList";
 //加入黑名单接口
 public final static String URL_ADD_BLACKLIST = URLParse.getUrlPath()+"/api/MyCenter/AddBurnNotice";
 //获取我的黑名单接口
 public final static String URL_GET_BLACKLIST = URLParse.getUrlPath()+"/api/MyCenter/HaveBurnNoticeList";

 //获取随机通话接口
 public final static String URL_RANDOM_CALL = URLParse.getUrlPath() + "/api/Communicate/HaveRandomCall";
 //通话建立接口:通话状态(0:通话断开 1:通话中)
 public final static String URL_RANDOM_CALL_STATE = URLParse.getUrlPath() + "/api/Communicate/RealTimeCallState";
 //删除视频
 public final static String URL_DELETE_VIDEO=URLParse.getUrlPath()+"/api/Videos/DelAudio";
 //评论视频
 public final static String URL_COMMENT_VIDEO=URLParse.getUrlPath()+"/api/Videos/AudioComments";
 //删除视频评论接口
 public final static String URL_DELETE_COMMENT_VIDEO=URLParse.getUrlPath()+"/api/Videos/DelAudioComments";
 //视频评论操作
 public final static String URL_COMMENT_VIDEO_ACTION=URLParse.getUrlPath()+"/api/Videos/DiscussAction";
 //设置视频价格
 public final static String URL_SET_VIDEO_PRICE=URLParse.getUrlPath()+"/api/Videos/SettingVideoPrice";
 //增加视频播放数
 public final static String URL_ADD_VIDEOPLAY_COUNT=URLParse.getUrlPath()+"/api/Videos/AddPlayAmount";
 //用户是否在线
 public final static String USER_IS_ONLINE=URLParse.getUrlPath()+"/api/EasemobIM/IMHavaUserStatus";
 /**
  * 收货地址
  * */
 /**获取收货地址列表*/
 public final static String URL_RECEIVE_ADDRESS = URLParse.getUrlPath() + "/MemberAddressInterface/GetHandler.ashx" ;
 /**新增收货地址接口*/
 public final static String URL_ADD_RECEIVE_ADDRESS = URLParse.getUrlPath() + "/MemberAddressInterface/PostHandler.ashx" ;
 /**修改收货地址接口*/
 public final static String URL_CHANGE_RECEIVE_ADDRESS = URLParse.getUrlPath() + "/MemberAddressInterface/UpdateHandler.ashx" ;
 /**删除收货地址*/
 public final static String URL_DELETE_RECEIVE_ADDRESS = URLParse.getUrlPath() + "/MemberAddressInterface/DeleteHandler.ashx" ;
 //商品详情
 public final static String URL_PRODUCT_DETAIL=URLParse.getUrlPath()+"/ProductDetailInterface/GetHandler.ashx";

 //二维码图片地址
 public final static String URL_QR_IMG = "http://a2.qpic.cn/psb?/3c8a962f-2fd9-49fe-93c4-b8133bbe7e9d/OFZGLFPClK2RDBafHVDZvEURqljBcW75Mpdt*uuUOPY!/b/dBgBAAAAAAAA&bo=GAEYAQAAAAADACU!&rf=viewer_4&save=1&d=1";
 //用户协议
 public final static String URL_USER_PROTOCOL = "http://59.110.48.164:8011/protocol.html";


}
