package com.kingofthehill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by patrikv on 07/03/16.
 */

@Controller
public class KingController {

    private final LapRepository lapRepository;

    @Autowired
    public KingController(final LapRepository lapRepository) {
        this.lapRepository = lapRepository;
    }

    @RequestMapping("/")
    public String hello(Model model) {
        List<Lap> laps = lapRepository.findAll();
        model.addAttribute("laps", laps);
        return "currentRace";
    }
}
