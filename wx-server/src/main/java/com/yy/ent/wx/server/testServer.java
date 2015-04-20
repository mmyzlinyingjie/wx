/**
 *
 */
/**
 * @author Administrator
 *
 */
package com.yy.ent.wx.server;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.yy.ent.client.srv.SrvReceiver;
//import com.yy.ent.commons.base.inject.Inject;


public class testServer
{
	//@Inject(instance = SrvReceiver.class)
    private static SrvReceiver srv = new SrvReceiver();

	private static Logger logger = Logger.getLogger(testServer.class);
	public static void main(String[] args) throws Exception
	{
		URL log4j = Thread.currentThread().getContextClassLoader().getResource("log4j.properties");
		PropertyConfigurator.configure(log4j);

		/*URL cherryUrl = Thread.currentThread().getContextClassLoader().getResource("cherry.xml");
        File cherryConfig = new File(cherryUrl.toURI());
		String cherryPath=cherryConfig.getAbsolutePath();
        Cherry cherry = new Cherry(cherryPath);
        cherry.init();*/

		URL cherrio = Thread.currentThread().getContextClassLoader().getResource("cherrio.xml");
        File cherrioConfig = new File(cherrio.toURI());
		String cherrioPath=cherrioConfig.getAbsolutePath();
		logger.info("cherrioPath:"+cherrioPath);


		URL daemon = Thread.currentThread().getContextClassLoader().getResource("daemon.properties");
		File daemonFile = new File(daemon.toURI());
		String daemonPath=daemonFile.getAbsolutePath();
		//String configURL = "D:\\dream\\dream-server\\src\\test\\java\\srv\\test\\log4j.properties";
		//PropertyConfigurator.configure(configURL);

		//String xmlPath="D:\\dream\\dream-server\\src\\test\\java\\srv\\test\\cherrio.xml";

		//String path = test.class.getResource("cherry.xml").getPath();
		//logger.info("cherry: "+path);

		srv.regDaemon(daemonPath);
		srv.initReceiver(cherrioPath);
		while(true)
		{
			Thread.sleep(1000);
			logger.info("===============sleep");
		}
	}
}