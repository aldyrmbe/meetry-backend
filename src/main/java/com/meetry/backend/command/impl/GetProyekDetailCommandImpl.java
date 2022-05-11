package com.meetry.backend.command.impl;

import com.meetry.backend.command.GetProyekDetailCommand;
import com.meetry.backend.command.model.GetProyekDetailCommandRequest;
import com.meetry.backend.entity.Session;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.constant.StatusProyek;
import com.meetry.backend.entity.folder.Folder;
import com.meetry.backend.entity.proyek.*;
import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.entity.user.Mitra;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.helper.ProyekHelper;
import com.meetry.backend.repository.FolderRepository;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.repository.user.MitraRepository;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.exception.UnauthorizedException;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.ProyekDetailWebResponse;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class GetProyekDetailCommandImpl implements GetProyekDetailCommand {

  private final ProyekHelper proyekHelper;

  private final MitraRepository mitraRepository;

  private final PenelitiRepository penelitiRepository;

  private final AccountOfficerRepository accountOfficerRepository;

  private final FolderRepository folderRepository;

  @Override
  public DefaultResponse<ProyekDetailWebResponse> execute(GetProyekDetailCommandRequest commandRequest) {

    Proyek proyek = getProyekById(commandRequest);

    return DefaultResponse.<ProyekDetailWebResponse>builder()
        .code(200)
        .status("OK")
        .data(toProyekDetailWebResponse(proyek))
        .build();
  }

  private void checkProyekAuthorization(Session session, Partisipan partisipan) {

    Role role = session.getRole();
    if (Role.PENELITI.equals(role)) {
      if (!partisipan.getPeneliti()
          .contains(session.getId()))
        throw new UnauthorizedException();
    }
    if (Role.MITRA.equals(role)) {
      if (!partisipan.getMitra()
          .contains(session.getId()))
        throw new UnauthorizedException();
    }
    if (Role.ACCOUNT_OFFICER.equals(role)) {
      if (!partisipan.getAccountOfficer()
          .equals(session.getId()))
        throw new UnauthorizedException();
    }
  }

  private Proyek getProyekById(GetProyekDetailCommandRequest commandRequest) {

    Proyek proyek = proyekHelper.findProyekById(commandRequest.getId());
    checkProyekAuthorization(commandRequest.getSession(), proyek.getPartisipan());
    return proyek;
  }

  private List<ProyekDetailWebResponse.OverviewProyek.Partisipan.DetailPartisipan> getMitra(Partisipan partisipan) {

    return partisipan.getMitra()
        .stream()
        .map(mitraId -> {
          Mitra mitra = mitraRepository.findById(mitraId)
              .orElseThrow(() -> new BaseException("Mitra tidak ditemukan"));
          return ProyekDetailWebResponse.OverviewProyek.Partisipan.DetailPartisipan.builder()
              .id(mitraId)
              .nama(mitra.getNamaPerusahaan())
              .fotoProfil(mitra.getFotoProfil())
              .profilePageUrl(String.format("/%s", mitra.getId()))
              .build();
        })
        .collect(Collectors.toList());
  }

  private List<ProyekDetailWebResponse.OverviewProyek.Partisipan.DetailPartisipan> getPeneliti(Partisipan partisipan) {

    return partisipan.getPeneliti()
        .stream()
        .map(penelitiId -> {
          Peneliti peneliti = penelitiRepository.findById(penelitiId)
              .orElseThrow(() -> new BaseException("Peneliti tidak ditemukan"));
          return ProyekDetailWebResponse.OverviewProyek.Partisipan.DetailPartisipan.builder()
              .id(penelitiId)
              .nama(peneliti.getNamaLengkap())
              .fotoProfil(peneliti.getFotoProfil())
              .profilePageUrl(peneliti.getAcadstaffLink())
              .build();
        })
        .collect(Collectors.toList());
  }

  private ProyekDetailWebResponse.OverviewProyek.Partisipan.DetailPartisipan getAccountOfficer(Partisipan partisipan) {

    if (Objects.nonNull(partisipan.getAccountOfficer())) {
      AccountOfficer accountOfficer = accountOfficerRepository.findById(partisipan.getAccountOfficer())
          .orElseThrow(() -> new BaseException("Account officer tidak ditemukan"));

      return ProyekDetailWebResponse.OverviewProyek.Partisipan.DetailPartisipan.builder()
          .id(accountOfficer.getId())
          .nama(accountOfficer.getName())
          .fotoProfil(accountOfficer.getProfilePhoto())
          .build();
    }

    return null;
  }

  private ProyekDetailWebResponse.OverviewProyek.Partisipan getPartisipan(Partisipan partisipan) {

    return ProyekDetailWebResponse.OverviewProyek.Partisipan.builder()
        .peneliti(getPeneliti(partisipan))
        .mitra(getMitra(partisipan))
        .accountOfficer(getAccountOfficer(partisipan))
        .build();
  }

  private String convertLongToDateString(Long timeInMillis) {

    String pattern = "d MMMMM yyyy";
    SimpleDateFormat date = new SimpleDateFormat(pattern, new Locale("id", "ID"));

    return date.format(new Date(timeInMillis));
  }

  private String getPeriode(Periode periode) {

    String start = convertLongToDateString(periode.getMulai());
    String end = convertLongToDateString(periode.getSelesai());

    return String.format("%s - %s", start, end);
  }

  private List<ProyekDetailWebResponse.OverviewProyek.Pendukung> getLinkPendukung(List<String> linkPendukungList) {

    return IntStream.range(0, linkPendukungList.size())
        .mapToObj(index -> ProyekDetailWebResponse.OverviewProyek.Pendukung.builder()
            .nama(String.format("Link %s", index+1))
            .value(linkPendukungList.get(index))
            .build())
        .collect(Collectors.toList());
  }

  private List<ProyekDetailWebResponse.OverviewProyek.Pendukung> getDokumenPendukung(
      List<DokumenPendukung> dokumenPendukungList) {

    return dokumenPendukungList.stream()
        .map(dokumen -> ProyekDetailWebResponse.OverviewProyek.Pendukung.builder()
            .nama(dokumen.getNama())
            .value(dokumen.getUrl())
            .build())
        .collect(Collectors.toList());
  }

  private List<ProyekDetailWebResponse.KebutuhanProyek> getKebutuhanProyekList(Proyek proyek) {

    return proyek.getListKebutuhanProyek().stream()
        .map(kebutuhanProyek -> ProyekDetailWebResponse.KebutuhanProyek.builder()
            .kebutuhanProyek(kebutuhanProyek.getKebutuhanProyek())
            .bentukKolaborasi(kebutuhanProyek.getBentukKolaborasi())
            .penjelasanTambahan(kebutuhanProyek.getPenjelasanTambahan())
            .partisipan(getParticipantName(proyek.getPemohon(), kebutuhanProyek.getPartisipan()))
            .build())
        .collect(Collectors.toList());
  }

  private String getParticipantName(Role pemohon, String id){
    if(Objects.isNull(id)){
      return StringUtil.EMPTY_STRING;
    }
    if(Role.PENELITI.equals(pemohon)){
      return mitraRepository.findById(id).get().getNamaPerusahaan();
    }
    return penelitiRepository.findById(id).get().getNamaLengkap();
  }

  private List<ProyekDetailWebResponse.Folder> getLogbook(Proyek proyek) {

    if(StatusProyek.DALAM_PENGAJUAN.equals(proyek.getStatus())){
      return new ArrayList<>();
    }

    List<Folder> folders = folderRepository.findAllByProyekId(proyek.getId());

    return folders
        .stream()
        .map(folder -> ProyekDetailWebResponse.Folder.builder()
            .id(folder.getId())
            .namaFolder(folder.getFolderName())
            .build())
        .collect(Collectors.toList());

  }

  private ProyekDetailWebResponse toProyekDetailWebResponse(Proyek proyek) {

    ProyekDetailWebResponse.OverviewProyek overviewProyek = ProyekDetailWebResponse.OverviewProyek.builder()
        .judul(proyek.getJudulProyek())
        .partisipan(getPartisipan(proyek.getPartisipan()))
        .periode(getPeriode(proyek.getPeriode()))
        .bidang(proyek.getBidang())
        .latarBelakang(proyek.getLatarBelakang())
        .tujuan(proyek.getTujuan())
        .sasaran(proyek.getSasaran())
        .output(proyek.getOutput())
        .kebermanfaatanProduk(proyek.getKebermanfaatanProduk())
        .indikatorKesuksesan(proyek.getIndikatorKesuksesan())
        .tingkatKesiapan(proyek.getTingkatKesiapan())
        .linkPendukung(getLinkPendukung(proyek.getLinkPendukung()))
        .dokumenPendukung(getDokumenPendukung(proyek.getDokumenPendukung()))
        .whatsappGroupLink(Optional.ofNullable(proyek.getWhatsappGroupLink())
            .orElse(null))
        .build();

    return ProyekDetailWebResponse.builder()
        .status(proyek.getStatus())
        .pemohon(proyek.getPemohon())
        .overviewProyek(overviewProyek)
        .kebutuhanProyek(getKebutuhanProyekList(proyek))
        .folders(getLogbook(proyek))
        .build();
  }
}
