package modsen.interns.pizza_modsen.attribute;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.attribute.dto.AttributeDTO;
import modsen.interns.pizza_modsen.attribute.dto.CreateAttributeDTO;
import modsen.interns.pizza_modsen.category.dto.CategoryDTO;
import modsen.interns.pizza_modsen.category.dto.CreateCategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(AttributeController.BASE_URL)
@RequiredArgsConstructor
public class AttributeController {
    public static final String BASE_URL = "/attributes";
    public static final String ID_PATH = "/{id}";

    private final AttributeService attributeService;

    @GetMapping
    public ResponseEntity<?> getAllAttributes() {
        List<AttributeDTO> attributes = attributeService.getAllAttributes();
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?> getAttributeById(@PathVariable Long id) {
        Optional<AttributeDTO> attributeDTO = attributeService.getAttributeDTOById(id);
        return attributeDTO.map(attribute -> new ResponseEntity<>(attribute, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createAttribute(@RequestBody CreateAttributeDTO attributeDTO) {
        AttributeDTO createdAttribute = attributeService.createAttribute(attributeDTO);
        return new ResponseEntity<>(createdAttribute, HttpStatus.CREATED);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<?> updateAttribute(@PathVariable Long id, @RequestBody CreateAttributeDTO attributeDTO) {
        AttributeDTO updatedAttribute = attributeService.updateAttribute(id, attributeDTO);
        return new ResponseEntity<>(updatedAttribute, HttpStatus.OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> deleteAttribute(@PathVariable Long id) {
        attributeService.deleteAttribute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
