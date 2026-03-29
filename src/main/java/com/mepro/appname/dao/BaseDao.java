package com.mepro.appname.dao;

import com.mepro.appname.helper.PagingHelper;
import com.mepro.appname.util.PagingUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseDao<T> implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    @PersistenceContext
    protected EntityManager entityManager;

    public boolean isSaved(T entity) {
        try {
            entityManager.persist(entity);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            logger.error("Error saat save entity", e);
            return false;
        }
    }

    public boolean isUpdated(T entity) {
        try {
            entityManager.merge(entity);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            logger.error("Error saat update entity", e);
            return false;
        }
    }

    public boolean saveAll(List<T> listObject) {
        Session session = null;
        boolean result = false;
        try {
            session = entityManager.unwrap(Session.class);
            for (int i = 0; i < listObject.size(); i++) {
                session.persist(listObject.get(i));
            }
            result = true;
        } catch (Exception e) {
            logger.error("Runtime exception occured on saveAll(): " + e.getMessage(), e);
            try {
                session.getTransaction().rollback();
            } catch (Exception ex) {
                logger.error("Runtime exception occured on rolling back transaction: " + ex.getMessage(), ex);
            }
            result = false;
        } finally {
            if (session != null) {
                try {
                    session.flush();
                } catch (Exception e) {
                    logger.error("Runtime exception occured while flushing hibernate session: " + e.getMessage(), e);
                    result = false;
                }
                try {
                    session.clear();
                } catch (Exception e) {
                    logger.error("Runtime exception occured while closing hibernate session: " + e.getMessage(), e);
                    result = false;
                }
            }
        }
        return result;
    }

    public boolean updateAll(List<T> listObject) {
        Session session = null;
        boolean result = false;
        try {
            session = entityManager.unwrap(Session.class);
            for (int i = 0; i < listObject.size(); i++) {
                session.merge(listObject.get(i));
            }
            result = true;
        } catch (Exception e) {
            logger.error("Runtime exception occured on saveAll(): " + e.getMessage(), e);
            try {
                session.getTransaction().rollback();
            } catch (Exception ex) {
                logger.error("Runtime exception occured on rolling back transaction: " + ex.getMessage(), ex);
            }
            result = false;
        } finally {
            if (session != null) {
                try {
                    session.flush();
                } catch (Exception e) {
                    logger.error("Runtime exception occured while flushing hibernate session: " + e.getMessage(), e);
                    result = false;
                }
                try {
                    session.clear();
                } catch (Exception e) {
                    logger.error("Runtime exception occured while closing hibernate session: " + e.getMessage(), e);
                    result = false;
                }
            }
        }
        return result;
    }
    
    protected NativeQuery<Object[]> createPagingQuery(
            Session session,
            String baseSql,
            PagingUtil paging) {
        String finalSql = PagingHelper.wrapPagingQuery(baseSql);
        NativeQuery<Object[]> query = session.createNativeQuery(finalSql, Object[].class);

        query.setParameter("startRow", PagingHelper.getStartRow(paging));
        query.setParameter("endRow", PagingHelper.getEndRow(paging));

        return query;
    }
}
