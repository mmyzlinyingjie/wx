package com.yy.ent.wx.servlet;


import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

import com.yy.ent.commons.base.inject.Inject;
import com.yy.ent.wx.service.RouterService;

public class WxMpServlet extends HttpServlet {

	protected WxMpInMemoryConfigStorage wxMpConfigStorage;
	protected WxMpService wxMpService;
	protected WxMpMessageRouter wxMpMessageRouter ;
	//@Inject(instance = RouterService.class)
	protected RouterService routerService = new RouterService();

	@Override
	public void init() throws ServletException {
		super.init();

		System.out.println("init--------------------------------");
		wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId("wxbea2b5d9b8ffad02"); // 设置微信公众号的appid
		wxMpConfigStorage.setSecret("c084c7231172adab97ea4ce515516333"); // 设置微信公众号的app
		wxMpConfigStorage.setToken("vzhanqun1234567890"); // 设置微信公众号的token
		wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
		wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
		

	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("service--------------------------------");

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");

		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			response.getWriter().println("非法请求");
			return;
		}
	
		WxMpMessageHandler handler2 = new WxMpMessageHandler() {
			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
					Map<String, Object> context, WxMpService wxMpService,
					WxSessionManager arg3) throws WxErrorException {
				WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT()
					
						.content("测试1931消息").fromUser(wxMessage.getToUserName())
						.toUser(wxMessage.getFromUserName()).build();
				return m;
			}
		};

		wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
		wxMpMessageRouter.rule().async(false).content("哈哈") // 拦截内容为“哈哈”的消息
				.handler(handler2).end();
	
	
		String openid = "oD6flst5M4hp6jinlGwvpXf982o8";
		WxMpCustomMessage message = WxMpCustomMessage.TEXT().toUser(openid)
				.content("Hello World").build();
		try {
			wxMpService.customMessageSend(message);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 多媒体管理

//		InputStream inputStream = new FileInputStream(new File(
//				"C:\\study\\tmp\\sp.mp4"));
//		WxMediaUploadResult res = null;
//		try {
//			res = wxMpService.mediaUpload(WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4,
//					inputStream);
//		} catch (WxErrorException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		System.out.println("type:" + res.getType() + "\ntime:"
//				+ res.getCreatedAt() + "\nmedia:" + res.getMediaId()
//				+ "\nmediaId:" + res.getThumbMediaId());
//
//		System.out.println(" file-------------------");

		String echostr = request.getParameter("echostr");
		if (StringUtils.isNotBlank(echostr)) {
			// 说明是一个仅仅用来验证的请求，回显echostr
			response.getWriter().println(echostr);
			return;
		}

		String encryptType = StringUtils.isBlank(request
				.getParameter("encrypt_type")) ? "raw" : request
				.getParameter("encrypt_type");

		if ("raw".equals(encryptType)) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request
					.getInputStream());

			WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
			System.out.println("content:" + inMessage.toString());

			
			if (outMessage != null) {
				
				// 设置菜单				
//				System.out.println("menu1-------------------------");
//				WxMenu wxMenu = new WxMenu();
//				WxMenuButton wb = new WxMenuButton();
//				wb.setKey("V1001_TODAY_MUSIC");
//				wb.setType("click");
//				wb.setName("薪资");
//				
//				WxMenuButton wb2 = new WxMenuButton();
//				wb2.setType("view");
//				wb2.setName("视频");
//				wb2.setUrl("http://www.baidu.com");
//				
//				List<WxMenuButton> lists = new ArrayList<WxMenuButton>(2);
//				lists.add(wb);
//				lists.add(wb2);
//				
//				
//				wxMenu.setButtons(lists);
//				
//				System.out.println("menu2-------------------------");
//				
//
//				System.out.println(wxMenu.toJson());
//				
//				try {
//					wxMpService.menuCreate(wxMenu);
//				} catch (WxErrorException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}


				


				
				
				response.getWriter().write(outMessage.toXml());
			} else {

				// 回复粉丝发过来的图片
				// outMessage = WxMpXmlOutMessage.IMAGE()
				// .mediaId(inMessage.getMediaId())
				// .fromUser(inMessage.getToUserName())
				// .toUser(inMessage.getFromUserName())
				// .build();

				// 回复粉丝发送的语音
				// outMessage = WxMpXmlOutMessage.VOICE()
				// .mediaId(inMessage.getMediaId())
				// .fromUser(inMessage.getToUserName())
				// .toUser(inMessage.getFromUserName()).build();
				// response.getWriter().write(outMessage.toXml());

//				outMessage = WxMpXmlOutMessage.VIDEO()
//						.mediaId("oeyuR8hXCnBck545oDmlJ8E3P9Otn5TBYV40yjwxqvUsolmN-z_lu0bRD8VhRk_G")
//						.fromUser(inMessage.getToUserName())
//						.toUser(inMessage.getFromUserName()).build();
//				response.getWriter().write(outMessage.toXml());
				

				
//				WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//				item.setDescription("description");
//				item.setPicUrl("http://mmyzlinyingjie.oicp.net/wx/ft.jpg");
//				item.setTitle("title");
//				item.setUrl("http://mmyzlinyingjie.oicp.net/wx/ct.txt");
//				
//				WxMpXmlOutNewsMessage.Item item2 = new WxMpXmlOutNewsMessage.Item();
//				item2.setDescription("description2");
//				item2.setPicUrl("http://mmyzlinyingjie.oicp.net/wx/ft2.jpg");
//				item2.setTitle("title2");
//				item2.setUrl("http://mmyzlinyingjie.oicp.net/wx/ct2.txt");
//				
//				WxMpXmlOutNewsMessage.Item item3 = new WxMpXmlOutNewsMessage.Item();
//				item3.setDescription("description3");
//				item3.setPicUrl("https://www.baidu.com/img/bd_logo1.png");
//				item3.setTitle("title3");
//				item3.setUrl("http://blog.csdn.net/yakson/article/details/22056273");
//				
//
//				WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
//				  .fromUser(inMessage.getToUserName())
//				  .toUser(inMessage.getFromUserName())
//				  .addArticle(item)
//				  .addArticle(item2)
//				  .addArticle(item3)
//				  .build();
//				
//				System.out.println("in it ================");
//				response.getWriter().write(m.toXml());
				
			}
			return;
		}

		if ("aes".equals(encryptType)) {
			// 是aes加密的消息
			String msgSignature = request.getParameter("msg_signature");
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
					request.getInputStream(), wxMpConfigStorage, timestamp,
					nonce, msgSignature);
			WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
			response.getWriter().write(
					outMessage.toEncryptedXml(wxMpConfigStorage));
			return;
		}

		response.getWriter().println("不可识别的加密类型");
		return;
	}
}