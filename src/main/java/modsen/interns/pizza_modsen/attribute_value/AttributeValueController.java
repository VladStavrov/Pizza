package modsen.interns.pizza_modsen.attribute_value;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.attribute_value.dto.AttributeValueDTO;
import modsen.interns.pizza_modsen.attribute_value.dto.CreateAttributeValueDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(AttributeValueController.BASE_URL)
public class AttributeValueController {

    public static final String BASE_URL = "/attribute-values";
    public static final String ID_PATH = "/{id}";

    private final AttributeValueService attributeValueService;

    @GetMapping
    public ResponseEntity<?> getAllAttributeValues() {
        List<AttributeValueDTO> attributeValues = attributeValueService.getAllAttributeValues();
        return new ResponseEntity<>(attributeValues, HttpStatus.OK);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<?> getAttributeValueById(@PathVariable Long id) {
        Optional<AttributeValueDTO> attributeValueDTO = attributeValueService.getAttributeValueDTOById(id);
        return attributeValueDTO.map(attributeValue -> new ResponseEntity<>(attributeValue, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createAttributeValue(@RequestBody CreateAttributeValueDTO attributeValueDTO) {
        AttributeValueDTO createdAttributeValue = attributeValueService.createAttributeValue(attributeValueDTO);
        return new ResponseEntity<>(createdAttributeValue, HttpStatus.CREATED);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<?> updateAttributeValue(@PathVariable Long id, @RequestBody CreateAttributeValueDTO attributeValueDTO) {
        AttributeValueDTO updatedAttributeValue = attributeValueService.updateAttributeValue(id, attributeValueDTO);
        return new ResponseEntity<>(updatedAttributeValue, HttpStatus.OK);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> deleteAttributeValue(@PathVariable Long id) {
        attributeValueService.deleteAttributeValue(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
