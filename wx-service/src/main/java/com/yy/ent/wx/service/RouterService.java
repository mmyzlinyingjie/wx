package com.yy.ent.wx.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.WxMenu.WxMenuButton;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.custombuilder.NewsBuilder;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yy.ent.cherroot.condition.DBCondition;
import com.yy.ent.cherroot.condition.DBCondition.OrderType;
import com.yy.ent.commons.base.dto.Property;
import com.yy.ent.commons.base.inject.Inject;
import com.yy.ent.wx.common.constants.Constants;
import com.yy.ent.wx.common.model.Fans;
import com.yy.ent.wx.common.model.FansIdol;
import com.yy.ent.wx.common.model.Image;
import com.yy.ent.wx.common.model.LocalMedia;
import com.yy.ent.wx.common.model.MassNews;
import com.yy.ent.wx.common.model.News;
import com.yy.ent.wx.common.model.Router;
import com.yy.ent.wx.common.model.Text;
import com.yy.ent.wx.common.model.Video;
import com.yy.ent.wx.common.model.Voice;
import com.yy.ent.wx.common.model.Wx1931;
import com.yy.ent.wx.common.util.MessageType;
import com.yy.ent.wx.dao.FansDao;
import com.yy.ent.wx.dao.FansIdolDao;
import com.yy.ent.wx.dao.ImageDao;
import com.yy.ent.wx.dao.LocalMediaDao;
import com.yy.ent.wx.dao.MassNewsDao;
import com.yy.ent.wx.dao.MultiDao;
import com.yy.ent.wx.dao.NewsDao;
import com.yy.ent.wx.dao.RouterDao;
import com.yy.ent.wx.dao.TextDao;
import com.yy.ent.wx.dao.VideoDao;
import com.yy.ent.wx.dao.VoiceDao;
import com.yy.ent.wx.dao.Wx1931Dao;
import com.yy.ent.wx.service.base.BaseService;


public class RouterService extends BaseService {

	private Logger logger = Logger.getLogger(RouterService.class);

	@Inject(instance = MultiDao.class)
	protected MultiDao multiDao;

	@Inject(instance = RouterDao.class)
	protected RouterDao routerDao;

	@Inject(instance = TextDao.class)
	protected TextDao textDao;

	@Inject(instance = ImageService.class)
	protected ImageService imageService;

	@Inject(instance = ImageDao.class)
	protected ImageDao imageDao;

	@Inject(instance = NewsDao.class)
	protected NewsDao newsDao;

	@Inject(instance = Wx1931Dao.class)
	protected Wx1931Dao wx1931Dao;

	@Inject(instance = VoiceDao.class)
	protected VoiceDao voiceDao;

	@Inject(instance = VideoDao.class)
	protected VideoDao videoDao;

	@Inject(instance = LocalMediaDao.class)
	protected LocalMediaDao localMediaDao;

	@Inject(instance = FansIdolDao.class)
	protected FansIdolDao fansIdolDao;

	@Inject(instance = FansDao.class)
	protected FansDao fansDao;
	
	
	@Inject(instance = MassNewsDao.class)
	protected MassNewsDao massNewsDao;

