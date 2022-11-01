package dev.jianmu.git.repo.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author laoji
 * @class GitRepo
 * @description git仓库
 * @create 2022-07-03 15:39
 */
public class GitRepo {

    /**
     * git仓库id
     */
    private String id;

    /**
     * 唯一标识
     */
    private String ref;

    /**
     * 拥有者
     */
    private String owner;

    /**
     * 所有分支
     */
    private List<Branch> branches = List.of();

    /**
     * 所有流水线
     */
    private List<Flow> flows = new ArrayList<>();

    public GitRepo() {
    }

    public GitRepo(String id) {
        this.id = id;
    }

    /**
     * 同步分支
     *
     * @param branches
     */
    public void syncBranches(String ref, String owner, List<Branch> branches) {
        this.ref = ref;
        this.owner = owner;
        this.branches = branches;
        var defaultBranch = this.findDefaultBranch();
        this.flows.forEach(flow -> {
            if (this.branches.stream().anyMatch(branch -> flow.getBranchName().equals(branch.getName()))) {
                return;
            }
            flow.setBranchName(defaultBranch.getName());
        });
    }

    private Branch findDefaultBranch() {
        return this.branches.stream()
                .filter(Branch::getIsDefault)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未找到默认分支：" + this.id));
    }

    public void addFlow(Flow flow) {
        this.flows.add(flow);
    }

    public void removeFlow(String projectId) {
        this.flows.stream()
                .filter(flow -> flow.getProjectId().equals(projectId))
                .findFirst()
                .ifPresent(this.flows::remove);
    }

    public Optional<Flow> findFlowByProjectId(String projectId) {
        return this.flows.stream()
                .filter(flow -> flow.getProjectId().equals(projectId))
                .findFirst();
    }

    public String getId() {
        return id;
    }

    public String getRef() {
        return ref;
    }

    public String getOwner() {
        return owner;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public List<Flow> getFlows() {
        return flows;
    }
}
