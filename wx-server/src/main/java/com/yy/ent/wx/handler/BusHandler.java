/**
 *
 */
/**
 * @author Administrator
 *
 */
package com.yy.ent.wx.handler;



import org.apache.log4j.Logger;

import com.yy.ent.client.handler.MessageResponse;
import com.yy.ent.commons.protopack.util.Uint;
import com.yy.ent.wx.common.protocol.ReqGift;
import com.yy.ent.wx.common.protocol.RspGift;
import com.yy.ent.srv.builder.Dispatch;

public class BusHandler extends DreamBaseHandler {


	private static Logger m_logger = Logger.getLogger(BusHandler.class);

    	/*int maxType = getComboYYHeader().getMax();
    	int minType = getComboYYHeader().getMin();
    	int moduleId = getComboYYHeader().getModuleId();
    	Uint srvId = getComboYYHeader().getServerId();
    	Uint subCh = getComboYYHeader().getSubCh();
    	Uint topCh = getComboYYHeader().getTopCh();
    	Uint uid = getComboYYHeader().getUid();
    	int uri = getComboYYHeader().getUri();
    	String urilong = getComboYYHeader().uri();
    	m_logger.info("===========maxType :"+maxType+" minType :"+minType+ " moduleId :"+moduleId+" srvId :"+srvId+" subCh :"+subCh+" topCh :"+topCh+" uid :"+uid+" uri :"+uri + " long uri: "+urilong);
*/


    //SEND GIFT REQ
    @Dispatch(uri= 8087,max=3410,min=23)
    public void handleGift(ReqGift gift) throws Exception
    {
    	m_logger.info("=========================");
    	//String test = gift.extendInfo.get("test");
    	m_logger.info("type :"+gift.type+" num :"+gift.num+" fromId :"+gift.fromId+" toId :"+gift.toId+" channelId :"+gift.channelId
    			+"fromName :"+gift.fromName+" toName :"+gift.toName+" free :"+gift.isFree);
    	int maxType = getComboYYHeader().getMax();
    	int minType = getComboYYHeader().getMin();
    	int moduleId = getComboYYHeader().getModuleId();
    	Uint srvId = getComboYYHeader().getServerId();
    	Uint subCh = getComboYYHeader().getSubCh();
    	Uint topCh = getComboYYHeader().getTopCh();
    	Uint uid = getComboYYHeader().getUid();
    	//int uri = getHeader().getUri();

    	String urilong = getComboYYHeader().uri();
    	m_logger.info("maxType :"+maxType+" minType :"+minType+ " moduleId :"+moduleId+" srvId :"+srvId+" subCh :"+subCh+" topCh :"+topCh+" uid :"+uid+" uri :"+getComboYYHeader().getUri() + " long uri: "+urilong);




    	RspGift rsp = new RspGift();
    	rsp.result=0;
    	rsp.freeLeft=0;
    	rsp.type=15;

    	int maxTypeRsp = 3410;
    	int minTypeRsp = 24;
    	//int nAppId = Integer.valueOf(appId).intValue();
    	MessageResponse res = new MessageResponse(maxTypeRsp, minTypeRsp);
    	res.putMarshall(rsp);
    	//sendUnicast(res);
    	m_logger.info("+++RspGift: "+rsp.toString());



    	//sendUnicast(res);
    	sendBroadcastSubCh(res);
    	m_logger.info("send end");

		//send yy normal
		//int uri = (1<<8|1);
		//sendYY(uri, rsp);



    	//send multilist
    	/*List<Uint> uids = new ArrayList<Uint>();
    	uids.add(new Uint(10));
    	uids.add(new Uint(11));
    	sendMulticast(res,uids);*/
    }


}