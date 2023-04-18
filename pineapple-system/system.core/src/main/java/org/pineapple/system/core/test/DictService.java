package org.pineapple.system.core.test;

import org.pineapple.system.core.test.mapper.SysDictMapper;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
@Service
public class DictService {
    private final SysDictMapper sysDictMapper;

    public DictService(SysDictMapper sysDictMapper) {
        this.sysDictMapper = sysDictMapper;
    }


    public void create() {
        SysDict sysDict = new SysDict();
        sysDict.setDictType("111");
        sysDict.setDictCode(2);
        sysDict.setDictText("1212");
        sysDict.setDictSort(10);
        sysDictMapper.create(sysDict);
    }
}
