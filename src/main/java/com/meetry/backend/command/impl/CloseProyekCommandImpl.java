package com.meetry.backend.command.impl;

import com.meetry.backend.command.CloseProyekCommand;
import com.meetry.backend.command.model.CloseProyekCommandRequest;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.proyek.Proyek;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.helper.NotificationHelper;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.ProyekRepository;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CloseProyekCommandImpl implements CloseProyekCommand {

    private final ProyekRepository proyekRepository;

    private final AuthHelper authHelper;

    private final ProyekHelper proyekHelper;

    private final NotificationHelper notificationHelper;

    @Override
    public BaseResponse execute(CloseProyekCommandRequest commandRequest) {

        Proyek proyek = proyekHelper.findProyekById(commandRequest.getProyekId());
        authHelper.checkAccountOfficerAuthorization(commandRequest.getSession(), proyek);
        proyekHelper.verifyProyekStatusToCloseProyek(proyek);
        closeProyek(proyek);

        return BaseResponse.builder()
            .code(200)
            .status("OK")
            .message("Proyek berhasil diselesaikan.")
            .build();
    }

    private void closeProyek(Proyek proyek){
        proyek.setStatus(StatusProyek.SELESAI);
        Proyek closedProyek = proyekRepository.save(proyek);
        notificationHelper.sendNotificationForClosingProyek(closedProyek);
    }
}
