package com.example.demo.infrastructure.common.components;

import com.example.demo.infrastructure.common.vo.PageVO;
import com.example.demo.infrastructure.util.BeanCopyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 适用于轻量级项目 - 面向对象数据库领域建模
 *
 */
@Service
public class OOQueryService {
    private Logger logger = LoggerFactory.getLogger(OOQueryService.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * @param page
     * @param pageSize
     * @param parent
     * @param target
     * @param params
     * @return
     * @apiNote 封装底层查询 用于继承的实体查询（单表策略）
     */
    public PageVO criteriaQuery(Long page, Long pageSize, Class<?> parent, Class<?> target, Map<String, Object> params, String descTimeField) {
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        Long index = (page - 1) * pageSize;
        StringBuilder resultSqlBuilder = new StringBuilder();
        StringBuilder countSqlBuilder = new StringBuilder();
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        countSqlBuilder.append("SELECT count(*) FROM ").append(parentName).append(" a ").append(targetName != null ? (" WHERE type(a)=" + targetName) : "").append(token);
        resultSqlBuilder.append("SELECT a FROM ").append(parentName).append(" a ").append(targetName != null ? ("  WHERE a.class=" + targetName) : "").append(token);

        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                String sqlMiddle = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                resultSqlBuilder.append(sqlMiddle);
                countSqlBuilder.append(sqlMiddle);
            }
        }

        if (descTimeField != null){
            resultSqlBuilder.append(" ORDER BY a.").append(descTimeField).append(" DESC");
        }
        logger.info("resultSql= {}", resultSqlBuilder.toString());
        logger.info("countSql= {}", countSqlBuilder.toString());
        final long totalSize = em.createQuery(countSqlBuilder.toString(), Long.class).getSingleResult();
        Long totalPage = (totalSize + pageSize - 1) / pageSize;
        logger.info("总条数: {}", totalSize);
        final List<?> resultList = em.createQuery(resultSqlBuilder.toString(), parent)
                .setFirstResult(index.intValue()).setMaxResults(pageSize.intValue())
                .getResultList();
        return new PageVO(resultList, totalPage, page, pageSize);
    }

    /**
     * @param parent
     * @param target
     * @return
     * @apiNote 获取父类子类的总条数
     */
    public Long getTotalCount(Class<?> parent, Class<?> target, Map<String, Object> params) {
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        String preSql = "SELECT count(*) FROM " + parentName + " a " + (targetName != null ? (" WHERE type(a)=" + targetName) : "") + token;
        StringBuilder sqlBuilder = new StringBuilder(preSql);
        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                String sqlMiddle = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                sqlBuilder.append(sqlMiddle);
            }
        }

        final long count = em.createQuery(sqlBuilder.toString(), Long.class).getSingleResult();
        return count;
    }

    /**
     * @param parent
     * @param target
     * @param params
     * @param beginDate
     * @param endDate
     * @param timeField
     * @return
     * @apiNote 流量查询
     */
    public Long getCountBetweenDate(Class<?> parent, Class<?> target, Map<String, Object> params, Date beginDate, Date endDate, String timeField) {
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        String preSql = "SELECT count(*) FROM " + parentName + " a " + (targetName != null ? ("  WHERE a.class=" + targetName) : "") + token;
        StringBuilder sqlBuilder = new StringBuilder(preSql);
        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                String sqlMiddle = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                sqlBuilder.append(sqlMiddle);
            }
        }
        if (beginDate != null && endDate != null && timeField != null) {
            String sqlMiddle = " AND a." + timeField + ">=:beginDate AND a." + timeField + "<:endDate";
            sqlBuilder.append(sqlMiddle);

        }
        final TypedQuery<Long> query = em.createQuery(sqlBuilder.toString(), Long.class);
        if (beginDate != null && endDate != null && timeField != null) {
            query.setParameter("beginDate", beginDate);
            query.setParameter("endDate", endDate);
        }
        final long count = query.getSingleResult();

        return count;
    }

    /**
     * @param parent
     * @param target
     * @param field
     * @param fieldValue
     * @apiNote 根据指定String类型字段插入或更新数据
     */
    @Transactional
    public void saveOrUpdateByField(Class<?> parent, Class<?> target, String field, String fieldValue, Object object){
        final String parentName = parent.getSimpleName();
        final String targetName = target.getSimpleName();
        String querySQL = "SELECT a FROM " + parentName + " a WHERE type(a)=" + targetName + " AND a." + field + "='" + fieldValue + "'";
        logger.info("==== saveOrUpdate sql=  {} ", querySQL);
        final List<?> resultList = em.createQuery(querySQL, parent).getResultList();
        try {
            for (Object o : resultList) {
//            em.remove(resultList.get(i));
                BeanCopyer.copy(object, o);
                em.merge(o);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (resultList.size() == 0){
            em.persist(object);
        }
    }

    @Transactional
    public void persisent(Object object) {
        em.persist(object);
    }

    @Transactional
    public void merge(Object object) {
        em.merge(object);
    }

    public Object findById(Class<?> parent, Long id) {
        final Object o = em.find(parent, id);
        return o;
    }

    @Transactional
    public void  delete(Object object) throws Exception{
        em.remove(object);
    }

    /**
     * @apiNote 根据条件删除对象(通常是删除外键关联的对象)
     * @param parent
     * @param target
     * @param params
     * @throws Exception
     */
    @Transactional
    @Modifying
    public void delete( Class<?> parent, Class<?> target, Map<String, Object> params) throws Exception{
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        StringBuilder resultSqlBuilder = new StringBuilder();
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        resultSqlBuilder.append("DELETE FROM ").append(parentName).append(" a ").append(targetName != null ? ("  WHERE a.class=" + targetName) : "").append(token);

        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                String sqlMiddle;
                if(entry.getValue() != null){
                    final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                    sqlMiddle  = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                }else {
                    sqlMiddle = "a." + attribute + "  IS NULL " + (entryCount == params.keySet().size() ? "" : " AND ");
                }
                resultSqlBuilder.append(sqlMiddle);
            }
        }
        em.createQuery(resultSqlBuilder.toString()).executeUpdate();
    }

    /**
     *
     * @param parent
     * @param target
     * @param params
     * @return
     * @throws Exception
     */
    public List<?> findByAttribute( Class<?> parent, Class<?> target, Map<String, Object> params) throws Exception{
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        StringBuilder resultSqlBuilder = new StringBuilder();
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        resultSqlBuilder.append("SELECT  a FROM ").append(parentName).append(" a ").append(targetName != null ? ("  WHERE a.class=" + targetName) : "").append(token);

        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                String sqlMiddle;
                if(entry.getValue() != null){
                    final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                    sqlMiddle  = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                }else {
                    sqlMiddle = "a." + attribute + "  IS NULL " + (entryCount == params.keySet().size() ? "" : " AND ");
                }
                resultSqlBuilder.append(sqlMiddle);
            }
        }
        final List<?> resultList = em.createQuery(resultSqlBuilder.toString(), parent).getResultList();
        return resultList;
    }


    /**
     * @apiNote 更新部分字段
     * @param object 只赋值部分属性的对象
     * @param clazz 对象的类型
     * @param id 对象的id
     * @return
     */
    @Transactional
    public Object updateFieldsById(Object object,Class<?> clazz, Long id) throws Exception{
        Object old = em.find(clazz, id);
        Object result = em.merge(old);
        BeanCopyer.copy(object,old);
        return result;
    }

}
