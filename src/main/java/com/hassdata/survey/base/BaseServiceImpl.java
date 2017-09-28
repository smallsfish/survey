package com.hassdata.survey.base;

import org.apache.ibatis.session.SqlSessionFactory;

import javax.annotation.Resource;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl <T> implements BaseService<T> {
    @Resource
    protected SqlSessionFactory sqlSessionFactory;
    public abstract BaseDao<T> getMapper();

    /**
     * 根据主键id获取
     */
    @Override
    public T find(Serializable id) {
        return this.getMapper().find(id);
    }


    /**
     * 根据主键id获取
     */
    @Override
    public T findByStringId(String id) {
        return this.getMapper().findByStringId(id);
    }


    /**
     * 保存
     */
    @Override
    public int save(T entity) {
        return this.getMapper().save(entity);
    }

    /**
     * 批量保存
     */
    @Override
    public void saveBatch(List<T> entities) {
        for(T entity : entities) {
            save(entity);
        }
    }

    /**
     * 更新
     */
    @Override
    public int update(T entity) {
        return this.getMapper().update(entity);
    }

    /**
     * 批量更新
     */
    @Override
    public void updateBatch(List<T> entities) {
        for(T entity : entities) {
            update(entity);
        }
    }

    /**
     * 根据主键更新部分属性
     */
    @Override
    public int updateParams(T params) {
        return this.getMapper().updateParams(params);
    }

    /**
     * 根据主键id删除
     */
    @Override
    public int delete(Serializable id) {
        return this.getMapper().delete(id);
    }

    /**
     * 根据主键id(String)删除
     */
    @Override
    public int deleteByStringId(String id) {
        return this.getMapper().deleteByStringId(id);
    }

    /**
     * 根据条件获取数目
     */
    @Override
    public long count(T params) {
        return this.getMapper().count(params);
    }

    /**
     * 根据条件获取一条记录
     */
    @Override
    public T getOne(T params, String orderBy) {
        Map<String, Object> map = buildParams(params, orderBy, null, null);
        return this.getMapper().getOne(map);
    }

    /**
     * 根据条件获取一条记录
     */
    @Override
    public T getOne(T params) {
        Map<String, Object> map = buildParams(params, null, null, null);
        System.out.println(this.getMapper());
        return this.getMapper().getOne(map);
    }

    /**
     * 根据条件获取列表
     */
    @Override
    public List<T> getAll(T params, String orderBy) {
        Map<String, Object> map = buildParams(params, orderBy, null, null);
        return this.getMapper().getAll(map);
    }

    /**
     * 根据条件获取列表
     */
    @Override
    public List<T> getAll(T params) {
        Map<String, Object> map = buildParams(params, null, null, null);
        return this.getMapper().getAll(map);
    }

    /**
     * 根据条件分页获取列表
     *
     */
    @Override
    public List<T> getScrollData(T params, String orderBy, int fromIndex, int pageSize) {
        Map<String, Object> map = buildParams(params, orderBy, fromIndex, pageSize);
        return this.getMapper().getScrollData(map);
    }

    /**
     * 根据条件分页获取记录数
     */
    @Override
    public long getScrollCount(T params) {
        Map<String, Object> map = buildParams(params, null, null, null);
        return this.getMapper().getScrollCount(map);
    }


    protected Map<String, Object> buildDefaultParamsMap(T params) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(params == null) {
            return map;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(params.getClass(), Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor pd : pds) {
                map.put(pd.getName(), pd.getReadMethod().invoke(params));
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Map<String, Object> buildParams(T params, String orderBy, Integer fromIndex, Integer pageSize) {
        Map<String, Object> map = buildDefaultParamsMap(params);
        if(orderBy != null) {
            map.put("orderBy", orderBy);
        }
        if(fromIndex != null && pageSize != null) {
            map.put("fromIndex", fromIndex);
            map.put("pageSize", pageSize);
            map.put("lastIndex", fromIndex + pageSize);
        }
        return map;
    }
}
