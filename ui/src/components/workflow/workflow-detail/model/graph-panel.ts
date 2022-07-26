import { TaskStatusEnum } from "@/api/dto/enumeration";
import { IGlobalParamseterVo, INodeDefVo } from "@/api/dto/project";
import { ITaskExecutionRecordVo, IWorkflowExecutionRecordVo } from "@/api/dto/workflow-execution-record";
import { fetchWorkflow, getGlobalParameters, listAsyncTaskInstance } from "@/api/view-no-auth";
import { checkWorkflowRunning } from "./utils/workflow";

type DslCallbackFnType = (dslSourceCode: string, nodeInfos: INodeDefVo[])=>void;
type TaskCallbackFnType = (taskRecords: ITaskExecutionRecordVo[])=>void;
type GlobalParamsCallbackFnType = (globalParams: IGlobalParamseterVo[])=>void;

export class GraphPanel {
  private ignoreSuspended: boolean = true;
  private ignoreTime: number = 0;
  private currentRecord: IWorkflowExecutionRecordVo;
  private eventSource: any;
  private readonly taskRecords: ITaskExecutionRecordVo[];
  private readonly dslCallbackFn: DslCallbackFnType;
  private readonly taskCallbackFn: TaskCallbackFnType;
  private readonly globalParamsCallbackFn: GlobalParamsCallbackFnType;
  constructor(currentRecord: IWorkflowExecutionRecordVo, dslCallbackFn: DslCallbackFnType, taskCallbackFn: TaskCallbackFnType, globalParamsCallbackFn: GlobalParamsCallbackFnType){
    this.currentRecord = currentRecord;
    this.taskRecords = [];
    this.dslCallbackFn = dslCallbackFn;
    this.taskCallbackFn = taskCallbackFn;
    this.globalParamsCallbackFn = globalParamsCallbackFn;
    this.getDslAndNodeinfos();
    this.getTaskRecords();
    this.getGlobalParams();
  }
  async getGlobalParams() {
    if (!this.currentRecord.triggerId) {
      // throw new Error('triggerId 缺失');
      console.log('triggerId 缺失');
      return;
    }
    const params:IGlobalParamseterVo[] = await getGlobalParameters(this.currentRecord.triggerId);
    this.globalParamsCallbackFn(params);
  }
  async getDslAndNodeinfos() {
    if (!this.currentRecord.workflowRef) {
      return;
    }
    const { dslText: dslSourceCode, nodes } = await fetchWorkflow(this.currentRecord.workflowRef, this.currentRecord.workflowVersion);
    const nodeInfos = nodes.filter(({ metadata }) => metadata).map(({ metadata }) => JSON.parse(metadata as string));
    this.dslCallbackFn(dslSourceCode, nodeInfos);
  }
  async getTaskRecords() {
    if (!this.currentRecord.triggerId) {
      return;
    }
    const taskRecords = (await listAsyncTaskInstance(this.currentRecord.triggerId)).map(instance => {
      return {
        instanceId: '',
        businessId: instance.id,
        nodeName: instance.asyncTaskRef,
        defKey: instance.asyncTaskType,
        startTime: instance.startTime,
        endTime: instance.endTime,
        status: instance.status,
      };
    });
    this.taskRecords.length = 0;
    this.taskRecords.push(...taskRecords);
    this.taskCallbackFn(taskRecords);
  }
  /**
   * listen刷新 graph数据
  */
  listen():void {
    const checkTaskRunning = (status: TaskStatusEnum, ignoreSuspended: boolean=true): boolean => {
      const statuses = [
        TaskStatusEnum.WAITING,
        TaskStatusEnum.RUNNING,
      ];
      if (!ignoreSuspended) {
        statuses.push(TaskStatusEnum.SUSPENDED);
      }
      return statuses.includes(status);
    };
    this.eventSource = setInterval(async()=>{
      // console.log('graph-panel 3秒', this.taskRecords.length);
      if (!this.taskRecords.length) {
        console.log('taskRecords -> 0，不监听graph-panel状态');
        return;
      }
      // 全量判断是否刷新 this.currentRecord
      if (!checkWorkflowRunning(this.currentRecord.status, this.ignoreSuspended) && !this.taskRecords.find(item => checkTaskRunning(item.status, this.ignoreSuspended))) {
        // 忽略挂起刷新状态(次数) 重置
        this.ignoreSuspended = true;
        this.ignoreTime = 0;
        console.log('忽略tasks挂起 重置');
        return;
      }
      // 不忽略挂起刷新次数 新增
      if (!this.ignoreSuspended) {
        // this.ignoreSuspended -> false
        this.ignoreTime++;
        console.log(`graph 重试(忽略)后刷新第${this.ignoreTime}次`);
      }
      // 不忽略挂起刷新次数->重置 忽略挂起状态->重置
      if (this.ignoreTime >= 20) {
        this.ignoreTime = 0;
        this.ignoreSuspended = true;
      }
      console.log('任务中有waiting running suspended状态，刷新任务', this.currentRecord.status, this.ignoreSuspended);
      try {
        await this.getTaskRecords();
      } catch (e: any) {
        // 捕获异常
        console.warn(e.message, e);
      }
    }, 3000);
  }
  refreshSuspended() {
    this.ignoreSuspended = false;
  }
  resetSuspended() {
    this.ignoreSuspended = true;
  }
  reflushGparam(record: IWorkflowExecutionRecordVo) {
    this.currentRecord = record;
  }
  /**
   * 销毁
   */
  destroy(): void {
    // this.eventSource && this.eventSource.close();
    this.eventSource && clearInterval(this.eventSource);
  }
}