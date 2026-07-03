package com.sh.sh_xiao_cheng_xu.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/ws/{openId}")
public class WebSocketServer {
    // 存每个社团的在线用户
    private static final Map<Integer, CopyOnWriteArraySet<Session>> societySessionMap = new ConcurrentHashMap<>();
    private Session session;
    private Integer bindSocietyId;

    // 用户打开小程序连接ws时执行
    @OnOpen
    public void onOpen(Session session, @PathParam("openId") String openId) {
        this.session = session;
        this.bindSocietyId = 1;
        societySessionMap.computeIfAbsent(bindSocietyId, k -> new CopyOnWriteArraySet<>()).add(session);
    }

    // 用户关掉小程序断开连接
    @OnClose
    public void onClose() {
        CopyOnWriteArraySet<Session> set = societySessionMap.get(bindSocietyId);
        if (set != null) set.remove(session);
    }

    @OnMessage
    public void onMessage(String msg) {}

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    // 重点：加了 static，外面代码直接 类名.方法名() 调用不报错
    public static void sendSocietyMsg(Integer societyId, String message) {
        CopyOnWriteArraySet<Session> sessionSet = societySessionMap.get(societyId);
        if (sessionSet == null || sessionSet.isEmpty()) return;
        for (Session s : sessionSet) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
