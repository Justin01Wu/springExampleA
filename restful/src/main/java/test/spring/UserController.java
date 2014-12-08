package test.spring;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import test.spring.beans.User;
import test.spring.exception.EntityNotFoundException;
import test.spring.service.UserService;
import test.spring.validator.UserValidator;

@Controller
@RequestMapping("/users")
public class UserController {

	protected final Log log = LogFactory.getLog(getClass());
	
	private static int id = 200;	
	
	@Autowired 
	private UserService userService;  

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		//binder.setValidator(new UserValidator());
		binder.addValidators(new UserValidator());
	}

	@RequestMapping(value = "/{id}", 
			method = RequestMethod.GET, 
			headers = { "Accept=application/json" }, 
			produces = { "application/json" })
	public @ResponseBody User getUser(@PathVariable("id") int id) {
		User user = userService.getUser(id);
		if (user == null) {
			if (log.isDebugEnabled()) {
				log.debug("can't find user for : " + id);
			}
			throw new EntityNotFoundException("can't find user for : " + id);
		} else {
			return user;
		}

	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody User createUser(@Valid @RequestBody User user){
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();		
		Validator validator = validatorFactory.getValidator();		
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		if(violations.size() >0){
			if (log.isDebugEnabled()) {
				log.debug("       ===>> can't password JSR 303 validator " );
			}
		}

		id ++;
		user.setId(id);
		if(log.isDebugEnabled()){
			log.debug("going to save a new user: " + user.getId() + ", " + user.getFirstName());
		}
		userService.saveUser(user);
		return user;
	}
	
}
