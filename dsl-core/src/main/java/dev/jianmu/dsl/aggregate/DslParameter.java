package dev.jianmu.dsl.aggregate;

/**
 * @class: Param
 * @description: 参数类
 * @author: Ethan Liu
 * @create: 2021-04-21 17:59
 **/
public class DslParameter {
    private String nodeName;
    private String definitionKey;
    private String name;
    private String value;
    private String linkedParameterId;

    public String getNodeName() {
        return nodeName;
    }

    public String getDefinitionKey() {
        return definitionKey;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getLinkedParameterId() {
        return linkedParameterId;
    }

    public void setLinkedParameterId(String linkedParameterId) {
        this.linkedParameterId = linkedParameterId;
    }

    @Override
    public String toString() {
        return "DslParameter{" +
                "nodeName='" + nodeName + '\'' +
                ", definitionKey='" + definitionKey + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", linkedParameterId='" + linkedParameterId + '\'' +
                '}';
    }

    public static final class Builder {
        private String nodeName;
        private String definitionKey;
        private String name;
        private String value;

        private Builder() {
        }

        public static Builder aDslParameter() {
            return new Builder();
        }

        public Builder nodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public Builder definitionKey(String definitionKey) {
            this.definitionKey = definitionKey;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public DslParameter build() {
            DslParameter dslParameter = new DslParameter();
            dslParameter.value = this.value;
            dslParameter.definitionKey = this.definitionKey;
            dslParameter.nodeName = this.nodeName;
            dslParameter.name = this.name;
            return dslParameter;
        }
    }
}
