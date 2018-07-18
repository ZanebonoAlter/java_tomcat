package com.tomcat.status.service.impl;

import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tomcat.status.service.MessageService;

@Service("MessageService")
public class MessageServiceImpl implements MessageService{

	@Override
	public void sendMessage(int number) throws ClientException{
		// TODO Auto-generated method stub
		 //设置超时时间-可自行调整
	    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	    System.setProperty("sun.net.client.defaultReadTimeout", "10000");

	    //初始化ascClient需要的几个参数
	    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
	    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）

	    //替换成你的AK
	    final String accessKeyId = "LTAIZ9RrdaRD8xIK";
	    final String accessKeySecret = "Pw1mTaFrzcrt64L3WwBMAMAkSk8VOC";//你的accessKeySecret，
	    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
	    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	    IAcsClient acsClient = new DefaultAcsClient(profile);

	    //组装请求对象
	     SendSmsRequest request = new SendSmsRequest();
	     //使用post提交
	     request.setMethod(MethodType.POST);
	     //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
	     request.setPhoneNumbers("13216157741");
	     //必填:短信签名-可在短信控制台中找到----就是签名名称
	     request.setSignName("张俊杰");
	     //必填:短信模板-可在短信控制台中找到 例如：SMS_127156997
	     request.setTemplateCode("SMS_139228720");
	     //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	     //request.setTemplateParam("{\"code\":\"177659\"}");
	     //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
	     //request.setSmsUpExtendCode("90997");
	     //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
	     //request.setOutId("yourOutId");

	    //请求失败这里会抛ClientException异常
	     SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
	     if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
	     //请求成功
	         System.out.println("短信发送成功请查收");
	     }else {
	        System.out.println("发送短信失败");
	    }   
	}

}
