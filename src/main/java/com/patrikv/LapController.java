package com.patrikv;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by patrikv on 06/03/16.
 */
@RestController
public class LapController {

    private final LapRepository lapRepository;
    private final SimpMessagingTemplate template;
    private final JsonFactory factory = new JsonFactory();

    @Autowired
    public LapController(final LapRepository lapRepository, final SimpMessagingTemplate template) {
        this.lapRepository = lapRepository;
        this.template = template;
    }

    @RequestMapping(value = "/addLap", method = RequestMethod.POST)
    public ResponseEntity<Lap> addLap(@RequestBody Lap lap) {
        lapRepository.save(lap);
        System.out.println("LapController.addLap");
        System.out.println(lap);
        MessageBuilder<String> builder = MessageBuilder.withPayload(createJSon(lap));
        template.convertAndSend("/topic/greetings", builder.build());
        return new ResponseEntity<>(lap, HttpStatus.OK);
    }

    private String createJSon(final Lap lap) {
        try {
            Writer writer = new StringWriter();
            JsonGenerator generator = factory.createGenerator(writer);
            generator.writeStartObject();
            generator.writeStringField("driver", lap.getDriver());
            generator.writeNumberField("transponder", lap.getTransponder());
            generator.writeNumberField("lapNr", lap.getLapNr());
            generator.writeNumberField("lapTime", lap.getLapTime());
            generator.writeEndObject();
            generator.close();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Lap>> laps() {
        List<Lap> laps = lapRepository.findAll();
        System.out.println("LapController.laps " + laps);
        return new ResponseEntity<>(laps, HttpStatus.OK);
    }

}
