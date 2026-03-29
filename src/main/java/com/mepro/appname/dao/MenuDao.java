package com.mepro.appname.dao;

import com.mepro.appname.dto.MenuDto;
import com.mepro.appname.dto.MenuGroupDto;
import com.mepro.appname.types.ActiveStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MenuDao {
    private static final Logger logger = LoggerFactory.getLogger(MenuDao.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Object[]> getMenuRaw(Long nik) throws Exception {
        Session session = entityManager.unwrap(Session.class);

        String sql = "SELECT "
                + "tb1.name menugroup, tb2.name menu, tb2.url url, tb2.namespace namespace, "
                + "tb2.idmenugroup idmenugroup, tb2.orderno orderno  "
                + "FROM mmenugroup tb1 "
                + " LEFT JOIN mmenu tb2 ON tb1.idmenugroup = tb2.idmenugroup "
                + "     AND tb2.status = :status "
                + " LEFT JOIN musergroupmenu tb3 ON tb2.idmenu = tb3.idmenu "
                + " LEFT JOIN musergroup tb4 ON tb3.idusergroup = tb4.idusergroup "
                + " LEFT JOIN musergroupmap tb5 ON tb4.idusergroup = tb5.idusergroup "
                + " LEFT JOIN muser tb6 ON tb5.iduser = tb6.iduser "
                + "WHERE "
                + " tb1.status = :status AND "
                + " tb2.status = :status AND "
                + " tb3.status = :status AND "
                + " tb4.status = :status AND "
                + " tb5.status = :status AND "
                + " tb6.status = :status AND "
                + " tb6.nik = :nik "
                + "ORDER BY tb2.idmenugroup, tb2.orderno ASC";

        NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class);
        query.setParameter("status", ActiveStatus.ACTIVE.getCode());
        query.setParameter("nik", nik);
        return query.list();
    }
    
    public List<MenuGroupDto> buildMenu(List<Object[]> rows) throws Exception {
        Map<String, MenuGroupDto> map = new LinkedHashMap<>();
        Session session = entityManager.unwrap(Session.class);
        String sql = "SELECT "
                + "tb1.name menugroup, tb2.name menu, tb2.url url, tb2.namespace namespace "
                + "FROM  mmenugroup tb1 "
                + " LEFT JOIN mmenu tb2 ON tb1.idmenugroup = tb2.idmenugroup "
                + "WHERE "
                + " tb1.status = :status AND "
                + " tb2.status = :status  "
                + "ORDER BY "
                + "  tb2.idmenugroup, tb2.orderno ASC ";
        NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class);
        query.setParameter("status", ActiveStatus.ACTIVE.getCode());
        rows = query.list();
        if (!rows.isEmpty()) {
            for (Object[] row : rows) {
                String groupName = (String) row[0];
                String menuName = (String) row[1];
                String url = (String) row[2];
                String namespace = (String) row[3];
                
                MenuGroupDto group = map.get(groupName);
                if (group == null) {
                    group = new MenuGroupDto();
                    group.setName(groupName);
                    map.put(groupName, group);
                }
                
                if (menuName != null) {
                    MenuDto menu = new MenuDto();
                    menu.setName(menuName);
                    menu.setUrl(url);
                    menu.setNamespace(namespace);
                    group.getMenus().add(menu);
                }
            }
        }
        return new ArrayList<>(map.values());
    }
}
