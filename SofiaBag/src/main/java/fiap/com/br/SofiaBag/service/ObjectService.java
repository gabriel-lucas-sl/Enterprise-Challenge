package fiap.com.br.SofiaBag.service;

import fiap.com.br.SofiaBag.dto.request.ObjectDTO;
import fiap.com.br.SofiaBag.dto.response.CalendarResponseDTO;
import fiap.com.br.SofiaBag.dto.response.MessageResponseDTO;
import fiap.com.br.SofiaBag.entity.Object;
import fiap.com.br.SofiaBag.mapper.ObjectMapper;
import fiap.com.br.SofiaBag.repository.ObjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__( @Autowired ))
public class ObjectService {

    private ObjectRepository objectRepository;

    private final ObjectMapper objectMapper = ObjectMapper.INSTANCE;

    public MessageResponseDTO createObject(ObjectDTO objectDTO) {
        Object objetToSave = objectMapper.toModel(objectDTO);
        Object savedObject = objectRepository.save(objetToSave);

        return createMessageResponse(savedObject, "create");
    }

    public List<CalendarResponseDTO> getUserObjects(String id, String reminder) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date reminderDate = format.parse(reminder);

            List<Object> allUserObjects = objectRepository.findObjectByUserIdAndReminder(id, reminderDate);

            return allUserObjects.stream()
                    .map(objectMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public MessageResponseDTO updateUserObject(ObjectDTO objectDTO) {
        String cdObject = objectDTO.getCdRfid();
        String userId = objectDTO.getUser().getId();

        Object object = objectRepository.findObjectByCdRfidAndUserId(cdObject, userId);
        if (object == null) return null; // create exception to handle it

        Object objectToUpdate = objectMapper.toModel(objectDTO);
        Object updatedObject = objectRepository.save(objectToUpdate);

        return createMessageResponse(updatedObject, "update");
    }

    public MessageResponseDTO deleteUserObject(String cdRfid, String userId) {
        Object objectToDelete = objectRepository.findObjectByCdRfidAndUserId(cdRfid, userId);
        if (objectToDelete == null) return null;

        objectRepository.delete(objectToDelete);

        return createMessageResponse(objectToDelete, "delete");
    }

    public List<CalendarResponseDTO> listAll() {
        List<Object> allObjects = objectRepository.findAll();

        return allObjects.stream()
                .map(objectMapper::toDTO)
                .collect(Collectors.toList());
    }

    private MessageResponseDTO createMessageResponse(Object savedObject, String messageFor) {
        if (messageFor.startsWith("create")) {
            return MessageResponseDTO.builder()
                    .message("Object " + savedObject.getName() + " created with ID " + savedObject.getCdRfid()).build();

        } else if (messageFor.startsWith("up")) {
            return MessageResponseDTO.builder()
                    .message("Object " + savedObject.getName() + " updeted with ID " + savedObject.getCdRfid()).build();

        } else {
            return MessageResponseDTO.builder()
                    .message("Object " + savedObject.getName() + " deleted with ID " + savedObject.getCdRfid()).build();
        }
    }
}
