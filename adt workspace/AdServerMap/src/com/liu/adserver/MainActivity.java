package com.liu.adserver;

import java.util.HashMap;

import org.json.JSONArray;

import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToTagRequest;
import com.baidu.yun.push.model.PushMsgToTagResponse;

public class MainActivity extends Activity {
	private EditText et_Title;// 标题
	private EditText et_Discript;// 内容
	private EditText et_jingdu;// 经度
	private EditText et_weidu;// 纬度
	private EditText et_location;// 位置关键字
	private EditText et_distance;// 距离
	private EditText et_tag;// 标签
	private EditText et_link;// 链接

	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			Toast.makeText(MainActivity.this, msg.obj.toString(), 0).show();
			return true;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

	}

	public void sendtongzhimessage(View view) {
		/*
		 * if (!checkInput(et_location, et_jingdu, et_weidu, et_distance)) {
		 * Toast.makeText(MainActivity.this, "位置关键字或者经纬度距离建议完整输入一个  正在全设备推送 ",
		 * 0).show();
		 * 
		 * } else { Toast.makeText(MainActivity.this, "正在根据筛选信息推送信息 ",
		 * 0).show(); }
		 * 
		 * new Thread(new Runnable() {
		 * 
		 * public void run() { // TODO 自动生成的方法存根 try { JSONObject array = new
		 * JSONObject(); array.put("title", et_Title.getText().toString());
		 * array.put("disc", et_Discript.getText().toString());
		 * array.put("distance", et_distance.getText().toString());
		 * array.put("jingdu", et_jingdu.getText().toString());
		 * array.put("location", et_location.getText().toString());
		 * array.put("time", et_time.getText().toString()); array.put("weidu",
		 * et_weidu.getText().toString()); array.put("link",
		 * et_link.getText().toString()); array.put("show", 1); if
		 * (et_tag.getText().toString().equals("")) { Message message =
		 * Message.obtain(); message.obj = "标签为空  正在根据筛选信息推送";
		 * handler.handleMessage(message);
		 * sendmessage(et_Title.getText().toString(), array); } else { Message
		 * message = Message.obtain(); message.obj = "标签不为空  正在根据筛选信息推送";
		 * handler.handleMessage(message);
		 * sendmessage(et_Title.getText().toString(), array); //
		 * PushMsgToAll.main(new String[]{}); } } catch (PushClientException e)
		 * { e.printStackTrace(); } catch (PushServerException e) { //
		 * e.printStackTrace(); } } }).start();
		 */
		if (!checkInput(et_location, et_jingdu, et_weidu, et_distance)) {
			Toast.makeText(MainActivity.this, "位置关键字或者经纬度距离建议完整输入一个  正在全设备推送 ",
					0).show();

		} else {
			Toast.makeText(MainActivity.this, "正在根据筛选信息推送信息 ", 0).show();

		}

		new Thread(new Runnable() {

			public void run() { // TODO 自动生成的方法存根
				try {
					JSONObject array = new JSONObject();
					array.put("title", et_Title.getText().toString());
					array.put("disc", et_Discript.getText().toString());
					array.put("distance", et_distance.getText().toString());
					array.put("jingdu", et_jingdu.getText().toString());
					array.put("location", et_location.getText().toString());
					array.put("weidu", et_weidu.getText().toString());
					array.put("link", et_link.getText().toString());
					array.put("show", 1);
					if (et_tag.getText().toString().equals("")) {
						Message message = Message.obtain();
						message.obj = "标签为空  正在根据筛选信息推送";
						handler.sendMessage(message);
						sendmessage(et_Title.getText().toString(), array);
					} else {
						Message message = Message.obtain();
						message.obj = "标签不为空  正在根据筛选信息推送";
						handler.sendMessage(message);
						try {
							sendToTagMessage(array);
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
					// PushMsgToAll.main(new String[]{});
				} catch (PushClientException e) {
					e.printStackTrace();
				} catch (PushServerException e) { //
					e.printStackTrace();
				}
			}
		}).start();

	}

	private boolean checkInput(EditText et_location2, EditText et_jingdu2,
			EditText et_weidu2, EditText et_distance2) {
		// TODO 自动生成的方法存根
		if (et_location2.getText().toString().equals("")) {
			if (et_jingdu2.getText().toString().equals("")
					|| et_weidu2.getText().toString().equals("")
					|| et_distance.getText().toString().equals("")) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public void sendtouchuanxiaoxi(View view) {
		if (!checkInput(et_location, et_jingdu, et_weidu, et_distance)) {
			Toast.makeText(MainActivity.this, "位置关键字或者经纬度距离建议完整输入一个  正在全设备推送 ",
					0).show();

		} else {
			Toast.makeText(MainActivity.this, "正在根据筛选信息推送信息 ", 0).show();

		}

		new Thread(new Runnable() {

			public void run() { // TODO 自动生成的方法存根
				try {
					JSONObject array = new JSONObject();
					array.put("title", et_Title.getText().toString());
					array.put("disc", et_Discript.getText().toString());
					array.put("distance", et_distance.getText().toString());
					array.put("jingdu", et_jingdu.getText().toString());
					array.put("location", et_location.getText().toString());
					array.put("weidu", et_weidu.getText().toString());
					array.put("link", et_link.getText().toString());
					array.put("show", 0);
					if (et_tag.getText().toString().equals("")) {
						Message message = Message.obtain();
						message.obj = "标签为空  正在根据筛选信息推送";
						handler.handleMessage(message);
						sendmessage(et_Title.getText().toString(), array);
					} else {
						Message message = Message.obtain();
						message.obj = "标签不为空  正在根据筛选信息推送";
						handler.handleMessage(message);
						try {
							sendToTagMessage(array);
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
					// PushMsgToAll.main(new String[]{});
				} catch (PushClientException e) {
					e.printStackTrace();
				} catch (PushServerException e) { //
					e.printStackTrace();
				}
			}
		}).start();

	}

	protected void sendToTagMessage(JSONObject array) throws Exception {
		// TODO 自动生成的方法存根
		String apiKey = "hOeL93bvRtdivdukZvINhET9s6pWaezS";
		String secretKey = "PDVhwyUC80FeRMdm7eU4N8RAwtZ8ZcTy";
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. specify request arguments
			// pushTagTpye = 1 for common tag pushing
			String string = array.toString();
			PushMsgToTagRequest request = new PushMsgToTagRequest()
					.addTagName(et_tag.getText().toString())
					.addMsgExpires(new Integer(3600))
					.addMessageType(0)
					// .addSendTime(System.currentTimeMillis() / 1000 + 70).
					.addMessage(
							"{\"title\":\"消息\",\"description\":" + string + "}")
					.addDeviceType(3);
			// 5. http request
			PushMsgToTagResponse response = pushClient.pushMsgToTag(request);
			// Http请求返回值解析
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime() + ",timerId: "
					+ response.getTimerId());
		} catch (PushClientException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMsg: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}
	}

	private void initView() {
		// TODO 自动生成的方法存根
		et_Discript = (EditText) findViewById(R.id.disc_et);
		et_distance = (EditText) findViewById(R.id.distance_et);
		et_jingdu = (EditText) findViewById(R.id.longitude_et);
		et_location = (EditText) findViewById(R.id.location_et);

		et_Title = (EditText) findViewById(R.id.title_et);
		et_weidu = (EditText) findViewById(R.id.latitude_et);
		et_tag = (EditText) findViewById(R.id.tag_et);
		et_link = (EditText) findViewById(R.id.link_et);

	}

	public void openweb(View view) {

		Intent intent = new Intent(MainActivity.this, webViewActivity.class);
		startActivity(intent);
		/*
		 * String url = "http://api.map.baidu.com/lbsapi/getpoint/index.html";
		 * 
		 * Intent notificationIntent = new Intent();
		 * notificationIntent.setAction("android.intent.action.VIEW"); Uri
		 * content_url = Uri.parse(url);
		 * notificationIntent.setData(content_url);
		 * startActivity(notificationIntent);
		 */
	}

	public void sendmessage(String title, JSONObject array)
			throws PushClientException, PushServerException {
		System.out.println(array.toString());
		// 1. get apiKey and secretKey from developer console
		/*
		 * String apiKey = "hOeL93bvRtdivdukZvINhET9s6pWaezS"; String secretKey
		 * = "PDVhwyUC80FeRMdm7eU4N8RAwtZ8ZcTy"; PushKeyPair pair = new
		 * PushKeyPair(apiKey, secretKey); // 2. build a BaidupushClient object
		 * to access released interfaces BaiduPushClient pushClient = new
		 * BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);
		 * 
		 * // 3. register a YunLogHandler to get detail interacting information
		 * // in this request. pushClient.setChannelLogHandler(new
		 * YunLogHandler() {
		 * 
		 * @Override public void onHandle(YunLogEvent event) {
		 * System.out.println(event.getMessage()); } }); try {
		 * PushMsgToAllRequest request = new PushMsgToAllRequest()
		 * .addMsgExpires(new Integer(3600)) .addMessageType(1) . //
		 * 设置消息类型,0表示透传消息,1表示通知,默认为0.
		 * addMessage("{\"title\":\"TEST\",\"description\":\"" + array + "\"}").
		 * // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例70秒后推送 addDeviceType(3); // 5. http
		 * request PushMsgToAllResponse response =
		 * pushClient.pushMsgToAll(request); // Http请求返回值解析
		 * System.out.println("msgId: " + response.getMsgId() + ",sendTime: " +
		 * response.getSendTime() + ",timerId: " + response.getTimerId()); }
		 * catch (PushClientException e) { if (BaiduPushConstants.ERROROPTTYPE)
		 * { throw e; } else { e.printStackTrace(); } } catch
		 * (PushServerException e) { if (BaiduPushConstants.ERROROPTTYPE) {
		 * throw e; } else { System.out.println(String.format(
		 * "requestId: %d, errorCode: %d, errorMsg: %s", e.getRequestId(),
		 * e.getErrorCode(), e.getErrorMsg())); } }
		 */
		String apiKey = "hOeL93bvRtdivdukZvINhET9s6pWaezS";
		String secretKey = "PDVhwyUC80FeRMdm7eU4N8RAwtZ8ZcTy";
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			String string = array.toString();
			System.out.println(string);
			// 4. specify request arguments
			PushMsgToAllRequest request = new PushMsgToAllRequest()
					.addMsgExpires(new Integer(3600))
					.addMessageType(0)
					. // 设置消息类型,0表示透传消息,1表示通知,默认为0.
					addMessage(
							"{\"title\":\"消息\",\"description\":" + string + "}")
					.
					// 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例70秒后推送
					addDeviceType(3);
			// 5. http request
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
			// Http请求返回值解析
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime() + ",timerId: "
					+ response.getTimerId());
		} catch (PushClientException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMsg: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
				// Toast.makeText(getApplicationContext(), e.getErrorMsg(),
				// 0).show();
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO 自动生成的方法存根
		super.onNewIntent(intent);
		et_jingdu.setText(intent.getDoubleExtra("jingdu", 0) + "");
		et_weidu.setText(intent.getDoubleExtra("weidu", 0) + "");
	}
}
