package com.yy.ent.wx.common.protocol;


import java.util.HashMap;
import java.util.Map;

import com.yy.ent.commons.protopack.marshal.BeanMarshal;

public class ReqGift extends BeanMarshal {

	public  int type;
	public int num;
	public int fromId;
	public int toId;
	public int channelId;
	public String fromName;
	public String toName;
	public int isFree;
	public Map<String, String> extendInfo = new HashMap< String, String>();

}
