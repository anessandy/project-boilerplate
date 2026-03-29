package com.mepro.appname.dao;

import com.mepro.appname.dto.UserInfoDto;
import com.mepro.appname.types.ActiveStatus;
import com.mepro.appname.util.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LoginDao {
    private static final Logger logger = LoggerFactory.getLogger(LoginDao.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<UserInfoDto> getListUserInfo(String userId, String password) throws Exception {
        List<UserInfoDto> listUserInfo = new ArrayList<>();
        Boolean isValid = false;
        Session session = entityManager.unwrap(Session.class);
        String sql = "SELECT tb1.nik nik, tb1.namalengkap namalengkap, "
                + "tb2.userid userid, tb2.password password, tb2.deskey, "
                + "tb5.idusergroup, tb5.name usergroup "
                + "FROM master_karyawan tb1 "
                + " LEFT JOIN users tb2 ON tb1.nik = tb2.nik "
                + " LEFT JOIN muser tb3 ON tb2.nik = tb3.nik "
                + " LEFT JOIN musergroupmap tb4 ON tb3.iduser = tb4.iduser "
                + " LEFT JOIN musergroup tb5 ON tb4.idusergroup = tb5.idusergroup "
                + "WHERE "
                + " tb1.status = :status AND "
                + " tb2.userid = :userId AND "
                + " tb2.status = :status AND "
                + " tb3.status = :status AND "
                + " tb4.status = :status AND "
                + " tb5.status = :status ";
        NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class);        
        query.setParameter("status", ActiveStatus.ACTIVE.getCode());
        query.setParameter("userId", userId.toUpperCase());
        
        List<Object[]> temp = query.list();
        if (!temp.isEmpty()) {
            for (Object[] row : temp) {
                Long rNik = (Long) row[0];
                String rNamaLengkap = (String) row[1];
                String rUserId = (String) row[2];
                String rPassword = (String) row[3];
                String rDeskey = (String) row[4];
                BigDecimal rIdUserGroup = (BigDecimal) row[5];
                String rUserGroup = (String) row[6];

                isValid = SecurityUtil.passwordCommonDecrypt(rPassword,
                        password, rDeskey);
                if (isValid) {
                    UserInfoDto data = new UserInfoDto();
                    data.setNik(rNik);
                    data.setNamaLengkap(rNamaLengkap);
                    data.setUserId(rUserId);
                    data.setPassword(rPassword);
                    data.setIdUserGroup(rIdUserGroup.longValue());
                    data.setUserGroup(rUserGroup);
                    listUserInfo.add(data);
                }
            }
        }
        return listUserInfo;
    }
}
