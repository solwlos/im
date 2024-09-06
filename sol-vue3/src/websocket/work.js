let websocket = null;

/* 存下与 shared worker 连接了的所有端口 */
const portPool = [];

onconnect = function (e) {
  const port = e.ports[0];
  portPool.push(port); // 将新连接的端口添加到池中

  const connectToWebSocket = (url) => {
    if (!websocket) {
      websocket = new WebSocket(url);

      websocket.onopen = function () {
        broadcast({ type: 'status', data: 'Connected' });
      };

      websocket.onmessage = function (msg) {
        broadcast({ type: 'message', data: msg.data });
      };

      websocket.onerror = function (_err) {
        broadcast({ type: 'error', data: 'Error' });
      };

      websocket.onclose = function () {
        broadcast({ type: 'status', data: 'Disconnected' });
        websocket = null; // 连接关闭后，清除 WebSocket 对象
      };
    }
  };

  port.onmessage = function (event) {
    if (event.data.command === 'connect') {
      connectToWebSocket(event.data.url);
    } else if (event.data.command === 'disconnect') {
      if (websocket) {
        websocket.close();
      }
    }
  };

  port.start();

  // 当页面关闭或刷新时，从端口池中移除端口
  port.onclose = function () {
    const index = portPool.indexOf(port);
    if (index !== -1) {
      portPool.splice(index, 1);
    }
  };
};

// 广播消息到所有连接的端口
function broadcast(message) {
  portPool.forEach((port) => {
    port.postMessage(message);
  });
}
