package co.turing.configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
@Api(tags = {"health"})
public class HealthCheck {
	@RequestMapping(value = "health", method = RequestMethod.GET)
	@ApiOperation(value = "Check if service is running", notes = "")
	public String testWrite() {
		return "RUNNING";
	}

}