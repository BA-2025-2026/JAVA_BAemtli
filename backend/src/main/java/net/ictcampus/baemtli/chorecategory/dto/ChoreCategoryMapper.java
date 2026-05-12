package net.ictcampus.baemtli.chorecategory.dto;

import net.ictcampus.baemtli.chorecategory.ChoreCategory;

import java.util.Optional;

public class ChoreCategoryMapper {

    public static ChoreCategoryDTO toDto(ChoreCategory category) {
        return new ChoreCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static void updateFromDto(ChoreCategory category, UpdateChoreCategoryDTO dto) {
        Optional.ofNullable(dto.getName())
                .filter(n -> !n.isBlank())
                .ifPresent(category::setName);

        Optional.ofNullable(dto.getDescription())
                .filter(d -> !d.isBlank())
                .ifPresent(category::setDescription);
    }
}
