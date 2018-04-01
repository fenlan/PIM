import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import redis.clients.jedis.Jedis;

public class PIMManager {
	private static String redisKey = "PIM";
	private static String  redisHost = "localhost";
	private static int  redisPort = 6379;
	private static Jedis jedis = new Jedis(redisHost,redisPort);
	private static Set<PIMEntity> list = new HashSet<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to PIM.");

		while (true) {
			System.out.println("---Enter a command (suported commands are List Create Save Load Quit)---");
			switch (sc.nextLine()) {
				case "List" : list(); break;
				case "Create" : create(); break;
				case "Save" : save(); break;
				case "Load" : load(); break;
				case "Quit" : System.exit(1); break;
				default:
					;
			}
		}
	}

	private static void list() {
		int i = 0;
		for (PIMEntity item : list) {
			System.out.println("Item " + ++i + ": " + item.toString());
		}
	}

	private static void create() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter an item type ( todo, note, contact or appointment )");
		switch (sc.nextLine()) {
			case "todo" : 
				PIMTodo todo = new PIMTodo();
				todo.setPIMTodo();
				list.add(todo);
				break;
			case "note" :
				PIMNote note = new PIMNote();
				note.setPIMNote();
				list.add(note);
				break;
			case "appointment" :
				PIMAppointment appointment = new PIMAppointment();
				appointment.setPIMAppointment();
				list.add(appointment);
				break;
			case "contact" : 
				PIMContact contact = new PIMContact();
				contact.setPIMContact();
				list.add(contact);
				break;
			default:
				;
		}
	}

	private static void save() {
		int i = 0;
		int length = list.size();
		String[] items = new String[length];
		for (PIMEntity item : list) {
			items[i++] = item.toString();
		}
		jedis.sadd(redisKey, items);
		System.out.println("Items have been saved.");
	}

	private static void load() {
		int i = 0;
		String regxTODO = "^TODO";
		String regxNOTE = "^NOTE";
		String regexAPP = "^APPOINTMENT";
		String regexCONTACT = "^CONTACT";
		Pattern patternTODO = Pattern.compile(regxTODO);
		Pattern patternNOTE = Pattern.compile(regxNOTE);
		Pattern patternAPP = Pattern.compile(regexAPP);
		Pattern patternCON = Pattern.compile(regexCONTACT);

        
		for (String item : jedis.smembers(redisKey)) {
			if (patternTODO.matcher(item).find()) {
				PIMTodo todo = new PIMTodo();
				todo.fromString(item);
				list.add(todo);
			}
			else if (patternNOTE.matcher(item).find()) {
				PIMNote note = new PIMNote();
				note.fromString(item);
				list.add(note);
			}
			else if (patternAPP.matcher(item).find()) {
				PIMAppointment app = new PIMAppointment();
				app.fromString(item);
				list.add(app);
			}
			else if (patternCON.matcher(item).find()) {
				PIMContact cont = new PIMContact();
				cont.fromString(item);
				list.add(cont);
			}
		}
		System.out.println("Item hava been loaded from redis.");
	}
	
}