package dev.jianmu.infrastructure.mapper.external_parameter;

import dev.jianmu.external_parameter.aggregate.ExternalParameterLabel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @author huangxi
 * @class ExternalParameterLabelRepositoryImpl
 * @description ExternalParameterLabelRepositoryImpl
 * @create 2022-07-13 15:22
 */
public interface ExternalParameterLabelMapper {
    @Insert("insert into jm_external_parameter_label(id, value, association_id, association_type, association_platform, created_time, last_modified_time) " +
            "values(#{id}, #{value}, #{associationId}, #{associationType}, #{associationPlatform}, #{createdTime}, #{lastModifiedTime})")
    void add(ExternalParameterLabel externalParameterLabel);

    @Delete("DELETE FROM `jm_external_parameter_label` WHERE `association_id` = #{associationId} AND `association_type` = #{associationType} AND association_platform = #{associationPlatform}")
    void deleteByAssociationIdAndType(@Param("associationId") String associationId,
                                      @Param("associationType") String associationType,
                                      @Param("associationPlatform") String associationPlatform);

    @Select("<script>" +
            "SELECT * FROM `jm_external_parameter_label`" +
            "<where>" +
            " <if test='associationId != null'> AND association_id = #{associationId} </if>" +
            " <if test='associationType != null'> AND association_type = #{associationType} </if>" +
            " <if test='associationPlatform != null'> AND association_platform = #{associationPlatform} </if>" +
            "</where>" +
            "order by created_time" +
            "</script>")
    @Result(column = "id", property = "id")
    @Result(column = "value", property = "value")
    @Result(column = "association_id", property = "associationId")
    @Result(column = "association_type", property = "associationType")
    @Result(column = "association_platform", property = "associationPlatform")
    @Result(column = "last_modified_time", property = "lastModifiedTime")
    @Result(column = "created_time", property = "createdTime")
    List<ExternalParameterLabel> findAll(@Param("associationId") String associationId,
                                         @Param("associationType") String associationType,
                                         @Param("associationPlatform") String associationPlatform);

    @Select("<script>" +
            "SELECT * FROM `jm_external_parameter_label`" +
            "<where> value = #{value}" +
            " <if test='associationId != null'> AND association_id = #{associationId} </if>" +
            " <if test='associationType != null'> AND association_type = #{associationType} </if>" +
            " <if test='associationPlatform != null'> AND association_platform = #{associationPlatform} </if>" +
            "</where>" +
            "</script>")
    @Result(column = "id", property = "id")
    @Result(column = "value", property = "value")
    @Result(column = "association_id", property = "associationId")
    @Result(column = "association_type", property = "associationType")
    @Result(column = "association_platform", property = "associationPlatform")
    @Result(column = "last_modified_time", property = "lastModifiedTime")
    @Result(column = "created_time", property = "createdTime")
    Optional<ExternalParameterLabel> findByValue(
            @Param("associationId") String associationId,
            @Param("associationType") String associationType,
            @Param("associationPlatform") String associationPlatform,
            @Param("value") String value);
}
