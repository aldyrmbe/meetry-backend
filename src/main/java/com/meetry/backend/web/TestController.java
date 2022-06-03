package com.meetry.backend.web;

import com.meetry.backend.command.CommandExecutor;
import com.meetry.backend.command.TestCommand;
import com.meetry.backend.command.model.TestCommandRequest;
import com.meetry.backend.entity.notifikasi.NotificationItem;
import com.meetry.backend.entity.notifikasi.Notifikasi;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.repository.NotificationRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.model.request.RealtimeNotificationWebSocketPayload;
import com.meetry.backend.web.model.request.TestWebRequest;
import com.meetry.backend.web.model.response.TestWebResponse;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    private final CommandExecutor commandExecutor;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationRepository notificationRepository;
    private final PenelitiRepository penelitiRepository;
    private final MitraRepository mitraRepository;

    @GetMapping(value = "/test")
    public String test(){
        return "String";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/testwebsocket")
    public void testWs(){
        RealtimeNotificationWebSocketPayload payload = new RealtimeNotificationWebSocketPayload(true);
        simpMessagingTemplate.convertAndSend("/notification/625f87318eb28e77769f1630", payload);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/testnofi")
    public void testNotif(){
        NotificationItem item = NotificationItem.builder()
            .createdAt(Instant.now().toEpochMilli())
            .description("OK")
            .title("OK")
            .redirectionUrl("OK")
            .build();

        Notifikasi notifikasi = Notifikasi.builder()
            .userId("okokok")
            .hasNewNotification(true)
            .items(Collections.singletonList(item))
            .build();

        String id = new ObjectId().toString();

        notificationRepository.save(notifikasi);
    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/migrateNotifications")
//    public void migrate(){
//        List<Mitra> mitraList = mitraRepository.findAll();
//        List<Peneliti> penelitiList = penelitiRepository.findAll();
//
//        mitraList.forEach(mitra -> {
//            Notifikasi notifikasi = Notifikasi.builder()
//                .userId(mitra.getId())
//                .items(new ArrayList<>())
//                .hasNewNotification(false)
//                .build();
//            notificationRepository.save(notifikasi);
//        });
//
//        penelitiList.forEach(peneliti -> {
//            Notifikasi notifikasi = Notifikasi.builder()
//                .userId(peneliti.getId())
//                .items(new ArrayList<>())
//                .hasNewNotification(false)
//                .build();
//            notificationRepository.save(notifikasi);
//        });
//    }
}
