import { ParamTypeEnum } from './enumeration';
import { CustomRule } from '@/components/workflow/workflow-editor/model/data/common';
import Schema, { Value } from 'async-validator';
import { ISelectableParam } from '@/components/workflow/workflow-expression-editor/model/data';

export const GLOBAL_PARAM_SCOPE = 'global';

export class GlobalParam {
  ref: string;
  name: string;
  type: ParamTypeEnum;
  required: boolean;
  value: string;

  constructor(ref: string, name: string, type: ParamTypeEnum, required: boolean, value: string) {
    this.ref = ref;
    this.name = name;
    this.type = type;
    this.required = required;
    this.value = value;
  }

  /**
   * 表单校验规则
   */
  getFormRules(): Record<string, CustomRule> {
    return {
      ref: [
        { required: true, message: '请输入唯一标识', trigger: 'blur' },
        { pattern: /^[a-zA-Z_]([a-zA-Z0-9_]+)?$/, message: '以英文字母或下划线开头，支持下划线、数字、英文字母', trigger: 'blur' },
      ],
      type: [{ required: true, trigger: 'change' }],
      required: [{ required: true, trigger: 'change', type: 'boolean' }],
      value: [{ required: true, message: '请输入值', trigger: 'blur' }],
    };
  }

  async validate(): Promise<void> {
    const validator = new Schema(this.getFormRules());
    const source: Record<string, Value> = {};
    Object.keys(this).forEach(key => (source[key] = (this as any)[key]));

    await validator.validate(source, {
      first: true,
    });
  }

}

/**
 * 检查唯一标识是否重复
 * @param globalParams
 */
export function checkDuplicate(globalParams: GlobalParam[]): void {
  const refArr: string[] = globalParams.map(({ ref }) => ref);
  const countObj = refArr.reduce<Record<string, number>>((obj, name) => {
    if (name in obj) {
      obj[name]++;
    } else {
      obj[name] = 1;
    }
    return obj;
  }, {});
  for (const i in countObj) {
    if (countObj[i] > 1 && i !== '') {
      throw new Error(`唯一标识 "${i}" 重复`);
    }
  }
}

/**
 * globalParam参数选择/级联选择
 * @param globalParams
 */
export function buildSelectableGlobalParam(globalParams: GlobalParam[]): ISelectableParam | undefined {
  if (!globalParams || globalParams.length === 0) {
    return undefined;
  }
  return {
    value: GLOBAL_PARAM_SCOPE,
    label: '全局参数',
    children: globalParams.filter(({ ref }) => ref).map(({ ref, name }) => {
      return {
        value: ref,
        label: name || ref,
      };
    }),
  };
}