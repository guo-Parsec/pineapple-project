package org.pineapple.support.flow.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pineapple.common.define.PageDefine;
import org.pineapple.common.uniforms.UniformResultDefinition;
import org.pineapple.common.uniforms.UniformResultTool;
import org.pineapple.support.flow.CommonProcessFlowEngine;
import org.pineapple.support.flow.api.BasicFlowApi;
import org.pineapple.support.flow.define.FlowDeployDefine;
import org.pineapple.support.flow.pojo.dto.DeploymentPageQueryDto;
import org.pineapple.support.flow.pojo.vo.DeploymentVo;
import org.pineapple.support.flow.pojo.vo.TaskVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>流程管理接口</p>
 *
 * @author hedwing
 * @since 2023/3/11
 **/
@Api(tags = "流程管理")
@RestController
@RequestMapping("/flow")
public class FlowController {

    private final BasicFlowApi basicFlowApi;

    private final CommonProcessFlowEngine commonProcessFlowEngine;

    public FlowController(BasicFlowApi basicFlowApi, CommonProcessFlowEngine commonProcessFlowEngine) {
        this.basicFlowApi = basicFlowApi;
        this.commonProcessFlowEngine = commonProcessFlowEngine;
    }

    @ApiOperation(value = "流程部署")
    @PostMapping("/deploy.do")
    public UniformResultDefinition<Void> deployFlow(String resourceLocation, @RequestBody FlowDeployDefine flowDeployDefine) {
        basicFlowApi.repository().deploy(resourceLocation, flowDeployDefine);
        return UniformResultTool.success();
    }

    @ApiOperation(value = "流程部署查询")
    @GetMapping("/deployment/page")
    public UniformResultDefinition<PageDefine<DeploymentVo>> page(@Validated DeploymentPageQueryDto dto) {
        return UniformResultTool.success(basicFlowApi.repository().queryPageProcessDeployment(dto));
    }

    @ApiOperation(value = "绘制流程图")
    @GetMapping("/process-diagram/generate.do")
    public UniformResultDefinition<Void> generateProcessDiagram(HttpServletResponse response, String processInstanceId) {
        basicFlowApi.generateProcessDiagram(response, processInstanceId);
        return UniformResultTool.success();
    }

    @ApiOperation(value = "通用两步流程启动")
    @PostMapping("/common-two-step-process/start.do")
    public UniformResultDefinition<Void> startCommonTwoStepProcess(String startUser) {
        commonProcessFlowEngine.startCommonTwoStepProcess(startUser);
        return UniformResultTool.success();
    }

    @ApiOperation(value = "通用两步流程再次提交")
    @PostMapping("/common-two-step-process/submit-to-confirm.do")
    public UniformResultDefinition<Void> submitToConfirm(String taskId, String userId) {
        commonProcessFlowEngine.submitToConfirm(taskId, userId);
        return UniformResultTool.success();
    }

    @ApiOperation(value = "通用两步流程确认")
    @PostMapping("/common-two-step-process/conform.do")
    public UniformResultDefinition<Void> confirmApply(String taskId, String userId, String confirm) {
        commonProcessFlowEngine.confirmApply(taskId, userId, confirm);
        return UniformResultTool.success();
    }

    @ApiOperation(value = "通用两步流程最终审核")
    @PostMapping("/common-two-step-process/final-approve.do")
    public UniformResultDefinition<Void> finalApprove(String taskId, String userId, String approve) {
        commonProcessFlowEngine.finalApprove(taskId, userId, approve);
        return UniformResultTool.success();
    }

    @ApiOperation(value = "查询待领取任务")
    @GetMapping("/find/user-un-claim-task")
    public UniformResultDefinition<List<TaskVo>> findUnClaimTaskByUserId(String userId) {
        return UniformResultTool.success(basicFlowApi.task().findUnClaimTaskByUserId(userId));
    }

    @ApiOperation(value = "查询已领取任务")
    @GetMapping("/find/user-assignee-task")
    public UniformResultDefinition<List<TaskVo>> findAssigneeTaskByUserId(String userId) {
        return UniformResultTool.success(basicFlowApi.task().findAssigneeTaskByUserId(userId));
    }

    @ApiOperation(value = "领取任务")
    @PostMapping("/claim-task.do")
    public UniformResultDefinition<Void> claimTask(String taskId, String userId) {
        basicFlowApi.task().claim(taskId, userId);
        return UniformResultTool.success();
    }
}
