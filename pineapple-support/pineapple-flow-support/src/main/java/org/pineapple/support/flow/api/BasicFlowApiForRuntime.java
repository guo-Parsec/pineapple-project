package org.pineapple.support.flow.api;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.pineapple.support.flow.pojo.dto.ProcessStartDto;

/**
 * <p>流程运行</p>
 *
 * @author hedwing
 * @since 2023/3/11
 **/
public interface BasicFlowApiForRuntime {
    /**
     * <p>获取RuntimeService</p>
     *
     * @return org.flowable.engine.RuntimeService
     * @author hedwing
     * @since 2023/3/11
     */
    RuntimeService getRuntimeService();

    /**
     * <p>流程启动</p>
     *
     * @param dto 流程启动参数
     * @return 流程实例
     * @author hedwing
     * @since 2023/3/11
     */
    ProcessInstance startProcess(ProcessStartDto dto);
}
