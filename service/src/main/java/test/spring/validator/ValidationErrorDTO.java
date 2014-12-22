package test.spring.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * validation result data transfer Object
 */

public class ValidationErrorDTO {

    private Map<String, List<String> > fieldErrors = new HashMap<String, List<String> >();
    private String generalMsg;
    
    public ValidationErrorDTO() { 
    }
 
    public void addFieldError(String field, String message) {
        
        List<String> msg  = fieldErrors.get(field);
        if(msg == null){
        	msg = new ArrayList<String>();
        	msg.add(message);
        	fieldErrors.put(field, msg);
        }else{
        	msg.add(message);
        }
        
    }

	public Map<String, List<String> > getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, List<String> > fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public String getGeneralMsg() {
		return generalMsg;
	}

	public void setGeneralMsg(String generalMsg) {
		this.generalMsg = generalMsg;
	}
 

}