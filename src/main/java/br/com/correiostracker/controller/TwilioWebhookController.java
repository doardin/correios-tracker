package br.com.correiostracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.correiostracker.application.tracker.TrackerApplicationService;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequestMapping("/v1/tracker")
@RequiredArgsConstructor
public class TwilioWebhookController {

    private final TrackerApplicationService service;

    @PostMapping(path = "/", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void postPackageInfo(@RequestParam(name = "To") String to, @RequestParam(name = "From") String from, @RequestParam(name = "Body") String body){
		service.handlerMessage(body, from);
	}
}
