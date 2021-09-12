package fiap.com.br.SofiaBag.controller;

import fiap.com.br.SofiaBag.dto.request.ObjectDTO;
import fiap.com.br.SofiaBag.dto.response.MessageResponseDTO;
import fiap.com.br.SofiaBag.dto.response.CalendarResponseDTO;
import fiap.com.br.SofiaBag.entity.Object;
import fiap.com.br.SofiaBag.service.ObjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/v1/object")
@AllArgsConstructor(onConstructor = @__( @Autowired ))
public class ObjectController {

    private ObjectService objectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createObject(@RequestBody @Valid ObjectDTO objectDTO) {
        return objectService.createObject(objectDTO);
    }

    @GetMapping("/id={id}&reminder={reminder}")
    @ResponseStatus(HttpStatus.OK)
    public List<CalendarResponseDTO> getUserObjects(@PathVariable String id, @PathVariable String reminder) throws ParseException {
        return objectService.getUserObjects(id, reminder);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO updateUserObject(@RequestBody @Valid ObjectDTO objectDTO) {
        return objectService.updateUserObject(objectDTO);
    }

    @DeleteMapping("/id={userId}&objectRfid={cdRfid}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO deleteUserObject(@PathVariable String cdRfid,@PathVariable String userId) {
        return objectService.deleteUserObject(cdRfid, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CalendarResponseDTO> listObjects() {
        return objectService.listAll();
    }
}
