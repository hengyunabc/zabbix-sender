package io.github.hengyunabc.zabbix.sender.test;

import com.alibaba.fastjson.JSONObject;
import io.github.hengyunabc.zabbix.sender.DataObject;
import io.github.hengyunabc.zabbix.sender.SenderResult;
import io.github.hengyunabc.zabbix.sender.ZabbixSender;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ZabbixSenderTest {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ZabbixSenderTest.class);
	String host;
	int port;
	String hostId;

	@Before
	public void setup() throws IOException {
		Properties props = new Properties();
		Reader reader = null;
		try {
			reader = new InputStreamReader(getClass().getResourceAsStream("/test.properties"));
			props.load(reader);
		} finally {
			if (reader != null) reader.close();
		}

		host = props.getProperty("zabbix.server.host", "127.0.0.1");
		port = Integer.parseInt(props.getProperty("zabbix.server.port","49156"));
		hostId = props.getProperty("zabbix.hostId", "172.17.42.1");
	}


	@Test
	public void test_LLD_rule() throws IOException {
		ZabbixSender zabbixSender = new ZabbixSender(host, port);

		DataObject dataObject = new DataObject();
		dataObject.setHost(hostId);
		dataObject.setKey("healthcheck[dw,notificationserver]");

		JSONObject data = new JSONObject();
		List<JSONObject> aray = new LinkedList<JSONObject>();
		JSONObject xxx = new JSONObject();
		xxx.put("hello", "hello");

		aray.add(xxx);
		data.put("data", aray);

		dataObject.setValue(data.toJSONString());
		dataObject.setClock(System.currentTimeMillis()/1000);
		SenderResult result = zabbixSender.send(dataObject);

		logger.info("result: {}, success: {}", result, result.success());
	}

	@Test
	public void test() throws IOException {
		ZabbixSender zabbixSender = new ZabbixSender(host, port);

		DataObject dataObject = new DataObject();
		dataObject.setHost(hostId);
		dataObject.setKey("a[test, jvm.mem.non-heap.used]");
		dataObject.setValue("10");
		dataObject.setClock(System.currentTimeMillis()/1000);
		SenderResult result = zabbixSender.send(dataObject);

		logger.info("result: {}, success: {}", result, result.success());
	}

}
