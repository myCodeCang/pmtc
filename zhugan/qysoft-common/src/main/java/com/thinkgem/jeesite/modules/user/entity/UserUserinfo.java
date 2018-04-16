/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils2;
import com.thinkgem.jeesite.common.utils.VerifyUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 会员列表Entity
 *
 * @author wyxiang
 * @version 2017-03-25
 */
public class UserUserinfo extends DataEntity<UserUserinfo> {

    private static final long serialVersionUID = 1L;
    private String id;
    @ExcelField(title="账号", align=2, sort=1)
    private String userName;        // 账号
    @ExcelField(title="手机号", align=2, sort=1)
    private String mobile;        // 手机号
    @ExcelField(title="真实姓名", align=2, sort=1)
    private String trueName;        // 真实姓名
    private String userNicename ;        // 昵称
    private String userPass;        // 登录密码
    private String bankPassword;        // 支付密码
    private  String zhuanquPass;    //专区密码
    private int levelNo;        // 当前层级
    private String officeId ;       //归属部门
    private Integer userNo;        // 点位编号
    private UserLevel userLevel;
    private String renZheng  ;        //实名认证
    private String userLevelId;
    private String avatar;        // 头像
    @ExcelField(title="身份证号", align=2, sort=1)
    private String idCard;        // 身份证号
    private String sex;        // 性别
    private String userEmail;        // 邮箱
    private String lastLoginIp;        // 最后登录ip
    private Date lastLoginTime;        // 最后登录时间
    private String userStatus  ;        // 用户状态
    private String userType  ;        // 用户类型
    @ExcelField(title="余额", align=2, sort=1)
    private BigDecimal money = new BigDecimal(0);        // 余额
    @ExcelField(title="积分", align=2, sort=1)
    private BigDecimal score = new BigDecimal(0);    // 积分
    @ExcelField(title="购肉券", align=2, sort=1)
    private BigDecimal coin = new BigDecimal(0);    // 报单币
    private BigDecimal money2 = new BigDecimal(0);    // 预留金额
    private BigDecimal money3 = new BigDecimal(0);        // 预留金额
    private BigDecimal money4 = new BigDecimal(0);        // 预留金额
    private BigDecimal money5 = new BigDecimal(0);        // 预留金额
    private BigDecimal money6 = new BigDecimal(0);        // 预留金额
    private String parentName;        // 接点人
    private String serverName;        // 报单中心
    private String walterName;        // 服务人
    private String parentList;        // 父列表
    private String isCheck;        // 是否检查
    private String weichat;        // 微信号
    private String qq;        // QQ号
    private String openid;        // 微信openid
    private Integer mainPerformance = 0 ;        // 主区业绩
    private Integer extendPerformance = 0;        // 扩展区业绩
    private int positionSite  ;        // 左右区
    private String isUsercenter;        // 是否报单中心
    private String usercenterLevel;        // 报单中心等级
    private String usercenterAddress;        // 报单中心地址
    private Date usercenterAddtime;        // 成为报单中心时间
    private String isSeeChild  ;        // 是否可查看下级
    private Date beginAddtime;		// 开始时间
    private Date endAddtime;		// 结束时间
    private int userZj;
    public int getUserZj() {
        return userZj;
    }

    public void setUserZj(int userZj) {
        this.userZj = userZj;
    }

    private int mainPerformanceRemainder; //左区余量
    private int extendPerformanceRemainder; //拓展区余量


    private Date activeTime; //激活时间
    private Date activeEndTime; //激活结束时间
    private String activeStatus ; //是否参加活动
    private String isShop;//是否是联营商家
    private Office office;
    private String areaId;
    private String shopId;
    //扩展查询
    private String levelIdBegin;    // 等级区间
    private  Date activeEndTimeBegin; //查询激活起止时间
    private  Date activeEndTimeEnd;
    private  String addressArea;
    private  String userTypeBegin;
    private int userNoBegin ; //用户红黑编号区间
    private int userNoEnd;
    private Area area; //区域级别
    private String auctionName; //拍卖品名称


    /**
     * 验证模型字段
     */
    public  void validateModule(boolean isInsert) throws ValidationException {


        if(mobile == null){
            throw new ValidationException("手机号不能为空!");
        }

//        if(StringUtils2.isBlank(userNicename)){
//            throw new ValidationException("昵称不能为空!");
//        }

        if (isInsert) {

            if (StringUtils2.isBlank(userPass)) {
                throw new ValidationException("失败:登陆密码不能为空!");

            } else {
                if(!VerifyUtils.isPassword(userPass)){
                    throw new ValidationException("失败:密码必须大于8位");
                }
            }

            if (StringUtils2.isBlank(bankPassword)) {
                throw new ValidationException("失败:支付密码不能为空!");

            } else {
                if(!VerifyUtils.isPassword(bankPassword)){
                    throw new ValidationException("失败:密码必须大于8位");
                }
            }

        }


//        if(StringUtils2.isBlank(userStatus)){
//            throw new ValidationException("用户类型不能为空!");
//        }
//        if(StringUtils2.isBlank(parentName)){
//            throw new ValidationException("接点人不能为空!");
//        }
//       if(StringUtils2.isBlank(serverName)){
//            throw new ValidationException("报单中心不能为空!");
//        }
//        if(StringUtils2.isBlank(walterName)){
//            throw new ValidationException("失败:推荐人不能为空!");
//        }
        if("".equals(officeId)){
            throw new ValidationException("失败:所属机构不能为空!");
        }
        
        
    }


