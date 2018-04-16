/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.modules.user.service.UserSequenceService;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen implements IdGenerator, SessionIdGenerator {

    private static SecureRandom random = new SecureRandom();

    private static UserSequenceService userSequenceService;

    private static final String STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid(String seqName) {

        if (userSequenceService == null) {
            userSequenceService = SpringContextHolder.getBean(UserSequenceService.class);
        }

        if (StringUtils2.isBlank(seqName)) {
            seqName = "seq_common";
        }
        return userSequenceService.getNextSequence(seqName);
        //return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String uuid() {
        return IdGen.uuid("");
    }

    public static String randomuuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 随机生成用户名
     *
     * @return
     */
    public static String ramdomUserName(int min, int max, String front) {
        int num = random.nextInt(max) % (max - min + 1) + min;
        return front + num;
    }


    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        long l = random.nextLong();
        while (l == Long.MIN_VALUE) {
            l = random.nextLong();
        }
        return Math.abs(l);
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Encodes.encodeBase62(randomBytes);
    }

    /**
     * Activiti ID 生成
     */
    @Override
    public String getNextId() {
        return IdGen.uuid();
    }

    @Override
    public Serializable generateId(Session session) {
        return IdGen.randomuuid();
    }

    /**
     * 随机生成指定位数的随机字符串
     *
     * @return
     */
    public static String randomStr(int bitCount) {
        random.setSeed(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < bitCount; i++) {
            char ch = STR.charAt(random.nextInt(STR.length()));
            sb.append(ch);
        }

        return sb.toString().toUpperCase();
    }

    /**
     * 随机生成指定位数的随机字符串
     *
     * @return
     */
    public static String randomNum(int bitCount) {
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < bitCount; i++) {
            int num = random.nextInt(10);
            sb.append(String.valueOf(num));
        }

        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) throws ParseException {
//		System.out.println(IdGen.uuid());
//		System.out.println(IdGen.uuid().length());
//		System.out.println(new IdGen().getNextId());
        /*System.out.println(randomStr(4));*/

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf.parse("2017-05-29 15:28:34");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createTime = LocalDateTime.ofInstant(parse.toInstant(), ZoneId.systemDefault());
        long minutes = Duration.between(createTime, now).toMinutes();
        System.out.println(minutes);*/
        /*String pattern = "^[0-9A-Za-z_#$*&%!/@]{8,}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher("11111!#$%&*11A@cc");
        System.out.println(m.matches ()) ;*/
    }

}
