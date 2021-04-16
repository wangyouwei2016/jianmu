package dev.jianmu.infrastructure.mybatis.parameter;

import dev.jianmu.infrastructure.mapper.parameter.ParameterMapper;
import dev.jianmu.parameter.aggregate.Parameter;
import dev.jianmu.parameter.repository.ParameterRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * @class: ParameterRepositoryImpl
 * @description: 参数仓储实现
 * @author: Ethan Liu
 * @create: 2021-04-10 09:55
 **/
@Repository
public class ParameterRepositoryImpl implements ParameterRepository {
    private final ParameterMapper parameterMapper;

    @Inject
    public ParameterRepositoryImpl(ParameterMapper parameterMapper) {
        this.parameterMapper = parameterMapper;
    }

    @Override
    public void addAll(List<Parameter> parameters) {
        if (!parameters.isEmpty()) {
            this.parameterMapper.addAll(parameters);
        }
    }

    @Override
    public List<Parameter> findByIds(Set<String> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        return this.parameterMapper.findByIds(ids);
    }
}