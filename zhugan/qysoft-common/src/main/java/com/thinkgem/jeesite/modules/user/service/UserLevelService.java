/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.UserLevelDao;
import com.thinkgem.jeesite.modules.user.entity.UserLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 用户等级表Service
 *
 * @author cai
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class, ValidationException.class})
public class UserLevelService extends CrudService<UserLevelDao, UserLevel> {

    private List<UserLevel> userLevelList;

    public UserLevel get(String id) {
        return super.get(id);
    }

    public List<UserLevel> findList(UserLevel userLevel) {
        return super.findList(userLevel);
    }

    public Page<UserLevel> findPage(Page<UserLevel> page, UserLevel userLevel) {
        return super.findPage(page, userLevel);
    }


    public void save(UserLevel userLevel) throws ValidationException {
        UserLevel level = findByLevalCode(userLevel.getLevelCode());
        if (level != null) {
            if (!level.getId().equals(userLevel.getId())) {
                throw new ValidationException("等级代码已存在!");
            }
        }
        super.save(userLevel);
    }


    public void delete(UserLevel userLevel) throws ValidationException {
        super.delete(userLevel);
    }


    /**
     * 根据level_code查询userlevel
     *
     * @param levelCode
     * @return
     */
    public UserLevel findByLevalCode(String levelCode) {
        return dao.findByLevalCode(levelCode);
    }


    /**
     * 从缓存中查询用户等级对象
     *
     * @return
     */
    public UserLevel getLevelByCatch(String levelId) {
        if (null == userLevelList) {
            userLevelList = findList(new UserLevel());
        }

        Optional<UserLevel> userLevelOptional = userLevelList.stream().filter(s -> s.getLevelCode().equals(levelId)).findFirst();
        if (userLevelOptional.isPresent()) {
            return userLevelOptional.get();
        }

        return null;

    }

}