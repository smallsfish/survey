package com.hassdata.survey.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao <T>{
    /**
     * 根据主键id获取
     */
    T find(Serializable id);


    /**
     * 根据主键id(String)获取
     */
    T findByStringId(String id);

    /**
     * 保存
     */
    int save(T entity);

    /**
     * 更新
     */
    int update(T entity);
    /**
     * 根据主键更新部分属性
     */
    int updateParams(T params);

    /**
     * 根据主键id删除
     */
    int delete(Serializable id);
    /**
     * 根据主键id(String)删除
     */
    int deleteByStringId(String id);

    /**
     * 根据条件获取数目
     */
    long count(T params);

    /**
     * 根据条件获取一条记录
     * @param orderBy 排序条件 a asc, b desc ...
     */
    T getOne(Map<String, Object> params);

    /**
     * 根据条件获取列表
     * @param orderBy 排序条件 a asc, b desc ...
     */
    List<T> getAll(Map<String, Object> params);

    /**
     * 根据条件分页获取列表
     * @param fromIndex 分页起始 0, 1, 2 ...
     * @param pageSize 每页显示记录数
     * @param orderBy 排序条件 a asc, b desc ...
     *
     */
    List<T> getScrollData(Map<String, Object> params);
    /**
     * 根据条件分页获取记录数
     * @param orderBy 排序条件 a asc, b desc ...
     */
    long getScrollCount(Map<String, Object> params);
}
