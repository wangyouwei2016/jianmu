export const PRIMARY_COLOR = '#096DD9';

export const NODE = {
  toolbarDistance: 10,
  iconSize: {
    width: 72,
    height: 72,
  },
  textMaxHeight: 40,
  selectedBorderWidth: 6,
};

export const EDGE = {
  stroke: {
    _default: '#667085',
    connecting: '#A7B0BB',
    hover: '#B9CFE6',
  },
};

export const PORT = {
  fill: {
    _default: '#FFFFFF',
    connectingSource: '#A7B0BB',
    connectingTarget: PRIMARY_COLOR,
  },
  r: 5,
};

export const PORTS = {
  groups: {
    top: {
      position: 'top',
      attrs: {
        circle: {
          r: 0,
          // 连接桩在连线交互时不可被连接
          magnet: false,
          stroke: '#A7B0BB',
          strokeWidth: 1,
          fill: PORT.fill._default,
        },
      },
    },
    right: {
      position: 'right',
      attrs: {
        circle: {
          r: 0,
          // 连接桩在连线交互时不可被连接
          magnet: false,
          stroke: '#A7B0BB',
          strokeWidth: 1,
          fill: PORT.fill._default,
        },
      },
    },
    bottom: {
      position: 'bottom',
      attrs: {
        circle: {
          r: 0,
          // 连接桩在连线交互时不可被连接
          magnet: false,
          stroke: '#A7B0BB',
          strokeWidth: 1,
          fill: PORT.fill._default,
        },
      },
    },
    left: {
      position: 'left',
      attrs: {
        circle: {
          r: 0,
          // 连接桩在连线交互时不可被连接
          magnet: false,
          stroke: '#A7B0BB',
          strokeWidth: 1,
          fill: PORT.fill._default,
        },
      },
    },
  },
  items: [
    {
      group: 'top',
    },
    {
      group: 'right',
    },
    {
      group: 'bottom',
    },
    {
      group: 'left',
    },
  ],
};