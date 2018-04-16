/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.UserUserinfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 会员列表DAO接口
 * @author wyxiang
 * @version 2017-03-25
 */
@MyBatisDao
public interface UserUserinfoDao extends CrudDao<UserUserinfo> {
	

	/**
	 * 锁表查询用户,涉及到用户资金等操作时需要锁表查询
	 * @param username
	 * @return
	 */
	public UserUserinfo getByNameLock(String username);

	/**
	 * 锁表查询用户,涉及到用户资金等操作时需要锁表查询
	 * @param id
	 * @return
	 */
	public UserUserinfo getLock(String id);

	/**
	 * 更新用户级别
	 * @param userId
	 * @param userLevelId
	 */
	public void updateUserLevelId(@Param("id") String userId, @Param("userLevelId") String userLevelId);
	/**
	 * 根据id修改用户头像
	 */
	public void setUserAvatar(UserUserinfo userinfo);
	/**
	 * 获取单条数据
	 * @param username
	 * @return
	 */
	public UserUserinfo getByName(String username);
	
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	public UserUserinfo getByMobile(String mobile);

	UserUserinfo getByShopId(String shopId);
	/**
	 * 根据用户名称 user_name 修改是否保单中心
	 * @param userinfo
	 * @return 
	 */
	public boolean setUserCenter(UserUserinfo userinfo);

	public List<UserUserinfo> getUsersByParentName( String parentName);


	public List<UserUserinfo> getUsersByServerName(String serverName);

	public List<UserUserinfo> getUsersByWalterName(String walterName);


	public void updateUserState(@Param("id") String id, @Param("state") String state);

	public void updateUserMobile(@Param("name") String name,@Param("mobile") String mobile);

	public void updateUserPwd(@Param("name") String name,@Param("newpassword") String newpassword);
	public void updateUserEmail(@Param("name") String name,@Param("userEmail") String newpassword);

	public void updateUserPaypass(@Param("name") String name,@Param("newpaypass") String newpaypass);

	public void updateUserMoney(@Param("name") String name, @Param("money") BigDecimal money);

	public void updateUserScore(@Param("name") String name, @Param("money") BigDecimal money );

	public void updateUserCoin(@Param("name") String name, @Param("coin") BigDecimal money );

	public 	void updateUserOtherMoney(@Param("name") String name, @Param("money") BigDecimal money, @Param("moneyType") String moneyType);

    public void updateUseroffice(@Param("officeId") String officeId,@Param("parentListLike") String parentListLike);
	public void updateUserMainPerformance(@Param("userName") String userName,@Param("performance") int performance);

	public void updateUserExtendPerformance(@Param("userName") String userName,@Param("performance") int performance);


	/**
	 * 主区余量
	 * @param userName
	 * @param performance
	 */
	public void updateUserMainRemainderPerformance(@Param("userName") String userName,@Param("performance") int performance);

	/**
	 * 拓展区余量
	 * @param userName
	 * @param performance
	 */
	public void updateUserExtendRemainderPerformance(@Param("userName") String userName,@Param("performance") int performance);

	/**
	 *  修改用户类型
	 * @param userName
	 * @param userType
	 */
    public void updateUserType(@Param("userName")  String userName, @Param("userType")  String userType);

    public void updateActiveTime(@Param("id")String id, @Param("activeTime")Date activeTime);

    public  int getMaxUserNo();

	public void updateUserZhuanqupass(@Param("userName") String userName, @Param("newZhuanquPass") String newZhuanquPass);

    public BigDecimal getTeamLevelMoney(UserUserinfo searchUser);

    public void updateIsShop(@Param("userName") String userName, @Param("isShop") String isShop);

	public void updateActiveStatus(@Param("userName") String userName, @Param("activeStatus") String activeStatus);

    public void updateIsProxy(@Param("userName") String userName,@Param("activeStatus") String activeStatus,@Param("mainPerformance") String mainPerformanceRemainder,@Param("userLevelId") String userLevelId);

    public BigDecimal getSumMoney2();

    public void updateUserRenzheng(@Param("renZheng") String renZheng,@Param("userName") String userName);

    public void updateUserNiceName(@Param("userName") String userName, @Param("nickName") String nickName);

    public void updateIsCheck(@Param("userName") String userName, @Param("isCheck") String isCheck);

    List<UserUserinfo> findTeamByUserType(@Param("parentListLike")String parentListLike, @Param("userType")int userType);

    public List<UserUserinfo> findLists(UserUserinfo userinfo);

	public void updateUserIsUsercenter(@Param("userName") String userName, @Param("isUsercenter") String isUsercenter);

    void updateIdCard(@Param("userName") String userName,@Param("idCard") String idCard);

	void updateShopId(@Param("userName") String userName,@Param("shopId") String shopId);

	UserUserinfo getByRemarks (String remarks);

    void updateLoginInfo(UserUserinfo user);

	BigDecimal sumUserMoney2(String likeNameId);

	UserUserinfo sumUserEveryMoney();

	void updateIsShop(String isShop);

	UserUserinfo getUserByUsercenterAddress(String usercenterAddress);

	void updateUsercenterAddressByName(@Param("userName") String userName ,@Param("usercenterAddress") String usercenterAddress);
}