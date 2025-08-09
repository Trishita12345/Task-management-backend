package com.example.auth.model.mapper;

import com.example.auth.model.dto.common.SelectOptionDTO;
import com.example.auth.model.enums.SelectOption;

import java.util.List;

public class EntityToSelectedOptionMapper {
    public static <T, V extends Object & SelectOption<T>> SelectOptionDTO<T> entityToSelectedOptionMapper(V entity){
        return new SelectOptionDTO<>(entity.getLabel(), entity.getValue());
    }
    public static <T, V extends Object & SelectOption<T>> List<SelectOptionDTO<T>> entityToSelectedOptionListMapper(List<V> entities){
        return entities.stream().map(EntityToSelectedOptionMapper::entityToSelectedOptionMapper).toList();
    }
}
