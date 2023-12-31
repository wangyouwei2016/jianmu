package dev.jianmu.infrastructure.mapper.task;

import dev.jianmu.task.aggregate.InstanceParameter;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * @author Ethan Liu
 * @class InstanceParameterMapper
 * @description InstanceParameterMapper
 * @create 2021-05-01 21:31
 */
public interface InstanceParameterMapper {
    @Insert("<script>" +
        "insert into task_instance_parameter(instance_id, serial_no, def_key, async_task_ref, business_id, trigger_id, ref, `type`, workflow_type, parameter_id, required) values" +
        "<foreach collection='instanceParameters' item='i' index='key' separator=','>" +
        "(#{i.instanceId}, #{i.serialNo}, #{i.defKey}, #{i.asyncTaskRef}, #{i.businessId}, #{i.triggerId}, #{i.ref}, #{i.type}, #{i.workflowType}, #{i.parameterId}, #{i.required})" +
        "</foreach>" +
        " </script>")
    void addAll(@Param("instanceParameters") Set<InstanceParameter> instanceParameters);

    @Delete("delete t1, t2 from task_instance_parameter t1 " +
        "left join parameter t2 on (t1.parameter_id = (t2.id collate utf8mb4_0900_ai_ci) and t2.default = 0)" +
        "where t1.trigger_id = #{triggerId}")
    void deleteByTriggerId(String triggerId);

    @Delete("<script>" +
        "delete from task_instance_parameter " +
        "where `trigger_id` IN <foreach collection='triggerIds' item='item' open='(' separator=',' close=')'> #{item}</foreach>" +
        "</script>")
    void deleteByTriggerIdIn(@Param("triggerIds") List<String> triggerIds);

    @Select("select * from task_instance_parameter where instance_id = #{instanceId}")
    @Result(column = "instance_id", property = "instanceId")
    @Result(column = "serial_no", property = "serialNo")
    @Result(column = "def_key", property = "defKey")
    @Result(column = "async_task_ref", property = "asyncTaskRef")
    @Result(column = "business_id", property = "businessId")
    @Result(column = "trigger_id", property = "triggerId")
    @Result(column = "parameter_id", property = "parameterId")
    @Result(column = "workflow_type", property = "workflowType")
    List<InstanceParameter> findByInstanceId(@Param("instanceId") String instanceId);

    @Select("select * from task_instance_parameter where instance_id = #{instanceId} and type = #{type}")
    @Result(column = "instance_id", property = "instanceId")
    @Result(column = "serial_no", property = "serialNo")
    @Result(column = "def_key", property = "defKey")
    @Result(column = "async_task_ref", property = "asyncTaskRef")
    @Result(column = "business_id", property = "businessId")
    @Result(column = "trigger_id", property = "triggerId")
    @Result(column = "parameter_id", property = "parameterId")
    @Result(column = "workflow_type", property = "workflowType")
    List<InstanceParameter> findByInstanceIdAndType(@Param("instanceId") String instanceId, @Param("type") InstanceParameter.Type type);

    @Select("SELECT T.* FROM task_instance_parameter as T," +
        "(" +
        "SELECT max(serial_no) as max_no, async_task_ref, ref FROM task_instance_parameter " +
        "    WHERE trigger_id=#{triggerId} " +
        "    GROUP BY  async_task_ref, ref" +
        ") as B " +
        "WHERE trigger_id=#{triggerId} " +
        "AND T.async_task_ref=B.async_task_ref AND T.ref=B.ref AND T.serial_no=B.max_no AND T.type='OUTPUT'")
    @Result(column = "instance_id", property = "instanceId")
    @Result(column = "serial_no", property = "serialNo")
    @Result(column = "def_key", property = "defKey")
    @Result(column = "async_task_ref", property = "asyncTaskRef")
    @Result(column = "business_id", property = "businessId")
    @Result(column = "trigger_id", property = "triggerId")
    @Result(column = "parameter_id", property = "parameterId")
    @Result(column = "workflow_type", property = "workflowType")
    List<InstanceParameter> findLastOutputParamByTriggerId(String triggerId);

    @Select("<script>" +
        "select parameter_id from task_instance_parameter " +
        "where `trigger_id` IN <foreach collection='triggerIds' item='item' open='(' separator=',' close=')'> #{item}</foreach>" +
        "</script>")
    List<String> findParameterIdByTriggerIdIn(@Param("triggerIds") List<String> triggerIds);
}
