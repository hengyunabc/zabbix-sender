# zabbix-sender
java zabbix-sender

First you should know zabbix sender:

https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/2.0

https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/1.8/java_example

If you don't have a zabbix server, recommend use docker to setup test environment.

https://hub.docker.com/u/zabbix/

Support zabbix server 2.4/3.0.


##Example

Zabbix Sender do not create host/item, you have to create them by yourself, or try to use [zabbix-api](https://github.com/hengyunabc/zabbix-api).

1. Create/select a host in zabbix server.
1. Create a item in zabbix server, which name is "testItem", type is "Zabbix trapper".
1. Send data.
1. If success, you can find data in web browser. Open "Monitoring"/"Latest data", then filter with Item name or Hosts.

```java
		String host = "127.0.0.1";
		int port = 10051;
		ZabbixSender zabbixSender = new ZabbixSender(host, port);

		DataObject dataObject = new DataObject();
		dataObject.setHost("172.17.42.1");
		dataObject.setKey("test_item");
		dataObject.setValue("10");
		// TimeUnit is SECONDS.
		dataObject.setClock(System.currentTimeMillis()/1000);
		SenderResult result = zabbixSender.send(dataObject);

		System.out.println("result:" + result);
		if (result.success()) {
			System.out.println("send success.");
		} else {
			System.err.println("sned fail!");
		}
```

## Maven dependency

```xml
<dependency>
    <groupId>io.github.hengyunabc</groupId>
    <artifactId>zabbix-sender</artifactId>
    <version>0.0.2</version>
</dependency>
```

## Others

https://github.com/hengyunabc/zabbix-api

## License
Apache License V2
