import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PIMContact extends PIMEntity {
	private String firstName;
	private String lastName;
	private String email;

	public PIMContact() { super(); }
	public PIMContact(String priority, String firstName, String lastName, String email) {
		super(priority);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public void setFirstName(String firstName) { this.firstName = firstName; }
	public String getFirstName() { return firstName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public String getLastName() { return lastName; }
	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return email; }

	public void fromString(String s) {
		String regx = "([^ ]*) ([^ ]*) ([^ ]*) ([^ ]*) ([^\n]*)";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(s);

		if (matcher.find()) {
			super.setPriority(matcher.group(2));
			setFirstName(matcher.group(3));
			setLastName(matcher.group(4));
			setEmail(matcher.group(5));
		}
	}
	public String toString() {
		return "CONTACT " + super.Priority + " " + firstName + " " + lastName + " " + email;
	}

	public void setPIMContact() {
		String getStr;
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter firstName text:");
		getStr = sc.next();
		setFirstName(getStr);

		System.out.println("Enter lastName text:");
		getStr = sc.next();
		setLastName(getStr);

		System.out.println("Enter email text:");
		getStr = sc.next();
		setEmail(getStr);

		System.out.println("Enter Contact priority:");
		getStr = sc.next();
		super.setPriority(getStr);
	}
}