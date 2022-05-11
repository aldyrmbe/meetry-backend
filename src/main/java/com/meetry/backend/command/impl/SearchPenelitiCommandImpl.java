package com.meetry.backend.command.impl;

import com.meetry.backend.command.SearchPenelitiCommand;
import com.meetry.backend.command.model.SearchPenelitiCommandRequest;
import com.meetry.backend.entity.user.Peneliti;
import com.meetry.backend.repository.user.PenelitiRepository;
import com.meetry.backend.web.model.response.DefaultResponse;
import com.meetry.backend.web.model.response.PaginationData;
import com.meetry.backend.web.model.response.SearchPenelitiWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SearchPenelitiCommandImpl implements SearchPenelitiCommand {

    private final PenelitiRepository penelitiRepository;

    @Override
    public DefaultResponse<SearchPenelitiWebResponse> execute(SearchPenelitiCommandRequest commandRequest) {

        Page<Peneliti> penelitiPage = getPenelitiPage(commandRequest);

        return DefaultResponse.<SearchPenelitiWebResponse>builder()
            .code(200)
            .status("OK")
            .data(toSearchPenelitiWebResponse(penelitiPage))
            .build();
    }

    private Page<Peneliti> getPenelitiPage(SearchPenelitiCommandRequest commandRequest){
        return penelitiRepository.searchPenelitiByName(commandRequest.getSearchQuery(), commandRequest.getPage());
    }

    private SearchPenelitiWebResponse toSearchPenelitiWebResponse(Page<Peneliti> penelitiPage){
        PaginationData paginationData = PaginationData.builder()
            .currentPage(penelitiPage.getPageable().getPageNumber() + 1)
            .totalPage(penelitiPage.getTotalPages())
            .build();

        List<SearchPenelitiWebResponse.PenelitiDetail> penelitiList = penelitiPage.getContent().stream()
            .map(peneliti -> SearchPenelitiWebResponse.PenelitiDetail.builder()
                .id(peneliti.getId())
                .nama(peneliti.getNamaLengkap())
                .fotoProfil(peneliti.getFotoProfil())
                .programStudi(peneliti.getProgramStudi())
                .profileUrl(peneliti.getAcadstaffLink())
                .build())
            .collect(Collectors.toList());

        return SearchPenelitiWebResponse.builder()
            .paginationData(paginationData)
            .penelitiList(penelitiList)
            .build();
    }


}
