import { TriggerTypeEnum } from '@/api/dto/enumeration';
import { IPageDto } from '@/api/dto/common';
import { WebhookRequstStateEnum } from '@/api/dto/enumeration';
/**
 * 事件参数vo
 */
export interface IEventParameterVo {
  parameterId: string;
  name: string;
  type: string;
  value: string;
}

/**
 * 触发器事件vo
 */
export interface ITriggerEventVo {
  id: string;
  projectId: string;
  triggerId: string;
  triggerType: TriggerTypeEnum;
  payload: string;
  occurredTime: string;
  parameters: IEventParameterVo[];
}

/**
 * 触发器webhook vo
 */
export interface ITriggerWebhookVo
  extends Readonly<{
    webhook: string;
  }> {}

/**
 * 分页返回Webhook请求列表 dto
 */
export interface ITriggerViewerDto
  extends Readonly<
    IPageDto & {
      projectId: string;
    }
  > {}

/**
 * WebRequest
 */
export interface IWebRequestVo
  extends Readonly<{
    id: string;
    projectId: string;
    workflowRef: string;
    workflowVersion: string;
    triggerId: string;
    userAgent: string;
    payload: string;
    statusCode: WebhookRequstStateEnum;
    errorMsg?: string;
    requestTime: string;
  }> {}
