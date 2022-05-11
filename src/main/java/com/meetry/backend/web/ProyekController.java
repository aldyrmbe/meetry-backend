package com.meetry.backend.web;

import com.meetry.backend.aspect.annotation.Authenticated;
import com.meetry.backend.command.*;
import com.meetry.backend.command.model.*;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.web.model.request.SetProyekOnDiscusssionWebRequest;
import com.meetry.backend.web.model.response.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/proyek")
@AllArgsConstructor
public class ProyekController {

  private final CommandExecutor commandExecutor;

  private final AuthHelper authHelper;

  @Authenticated(value = {Role.PENELITI, Role.MITRA})
  @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public BaseResponse ajukanProyek(HttpServletRequest httpServletRequest,
      @RequestPart("data") String data,
      @RequestPart(value = "files", required = false) MultipartFile[] files) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);
    AjukanProyekCommandRequest commandRequest = AjukanProyekCommandRequest.builder()
        .session(session)
        .data(data)
        .files(files)
        .build();

    return commandExecutor.execute(AjukanProyekCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.GET, value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultResponse<ListProyekWebResponse> getProyekList(
      HttpServletRequest httpServletRequest,
      @RequestParam(value = "status", required = false) StatusProyek status,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "searchQuery", required = false) String searchQuery) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);
    GetListProyekCommandRequest commandRequest = GetListProyekCommandRequest.builder()
        .session(session)
        .status(status)
        .searchQuery(searchQuery)
        .page(page)
        .build();

    return commandExecutor.execute(GetListProyekCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DefaultResponse<ProyekDetailWebResponse> getProyekDetail(HttpServletRequest httpServletRequest,
      @PathVariable("id") String id) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);
    GetProyekDetailCommandRequest commandRequest = GetProyekDetailCommandRequest.builder()
        .session(session)
        .id(id)
        .build();

    return commandExecutor.execute(GetProyekDetailCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.GET, value = "/folder/{folderId}")
  public DefaultResponse<GetSubFoldersWebResponse> getSubFolders(HttpServletRequest httpServletRequest,
      @PathVariable("folderId") String folderId) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);
    GetSubFoldersCommandRequest commandRequest = GetSubFoldersCommandRequest.builder()
        .session(session)
        .folderId(folderId)
        .build();

    return commandExecutor.execute(GetSubFoldersCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.POST, value = "/folder/{folderId}/addSubFolder")
  public BaseResponse addSubFolder(HttpServletRequest httpServletRequest,
      @PathVariable("folderId") String folderId, @RequestParam("subFolderName") String subFolderName) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);
    AddSubFolderCommandRequest commandRequest = AddSubFolderCommandRequest.builder()
        .session(session)
        .folderId(folderId)
        .subFolderName(subFolderName)
        .build();

    return commandExecutor.execute(AddSubFolderCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.DELETE, value = "/subFolder/{subFolderId}")
  public BaseResponse deleteSubFolder(HttpServletRequest httpServletRequest,
      @PathVariable("subFolderId") String subFolderId) {

    DeleteSubFolderCommandRequest commandRequest = DeleteSubFolderCommandRequest.builder()
        .subFolderId(subFolderId)
        .build();

    return commandExecutor.execute(DeleteSubFolderCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.PUT, value = "/folder/{folderId}/subFolder/{subFolderId}")
  public BaseResponse editSubFolder(HttpServletRequest httpServletRequest,
      @PathVariable("folderId") String folderId,
      @PathVariable("subFolderId") String subFolderId,
      @RequestParam String subFolderName) {

    EditSubFolderCommandRequest commandRequest = EditSubFolderCommandRequest.builder()
        .folderId(folderId)
        .subFolderId(subFolderId)
        .subFolderName(subFolderName)
        .build();

    return commandExecutor.execute(EditSubFolderCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ERIC})
  @RequestMapping(method = RequestMethod.PUT, value = "/{proyekId}/setOnDiscussion")
  public BaseResponse setProyekOnDiscussion(HttpServletRequest httpServletRequest,
      @PathVariable("proyekId") String proyekId, @RequestBody SetProyekOnDiscusssionWebRequest webRequest) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    SetProyekOnDiscussionCommandRequest commandRequest = SetProyekOnDiscussionCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .partisipan(webRequest.getPartisipan())
        .accountOfficer(webRequest.getAccountOfficer())
        .whatsappGroupLink(webRequest.getWhatsappGroupLink())
        .build();

    return commandExecutor.execute(SetProyekOnDiscussionCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.PUT, value = "/{proyekId}/activate")
  public BaseResponse activateProyek(HttpServletRequest httpServletRequest,
      @PathVariable("proyekId") String proyekId,
      @RequestPart(value = "files", required = false) MultipartFile[] files) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    ActivateProyekCommandRequest commandRequest = ActivateProyekCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .files(files)
        .build();

    return commandExecutor.execute(ActivateProyekCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.PUT, value = "/{proyekId}/close")
  public BaseResponse closeProyek(HttpServletRequest httpServletRequest, @PathVariable("proyekId") String proyekId) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    CloseProyekCommandRequest commandRequest = CloseProyekCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .build();

    return commandExecutor.execute(CloseProyekCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ACCOUNT_OFFICER, Role.ERIC})
  @RequestMapping(method = RequestMethod.PUT, value = "/{proyekId}/cancel")
  public BaseResponse cancelProyek(HttpServletRequest httpServletRequest, @PathVariable("proyekId") String proyekId) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    CancelProyekCommandRequest commandRequest = CancelProyekCommandRequest.builder()
        .session(session)
        .proyekId(proyekId)
        .build();

    return commandExecutor.execute(CancelProyekCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ERIC})
  @RequestMapping(method = RequestMethod.GET, value = "/getProyekOnRequest")
  public DefaultResponse<GetListProyekOnRequestWebRequest> getProyekOnRequest(HttpServletRequest httpServletRequest,
      @RequestParam("pemohon") Role pemohon,
      @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
      @RequestParam(value = "page", defaultValue = "0") int page) {
    GetListProyekOnRequestCommandRequest commandRequest = GetListProyekOnRequestCommandRequest.builder()
        .pemohon(pemohon)
        .searchQuery(searchQuery)
        .page(page)
        .build();

    return commandExecutor.execute(GetListProyekOnRequestCommand.class, commandRequest);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getUniv")
  public BaseResponse getUniv() {

    GetIndonesianUniversitiesCommandRequest commandRequest = new GetIndonesianUniversitiesCommandRequest();

    return commandExecutor.execute(GetIndonesianUniversitiesCommand.class, commandRequest);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/university")
  public DefaultResponse<List<GetUniversitiesByNameWebResponse>> getUniversitiesByName(
      @RequestParam("query") String query) {

    System.out.println("called");
    GetUniversitiesByNameCommandRequest commandRequest = GetUniversitiesByNameCommandRequest.builder()
        .query(query)
        .build();

    return commandExecutor.execute(GetUniversitiesByNameCommand.class, commandRequest);
  }

}
