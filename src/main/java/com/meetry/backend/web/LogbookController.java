package com.meetry.backend.web;

import com.meetry.backend.aspect.annotation.Authenticated;
import com.meetry.backend.command.*;
import com.meetry.backend.command.model.*;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.web.model.request.AddCommentWebRequest;
import com.meetry.backend.web.model.request.AddLogbookWebRequest;
import com.meetry.backend.web.model.response.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/logbook")
@AllArgsConstructor
public class LogbookController {

  private final CommandExecutor commandExecutor;

  private final AuthHelper authHelper;

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.POST, value = "/{proyekId}/{subFolderId}")
  public BaseResponse addLogbook(HttpServletRequest httpServletRequest, @PathVariable("proyekId") String proyekId,
      @PathVariable("subFolderId") String subFolderId,
      @RequestBody AddLogbookWebRequest webRequest) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    AddLogbookCommandRequest commandRequest = AddLogbookCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .subFolderId(subFolderId)
        .judul(webRequest.getJudul())
        .deskripsi(webRequest.getDeskripsi())
        .waktu(webRequest.getWaktu())
        .tags(webRequest.getTags())
        .build();

    return commandExecutor.execute(AddLogbookCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.DELETE, value = "/{proyekId}/{logbookId}")
  public BaseResponse deleteLogbook(HttpServletRequest httpServletRequest,
      @PathVariable("proyekId") String proyekId,
      @PathVariable("logbookId") String logbookId) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    DeleteLogbookCommandRequest commandRequest = DeleteLogbookCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .logbookId(logbookId)
        .build();

    return commandExecutor.execute(DeleteLogbookCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.PUT, value = "/{proyekId}/{logbookId}")
  public BaseResponse editLogbook(HttpServletRequest httpServletRequest, @PathVariable("proyekId") String proyekId,
      @PathVariable("logbookId") String logbookId,
      @RequestBody AddLogbookWebRequest webRequest) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    EditLogbookCommandRequest commandRequest = EditLogbookCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .logbookId(logbookId)
        .judul(webRequest.getJudul())
        .deskripsi(webRequest.getDeskripsi())
        .waktu(webRequest.getWaktu())
        .tags(webRequest.getTags())
        .build();

    return commandExecutor.execute(EditLogbookCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.GET, value = "/{proyekId}/{logbookId}")
  public DefaultResponse<GetLogbookByIdWebResponse> getLogbookById(HttpServletRequest httpServletRequest,
      @PathVariable("proyekId") String proyekId,
      @PathVariable("logbookId") String logbookId) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    GetLogbookByIdCommandRequest commandRequest = GetLogbookByIdCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .logbookId(logbookId)
        .build();

    return commandExecutor.execute(GetLogbookByIdCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.GET, value = "/{proyekId}/{subFolderId}/getLogbooks")
  public DefaultResponse<GetLogbooksWebResponse> getLogbooks(HttpServletRequest httpServletRequest,
      @PathVariable("proyekId") String proyekId, @PathVariable("subFolderId") String subFolderId,
      @RequestParam(value = "page", defaultValue = "0") int page) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    GetLogbooksCommandRequest commandRequest = GetLogbooksCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .subFolderId(subFolderId)
        .page(page)
        .build();

    return commandExecutor.execute(GetLogbooksCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.POST, value = "/{proyekId}/{subFolderId}/{logbookId}/comment")
  public BaseResponse addComment(HttpServletRequest httpServletRequest,
      @PathVariable("proyekId") String proyekId,
      @PathVariable("subFolderId") String subFolderId,
      @PathVariable("logbookId") String logbookId,
      @RequestBody AddCommentWebRequest webRequest) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    AddCommentCommandRequest commandRequest = AddCommentCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .subFolderId(subFolderId)
        .logbookId(logbookId)
        .content(webRequest.getContent())
        .build();

    return commandExecutor.execute(AddCommentCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.GET, value = "/{proyekId}/{subFolderId}/{logbookId}/comment")
  public DefaultResponse<List<GetLogbookCommentsWebResponse>> getLogbookComments(
      HttpServletRequest httpServletRequest,
      @PathVariable("proyekId") String proyekId,
      @PathVariable("subFolderId") String subFolderId,
      @PathVariable("logbookId") String logbookId) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    GetLogbookCommentsCommandRequest commandRequest = GetLogbookCommentsCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .subFolderId(subFolderId)
        .logbookId(logbookId)
        .build();

    return commandExecutor.execute(GetLogbookCommentsCommand.class, commandRequest);
  }

}