	/**
	 * @param wxMpService
	 * @param jsonData
	 *            图片类Image的json
	 * @return 返回修改记录数
	 */
	public int fileUploadImage(WxMpService wxMpService, String jsonData) {

		int result = 0;
		JSONObject jo = JSON.parseObject(jsonData);
		String url = (String) jo.get("url");
		String desc = (String) jo.get("desc");
		String str = (String) jo.get("id");
		int id = Integer.valueOf(str);
		try {
			// 0为新增
			if (id == 0) {
				String fileUrl = Constants.UPLOAD_FILE_PATH
						+ url.substring(url.lastIndexOf("/"), url.length());
				System.out.println("微信上传文件url:" + fileUrl);
				InputStream inputStream = new FileInputStream(new File(fileUrl));
				WxMediaUploadResult res = wxMpService.mediaUpload(
						WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
				Image image = new Image();
				image.setDesc(desc);
				image.setMedia_id(res.getMediaId());
				image.setCreateTime(new Date());
				image.setUrl(url);
				result = imageService.save(image);

			} else {
				String fileUrl = Constants.UPLOAD_FILE_PATH
						+ url.substring(url.lastIndexOf("/"), url.length());
				System.out.println("微信上传文件url:" + fileUrl);
				InputStream inputStream = new FileInputStream(new File(fileUrl));
				WxMediaUploadResult res = wxMpService.mediaUpload(
						WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);
				Image image = imageDao.query((long) id);
				image.setDesc(desc);
				image.setMedia_id(res.getMediaId());
				image.setCreateTime(new Date());
				image.setUrl(url);
				result = imageDao.update(image);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 此接口还没被使用
	 * 
	 * @param wxMpService
	 * @param filePath
	 * @param id
	 * @param desc
	 * @return
	 */
	public int fileUploadVoice(WxMpService wxMpService, String filePath,
			String desc) {

		int result = 0;
		try {
			InputStream inputStream = new FileInputStream(new File(filePath));
			WxMediaUploadResult res = wxMpService.mediaUpload(
					WxConsts.MEDIA_VOICE, WxConsts.FILE_ARM, inputStream);

			Voice voice = new Voice();
			voice.setDesc("音频");
			voice.setMedia_id(res.getMediaId());
			result = voiceDao.insert(voice);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传视频
	 * 
	 * @param wxMpService
	 * @param filePath
	 * @param id
	 * @param desc
	 * @return
	 */
	public int fileUploadVideo(WxMpService wxMpService, String filePath,
			String desc, String title) {

		int result = 0;
		try {
			InputStream inputStream = new FileInputStream(new File(filePath));
			WxMediaUploadResult res = wxMpService.mediaUpload(
					WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, inputStream);

			Video video = new Video();
			video.setDesc(desc);
			video.setMedia_id(res.getMediaId());
			video.setTitle(title);
			result = videoDao.insert(video);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 给微信服务器设置路由(暂时支持文本路由和图片路由)
	 * 
	 * @param wxMpMessageRouter
	 * @return
	 */
	public List<Property> setRouter(WxMpMessageRouter wxMpMessageRouter) {

		try {
			DBCondition db = new DBCondition();
			db.addOrder("sortord", OrderType.ASC);
			List<Router> list = routerDao.query(db);
			for (Router router : list) {
				System.out.println("按顺序查询出来的路由：" + router.getIntercept());
				int type = router.getType();
				int type_id = router.getType_id();
				String intercept = router.getIntercept();

				switch (type) {
				case MessageType.WX_TEXT:
					Text text = textDao.query((long) type_id);
					wxMpMessageRouter = setTextHandler(intercept,
							text.getContent(), wxMpMessageRouter);
					break;
				case MessageType.WX_IMAGE:
					Image image = imageDao.query((long) type_id);
					wxMpMessageRouter = setImageHandler(intercept,
							image.getMedia_id(), wxMpMessageRouter);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 辅助方法
	 * 
	 * @param intercept
	 * @param media_id
	 * @param wxMpMessageRouter
	 * @return
	 */
	public WxMpMessageRouter setImageHandler(String intercept,
			final String media_id, WxMpMessageRouter wxMpMessageRouter) {

		WxMpMessageHandler handler = new WxMpMessageHandler() {
			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
					Map<String, Object> context, WxMpService wxMpService,
					WxSessionManager arg3) throws WxErrorException {
				WxMpXmlOutImageMessage m = WxMpXmlOutMessage.IMAGE()
						.mediaId(media_id).fromUser(wxMessage.getToUserName())
						.toUser(wxMessage.getFromUserName()).build();
				return m;
			}
		};

		return wxMpMessageRouter.rule().async(false).content(intercept) // 拦截内容为“哈哈”的消息
				.handler(handler).end();
	}

	/**
	 * 辅助方法
	 * 
	 * @param intercept
	 * @param concent
	 * @param wxMpMessageRouter
	 * @return
	 */
	public WxMpMessageRouter setTextHandler(String intercept,
			final String concent, WxMpMessageRouter wxMpMessageRouter) {

		System.out.println("设置文字路由,拦截的文字为：" + intercept);
		WxMpMessageHandler handler = new WxMpMessageHandler() {
			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
					Map<String, Object> context, WxMpService wxMpService,
					WxSessionManager arg3) throws WxErrorException {
				WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT()
						.content(concent).fromUser(wxMessage.getToUserName())
						.toUser(wxMessage.getFromUserName()).build();
				return m;
			}
		};

		return wxMpMessageRouter.rule().async(false).content(intercept) // 拦截内容为“哈哈”的消息
				.handler(handler).end();
		// return
		// wxMpMessageRouter.rule().async(false).rContent(intercept+"*")//
		// 拦截内容为“哈哈”的消息
		// .handler(handler).end();
	}

	/**
	 * 设置微信公众号菜单
	 * 
	 * @param wxMpService
	 * @return
	 * @throws WxErrorException
	 */
	public boolean setMenu(WxMpService wxMpService) {

		// 设置菜单
		WxMenu wxMenu = new WxMenu();
		WxMenuButton caidan = new WxMenuButton();
		caidan.setKey("caidan");
		caidan.setType("click");
		caidan.setName("彩蛋区");

		WxMenuButton yszx = new WxMenuButton();
		yszx.setName("一手资讯");
		List<WxMenuButton> subYszx = new ArrayList<WxMenuButton>(5);
		 WxMenuButton yszx1 = new WxMenuButton();
		 yszx1.setKey("往期内容");
		 yszx1.setName("往期内容");
		 yszx1.setType("click");
		WxMenuButton yszx2 = new WxMenuButton();
		yszx2.setUrl("http://www.1931.com/dream/mobile/newsPhotos.action");
		yszx2.setName("精彩留影");
		yszx2.setType("view");
		WxMenuButton yszx3 = new WxMenuButton();
		yszx3.setUrl("http://www.1931.com/dream/mobile/newsideos.action");
		yszx3.setName("热门视频");
		yszx3.setType("view");
		WxMenuButton yszx4 = new WxMenuButton();
		yszx4.setKey("我们的歌");
		yszx4.setName("我们的歌");
		yszx4.setType("click");
		WxMenuButton yszx5 = new WxMenuButton();
		yszx5.setUrl("http://bbs.1931.com/thread-20512-1-1.html");
		yszx5.setName("直播时间表");
		yszx5.setType("view");
		 subYszx.add(yszx1);
		subYszx.add(yszx2);
		subYszx.add(yszx3);
		subYszx.add(yszx4);
		subYszx.add(yszx5);
		yszx.setSubButtons(subYszx);

		WxMenuButton fans = new WxMenuButton();
		fans.setName("粉丝区");
		List<WxMenuButton> subFans = new ArrayList<WxMenuButton>(4);
		WxMenuButton fans1 = new WxMenuButton();
		fans1.setKey("白队");
		fans1.setName("白    队");
		fans1.setType("click");
		WxMenuButton fans2 = new WxMenuButton();
		fans2.setKey("红队");
		fans2.setName("红    队");
		fans2.setType("click");
		WxMenuButton fans3 = new WxMenuButton();
		fans3.setUrl("http://bbs.1931.com/");
		fans3.setName("讨 论 区");
		fans3.setType("view");
		WxMenuButton fans4 = new WxMenuButton();
		fans4.setKey("我的偶像");
		fans4.setName("我的偶像");
		fans4.setType("click");
		WxMenuButton fans5 = new WxMenuButton();
		fans5.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbea2b5d9b8ffad02&redirect_uri=http%3A%2F%2Fmynona.xicp.net%2Fwx%2FsetFocus.action&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
		fans5.setName("关注设置");
		fans5.setType("view");
		subFans.add(fans1);
		subFans.add(fans2);
		subFans.add(fans3);
		subFans.add(fans4);
		subFans.add(fans5);
		fans.setSubButtons(subFans);

		List<WxMenuButton> lists = new ArrayList<WxMenuButton>(2);
		lists.add(yszx);
		lists.add(fans);
		lists.add(caidan);

		wxMenu.setButtons(lists);
		try {
			wxMpService.menuCreate(wxMenu);
		} catch (WxErrorException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 查询微信公众号菜单
	 * 
	 * @param wxMpService
	 * @throws WxErrorException
	 */
	public void queryMenu(WxMpService wxMpService) {
		WxMenu wxMenu = null;
		try {
			wxMenu = wxMpService.menuGet();
		} catch (WxErrorException e) {
			logger.error(e);
			e.printStackTrace();
		}
		System.out.println("=========menu============");
		System.out.println(wxMenu.toJson());
	}

	/**
	 * 删除微信公众号菜单
	 * 
	 * @param wxMpService
	 * @throws WxErrorException
	 */
	public void deleteMenu(WxMpService wxMpService) {
		try {
			wxMpService.menuDelete();
		} catch (WxErrorException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 构建图文信息列表
	 * 
	 * @param nb
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public NewsBuilder newNews(NewsBuilder nb, int type) {

		DBCondition db = new DBCondition();
		db.addCondition("type", type);
		db.addOrder("sortord", OrderType.ASC);
		List<News> list = null;
		try {
			list = newsDao.query(db);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		int count = 0;
		for (News news : list) {
			WxMpCustomMessage.WxArticle item = new WxMpCustomMessage.WxArticle();
			item.setDescription(news.getDescription());
			item.setPicUrl(news.getPicUrl());
			item.setTitle(news.getTitle());
			if (type == 4) {
				item.setUrl("http://mynona.xicp.net/wx/video.jsp?url="
						+ news.getUrl());
			} else {
				item.setUrl(news.getUrl());
			}
			nb = nb.addArticle(item);
			count++;
			if (count > 9)
				break;
		}
		return nb;
	}

	public int getRandomVideosByName(NewsBuilder nb, int type, String name) {

		List<Property> pros = multiDao.queryCollection("newVideo", name, name,
				name);
		if (pros.size() > 10) {
			int[] index = randomArray(0, pros.size() - 1, 10);
			for (int i = 0; i < index.length; i++) {
				Property pro = pros.get(index[i]);
				WxMpCustomMessage.WxArticle item = new WxMpCustomMessage.WxArticle();
				item.setDescription(pro.get("description"));
				item.setPicUrl(pro.get("picUrl"));
				item.setTitle(pro.get("title"));
				item.setUrl(pro.get("url"));
				nb = nb.addArticle(item);

			}
		} else {
			for (Property pro : pros) {
				WxMpCustomMessage.WxArticle item = new WxMpCustomMessage.WxArticle();
				item.setDescription(pro.get("description"));
				item.setPicUrl(pro.get("picUrl"));
				item.setTitle(pro.get("title"));
				item.setUrl(pro.get("url"));
				nb = nb.addArticle(item);
			}
		}
		System.out.println("///////////发送  " + name + "  图文:" + pros.size());
		return pros.size();
	}

	/**
	 * 发送符合条件的一张图片
	 * 
	 * @param ib
	 * @param name
	 *            图片描述
	 * @return
	 * @throws WxErrorException
	 * @throws IOException
	 */
	public int getImageByName(String user, String name, WxMpService wxMpService)
			throws WxErrorException {

		List<Property> pros = multiDao.queryCollection("image", name, name);
		int count = 5;
		count = pros.size() > 5 ? 5 : pros.size();
		int[] index = randomArray(0, pros.size() - 1, count);
		for (int i = 0; i < count; i++) {
			Property pro = pros.get(index[i]);
			WxMpCustomMessage message = WxMpCustomMessage.IMAGE().toUser(user)
					.mediaId(pro.get("media_id")).build();
			wxMpService.customMessageSend(message);
		}
		System.out.println("///////////发送  " + name + "  图片:" + count);
		return count;
	}

	public int getVoiceByName(String user, String name, WxMpService wxMpService)
			throws WxErrorException {

		List<Property> pros = multiDao.queryCollection("voice", name);
		int count = 5;
		count = pros.size() > 5 ? 5 : pros.size();
		int[] index = randomArray(0, pros.size() - 1, count);
		for (int i = 0; i < count; i++) {
			Property pro = pros.get(index[i]);
			WxMpCustomMessage message = WxMpCustomMessage.VOICE().toUser(user)
					.mediaId(pro.get("media_id")).build();
			wxMpService.customMessageSend(message);
		}
		System.out.println("///////////发送  " + name + "  语音:" + count);
		return count;
	}

	/**
	 * 微信公众号事件处理、彩蛋区
	 * 
	 * @param outMessage
	 * @param inMessage
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void dispose(WxMpXmlMessage inMessage, WxMpService wxMpService)
			throws Exception {

		WxMpCustomMessage message;
		String msgType = inMessage.getMsgType();

		// 对菜单事件的处理
		if (msgType.equals("event")) {
			System.out.println("-----------event-------------------");
			String eventKey = inMessage.getEventKey();
			if (eventKey != null) {
				if (eventKey.equals("白队")) {
					NewsBuilder nb = WxMpCustomMessage.NEWS();
					message = newNews(nb, 1)
							.toUser(inMessage.getFromUserName()).build();
					wxMpService.customMessageSend(message);

				} else if (eventKey.equals("红队")) {
					NewsBuilder nb = WxMpCustomMessage.NEWS();
					message = newNews(nb, 2)
							.toUser(inMessage.getFromUserName()).build();
					wxMpService.customMessageSend(message);

				} else if (eventKey.equals("caidan")) {

					String contentText = "试试回复任意内容，比如:\n"
							+ getEggshellContent(5);
					message = WxMpCustomMessage.TEXT().content(contentText)
							.toUser(inMessage.getFromUserName()).build();
					wxMpService.customMessageSend(message);

				} else if (eventKey.equals("我们的歌")) {

					// 发送图文消息
					NewsBuilder nb = WxMpCustomMessage.NEWS();
					message = newNews(nb, 3)
							.toUser(inMessage.getFromUserName()).build();
					wxMpService.customMessageSend(message);
				}  else if (eventKey.equals("往期内容")) {

					// 发送图文消息
					NewsBuilder nb = WxMpCustomMessage.NEWS();
					message = getMassNews(inMessage, wxMpService, nb, 1)
							.toUser(inMessage.getFromUserName()).build();
					wxMpService.customMessageSend(message);
				} else if (eventKey.equals("我的偶像")) {

					System.out.println("============我的偶像==============");
					// 查询相关偶像关注表
					List<Property> pros = multiDao.queryCollection(
							"fans_idol_name", inMessage.getFromUserName());
					if (pros == null || pros.size() == 0) {
						// 回复引导关注信息
						String contentText = "请在“关注设置”里面设置你喜欢的偶像，么么哒(｡◕ˇ∀ˇ◕)ﾉ";
						message = WxMpCustomMessage.TEXT().content(contentText)
								.toUser(inMessage.getFromUserName()).build();
						wxMpService.customMessageSend(message);
					} else {
						// 回复偶像信息
						for (Property pro : pros) {

							inMessage.setContent(pro.get("idol_name"));
							disposeText(inMessage, wxMpService);
							// inMessage.setContent(pro.get("nick_name"));
							// disposeText(inMessage, wxMpService);
						}
					}

					// 返回偶像信息
				} else if (eventKey.equals("我们的歌")) {

					// 发送图文消息
					NewsBuilder nb = WxMpCustomMessage.NEWS();
					message = newNews(nb, 3)
							.toUser(inMessage.getFromUserName()).build();
					wxMpService.customMessageSend(message);
				}
			}
			// 给新用户的回复
			if (inMessage.getEvent().equals("subscribe")) {
				String content = "欢迎关注1931粉丝团，么么哒!";
				message = WxMpCustomMessage.TEXT()
						.toUser(inMessage.getFromUserName()).content(content)
						.build();
				wxMpService.customMessageSend(message);
				// 保存openid
				Fans fans = new Fans();
				fans.setFansId(inMessage.getFromUserName());
				fansDao.insert(fans);
			}
		}
		// 对语音事件的处理
		if (msgType.equals("voice")) {

			// 保存语音资源
			if (inMessage.getFromUserName().equals(
					"oD6flst5M4hp6jinlGwvpXf982o8")) {
				Voice voice = new Voice();
				voice.setDesc("来自mynona");
				voice.setMedia_id(inMessage.getMediaId());
				try {
					voiceDao.insert(voice);
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
				message = WxMpCustomMessage.VOICE()
						.mediaId(inMessage.getMediaId())
						.toUser(inMessage.getFromUserName()).build();
				wxMpService.customMessageSend(message);
			}
			if (inMessage.getFromUserName().equals(
					"oD6flsi1L6NDiryqefCalDF1k6XE")) {
				Voice voice = new Voice();
				voice.setDesc("来自Yuen");
				voice.setMedia_id(inMessage.getMediaId());
				try {
					voiceDao.insert(voice);
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
				message = WxMpCustomMessage.VOICE()
						.mediaId(inMessage.getMediaId())
						.toUser("oD6flst5M4hp6jinlGwvpXf982o8").build();
				wxMpService.customMessageSend(message);
			}
		}
		// 对图片事件的处理
		if (msgType.equals("image")) {

			String contentText = "试回复:\n" + getEggshellContent(3);
			message = WxMpCustomMessage.TEXT().content(contentText)
					.toUser(inMessage.getFromUserName()).build();
			wxMpService.customMessageSend(message);
		}
		// 对短视频事件的处理
		if (msgType.equals("shortvideo")) {

			String contentText = "试回复:\n" + getEggshellContent(3);
			message = WxMpCustomMessage.TEXT().content(contentText)
					.toUser(inMessage.getFromUserName()).build();
			wxMpService.customMessageSend(message);
		}
		// 对文本时间的处理
		if (msgType.equals("text")) {

			disposeText(inMessage, wxMpService);
		}

	}

	public void disposeText(WxMpXmlMessage inMessage, WxMpService wxMpService)
			throws WxErrorException {

		WxMpCustomMessage message;
		String content = inMessage.getContent().replace(" ", "");
		int count = 0;

		me.chanjar.weixin.mp.bean.custombuilder.NewsBuilder nb = WxMpCustomMessage
				.NEWS();
		count += getRandomVideosByName(nb, 4, content);
		message = nb.toUser(inMessage.getFromUserName()).build();
		if (count != 0)
			wxMpService.customMessageSend(message);

		count += getImageByName(inMessage.getFromUserName(), content,
				wxMpService);

		// 语音
		count += getVoiceByName(inMessage.getFromUserName(), content,
				wxMpService);

		if (count == 0) {
			String contentText = "回复以下试试:\n" + getEggshellContent(3);
			message = WxMpCustomMessage.TEXT().content(contentText)
					.toUser(inMessage.getFromUserName()).build();
			wxMpService.customMessageSend(message);
		}
	}

	/**
	 * 获取图文信息列表
	 * 
	 * @param type
	 *            为1表示自拍萌照
	 * @return
	 * @throws Exception
	 */
	public List<Wx1931> getWx1931(int type) throws Exception {

		DBCondition db = new DBCondition();
		db.addCondition("type", type);
		db.addOrder("sortord", OrderType.ASC);
		List<Wx1931> list = wx1931Dao.query(db);
		return list;
	}

	/**
	 * 保存路由
	 * 
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public int saveRouter(String jsonData) throws Exception {

		Router routerJson = JSON.parseObject(jsonData, Router.class);
		Router router = new Router();
		router.setIntercept(routerJson.getIntercept());
		router.setSortord(routerJson.getSortord());
		router.setType(routerJson.getType());
		router.setType_id(routerJson.getType_id());
		return routerDao.insert(router);
	}

	/**
	 * 更新路由
	 * 
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public int updateRouter(String jsonData) throws Exception {

		Router routerJson = JSON.parseObject(jsonData, Router.class);
		Router router = routerDao.query((long) routerJson.getId());
		router.setIntercept(routerJson.getIntercept());
		router.setSortord(routerJson.getSortord());
		router.setType(routerJson.getType());
		router.setType_id(routerJson.getType_id());
		return routerDao.update(router);
	}

	/**
	 * 删除路由
	 * 
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public int deleteRouter(String jsonData) throws Exception {

		JSONObject jo = JSON.parseObject(jsonData);
		String str = (String) jo.get("data");
		int id = Integer.valueOf(str);
		Router router = routerDao.query((long) id);
		return routerDao.delete(router);
	}

	public int deleteImage(String jsonData) throws Exception {

		JSONObject jo = JSON.parseObject(jsonData);
		String str = (String) jo.get("data");
		int id = Integer.valueOf(str);
		Image image = imageDao.query((long) id);
		return imageDao.delete(image);
	}

	public int deleteText(String jsonData) throws Exception {

		JSONObject jo = JSON.parseObject(jsonData);
		String str = (String) jo.get("data");
		int id = Integer.valueOf(str);
		Text text = textDao.query((long) id);
		return textDao.delete(text);
	}

	/**
	 * 获取路由列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Property> getRouterList() {

		List<Property> proList = new ArrayList<Property>();
		try {
			DBCondition db = new DBCondition();
			List<Router> routerList = routerDao.query(db);
			for (Router router : routerList) {
				Property pro = new Property();
				pro.put("id", router.getId());
				pro.put("intercept", router.getIntercept());
				pro.put("sortord", router.getSortord());
				pro.put("type", router.getType());
				pro.put("type_id", router.getType_id());
				int type = router.getType();
				switch (type) {
				case 1:
					Text text = textDao.query((long) router.getType_id());
					if (text != null)
						pro.put("desc", text.getContent());
					break;
				case 2:
					Image image = imageDao.query((long) router.getType_id());
					if (image != null)
						pro.put("desc", image.getDesc());
					break;
				default:
					break;
				}
				proList.add(pro);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return proList;
	}

	/**
	 * 获取媒体文件
	 * 
	 * @param type
	 *            类型值 1为文本 ， 2为图片
	 * @return
	 * @throws Exception
	 */
	public List getMediaList(int type) {

		List list = null;
		try {
			switch (type) {
			case 1:
				List<Text> texts = new ArrayList<Text>();
				DBCondition db = new DBCondition();
				texts = textDao.query(db);
				return texts;
			case 2:
				DBCondition db2 = new DBCondition();
				List<Property> prolist = new ArrayList<Property>();
				List<Image> images = new ArrayList<Image>();
				images = imageDao.query(db2);
				for (Image image : images) {
					Property pro = new Property();
					pro.put("id", image.getId());
					pro.put("media_id", image.getMedia_id());
					pro.put("url", image.getUrl());
					pro.put("createTime",
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(image.getCreateTime()));
					pro.put("desc", image.getDesc());
					prolist.add(pro);
				}
				return prolist;

			default:
				break;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 上传文件
	 * 
	 * @param savePath
	 * @param item
	 * @param md5Name
	 */
	public void uploadFile(String savePath, FileItem item, String md5Name) {

		System.out.println("进入文件上传------------------");
		try {
			System.out.println("上传文件:" + savePath + "\\" + md5Name + ".jpg");
			InputStream in = item.getInputStream();
			FileOutputStream out = new FileOutputStream(savePath + "\\"
					+ md5Name + ".jpg");
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
			item.delete();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 检查MD5 true为已存在文件，不再上传
	 * 
	 * @param md5
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public boolean checkMd5(String md5, int type) throws Exception {

		DBCondition db = new DBCondition();
		db.addCondition("md5", md5);
		List<LocalMedia> lmLists = localMediaDao.query(db);
		if (lmLists != null && lmLists.size() > 0) {
			System.out.println("MD5校验，此文件已存在");
			return true;
		}
		LocalMedia lo = new LocalMedia();
		lo.setMd5(md5);
		lo.setCreateTime(new Date());
		lo.setType(type);
		localMediaDao.insert(lo);
		return false;
	}

	/**
	 * 
	 * @param savePath
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String uploadImage(HttpServletRequest request) throws Exception {

		String md5Name = null;
		File file = new File(Constants.UPLOAD_FILE_PATH);
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("新建目录：" + Constants.UPLOAD_FILE_PATH);
			file.mkdir();
		}
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			if (!ServletFileUpload.isMultipartContent(request)) {
				return "fail";
			}
			List<FileItem> list = upload.parseRequest(request);
			System.out.println("------------listSize--------------"
					+ list.size());
			if (list.size() >= 2) {
				boolean checkMd5 = false;
				FileItem itemMd5 = list.get(0);
				String name = itemMd5.getFieldName();
				if (name.equals("md5")) {
					md5Name = itemMd5.getString("UTF-8");
					checkMd5 = checkMd5(md5Name, 2);
					System.out.println("md5校验结果：" + checkMd5);
				}
				FileItem itemFile = list.get(list.size() - 1);
				if (!checkMd5) {
					uploadFile(Constants.UPLOAD_FILE_PATH, itemFile, md5Name);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "fail";
		}
		return "http://localhost:8080/wx_upload/" + md5Name + ".jpg";
	}

	/**
	 * 
	 * @param wxMpService
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public int saveText(WxMpService wxMpService, String jsonData) {

		int result = 0;
		try {
			JSONObject jo = JSON.parseObject(jsonData);
			String content = (String) jo.get("content");
			String desc = (String) jo.get("desc");
			String str = (String) jo.get("id");
			int id = Integer.valueOf(str);

			System.out.println("content:" + content);
			System.out.println("title:" + desc);
			if (id == 0) {
				Text text = new Text();
				text.setDesc(desc);
				text.setContent(content);
				result = textDao.insert(text);
			} else {
				Text text = textDao.query((long) id);
				text.setDesc(desc);
				text.setContent(content);
				result = textDao.update(text);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 随机指定范围内N个不重复的数 在初始化的无重复待选数组中随机产生一个数放入结果中，
	 * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换 然后从len-2里随机产生下一个随机数，如此类推
	 * 
	 * @param max
	 *            指定范围最大值
	 * @param min
	 *            指定范围最小值
	 * @param n
	 *            随机数个数
	 * @return int[] 随机数结果集
	 */
	public static int[] randomArray(int min, int max, int n) {
		int len = max - min + 1;

		if (max < min || n > len) {
			return null;
		}

		// 初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = min; i < min + len; i++) {
			source[i - min] = i;
		}

		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			// 待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			// 将随机到的数放入结果集
			result[i] = source[index];
			// 将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
	}

	public String getEggshellContent(int count) {

		List<Property> images = multiDao.queryCollection("imageEgg");
		int[] imagesIndex = randomArray(0, images.size() - 1, count);
		String imageContent = "";
		if (imagesIndex != null) {
			for (int i = 0; i < imagesIndex.length; i++) {
				Property pro = images.get(imagesIndex[i]);
				if (pro != null)
					imageContent += pro.get("description") + "\n";
			}
		}

		List<Property> voices = multiDao.queryCollection("voiceEgg");
		int[] voicesIndex = randomArray(0, voices.size() - 1, 1);
		String voiceContent = "";
		if (voicesIndex != null) {
			for (int i = 0; i < voicesIndex.length; i++) {
				Property pro = voices.get(voicesIndex[i]);
				if (pro != null)
					voiceContent += pro.get("description");
			}
		}

		List<Property> news = multiDao.queryCollection("newsEgg");
		int[] newsIndex = randomArray(0, news.size() - 1, 2);
		String newContent = "";
		if (newsIndex != null) {
			for (int i = 0; i < newsIndex.length; i++) {
				Property pro = news.get(newsIndex[i]);
				if (pro != null)
					newContent += pro.get("description") + "\n";
			}
		}
		return newContent + imageContent + voiceContent;
	}

	public Map setFocus(WxMpService wxMpService, String code)
			throws WxErrorException {

		System.out.println("before------------------");
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
		wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		String openId = wxMpOAuth2AccessToken.getOpenId();
//		System.out.println("openId ------------------------"+ openId);
//		 String openId = "test_openId";
		List<Property> pros = multiDao.queryCollection("fans_idol", openId);
		// List<Integer> list = new ArrayList<Integer>();
		// for(Property pro: pros){
		// System.out.println(pro.get("idol_id"));
		// System.out.println(pro.get("idol_name"));
		// }
		System.out.println("after-----------------");
		Map map = new HashMap();
		map.put("fans_id", openId);
		map.put("idol", pros);
		return map;
	}

	public int addFocus(String jsonData) throws Exception {

		System.out.println("-----------addFoucs-----------");
		System.out.println(jsonData);
		JSONObject jo = JSON.parseObject(jsonData);
		String fans_id = (String) jo.get("fans_id");
		String idol_id = (String) jo.get("idol_id");
		FansIdol fansIdol = new FansIdol();
		fansIdol.setFans_id(fans_id);
		fansIdol.setIdol_id(Integer.valueOf(idol_id));

		DBCondition db = new DBCondition();
		db.addCondition("fans_id", fans_id);
		db.addCondition("idol_id", idol_id);
		List<FansIdol> list = fansIdolDao.query(db);
		if (list == null || list.size() == 0) {
			return fansIdolDao.insert(fansIdol);
		}
		return 0;
	}

	public int deleteFocus(String jsonData) {

		System.out.println(jsonData);
		JSONObject jo = JSON.parseObject(jsonData);
		String fans_id = (String) jo.get("fans_id");
		String idol_id = (String) jo.get("idol_id");

		return multiDao.delete("delete_fans_idol", fans_id, idol_id);
	}

	public List<Fans> getFansId() throws Exception {

		DBCondition db = new DBCondition();
		return fansDao.query(db);
	}

	public String sendMassText(WxMpService wxMpService, String data)
			throws Exception {

		System.out.println("群发内容：" + data);
		WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
		massMessage.setMsgType(WxConsts.MASS_MSG_TEXT);
		massMessage.setContent(data);
		// 最多支持10,000个群发用户
		List<String> listFansStr = massMessage.getToUsers();
		List<Fans> listFan = getFansId();
		for (Fans fans : listFan) {
			listFansStr.add(fans.getFansId());
			System.out.println("群发给...." + fans.getFansId());
		}

		WxMpMassSendResult  result = wxMpService.massOpenIdsMessageSend(massMessage);
		return result.toString();
	}
	
	public void addMassArticle(WxMpMassNews news, int type) throws Exception{
		
		DBCondition db = new DBCondition();
		db.addCondition("type", type);
		db.addOrder("sortord", OrderType.ASC);
		List<MassNews> lists = massNewsDao.query(db);
		for(MassNews mnews :lists){
			WxMpMassNews.WxMpMassNewsArticle article = new WxMpMassNews.WxMpMassNewsArticle();
			article.setTitle(mnews.getTitle());
			article.setContent(mnews.getContent());
			article.setThumbMediaId(mnews.getThumbMediaId());
			article.setShowCoverPic(mnews.isShowCoverPic());
			article.setAuthor(mnews.getAuthor());
			article.setContentSourceUrl(mnews.getContentSourceUrl());
			article.setDigest(mnews.getDigest());
			news.addArticle(article);
		}
	}

	public String sendMassNews(WxMpService wxMpService, int type) throws Exception {

		WxMpMassNews news = new WxMpMassNews();
		addMassArticle(news, type);
		WxMpMassUploadResult massUploadResult = wxMpService.massNewsUpload(news);
		WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
		massMessage.setMsgType(WxConsts.MASS_MSG_NEWS);
		massMessage.setMediaId(massUploadResult.getMediaId());
		
		// 最多支持10,000个群发用户
		List<String> listFansStr = massMessage.getToUsers();
		List<Fans> listFan = getFansId();
		for (Fans fans : listFan) {
			listFansStr.add(fans.getFansId());
		}

		WxMpMassSendResult massResult = wxMpService.massOpenIdsMessageSend(massMessage);
		return massResult.getErrorMsg();
	}
	
	
	public NewsBuilder getMassNews(WxMpXmlMessage inMessage, WxMpService wxMpService, NewsBuilder nb, int type) throws WxErrorException {

		DBCondition db = new DBCondition();
		db.addCondition("type", type);
		db.addOrder("sortord", OrderType.ASC);
		List<MassNews> list = null;
		try {
			list = massNewsDao.query(db);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		int count = 0;
		for (MassNews news : list) {
			WxMpCustomMessage.WxArticle item = new WxMpCustomMessage.WxArticle();
			item.setDescription(news.getContent());
			item.setPicUrl(news.getThumbMediaId());
			item.setTitle(news.getTitle());
			item.setUrl(news.getContentSourceUrl());
			nb = nb.addArticle(item);
			count++;
			if (count > 9)
				break;
		}

		return nb;
	}
}
