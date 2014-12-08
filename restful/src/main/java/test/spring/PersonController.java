package test.spring;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import test.spring.beans.Person;
import test.spring.service.PersonService;

@Controller
@RequestMapping("/")
public class PersonController {

	protected final Log log = LogFactory.getLog(getClass());

//	// Spring standard way to get a bean
//	@Autowired 	@Qualifier("default34")
//	// @Autowired default use type to find target bean
//	private PersonService personService;

	 @Inject @Named("default23")
	 private PersonService personService;

	@RequestMapping(value = "defaultPerson", method = RequestMethod.GET)
	public String showdefaultPerson(Map<String, Object> model) {
		if (log.isDebugEnabled()) {
			log.debug("showFirstPage of FirstController...");
		}
		Person person = personService.getPerson(99); // get default person
		model.put("person", person);
		model.put("author", "Justin Wu");
		return "firstPage";
	}

}
