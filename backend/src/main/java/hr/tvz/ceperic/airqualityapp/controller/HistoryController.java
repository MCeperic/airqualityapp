package hr.tvz.ceperic.airqualityapp.controller;

import hr.tvz.ceperic.airqualityapp.service.HistoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/aqa")
public class HistoryController {

    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

}
