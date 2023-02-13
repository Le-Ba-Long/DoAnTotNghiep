package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.LanguageDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.LanguageSearchDto;
import com.longkubi.qlns.model.entity.Language;
import com.longkubi.qlns.repository.LanguageRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.ILanguageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.longkubi.qlns.common.ErrorMessage.*;
@Transactional
@Service
public class LanguageServiceImpl implements ILanguageService {
    @Autowired
    private LanguageRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public ResponseData<LanguageDto> create(LanguageDto languageDto, String token) {
        ErrorMessage errorMessage = validateLanguage(languageDto,null,Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Language entity = new Language();
        modelMapper.map(languageDto, entity);
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), LanguageDto.class));
    }


    @Override
    public ResponseData<LanguageDto> update(LanguageDto languageDto, UUID id, String token) {
        ErrorMessage errorMessage = validateLanguage(languageDto,id,Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        Language entity = repo.getLanguageById(id);
        entity.setCode(languageDto.getCode());
        entity.setName(languageDto.getName());
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDescription(languageDto.getDescription());
        entity.setDateChange(new Date());
        return new ResponseData<>(modelMapper.map(repo.save(entity), LanguageDto.class));
    }

    @Override
    public ResponseData<List<LanguageDto>> getAll() {
     //   List<Language> languageDtos = repo.findAll();
        List<Language> languageDtos = repo.getAll();
        if (languageDtos.isEmpty()) return new ResponseData<>(SUCCESS, null);
        return new ResponseData<>(languageDtos.stream()
                .map(dto -> modelMapper.map(dto, LanguageDto.class)).collect(Collectors.toList()));
    }

    @Override
    public ResponseData<Page<LanguageDto>> searchByDto(LanguageSearchDto searchDto) {
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("code"));
        Page<LanguageDto>result = repo.findAll(pageable).map(LanguageDto::convertFromEntityToDto);
        if(result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY,null);
        return new ResponseData<>(result);
    }


    @Override
    public ResponseData<Boolean> deleteById(UUID id, String token) {
        if (Boolean.TRUE.equals(repo.existsLanguageById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS,true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }

    private ErrorMessage validateLanguage(LanguageDto languageDto,UUID id,String action) {
        if (Constant.Insert.equals(action)){
            if (Objects.isNull(languageDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsLanguageByCode(languageDto.getCode()))) return CODE_ALREADY_EXIST;
            if (Boolean.TRUE.equals(repo.existsLanguageByName(languageDto.getName()))) return NAME_EXIST;
        }else {
            if (Objects.isNull(languageDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(languageDto.getCode(),id) > 0) return CODE_ALREADY_EXIST;
            if (repo.exclusionName(languageDto.getName(),id)> 0) return NAME_EXIST;
        }
        return SUCCESS;
    }
}
