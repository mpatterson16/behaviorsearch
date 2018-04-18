package bsearch.fx;
//TODO add comments
public class DataCollectionTableRow {
	private String variable;
	private String code;
	
	public DataCollectionTableRow(String variable, String code) {
		this.variable = variable;
		this.code = code;
	}
	
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getVariable() {
		return variable;
	}
	
	public String getCode() {
		return code;
	}
	
	public String toString() {
		return variable + " " + code;
	}

}
