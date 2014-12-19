package test.spring.validator;

import javax.inject.Named;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.InitializingBean;

@Named    // JSR spec way to set an entity
@XmlRootElement
public class ErrorInfo implements InitializingBean {
	private int status;  // http status
	private int code;
	private String msg;	
	private String devMsg;   // for example:  "File resource for path /uploads/foobar.txt does not exist.  Please wait 10 minutes until the upload batch completes before checking again."
	private String moreInfo;  // for example:  "http://www.mycompany.com/errors/40483"	
	
	
	public ErrorInfo(){
	}
	
	public ErrorInfo(int code, String msg){
		this.code =  code;
		this.msg =  msg;
	}
	
	public ErrorInfo(int status, int code, String msg){
		this(code, msg);
		this.status = status;		
	}	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDevMsg() {
		return devMsg;
	}

	public void setDevMsg(String devMsg) {
		this.devMsg = devMsg;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("             ==> afterPropertiesSet in ErrorInfo...");
	}

	

}
