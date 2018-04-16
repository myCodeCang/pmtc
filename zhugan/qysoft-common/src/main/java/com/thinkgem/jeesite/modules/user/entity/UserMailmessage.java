/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 站内信Entity
 * @author cais
 * @version 2017-04-26
 */
public class UserMailmessage extends DataEntity<UserMailmessage> {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String messageParentId;		// 父消息
	private String fromMemberName;		// 发送人
	private String toMemberName;		// 接收人
	private String messageTitle;		// 消息标题
	private String messageBody;		// 消息内容
	private Date addtime;		// 添加时间
	private String messageType;		// 消息类型
	private String readMemberName;		// 消息读取人
	private String sendboxStatus;		// 发件箱状态：1-&gt;正常，0-&gt;已删除
	private String receiveboxStatus;		// 收件箱状态：1-&gt;正常，0-&gt;已删除
	private Date beginAddtime;		// 开始 添加时间
	private Date endAddtime;		// 结束 添加时间
	private String sendName;		//发件箱筛选
	private String receName;		//发件箱筛选

	/**
	 * 验证模型字段
	 */
	public  void validateModule(boolean isInsert) throws ValidationException {
		if(StringUtils2.isBlank(id)){
			throw new ValidationException("ID不能为空!");
		}
		if(StringUtils2.isBlank(messageParentId)){
			throw new ValidationException("父消息不能为空!");
		}
		if(StringUtils2.isBlank(fromMemberName)){
			throw new ValidationException("发送人不能为空!");
		}
		if(StringUtils2.isBlank(toMemberName)){
			throw new ValidationException("接收人不能为空!");
		}if(StringUtils2.isBlank(messageTitle)){
			throw new ValidationException("消息标题不能为空!");
		}
		if(addtime == null){
			throw new ValidationException("添加时间不能为空!");
		}
		if(StringUtils2.isBlank(messageType)){
			throw new ValidationException("消息类型不能为空!");
		}
		if(StringUtils2.isBlank(readMemberName)){
			throw new ValidationException("消息读取人不能为空!");
		}
		if(beginAddtime == null){
			throw new ValidationException("添加时间不能为空!");
		}
		if(endAddtime == null){
			throw new ValidationException("结束时间不能为空!");
		}


	}

	@Override
	public void preInsert() throws ValidationException {
		validateModule(true);
		super.preInsert();
	}

	@Override
	public void preUpdate() throws  ValidationException{
		validateModule(false);
		super.preUpdate();
	}


	public UserMailmessage() {
		super();
	}

	public UserMailmessage(String id){
		super(id);
	}

	@Length(min=0, max=11, message="父消息长度必须介于 0 和 11 之间")
	public String getMessageParentId() {
		return messageParentId;
	}

	public void setMessageParentId(String messageParentId) {
		this.messageParentId = messageParentId;
	}
	
	@Length(min=1, max=255, message="发送人长度必须介于 1 和 255 之间")
	public String getFromMemberName() {
		return fromMemberName;
	}

	public void setFromMemberName(String fromMemberName) {
		this.fromMemberName = fromMemberName;
	}
	
	@Length(min=1, max=255, message="接收人长度必须介于 1 和 255 之间")
	public String getToMemberName() {
		return toMemberName;
	}

	public void setToMemberName(String toMemberName) {
		this.toMemberName = toMemberName;
	}
	
	@Length(min=1, max=255, message="消息标题长度必须介于 1 和 255 之间")
	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@Length(min=0, max=10, message="消息类型长度必须介于 0 和 10 之间")
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	@Length(min=0, max=4000, message="消息读取人长度必须介于 0 和 4000 之间")
	public String getReadMemberName() {
		return readMemberName;
	}

	public void setReadMemberName(String readMemberName) {
		this.readMemberName = readMemberName;
	}
	
	@Length(min=0, max=1, message="发件箱状态：1-&gt;正常，0-&gt;已删除长度必须介于 0 和 1 之间")
	public String getSendboxStatus() {
		return sendboxStatus;
	}

	public void setSendboxStatus(String sendboxStatus) {
		this.sendboxStatus = sendboxStatus;
	}
	
	@Length(min=0, max=1, message="收件箱状态：1-&gt;正常，0-&gt;已删除长度必须介于 0 和 1 之间")
	public String getReceiveboxStatus() {
		return receiveboxStatus;
	}

	public void setReceiveboxStatus(String receiveboxStatus) {
		this.receiveboxStatus = receiveboxStatus;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBeginAddtime() {
		return beginAddtime;
	}

	public void setBeginAddtime(Date beginAddtime) {
		this.beginAddtime = beginAddtime;
	}
	
	public Date getEndAddtime() {
		return endAddtime;
	}

	public void setEndAddtime(Date endAddtime) {
		this.endAddtime = endAddtime;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getReceName() {
		return receName;
	}

	public void setReceName(String receName) {
		this.receName = receName;
	}
		
}