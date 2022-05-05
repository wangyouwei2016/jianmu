import { Edge, Graph, Node } from '@antv/x6';
import { CustomX6NodeProxy } from './data/custom-x6-node-proxy';
import { NodeTypeEnum } from './data/enumeration';
import { NODE, PORT } from '../shape/gengral-config';

export class WorkflowNodeToolbar {
  private readonly proxy: any;
  private readonly el: HTMLElement;
  private readonly graph: Graph;
  private node?: Node;

  constructor(proxy: any, graph: Graph) {
    this.proxy = proxy;
    this.el = this.proxy.nodeToolbar.$el;
    this.graph = graph;
  }

  show(node: Node): void {
    if (this.graph.isSelected(node)) {
      // 节点已被选中时，忽略
      return;
    }

    this.node = node;

    // 显示连接桩
    this.showPorts(node);

    this.move();
  }

  hide(e?: MouseEvent): void {
    if (!this.node || e && e.relatedTarget === this.el) {
      return;
    }

    // 隐藏连接桩
    this.hidePorts();

    this.deleteNode();
  }

  private deleteNode() {
    delete this.node;
    this.el.style.left = '';
    this.el.style.top = '';
  }

  removeNode(): void {
    if (!this.node) {
      return;
    }

    const node = this.node;
    const nodeProxy = new CustomX6NodeProxy(node);
    const nodeData = nodeProxy.getData();

    let msg = '<div>确定要删除吗?</div>';
    msg += `<div style="margin-top: 5px; font-size: 12px; line-height: normal;">名称：${nodeData.getName()}</div>`;
    let title = '删除';
    switch (nodeData.getType()) {
      case NodeTypeEnum.ASYNC_TASK:
      case NodeTypeEnum.SHELL:
        title += '节点';
        break;
      case NodeTypeEnum.CRON:
      case NodeTypeEnum.WEBHOOK:
        title += '触发器';
        break;
    }

    this.proxy.$confirm(msg, title, {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
      dangerouslyUseHTMLString: true,
    }).then(async () => {
      this.graph.removeCell(node);

      this.deleteNode();
    }).catch(() => {
    });
  }

  private move(): void {
    if (!this.node) {
      return;
    }

    const node = this.node;

    // 保证不偏移
    setTimeout(() => {
      // 画布偏移量
      const { tx, ty } = this.graph.translate();
      // 相对于画布的绝对坐标
      const { x, y } = node.getPosition();
      // 缩放比例
      const zoom = this.graph.zoom();
      // 节点大小
      const { width: nodeW } = node.size();
      // 节点工具栏大小
      const { offsetWidth: toolbarW, offsetHeight: toolbarH } = this.el;
      // 缩放导致的y轴偏移量
      const scaleYOffset = (zoom - 1) * toolbarH / 2;
      const left = x * zoom + tx + (nodeW * zoom - toolbarW) / 2;
      const top = y * zoom + ty - toolbarH - scaleYOffset;

      // 与节点宽度保持一致
      this.el.style.width = `${NODE.iconSize.width}px`;
      this.el.style.transform = `scale(${zoom},${zoom})`;
      this.el.style.left = `${left}px`;
      this.el.style.top = `${top}px`;
    });
  }

  /**
   * 显示连接桩
   * pipeline节点边只能有一个进、一个出
   * @param currentNode
   * @private
   */
  private showPorts(currentNode: Node) {
    const currentNodePortIds = currentNode.getPorts().map(metadata => metadata.id);
    const allEdges = this.graph.getEdges();

    if (allEdges.find(edge => {
      const { port: sourcePortId } = edge.getSource() as Edge.TerminalCellData;
      return currentNodePortIds.includes(sourcePortId);
    })) {
      // 表示当前节点存在出的边
      return;
    }

    const excludedNodes = this.getNodesInLine(currentNode, allEdges);
    // 环路检测：排除以当前节点为终点的上游所有节点
    const nodes = this.graph.getNodes()
      .filter(node => !excludedNodes.includes(node))
      // 筛选不存在出的边
      .filter(node => {
        const nodePortIds = node.getPorts().map(metadata => metadata.id);
        return !allEdges.find(edge => {
          const { port: targetPortId } = edge.getTarget() as Edge.TerminalCellData;
          return nodePortIds.includes(targetPortId);
        });
      });

    if (nodes.length === 0) {
      return;
    }

    nodes.push(currentNode);

    nodes.forEach(node =>
      node.getPorts().forEach(port => {
        node.portProp(port.id!, {
          attrs: {
            circle: {
              r: PORT.r,
              // 连接桩在连线交互时可以被连接
              magnet: true,
            },
          },
        });
      }));
  }

  /**
   * 隐藏连接桩
   * @private
   */
  private hidePorts() {
    this.graph.getNodes().forEach(node =>
      node.getPorts().forEach(port =>
        node.portProp(port.id!, {
          attrs: {
            circle: {
              r: 0,
              // 连接桩在连线交互时不可被连接
              magnet: false,
            },
          },
        })));
  }

  /**
   * 获取以当前节点为终点的上游所有节点
   * @param targetNode
   * @param edges
   * @private
   */
  private getNodesInLine(targetNode: Node, edges: Edge[]): Node[] {
    const nodes: Node[] = [targetNode];

    const targetNodePortsIds = targetNode.getPorts().map(metadata => metadata.id);
    const edge = edges.find(edge => {
      const { port: targetPortId } = edge.getTarget() as Edge.TerminalCellData;

      return targetNodePortsIds.includes(targetPortId);
    });

    if (edge) {
      nodes.push(...this.getNodesInLine(edge.getSourceNode()!, edges));
    }

    return nodes;
  }
}