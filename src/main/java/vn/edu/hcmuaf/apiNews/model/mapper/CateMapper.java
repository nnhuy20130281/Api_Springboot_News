package vn.edu.hcmuaf.apiNews.model.mapper;

import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.model.dto.CateDto;

import java.util.Set;

public class CateMapper {

    public static CateDto toCateDto(Category category) {
        CateDto cateDto = new CateDto();
        cateDto.setId(category.getId());
        cateDto.setName(category.getName());
        cateDto.setIsDelete(category.getIsDelete());
        return cateDto;
    }

    public static Set<CateDto> toCateDto(Set<Category> categories) {
        return categories.stream().map(CateMapper::toCateDto).collect(java.util.stream.Collectors.toSet());
    }
}
