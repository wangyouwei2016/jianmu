package dev.jianmu.infrastructure.mybatis.hub;

import dev.jianmu.hub.intergration.aggregate.NodeDefinitionVersion;
import dev.jianmu.hub.intergration.repository.NodeDefinitionVersionRepository;
import dev.jianmu.infrastructure.mapper.hub.NodeDefinitionVersionMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @class: NodeDefinitionVersionRepositoryImpl
 * @description: NodeDefinitionVersionRepositoryImpl
 * @author: Ethan Liu
 * @create: 2021-09-08 21:52
 **/
@Repository
public class NodeDefinitionVersionRepositoryImpl implements NodeDefinitionVersionRepository {
    private final NodeDefinitionVersionMapper nodeDefinitionVersionMapper;

    public NodeDefinitionVersionRepositoryImpl(NodeDefinitionVersionMapper nodeDefinitionVersionMapper) {
        this.nodeDefinitionVersionMapper = nodeDefinitionVersionMapper;
    }

    @Override
    public Optional<NodeDefinitionVersion> findByRefAndVersion(String ref, String version) {
        return this.nodeDefinitionVersionMapper.findByRefAndVersion(ref, version);
    }

    @Override
    public void saveOrUpdate(NodeDefinitionVersion nodeDefinitionVersion) {
        this.nodeDefinitionVersionMapper.saveOrUpdate(nodeDefinitionVersion);
    }
}
