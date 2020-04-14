package boardgame.exceptions;

@SuppressWarnings("serial")
public class InvalidSettingsException extends RuntimeException {
	private boolean value;
	private String setting;
	public <T> InvalidSettingsException(String setting, boolean value) {
		this.value = value;
		this.setting = setting;
	}
	
	@Override
	public String toString() {
		return setting + " is " + value + ", but should be " + !value;
	}
}
