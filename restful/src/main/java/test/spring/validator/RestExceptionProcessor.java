package test.spring.validator;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import test.spring.exception.PersonCantDeleteException;
import test.spring.exception.UserNameExistsException;
import test.spring.exception.WriteReadOnlyPropertyException;

/**
 * general exception handler for all controller, it is using @ControllerAdvice, which start from Spring 3.2
 * please also see this ( difference is BindingResult ) :  
 * http://springinpractice.com/2013/10/09/generating-json-error-object-responses-with-spring-web-mvc/
 * http://fruzenshtein.com/spring-rest-exception-handling-3/
 *
 */
@ControllerAdvice
public class RestExceptionProcessor {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
    private MessageSource messageSource;

	@ExceptionHandler(PersonCantDeleteException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo personCanTDelete(HttpServletRequest req,
			PersonCantDeleteException ex) {

		String errorMessage = "can't delete person: " + ex.getId();
		return new ErrorInfo(6786, errorMessage);
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
    	
		if(log.isDebugEnabled()){
			log.debug("going to handle MethodArgumentNotValidException " );
		}
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
 
        return processFieldErrors(fieldErrors);
    }
    
    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO(); 
        
        for (FieldError fieldError: fieldErrors) {
        	if(fieldError.getDefaultMessage() != null){
        		if(log.isDebugEnabled()){
        			log.debug(fieldError.getDefaultMessage() );
        		}
        		dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        	}else{
            	StringBuilder errorMessage = new StringBuilder("");
                errorMessage.append(fieldError.getCode()).append(".");
                errorMessage.append(fieldError.getObjectName()).append(".");
                errorMessage.append(fieldError.getField());
                //String localizedErrorMsg = localizeErrorMessage(errorMessage.toString());
            	//TODO: implement internationalization 
        		if(log.isDebugEnabled()){
        			log.debug(errorMessage.toString());
        		}
                dto.addFieldError(fieldError.getField(), errorMessage.toString());
        	}

        }
 
        return dto;
    }
    
    public String localizeErrorMessage(String errorCode) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(errorCode, null, locale);
        return errorMessage;
    }
    
    @ExceptionHandler(UserNameExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(UserNameExistsException ex) {
    	
    	ValidationErrorDTO dto = new ValidationErrorDTO();
		if(log.isDebugEnabled()){
			log.debug("going to handle UserNameExistsException " );
		}
		 dto.addFieldError(ex.getFieldName(), ex.getMessage());
 
        return dto;
    }

    @ExceptionHandler(WriteReadOnlyPropertyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processReadOnlyError(WriteReadOnlyPropertyException ex) {
    	
    	ValidationErrorDTO dto = new ValidationErrorDTO();
		if(log.isDebugEnabled()){
			log.debug("going to handle WriteReadOnlyPropertyException " );
		}
		 dto.setGeneralMsg("some fields are readOnly, can't be changed, please see API docs for details");
 
        return dto;
    }

    
    
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processUnrecognizedProperty(HttpMessageNotReadableException ex) {
    	
    	ValidationErrorDTO dto = new ValidationErrorDTO();
		if(log.isDebugEnabled()){
			log.debug("going to handle HttpMessageNotReadableException " );
		}
		 dto.setGeneralMsg( ex.getMessage());
 
        return dto;
    }

}
