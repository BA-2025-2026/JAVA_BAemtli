package net.ictcampus.baemtli.chorecategory.dto;

import net.ictcampus.baemtli.chorecategory.ChoreCategory;

public class ChoreCategoryMapper {

    public static ChoreCategoryDTO toDto(ChoreCategory category) {
        return new ChoreCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static void updateFromDto(ChoreCategory category, UpdateChoreCategoryDTO dto) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            category.setName(dto.getName());
        }

        if (dto.getDescription() != null) {
            category.setDescription(dto.getDescription());
        }
    }
}
