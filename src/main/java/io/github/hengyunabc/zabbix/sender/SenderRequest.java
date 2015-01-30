package io.github.hengyunabc.zabbix.sender;

import java.util.List;

import com.alibaba.fastjson.JSON;

public class SenderRequest {
	static final byte header[] = { 'Z', 'B', 'X', 'D', '\1' };

	String request = "sender data";

	long clock;

	List<DataObject> data;

	public byte[] toBytes() {
		// https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/2.0
		// https://www.zabbix.org/wiki/Docs/protocols/zabbix_sender/1.8/java_example

		byte[] jsonBytes = JSON.toJSONBytes(this);

		byte[] result = new byte[header.length + 4 + 4 + jsonBytes.length];

		System.arraycopy(header, 0, result, 0, header.length);

		result[header.length] = (byte) (jsonBytes.length & 0xFF);
		result[header.length + 1] = (byte) ((jsonBytes.length >> 8) & 0x00FF);
		result[header.length + 2] = (byte) ((jsonBytes.length >> 16) & 0x0000FF);
		result[header.length + 3] = (byte) ((jsonBytes.length >> 24) & 0x000000FF);

		System.arraycopy(jsonBytes, 0, result, header.length + 4 + 4,
				jsonBytes.length);
		return result;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public long getClock() {
		return clock;
	}

	public void setClock(long clock) {
		this.clock = clock;
	}

	public List<DataObject> getData() {
		return data;
	}

	public void setData(List<DataObject> data) {
		this.data = data;
	}
}
