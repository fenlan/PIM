import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class PIMCollection implements Collection {
    private static String regxTODO = "^TODO";
    private static String regxNOTE = "^NOTE";
    private static String regexAPP = "^APPOINTMENT";
    private static String regexCONTACT = "^CONTACT";
    private static Pattern patternTODO = Pattern.compile(regxTODO);
    private static Pattern patternNOTE = Pattern.compile(regxNOTE);
    private static Pattern patternAPP = Pattern.compile(regexAPP);
    private static Pattern patternCON = Pattern.compile(regexCONTACT);

    public Set<PIMEntity> list = new HashSet<>();
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        int i = 0;
        PIMEntity[] result = new PIMEntity[size];
        for (PIMEntity item : list)
            result[i++] = item;
        return result;
    }

    @Override
    public boolean add(Object o) {
        boolean flag = true;
        try {
            PIMEntity p = (PIMEntity) o;
            list.add(p);
            size++;
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean remove(Object o) {
        boolean flag = true;
        try {
            PIMEntity p = (PIMEntity) o;
            list.remove(p);
            size--;
        }catch (Exception e){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean addAll(Collection c) {
        boolean flag = true;
        Iterator iterator = c.iterator();
        while (iterator.hasNext()){
            try {
                PIMEntity p = (PIMEntity) iterator.next();
                list.add(p);
                size++;
            }catch (Exception e){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public void clear() {
        list.clear();
        size = 0;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean flag = false;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
                PIMEntity p = (PIMEntity) iterator.next();
                if (!c.contains(p)) {
                    list.remove(p);
                    size--;
                    flag = true;
                }
        }
        return flag;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean flag = true;
        Iterator iterator = c.iterator();
        while (iterator.hasNext()){
            try {
                PIMEntity p = (PIMEntity) iterator.next();
                if (list.contains(p)) list.remove(p);
            }catch (Exception e){
                flag = false;
            }
        }
        size = 0;
        return flag;
    }

    @Override
    public boolean containsAll(Collection c) {
        Iterator iterator = c.iterator();
        while (iterator.hasNext()){
            try {
                PIMEntity p = (PIMEntity) iterator.next();
                if (!list.contains(p)) return false;
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        int i = 0;
        PIMEntity[] p;
        Class cs = a.getClass();
        p = (PIMEntity[]) Array.newInstance(cs,size);
        for (PIMEntity item : list)
            p[i++] = item;
        return p;
    }

    public Collection getTodos() {
        PIMCollection pimCollection = new PIMCollection();
        for(PIMEntity item : list){
            String str = item.toString();
            if (patternTODO.matcher(str).find())
                pimCollection.add(item);
        }

        return pimCollection;
    }

    public Collection getAppointments() {
        PIMCollection pimCollection = new PIMCollection();
        for(PIMEntity item : list){
            String str = item.toString();
            if (patternAPP.matcher(str).find())
                pimCollection.add(item);
        }

        return pimCollection;
    }

    public Collection getContact() {
        PIMCollection pimCollection = new PIMCollection();
        for(PIMEntity item : list){
            String str = item.toString();
            if (patternCON.matcher(str).find())
                pimCollection.add(item);
        }

        return pimCollection;
    }

    public Collection getNotes() {
        PIMCollection pimCollection = new PIMCollection();
        for(PIMEntity item : list){
            String str = item.toString();
            if (patternNOTE.matcher(str).find())
                pimCollection.add(item);
        }

        return pimCollection;
    }

    public Collection getItemsForDate(Date date) {
        PIMTodo todoitem = null;
        PIMAppointment appitem = null;
        PIMCollection pimCollection = new PIMCollection();
        for (PIMEntity item : list) {
            String str = item.toString();
            if (patternTODO.matcher(str).find()) {
                if (item instanceof PIMTodo)
                    todoitem = (PIMTodo)item;
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                if (todoitem.getDate().equals(dateFormat.format(date))) pimCollection.add(item);
            }
            else if (patternAPP.matcher(str).find()) {
                if (item instanceof PIMAppointment)
                    appitem = (PIMAppointment)item;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (appitem.getDate().equals(dateFormat.format(date))) pimCollection.add(item);
            }
        }

        return pimCollection;
    }

}