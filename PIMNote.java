import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PIMNote extends PIMEntity {
	private String noteItem;

	public PIMNote() { super(); }
	public PIMNote(String priority, String noteItem) {
		super(priority);
		this.noteItem = noteItem;
	}

	public void setNoteItem(String noteItem) { this.noteItem = noteItem; }
	public String getNoteItem() { return noteItem; }

	public void fromString(String s) {
		String regx = "([^ ]*) ([^ ]*) ([^\n]*)";
		Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
        	super.setPriority(matcher.group(2));
        	setNoteItem(matcher.group(3));
        }
	}
	public String toString() {
		return "NOTE " + super.Priority + " " + noteItem;
	}

	public void setPIMNote() {
		String getStr;
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter note text:");
		getStr = sc.nextLine();
		setNoteItem(getStr);

		System.out.println("Enter note priority:");
		getStr = sc.next();
		super.setPriority(getStr);
	}
}