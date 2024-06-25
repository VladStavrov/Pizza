package modsen.interns.pizza_modsen.attribute;

import lombok.RequiredArgsConstructor;
import modsen.interns.pizza_modsen.attribute.dto.AttributeDTO;
import modsen.interns.pizza_modsen.attribute.dto.CreateAttributeDTO;
import modsen.interns.pizza_modsen.category.CategoryService;
import modsen.interns.pizza_modsen.model.Attribute;
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
public class AttributeService {
    private final AttributeRepository attributeRepository;
    private final ModelMapper modelMapper;

    public List<AttributeDTO> getAllAttributes(){
        return attributeRepository.findAll()
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AttributeDTO> getAttributeDTOById(Long id){
        return attributeRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Attribute getAttributeById(Long id){
        return attributeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attribute not found with id: " + id));
    }

    public AttributeDTO createAttribute(CreateAttributeDTO attributeDTO){
        Attribute attribute = convertToEntity(attributeDTO);
        Attribute savedAttribute = attributeRepository.save(attribute);
        return convertToDTO(savedAttribute);
    }

    public AttributeDTO updateAttribute(Long attributeId, CreateAttributeDTO attributeDTO){
        Attribute existingAttribute = getAttributeById(attributeId);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(attributeDTO, existingAttribute);
        Attribute updateAttribute = attributeRepository.save(existingAttribute);
        return convertToDTO(updateAttribute);
    }

    public void deleteAttribute(Long id){attributeRepository.deleteById(id);}

    private AttributeDTO convertToDTO(Attribute attribute){return modelMapper.map(attribute, AttributeDTO.class);}

    private Attribute convertToEntity(Object attributeDTO){return modelMapper.map(attributeDTO, Attribute.class);}
}
