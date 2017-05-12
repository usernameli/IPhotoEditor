package com.liu.adserver;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;

public class PushMsgToAll {  
    public static void main(String[] args) 
            throws PushClientException,PushServerException {
        // 1. get apiKey and secretKey from developer console
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
            PushMsgToAllRequest request = new PushMsgToAllRequest()
                    .addMsgExpires(new Integer(3600)).
                     addMessageType(1).              //������Ϣ����,0��ʾ͸����Ϣ,1��ʾ֪ͨ,Ĭ��Ϊ0.
                     addMessage("{\"title\":\"TEST\",\"description\":\"Hello Baidu push!\"}").
                    // ���ö�ʱ����ʱ�䣬���賬����ǰʱ��һ���ӣ���λ��.ʵ��70�������
                    addDeviceType(3);
            // 5. http request
            PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
            // Http���󷵻�ֵ����
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
}