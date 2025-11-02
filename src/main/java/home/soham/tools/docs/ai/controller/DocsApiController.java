package home.soham.tools.docs.ai.controller;


import home.soham.tools.docs.ai.service.RagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/search")

public class DocsApiController {
    private final RagService ragService;

    public DocsApiController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/stream")
    public Flux<String> fetchStreaming(@RequestParam("q")String query){
        return ragService.retrieveAndGenerateStreaming(query);
    }


    @GetMapping("/insurance")
    public Flux<String> streamInsuranceInformation(@RequestParam("q")String query){
        return ragService.queryDatabaseAndAnswerInsuranceDetails(query);
    }
}
