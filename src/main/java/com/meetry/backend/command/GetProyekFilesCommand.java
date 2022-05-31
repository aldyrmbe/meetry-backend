package com.meetry.backend.command;

import com.meetry.backend.command.model.GetProyekFilesCommandRequest;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.GetProyekFilesWebResponse;

import java.util.List;

public interface GetProyekFilesCommand
    extends Command<GetProyekFilesCommandRequest, DefaultResponse<List<GetProyekFilesWebResponse>>> {
}
