package modsen.interns.pizza_modsen.attribute_value;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.attribute_value.dto.AttributeValueDTO;
import modsen.interns.pizza_modsen.attribute_value.dto.CreateAttributeValueDTO;
import modsen.interns.pizza_modsen.model.Attribute;
import modsen.interns.pizza_modsen.model.AttributeValue;
import modsen.interns.pizza_modsen.model.Product;
import modsen.interns.pizza_modsen.attribute.AttributeService;
import modsen.interns.pizza_modsen.product.ProductService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttributeValueService {

    private final AttributeValueRepository attributeValueRepository;
    private final AttributeService attributeService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public List<AttributeValueDTO> getAllAttributeValues() {
        return attributeValueRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AttributeValueDTO> getAttributeValueDTOById(Long id) {
        return attributeValueRepository.findById(id)
                .map(this::convertToDTO);
    }

    public AttributeValue getAttributeValueById(Long id) {
        return attributeValueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AttributeValue not found with id: " + id));
    }

    public AttributeValueDTO createAttributeValue(CreateAttributeValueDTO createAttributeValueDTO) {
        AttributeValue attributeValue = convertToEntity(createAttributeValueDTO);

        Attribute attribute = attributeService.getAttributeById(createAttributeValueDTO.getAttributeId());
        Product product = productService.getProductById(createAttributeValueDTO.getProductId());

        attributeValue.setAttribute(attribute);
        attributeValue.setProduct(product);

        AttributeValue savedAttributeValue = attributeValueRepository.save(attributeValue);
        return convertToDTO(savedAttributeValue);
    }

    public AttributeValueDTO updateAttributeValue(Long attributeValueId, CreateAttributeValueDTO createAttributeValueDTO) {
        AttributeValue existingAttributeValue = getAttributeValueById(attributeValueId);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(createAttributeValueDTO, existingAttributeValue);

        Attribute attribute = attributeService.getAttributeById(createAttributeValueDTO.getAttributeId());
        Product product = productService.getProductById(createAttributeValueDTO.getProductId());

        existingAttributeValue.setAttribute(attribute);
        existingAttributeValue.setProduct(product);

        AttributeValue updatedAttributeValue = attributeValueRepository.save(existingAttributeValue);
        return convertToDTO(updatedAttributeValue);
    }

    public void deleteAttributeValue(Long id) {
        attributeValueRepository.deleteById(id);
    }

    private AttributeValueDTO convertToDTO(AttributeValue attributeValue) {
        return modelMapper.map(attributeValue, AttributeValueDTO.class);
    }

    private AttributeValue convertToEntity(Object attributeValueDTO) {
        return modelMapper.map(attributeValueDTO, AttributeValue.class);
    }
}
