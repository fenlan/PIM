import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PIMAppointment extends PIMEntity implements Date {
	private String date;
	private String description;

	public PIMAppointment() { super(); }
	public PIMAppointment(String priority, String date, String description) {
		super(priority);
		this.date = date;
		this.description = description;
	}

	public void setDate(String date) { this.date = date; }
	public String getDate() { return date; }

	public void setDescription(String description) { this.description = description; }
	public String getDescription() { return description; }

	public void fromString(String s) {
		String regx = "([^ ]*) ([^ ]*) ([^ ]*) ([^\n]*)";
		Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
        	super.setPriority(matcher.group(2));
        	setDate(matcher.group(3));
        	setDescription(matcher.group(4));
        }
	}
	public String toString() { return "APPOINTMENT " + super.Priority + " " + date + " " + description; }

	public void setPIMAppointment() {
		String getStr;
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter date for appointment item:");
		getStr = sc.nextLine();
		while (!checkFormat(getStr)) {
			System.out.println("Enter the right format date for appointment item:");
			getStr = sc.nextLine();
		}
		setDate(getStr);

		System.out.println("Enter appointment text:");
		getStr = sc.nextLine();
		setDescription(getStr);

		System.out.println("Enter appointment priority:");
		getStr = sc.nextLine();
		super.setPriority(getStr);
	}
	
	public boolean checkFormat(String date) {
		if (date == null)			return false;

    	//set the format to use as a constructor argument
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    	if (date.trim().length() != dateFormat.toPattern().length())	return false;

    	dateFormat.setLenient(false);

    	try {
      		//parse the date parameter
      		dateFormat.parse(date.trim());
    	}
    	catch (ParseException pe) {
      		return false;
    	}
    	return true;
	}
	
}