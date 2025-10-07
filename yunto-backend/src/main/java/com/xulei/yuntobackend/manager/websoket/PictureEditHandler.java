package com.xulei.yuntobackend.manager.websoket;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xulei.yuntobackend.manager.websoket.disruptor.PictureEditEventProducer;
import com.xulei.yuntobackend.manager.websoket.model.PictureEditActionEnum;
import com.xulei.yuntobackend.manager.websoket.model.PictureEditMessageTypeEnum;
import com.xulei.yuntobackend.manager.websoket.model.PictureEditRequestMessage;
import com.xulei.yuntobackend.manager.websoket.model.PictureEditResponseMessage;
import com.xulei.yuntobackend.model.entity.User;
import com.xulei.yuntobackend.service.UserService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PictureEditHandler extends TextWebSocketHandler {

    @Resource
    private UserService userService;

    // 每张图片的编辑状态，key: pictureId, value: 当前正在编辑的用户 ID
    private final Map<Long, Long> pictureEditingUsers = new ConcurrentHashMap<>();

    // 保存所有连接的会话，key: pictureId, value: 用户会话集合
    private final Map<Long, Set<WebSocketSession>> pictureSessions = new ConcurrentHashMap<>();
    @Autowired
    private PictureEditEventProducer pictureEditEventProducer;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 保存会话到集合中
        User user = (User) session.getAttributes().get("user");
        Long pictureId = (Long) session.getAttributes().get("pictureId");
        pictureSessions.putIfAbsent(pictureId, ConcurrentHashMap.newKeySet());
        pictureSessions.get(pictureId).add(session);

        // 构造响应
        PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
        pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.INFO.getValue());
        String message = String.format("%s加入编辑", user.getUserName());
        pictureEditResponseMessage.setMessage(message);
        pictureEditResponseMessage.setUser(userService.getUserVO(user));
        // 广播给同一张图片的用户
        broadcastToPicture(pictureId, pictureEditResponseMessage);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 将消息解析为 PictureEditMessage
        PictureEditRequestMessage pictureEditRequestMessage = JSONUtil.toBean(message.getPayload(), PictureEditRequestMessage.class);
        String type = pictureEditRequestMessage.getType();
        PictureEditMessageTypeEnum pictureEditMessageTypeEnum = PictureEditMessageTypeEnum.valueOf(type);

        // 从 Session 属性中获取公共参数
        Map<String, Object> attributes = session.getAttributes();
        User user = (User) attributes.get("user");
        Long pictureId = (Long) attributes.get("pictureId");

        // 生产消息
        pictureEditEventProducer.publishEvent(pictureEditRequestMessage, session, user, pictureId);
    }


    /**
     * 向指定图片的所有连接会话广播编辑消息
     *
     * @param pictureId                  图片ID，用于查找相关会话
     * @param pictureEditResponseMessage 要广播的图片编辑响应消息
     * @param excludeSession             要排除的会话，不向该会话发送消息
     * @throws Exception 可能抛出的异常
     */
    private void broadcastToPicture(Long pictureId, PictureEditResponseMessage pictureEditResponseMessage, WebSocketSession excludeSession) throws Exception {
        // 获取与该图片关联的所有WebSocket会话
        Set<WebSocketSession> sessionSet = pictureSessions.get(pictureId);
        // 如果会话集合不为空
        if (CollUtil.isNotEmpty(sessionSet)) {
            // 创建 ObjectMapper 实例用于JSON序列化
            ObjectMapper objectMapper = new ObjectMapper();
            // 配置序列化：将 Long 类型转为 String，解决丢失精度问题
            SimpleModule module = new SimpleModule();
            module.addSerializer(Long.class, ToStringSerializer.instance);
            module.addSerializer(Long.TYPE, ToStringSerializer.instance); // 支持 long 基本类型
            objectMapper.registerModule(module);
            // 序列化为 JSON 字符串
            String message = objectMapper.writeValueAsString(pictureEditResponseMessage);
            TextMessage textMessage = new TextMessage(message);
            for (WebSocketSession session : sessionSet) {
                // 排除掉的 session 不发送
                if (excludeSession != null && excludeSession.equals(session)) {
                    continue;
                }
                if (session.isOpen()) {
                    session.sendMessage(textMessage);
                }
            }
        }
    }

    /**
     * 广播图片编辑信息到所有订阅该图片的客户端
     *
     * @param pictureId                  图片ID，用于标识需要广播的图片
     * @param pictureEditResponseMessage 图片编辑响应消息，包含图片编辑的相关信息
     * @throws Exception 如果广播过程中发生错误，抛出异常
     */
    // 全部广播
    private void broadcastToPicture(Long pictureId, PictureEditResponseMessage pictureEditResponseMessage) throws Exception {
        // 调用重载方法，传入null作为指定用户参数，表示广播给所有订阅该图片的用户
        broadcastToPicture(pictureId, pictureEditResponseMessage, null);
    }

    /**
     * 处理用户进入编辑图片的消息
     *
     * @param pictureEditRequestMessage 图片编辑请求消息
     * @param session                   WebSocket会话
     * @param user                      用户信息
     * @param pictureId                 图片ID
     * @throws Exception 可能抛出的异常
     */
    public void handleEnterEditMessage(PictureEditRequestMessage pictureEditRequestMessage, WebSocketSession session, User user, Long pictureId) throws Exception {
        // 没有用户正在编辑该图片，才能进入编辑
        if (!pictureEditingUsers.containsKey(pictureId)) {
            // 设置当前用户为编辑用户
            pictureEditingUsers.put(pictureId, user.getId());
            PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
            pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.ENTER_EDIT.getValue());
            String message = String.format("%s开始编辑图片", user.getUserName());
            pictureEditResponseMessage.setMessage(message);
            pictureEditResponseMessage.setUser(userService.getUserVO(user));
            broadcastToPicture(pictureId, pictureEditResponseMessage);
        }
    }


    /**
     * 处理图片编辑动作消息
     *
     * @param pictureEditRequestMessage 图片编辑请求消息对象
     * @param session                   WebSocket会话对象
     * @param user                      用户对象
     * @param pictureId                 图片ID
     * @throws Exception 可能抛出的异常
     */
    public void handleEditActionMessage(PictureEditRequestMessage pictureEditRequestMessage, WebSocketSession session, User user, Long pictureId) throws Exception {
        // 获取当前图片的编辑者ID
        Long editingUserId = pictureEditingUsers.get(pictureId);
        // 获取编辑动作类型
        String editAction = pictureEditRequestMessage.getEditAction();
        // 根据动作值获取对应的枚举
        PictureEditActionEnum actionEnum = PictureEditActionEnum.getEnumByValue(editAction);
        // 如果动作枚举为空，直接返回
        if (actionEnum == null) {
            return;
        }
        // 确认是当前编辑者
        if (editingUserId != null && editingUserId.equals(user.getId())) {
            // 创建编辑响应消息对象
            PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
            // 设置消息类型为编辑动作
            pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.EDIT_ACTION.getValue());
            // 构建消息内容，包含用户名和编辑动作
            String message = String.format("%s执行%s", user.getUserName(), actionEnum.getText());
            pictureEditResponseMessage.setMessage(message);
            // 设置编辑动作
            pictureEditResponseMessage.setEditAction(editAction);
            // 设置用户信息
            pictureEditResponseMessage.setUser(userService.getUserVO(user));
            // 广播给除了当前客户端之外的其他用户，否则会造成重复编辑
            broadcastToPicture(pictureId, pictureEditResponseMessage, session);
        }
    }

    /**
     * 处理用户退出图片编辑的请求
     *
     * @param pictureEditRequestMessage 图片编辑请求消息
     * @param session                   WebSocket会话
     * @param user                      当前用户
     * @param pictureId                 图片ID
     * @throws Exception 可能抛出的异常
     */
    public void handleExitEditMessage(PictureEditRequestMessage pictureEditRequestMessage, WebSocketSession session, User user, Long pictureId) throws Exception {
        // 获取当前正在编辑该图片的用户ID
        Long editingUserId = pictureEditingUsers.get(pictureId);
        // 检查当前用户是否是编辑者
        if (editingUserId != null && editingUserId.equals(user.getId())) {
            // 移除当前用户的编辑状态
            pictureEditingUsers.remove(pictureId);
            // 构造响应，发送退出编辑的消息通知
            PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
            // 设置消息类型为退出编辑
            pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.EXIT_EDIT.getValue());
            // 设置退出编辑的消息内容
            String message = String.format("%s退出编辑图片", user.getUserName());
            pictureEditResponseMessage.setMessage(message);
            // 设置用户信息
            pictureEditResponseMessage.setUser(userService.getUserVO(user));
            // 向所有关注该图片的广播消息
            broadcastToPicture(pictureId, pictureEditResponseMessage);
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Long pictureId = (Long) attributes.get("pictureId");
        User user = (User) attributes.get("user");
        // 移除当前用户的编辑状态
        handleExitEditMessage(null, session, user, pictureId);

        // 删除会话
        Set<WebSocketSession> sessionSet = pictureSessions.get(pictureId);
        if (sessionSet != null) {
            sessionSet.remove(session);
            if (sessionSet.isEmpty()) {
                pictureSessions.remove(pictureId);
            }
        }

        // 响应
        PictureEditResponseMessage pictureEditResponseMessage = new PictureEditResponseMessage();
        pictureEditResponseMessage.setType(PictureEditMessageTypeEnum.INFO.getValue());
        String message = String.format("%s离开编辑", user.getUserName());
        pictureEditResponseMessage.setMessage(message);
        pictureEditResponseMessage.setUser(userService.getUserVO(user));
        broadcastToPicture(pictureId, pictureEditResponseMessage);
    }

}
