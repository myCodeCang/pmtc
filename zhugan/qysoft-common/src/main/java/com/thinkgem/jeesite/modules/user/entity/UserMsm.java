/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 保存用户短信Entity
 *
 * @author yankai
 * @version 2017-05-28
 */
public class UserMsm extends DataEntity<UserMsm> {

    private static final long serialVersionUID = 1L;


    private String mobile;        // 用户名
    private String msg;        // 短信内容
    private Date createTime;        // 创建时间

    public UserMsm() {
        super();
    }

    public UserMsm(String id) {
        super(id);
    }

    /**
     * 验证模型字段
     */
    public  void validateModule(boolean isInsert) throws ValidationException {
        if (mobile == null) {
            throw new ValidationException("手机号不能为空");
        }

        if (StringUtils2.isBlank(msg)) {
            throw new ValidationException("短信内容不能为空");
        }

        if (StringUtils2.isBlank(mobile)) {
            throw new ValidationException("电话号码不能为空");
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

    @Length(min = 0, max = 255, message = "用户名长度必须介于 0 和 255 之间")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Length(min = 0, max = 255, message = "短信内容长度必须介于 0 和 255 之间")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static String generateVerifyCode(int bitCount) {
        return IdGen.randomNum(bitCount);
    }
}