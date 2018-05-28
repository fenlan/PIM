import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PIMTodo extends PIMEntity implements Date {
	private String date;
	private String todoItem;

	public PIMTodo() { super(); }
	public PIMTodo(String priority, String date, String todoItem) {
		super(priority);
		this.date = date;
		this.todoItem = todoItem;
	}

	public void setDate(String date) { this.date = date; }
	public String getDate() { return date; }

	public void setTodoItem(String todoItem) { this.todoItem = todoItem; }
	public String getTodoItem() { return todoItem; }

	public void fromString(String s) {
		String regx = "([^ ]*) ([^ ]*) ([^ ]*) ([^\n]*)";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(s);

		if (matcher.find()) {
			super.setPriority(matcher.group(2));
			setDate(matcher.group(3));
			setTodoItem(matcher.group(4));
		}
	}
	public String toString() { return "TODO " + super.Priority + " " + date + " " + todoItem ; }

	public void setPIMTodo() {
		String getStr;
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter date for todo item (MM/dd/yyyy):");
		getStr = sc.nextLine();
		while (!checkFormat(getStr)) {
			System.out.println("Enter the right format date for todo item:");
			getStr = sc.nextLine();
		}
		setDate(getStr);

		System.out.println("Enter todo text:");
		getStr = sc.nextLine();
		setTodoItem(getStr);

		System.out.println("Enter todo priority:");
		getStr = sc.nextLine();
		super.setPriority(getStr);
	}
	
	public boolean checkFormat(String date) {
		if (date == null)			return false;

		//set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

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