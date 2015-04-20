package com.yy.ent.wx.common.protocol;


import java.util.HashMap;
import java.util.Map;

import com.yy.ent.commons.protopack.marshal.BeanMarshal;

public class RspGift extends BeanMarshal{
	public short result;
	public int freeLeft;
	public int type;
	public Map<String,String> extendInfo = new HashMap<String, String>();
	public String toString()
	{
		String log="result: "+result+"freeLeft: "+freeLeft+"type: "+type;
		return log;
	}
}
