package net.ictcampus.baemtli.chorecategory;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.baemtli.chorecategory.dto.ChoreCategoryDTO;
import net.ictcampus.baemtli.chorecategory.dto.ChoreCategoryMapper;
import net.ictcampus.baemtli.chorecategory.dto.CreateChoreCategoryDTO;
import net.ictcampus.baemtli.chorecategory.dto.UpdateChoreCategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoreCategoryService {

    private final ChoreCategoryRepository choreCategoryRepository;

    public ChoreCategoryService(ChoreCategoryRepository choreCategoryRepository) {
        this.choreCategoryRepository = choreCategoryRepository;
    }

    public List<ChoreCategoryDTO> getAllCategories() {
        return choreCategoryRepository.findAll().stream()
                .map(ChoreCategoryMapper::toDto)
                .toList();
    }

    public ChoreCategoryDTO getCategoryById(Integer id) {
        ChoreCategory category = choreCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chore category not found"));
        return ChoreCategoryMapper.toDto(category);
    }

    public ChoreCategoryDTO createCategory(CreateChoreCategoryDTO dto) {
        if (choreCategoryRepository.findByName(dto.getName()).isPresent()) {
            throw new EntityExistsException("Chore category with this name already exists");
        }

        ChoreCategory category = new ChoreCategory()
                .setName(dto.getName())
                .setDescription(dto.getDescription());

        ChoreCategory savedCategory = choreCategoryRepository.save(category);
        return ChoreCategoryMapper.toDto(savedCategory);
    }

    public ChoreCategoryDTO updateCategory(Integer id, UpdateChoreCategoryDTO dto) {
        ChoreCategory category = choreCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chore category not found"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            choreCategoryRepository.findByName(dto.getName())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(id)) {
                            throw new EntityExistsException("Chore category with this name already exists");
                        }
                    });
            category.setName(dto.getName());
        }

        ChoreCategoryMapper.updateFromDto(category, dto);
        ChoreCategory updatedCategory = choreCategoryRepository.save(category);
        return ChoreCategoryMapper.toDto(updatedCategory);
    }

    public void deleteCategory(Integer id) {
        if (!choreCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Chore category not found");
        }
        try {
            choreCategoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalStateException("Chore category cannot be deleted as it is still in use", e);
        }
    }
}
