package com.thinkgem.jeesite.modules.user.entity;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by wyxiang on 17/4/30.
 */
public class UserChargeValidate  extends DataEntity<UserChargeValidate> {



    @NotEmpty(message = "充值帐号不能为空!")
    private String userName;

    @NotEmpty
    private String chargeMoney;

    @NotEmpty
    private String chargeType;

    @NotEmpty
    private String commt;


    /**
     * 验证模型字段
     */
    public  void validateModule(boolean isInsert) throws ValidationException {

        if(StringUtils2.isBlank(userName)){
            throw new ValidationException("用户名不能为空!");
        }
        if(StringUtils2.isBlank(chargeMoney)){
            throw new ValidationException("充值金额不能为空!");
        }
        if(StringUtils2.isBlank(chargeType)){
            throw new ValidationException("充值类型不能为空!");
        }
        if(StringUtils2.isBlank(commt)){
            throw new ValidationException("备注不能为空!");
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getCommt() {
        return commt;
    }

    public void setCommt(String commt) {
        this.commt = commt;
    }
}