    //扩展字段
    private String parentListLike ;


    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert() throws ValidationException {
        if(StringUtils2.isBlank(this.getUserNicename())){
            this.setUserNicename("匿名用户");
        }
        if(StringUtils2.isBlank(this.getUserStatus())){
            this.setUserStatus("1");
        }
        if(StringUtils2.isBlank(this.getOfficeId())){
            this.setOfficeId("1");
        }
        if(StringUtils2.isBlank(this.getRenZheng())){
            this.setRenZheng("0");
        }
        validateModule(true);
        super.preInsert();

        this.setId(IdGen.uuid("seq_userinfo"));

    }


    @Override
    public void preUpdate() throws ValidationException {
        validateModule(false);
        super.preUpdate();

    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
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
    public String getId() {
		return id;
	}

    public String getUserTypeBegin() {
        return userTypeBegin;
    }

    public void setUserTypeBegin(String userTypeBegin) {
        this.userTypeBegin = userTypeBegin;
    }

    public String getIsShop() {
        return isShop;
    }

    public void setIsShop(String isShop) {
        this.isShop = isShop;
    }

    public void setId(String id) {
		this.id = id;
	}

    public String getRenZheng() {
        return renZheng;
    }

    public void setRenZheng(String renZheng) {
        this.renZheng = renZheng;
    }

    public String getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(String userLevelId) {
        this.userLevelId = userLevelId;
    }

    public String getLevelIdBegin() {
        return levelIdBegin;
    }

    public void setLevelIdBegin(String levelIdBegin) {
        this.levelIdBegin = levelIdBegin;
    }

    public String getZhuanquPass() {
        return zhuanquPass;
    }

    public void setZhuanquPass(String zhuanquPass) {
        this.zhuanquPass = zhuanquPass;
    }

    public UserUserinfo() {
        super();
    }

    public UserUserinfo(String id) {
        super(id);
    }

    @Length(min = 1, max = 255, message = "账号长度必须介于 1 和 255 之间")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Length(min = 0, max = 50, message = "手机号长度必须介于 0 和 50 之间")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Length(min = 0, max = 50, message = "真实姓名长度必须介于 0 和 50 之间")
    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    @Length(min = 0, max = 50, message = "昵称长度必须介于 0 和 50 之间")
    public String getUserNicename() {
        return userNicename;
    }

    public void setUserNicename(String userNicename) {
        this.userNicename = userNicename;
    }

    @Length(min = 1, max = 60, message = "登录密码长度必须介于 1 和 60 之间")
    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {this.userPass = userPass;}

    @Length(min = 1, max = 60, message = "支付密码长度必须介于 1 和 60 之间")
    public String getBankPassword() {
        return bankPassword;
    }

    public void setBankPassword(String bankPassword) {this.bankPassword = bankPassword;}

    @Length(min = 0, max = 11, message = "当前层级长度必须介于 0 和 11 之间")
    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    @Length(min = 0, max = 11, message = "点位编号长度必须介于 0 和 11 之间")
    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }


    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }

    @Length(min = 0, max = 255, message = "头像长度必须介于 0 和 255 之间")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Length(min = 0, max = 50, message = "身份证号长度必须介于 0 和 50 之间")
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Length(min = 0, max = 1, message = "性别长度必须介于 0 和 1 之间")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Length(min = 0, max = 255, message = "邮箱长度必须介于 0 和 255 之间")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Length(min = 0, max = 255, message = "最后登录ip长度必须介于 0 和 255 之间")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Length(min = 0, max = 1, message = "用户状态长度必须介于 0 和 1 之间")
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Length(min = 0, max = 1, message = "用户类型长度必须介于 0 和 1 之间")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getScore() {
        return score;
    }

    public BigDecimal getCoin() {
        return coin;
    }

    public void setCoin(BigDecimal coin) {
        this.coin = coin;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getMoney2() {
        return money2;
    }

    public void setMoney2(BigDecimal money2) {
        this.money2 = money2;
    }

    public BigDecimal getMoney3() {
        return money3;
    }

    public void setMoney3(BigDecimal money3) {
        this.money3 = money3;
    }

    public BigDecimal getMoney4() {
        return money4;
    }

    public void setMoney4(BigDecimal money4) {
        this.money4 = money4;
    }

    public BigDecimal getMoney5() {
        return money5;
    }

    public void setMoney5(BigDecimal money5) {
        this.money5 = money5;
    }

    public BigDecimal getMoney6() {
        return money6;
    }

    public void setMoney6(BigDecimal money6) {
        this.money6 = money6;
    }

    @Length(min = 0, max = 255, message = "接点人长度必须介于 0 和 255 之间")
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Length(min = 0, max = 255, message = "报单中心长度必须介于 0 和 255 之间")
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Length(min = 0, max = 255, message = "服务人长度必须介于 0 和 255 之间")
    public String getWalterName() {
        return walterName;
    }

    public void setWalterName(String walterName) {
        this.walterName = walterName;
    }

    public String getParentList() {
        return parentList;
    }

    public void setParentList(String parentList) {
        this.parentList = parentList;
    }

    @Length(min = 0, max = 1, message = "是否检查长度必须介于 0 和 1 之间")
    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    @Length(min = 0, max = 50, message = "微信号长度必须介于 0 和 50 之间")
    public String getWeichat() {
        return weichat;
    }

    public void setWeichat(String weichat) {
        this.weichat = weichat;
    }

    @Length(min = 0, max = 50, message = "QQ号长度必须介于 0 和 50 之间")
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Length(min = 0, max = 100, message = "微信openid长度必须介于 0 和 100 之间")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Length(min = 0, max = 11, message = "主区业绩长度必须介于 0 和 11 之间")
    public Integer getMainPerformance() {
        return mainPerformance;
    }

    public void setMainPerformance(Integer mainPerformance) {
        this.mainPerformance = mainPerformance;
    }

    @Length(min = 0, max = 11, message = "扩展区业绩长度必须介于 0 和 11 之间")
    public Integer getExtendPerformance() {
        return extendPerformance;
    }

    public void setExtendPerformance(Integer extendPerformance) {
        this.extendPerformance = extendPerformance;
    }

    @Length(min = 0, max = 4, message = "左右区长度必须介于 0 和 4 之间")
    public int getPositionSite() {
        return positionSite;
    }

    public void setPositionSite(int positionSite) {
        this.positionSite = positionSite;
    }

    @Length(min = 0, max = 4, message = "是否报单中心长度必须介于 0 和 4 之间")
    public String getIsUsercenter() {
        return isUsercenter;
    }

    public void setIsUsercenter(String isUsercenter) {
        this.isUsercenter = isUsercenter;
    }

    @Length(min = 0, max = 4, message = "报单中心等级长度必须介于 0 和 4 之间")
    public String getUsercenterLevel() {
        return usercenterLevel;
    }

    public void setUsercenterLevel(String usercenterLevel) {
        this.usercenterLevel = usercenterLevel;
    }

    @Length(min = 0, max = 255, message = "报单中心地址长度必须介于 0 和 255 之间")
    public String getUsercenterAddress() {
        return usercenterAddress;
    }

    public void setUsercenterAddress(String usercenterAddress) {
        this.usercenterAddress = usercenterAddress;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUsercenterAddtime() {
        return usercenterAddtime;
    }

    public void setUsercenterAddtime(Date usercenterAddtime) {
        this.usercenterAddtime = usercenterAddtime;
    }

    @Length(min = 0, max = 4, message = "是否可查看下级长度必须介于 0 和 4 之间")
    public String getIsSeeChild() {
        return isSeeChild;
    }

    public void setIsSeeChild(String isSeeChild) {
        this.isSeeChild = isSeeChild;
    }

    public String getParentListLike() {
        return parentListLike;
    }

    public void setParentListLike(String parentListLike) {
        this.parentListLike = parentListLike;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(Date activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public Date getActiveEndTimeBegin() {
        return activeEndTimeBegin;
    }

    public void setActiveEndTimeBegin(Date activeEndTimeBegin) {
        this.activeEndTimeBegin = activeEndTimeBegin;
    }

    public Date getActiveEndTimeEnd() {
        return activeEndTimeEnd;
    }

    public void setActiveEndTimeEnd(Date activeEndTimeEnd) {
        this.activeEndTimeEnd = activeEndTimeEnd;
    }

    public int getUserNoBegin() {
        return userNoBegin;
    }

    public void setUserNoBegin(int userNoBegin) {
        this.userNoBegin = userNoBegin;
    }

    public int getUserNoEnd() {
        return userNoEnd;
    }

    public void setUserNoEnd(int userNoEnd) {
        this.userNoEnd = userNoEnd;
    }

    public int getMainPerformanceRemainder() {
        return mainPerformanceRemainder;
    }

    public void setMainPerformanceRemainder(int mainPerformanceRemainder) {
        this.mainPerformanceRemainder = mainPerformanceRemainder;
    }

    public int getExtendPerformanceRemainder() {
        return extendPerformanceRemainder;
    }

    public void setExtendPerformanceRemainder(int extendPerformanceRemainder) {
        this.extendPerformanceRemainder = extendPerformanceRemainder;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}