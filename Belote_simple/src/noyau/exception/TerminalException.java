package noyau.exception;

public class TerminalException extends RuntimeException {
	Exception ex;

	public TerminalException(Exception e) {
		ex = e;
	}
}