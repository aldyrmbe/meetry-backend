package com.meetry.backend.web;

import com.meetry.backend.aspect.annotation.Authenticated;
import com.meetry.backend.command.*;
import com.meetry.backend.command.model.*;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.user.ERIC;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.repository.user.ERICRepository;
import com.meetry.backend.web.model.request.RegisterAccountOfficerWebRequest;
import com.meetry.backend.web.model.request.LoginWebRequest;
import com.meetry.backend.web.model.response.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

  private final CommandExecutor commandExecutor;

  private final AuthHelper authHelper;

  private final ERICRepository ericRepository;

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/register/peneliti",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public BaseResponse registerPeneliti(
      @RequestPart("data") String data,
      @RequestPart(value = "fotoProfil", required = false) MultipartFile fotoProfil) {

    RegisterPenelitiCommandRequest commandRequest = RegisterPenelitiCommandRequest.builder()
        .data(data)
        .fotoProfil(fotoProfil)
        .build();

    return commandExecutor.execute(RegisterPenelitiCommand.class, commandRequest);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/register/mitra",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public BaseResponse registerMitra(
      @RequestPart("data") String data,
      @RequestPart(value = "fotoProfil", required = false) MultipartFile fotoProfil) {

    RegisterMitraCommandRequest commandRequest = RegisterMitraCommandRequest.builder()
        .data(data)
        .fotoProfil(fotoProfil)
        .build();

    return commandExecutor.execute(RegisterMitraCommand.class, commandRequest);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public LoginWebResponse login(@RequestBody LoginWebRequest loginWebRequest, HttpServletResponse httpServletResponse) {

    LoginCommandRequest commandRequest = LoginCommandRequest.builder()
        .email(loginWebRequest.getEmail())
        .password(loginWebRequest.getPassword())
        .httpServletResponse(httpServletResponse)
        .build();

    return commandExecutor.execute(LoginCommand.class, commandRequest);
  }

  @RequestMapping(
      method = RequestMethod.GET,
      value = "")
  @Authenticated
  public UserWebResponse getCurrentLoggedInUser(HttpServletRequest httpServletRequest) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);
    GetCurrentLoggedInUserCommandRequest commandRequest = GetCurrentLoggedInUserCommandRequest.builder()
        .session(session)
        .build();

    return commandExecutor.execute(GetCurrentLoggedInUserCommand.class, commandRequest);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/logout")
  @Authenticated
  public BaseResponse logOut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    LogOutCommandRequest commandRequest = LogOutCommandRequest.builder()
        .httpServletRequest(httpServletRequest)
        .httpServletResponse(httpServletResponse)
        .build();

    return commandExecutor.execute(LogOutCommand.class, commandRequest);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/addERIC")
  public BaseResponse addERIC() {

    ERIC eric = ERIC.builder()
        .email("eric@ugm.ac.id")
        .password("$2a$10$U00LEfuL9drVgdCHLooOBeYuABqZ1mkR7a33HeaD8uM4PrtzXkrNS")
        .role(Role.ERIC)
        .build();

    ericRepository.save(eric);

    return BaseResponse.builder()
        .code(200)
        .status("OK")
        .message("ERIC didaftarkan")
        .build();
  }

  @Authenticated(value = {Role.ERIC})
  @RequestMapping(method = RequestMethod.POST, value = "/addAccountOfficer")
  public BaseResponse registerAccountOfficer(HttpServletRequest httpServletRequest,
      @RequestBody RegisterAccountOfficerWebRequest webRequest) {

    RegisterAccountOfficerCommandRequest commandRequest = RegisterAccountOfficerCommandRequest.builder()
        .name(webRequest.getName())
        .email(webRequest.getEmail())
        .password(webRequest.getPassword())
        .build();

    return commandExecutor.execute(RegisterAccountOfficerCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.GET, value = "/notification")
  public DefaultResponse<NotificationDataWebResponse> getNotificationData(HttpServletRequest httpServletRequest,
      @RequestParam(value = "page", required = false, defaultValue = "0") int page) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);

    GetNotificationCommandRequest commandRequest = GetNotificationCommandRequest.builder()
        .session(session)
        .page(page)
        .build();

    return commandExecutor.execute(GetNotificationCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.PUT, value = "/openNotification/{userId}/{notificationId}")
  public void openNotification(HttpServletRequest httpServletRequest, @PathVariable("userId") String userId,
      @PathVariable("notificationId") String notificationId) {

    OpenNotificationCommandRequest commandRequest = OpenNotificationCommandRequest.builder()
        .userId(userId)
        .notificationId(notificationId)
        .build();

    commandExecutor.execute(OpenNotificationCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ERIC})
  @RequestMapping(method = RequestMethod.GET, value = "/getListAccountOfficers")
  public DefaultResponse<GetAccountOfficerListWebResponse> getListAccountOfficers(
      HttpServletRequest httpServletRequest,
      @RequestParam(value = "page", required = false, defaultValue = "0") int page) {

    GetListAccountOfficersCommandRequest commandRequest = GetListAccountOfficersCommandRequest.builder()
        .page(page)
        .build();

    return commandExecutor.execute(GetListAccountOfficersCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ERIC})
  @RequestMapping(method = RequestMethod.GET, value = "/getListUsernames")
  public DefaultResponse<List<GetListUsernamesWebResponse>> getListUsernames(HttpServletRequest httpServletRequest,
      @RequestParam("role") Role role, @RequestParam(value = "query", defaultValue = "") String query) {

    GetListUsernamesCommandRequest commandRequest = GetListUsernamesCommandRequest.builder()
        .role(role)
        .query(query)
        .build();

    return commandExecutor.execute(GetListUsernamesCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.GET, value = "/mitra/{mitraId}")
  public DefaultResponse<GetMitraProfileWebResponse> getMitraProfile(HttpServletRequest httpServletRequest,
      @PathVariable("mitraId") String mitraId) {

    GetMitraProfileCommandRequest commandRequest = GetMitraProfileCommandRequest.builder()
        .mitraId(mitraId)
        .build();

    return commandExecutor.execute(GetMitraProfileCommand.class, commandRequest);
  }

  @Authenticated
  @RequestMapping(method = RequestMethod.GET, value = "/peneliti/{penelitiId}")
  public DefaultResponse<GetPenelitiProfileWebResponse> getPenelitiProfile(HttpServletRequest httpServletRequest,
      @PathVariable("penelitiId") String penelitiId) {

    GetPenelitiProfileCommandRequest commandRequest = GetPenelitiProfileCommandRequest.builder()
        .penelitiId(penelitiId)
        .build();

    return commandExecutor.execute(GetPenelitiProfileCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.PENELITI})
  @RequestMapping(method = RequestMethod.GET, value = "/peneliti/acadstaffLink")
  public DefaultResponse<String> getAcadstaffLink(HttpServletRequest httpServletRequest) {

    Session session = authHelper.getSessionFromCookie(httpServletRequest);
    GetAcadstaffLinkCommandRequest commandRequest = GetAcadstaffLinkCommandRequest.builder()
        .session(session)
        .build();

    return commandExecutor.execute(GetAcadstaffLinkCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ERIC})
  @RequestMapping(method = RequestMethod.GET, value = "/searchMitra")
  public DefaultResponse<SearchMitraWebResponse> searchMitra(HttpServletRequest httpServletRequest,
      @RequestParam(value = "searchQuery", defaultValue = "") String searchQuery,
      @RequestParam(value = "page", defaultValue = "0") int page) {

    SearchMitraCommandRequest commandRequest = SearchMitraCommandRequest.builder()
        .searchQuery(searchQuery)
        .page(page)
        .build();

    return commandExecutor.execute(SearchMitraCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ERIC})
  @RequestMapping(method = RequestMethod.GET, value = "/searchPeneliti")
  public DefaultResponse<SearchPenelitiWebResponse> searchPeneliti(HttpServletRequest httpServletRequest,
      @RequestParam(value = "searchQuery", defaultValue = "") String searchQuery,
      @RequestParam(value = "page", defaultValue = "0") int page) {

    SearchPenelitiCommandRequest commandRequest = SearchPenelitiCommandRequest.builder()
        .searchQuery(searchQuery)
        .page(page)
        .build();

    return commandExecutor.execute(SearchPenelitiCommand.class, commandRequest);
  }

  @Authenticated(value = {Role.ERIC})
  @RequestMapping(method = RequestMethod.GET, value = "/searchAccountOfficer")
  public DefaultResponse<SearchAccountOfficerWebResponse> searchAccountOfficer(HttpServletRequest httpServletRequest,
      @RequestParam(value = "searchQuery", defaultValue = "") String searchQuery,
      @RequestParam(value = "page", defaultValue = "0") int page) {

    SearchAccountOfficerCommandRequest commandRequest = SearchAccountOfficerCommandRequest.builder()
        .searchQuery(searchQuery)
        .page(page)
        .build();

    return commandExecutor.execute(SearchAccountOfficerCommand.class, commandRequest);
  }
}
