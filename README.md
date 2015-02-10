# zabbix-sender
java zabbix-sender

First you should know zabbix sender:

https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/2.0

https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/1.8/java_example

If you don't have a zabbix server, recommend use docker to setup test enviroment.

https://github.com/berngp/docker-zabbix

##Example

```java
		String host = "127.0.0.1";
		int port = 10051;
		ZabbixSender zabbixSender = new ZabbixSender(host, port);

		DataObject dataObject = new DataObject();
		dataObject.setHost("172.17.42.1");
		dataObject.setKey("test_item");
		dataObject.setValue("10");
		dataObject.setClock(System.currentTimeMillis());
		SenderResult result = zabbixSender.send(dataObject);

		System.out.println("result:" + result);
		if (result.success()) {
			System.out.println("send success.");
		} else {
			System.err.println("sned fail!");
		}
		```
