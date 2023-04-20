package org.pineapple.system.core.test;

import org.pineapple.common.access.provider.fill.ExtPersistableModelFillProvider;
import org.pineapple.common.access.provider.fill.FieldFillProvider;
import org.pineapple.common.access.provider.fill.FieldFillProviderRegistry;
import org.pineapple.common.base.response.ActionHelper;
import org.pineapple.common.base.response.ActionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@RestController
public class SysDictController {
    private final DictService dictService;

    public SysDictController(DictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping(value = "/create")
    public ActionResponse<Void> create() {
        FieldFillProvider provider = FieldFillProviderRegistry.getInstance().get(ExtPersistableModelFillProvider.class);
        dictService.create();
        return ActionHelper.success();
    }
}
