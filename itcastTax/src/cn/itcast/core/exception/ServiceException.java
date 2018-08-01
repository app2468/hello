package cn.itcast.core.exception;

public class ServiceException extends SysException {

	public ServiceException() {
		super("ÒµÎñ²Ù×÷Ê§°Ü£¡");
	}

	public ServiceException(String message) {
		super(message);
	}
}
