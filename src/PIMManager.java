import java.util.Date;
import java.util.Scanner;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import redis.clients.jedis.Jedis;

public class PIMManager {
	private static String redisKey = "PIM";
	private static String  redisHost = "localhost";
	private static int  redisPort = 6379;
	private static Jedis jedis = new Jedis(redisHost,redisPort);
	// private static Set<PIMEntity> list = new HashSet<>();
	private static PIMCollection list = new PIMCollection();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to PIM.");

		while (true) {
			System.out.println("---Enter a command (suported commands are List Create Save Load Quit)---");
			System.out.println("---Enter a command (suported commands are GETTODO GETNOTE GETAPP GETCON)---");
			System.out.println("---Enter a command (suported commands GETDATE)---");
			switch (sc.nextLine()) {
				case "List" : list(); break;
				case "Create" : create(); break;
				case "Save" : save(); break;
				case "Load" : load(); break;
				case "Quit" : System.exit(1); break;
				case "GETTODO" : getTODO(); break;
				case "GETNOTE" : getNOTE(); break;
				case "GETAPP" : getAPP(); break;
				case "GETCON" : getCON(); break;
				case "GETDATE" : getDATE(); break;
				default:
					;
			}
		}
	}

	private static void list() {
		int i = 0;
		for (PIMEntity item : list.list) {
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
		for (PIMEntity item : list.list) {
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

	private static void getTODO() {
		int i = 0;
		Iterator iterator = list.getTodos().iterator();
		while (iterator.hasNext())
			System.out.println("Item " + ++i + ": " + (PIMEntity)iterator.next());
	}

	private static void getNOTE() {
		int i = 0;
		Iterator iterator = list.getNotes().iterator();
		while (iterator.hasNext())
			System.out.println("Item " + ++i + ": " + (PIMEntity)iterator.next());
	}

	private static void getAPP() {
		int i = 0;
		Iterator iterator = list.getAppointments().iterator();
		while (iterator.hasNext())
			System.out.println("Item " + ++i + ": " + (PIMEntity)iterator.next());
	}

	private static void getCON() {
		int i = 0;
		Iterator iterator = list.getContact().iterator();
		while (iterator.hasNext())
			System.out.println("Item " + ++i + ": " + (PIMEntity)iterator.next());
	}

	private static void getDATE() {
		int i = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Please Enter date as yyyy-MM-dd");
		String date = sc.nextLine();
		Date date1 = null;
		try {
			 date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			 // System.out.println(date1);
		} catch (Exception e) {
			System.out.println(e);
		}
		Iterator iterator = list.getItemsForDate(date1).iterator();
		while (iterator.hasNext())
			System.out.println("Item " + ++i + ": " + (PIMEntity)iterator.next());
	}
	
}