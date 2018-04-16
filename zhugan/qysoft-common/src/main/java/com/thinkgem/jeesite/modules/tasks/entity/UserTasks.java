/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tasks.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 任务调度Entity
 * @author yankai
 * @version 2017-06-17
 */
public class UserTasks extends DataEntity<UserTasks> {
	
	private static final long serialVersionUID = 1L;

	private PropertyChangeSupport propertyChangeSupport = null;

	private String name;		// 任务名称
	private String taskGroup;	//任务组
	private String description;		// 任务描述
	private String status;		// 状态  0--停止  1--运行
	private Date lastTime;		// 上次执行时间
	private String lastState;		// 上次执行状态 0--失败   1--成功
	private String scheduleCron;		// 调度表达式
	private String beanName;		// 业务Bean在Spring容器中的名称

	public static final String TASK_STATUS_RUN = "1";
	public static final String TASK_STATUS_STOP = "0";

	public static final String TASK_RUN_SUCCESS = "1";
	public static final String TASK_RUN_FAILED = "0";
	
	public UserTasks() {
		super();
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public UserTasks(String id){
		super(id);
	}

	@Length(min=0, max=64, message="任务名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=128, message="任务名称长度必须介于 0 和 128 之间")
	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}
	
	@Length(min=0, max=255, message="任务描述长度必须介于 0 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=1, message="状态  0--停止  1--运行长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (this.status != null && this.status.equals(status)) {
			return;
		}

		String oldValue = this.status;
		this.status = status;

		if (!StringUtils2.isBlank(id)) {
			firePropertyChange("status", oldValue, status);
		}
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		if (this.lastTime != null && this.lastTime.equals(lastTime)) {
			return;
		}

		Date oldValue = this.lastTime;
		this.lastTime = lastTime;
		firePropertyChange("lastTime", oldValue, lastTime);
	}
	
	@Length(min=0, max=1, message="上次执行状态 0--失败   1--成功长度必须介于 0 和 1 之间")
	public String getLastState() {
		return lastState;
	}

	public void setLastState(String lastState) {
		if (this.lastState != null && this.lastState.equals(lastState)) {
			return;
		}

		String oldValue = this.lastState;
		this.lastState = lastState;
		firePropertyChange("lastState", oldValue, lastState);
	}
	
	@Length(min=0, max=255, message="调度表达式长度必须介于 0 和 255 之间")
	public String getScheduleCron() {
		return scheduleCron;
	}

	public void setScheduleCron(String scheduleCron) {
		if (this.scheduleCron != null && this.scheduleCron.equals(scheduleCron)) {
			return;
		}

		String oldValue = this.scheduleCron;
		this.scheduleCron = scheduleCron;
		if (!StringUtils2.isBlank(id)) {
			firePropertyChange("scheduleCron", oldValue, scheduleCron);
		}
	}
	
	@Length(min=0, max=255, message="业务Bean在Spring容器中的名称长度必须介于 0 和 255 之间")
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public void preInsert() throws ValidationException {
		super.preInsert();

		if (StringUtils2.isBlank(lastState)) {
			lastState = TASK_RUN_FAILED;
		}

		if (StringUtils2.isBlank(status)) {
			status = TASK_STATUS_RUN;
		}

		validateModule(true);
	}

	@Override
	public void preUpdate() throws ValidationException {
		super.preUpdate();
		if (StringUtils2.isBlank(lastState)) {
			lastState = TASK_RUN_FAILED;
		}

		if (StringUtils2.isBlank(status)) {
			status = TASK_STATUS_RUN;
		}

		validateModule(false);
	}

	@Override
	protected void validateModule(boolean isInsert) throws ValidationException {
		if (StringUtils2.isBlank(name)) {
			throw new ValidationException("失败: 任务名称不能为空!");
		}

		if (StringUtils2.isBlank(taskGroup)) {
			throw new ValidationException("失败: 任务组不能为空!");
		}

		if (StringUtils2.isBlank(description)) {
			throw new ValidationException("失败: 任务描述不能为空!");
		}

		if (StringUtils2.isBlank(scheduleCron)) {
			throw new ValidationException("失败: 调度表达式不能为空!");
		}

		if (StringUtils2.isBlank(beanName)) {
			throw new ValidationException("失败: 业务Bean名称不能为空!");
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}

		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners() {
		return propertyChangeSupport.getPropertyChangeListeners();
	}

	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}