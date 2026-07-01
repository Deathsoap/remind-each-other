package com.sh.sh_xiao_cheng_xu.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{openId}")
public class WebSocketServer {

    // 在线用户：key=openId，value=连接会话
    public static ConcurrentHashMap<String, Session> onlineUserMap = new ConcurrentHashMap<>();
    // 社团用户映射：key=社团ID，value=社团内所有openId集合
    public static ConcurrentHashMap<Integer, Set<String>> societyUserMap = new ConcurrentHashMap<>();

    private Session session;
    private String openId;

    // 用户打开小程序建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("openId") String openId) {
        this.session = session;
        this.openId = openId;
        onlineUserMap.put(openId, session);
    }

    // 用户关闭小程序断开连接
    @OnClose
    public void onClose() {
        onlineUserMap.remove(openId);
    }

    // 向指定社团全体在线成员推送提醒消息
    public static void sendSocietyMsg(Integer societyId, String jsonMsg) {
        Set<String> openIdSet = societyUserMap.get(societyId);
        if (openIdSet == null || openIdSet.isEmpty()) return;
        for (String openId : openIdSet) {
            Session session = onlineUserMap.get(openId);
            if (session != null && session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(jsonMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
