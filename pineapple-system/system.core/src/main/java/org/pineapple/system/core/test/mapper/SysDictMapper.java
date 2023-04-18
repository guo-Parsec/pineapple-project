package org.pineapple.system.core.test.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.pineapple.system.core.test.SysDict;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Repository
// INSERT INTO pineapple_db.sys_dict (id, dict_type, dict_code, dict_text, dict_sort, created_by, created_time, updated_by, updated_time) VALUES (1, 'status', 0, '启用', 10, 'system', '2023-04-14 22:04:25', 'system', '2023-04-14 22:04:25');
@Mapper
public interface SysDictMapper {
//    @NotAllowInterceptor({InterceptorNameConstant.PrimaryKeyGeneratorInterceptor})
    @Insert("insert into sys_dict(id, dict_type, dict_code, dict_text, dict_sort, created_by, " +
            "created_time, updated_by, updated_time) values " +
            "(#{id},#{dictType},#{dictCode},#{dictText}," +
            "#{dictSort},#{createdBy},#{createdTime},#{updatedBy},#{updatedTime})")
    void create(SysDict sysDict);
}
