package ua.sumdu.j2se.Danil.tasks;

import java.util.*;

/**
 * Created by User on 25.04.2016.
 */
public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            Date nextTime = t.nextTimeAfter(start);
            if (nextTime != null && nextTime.compareTo(end) <= 0) {
                result.add(t);
            }
        }
        return result;
    }

    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        TreeMap<Date, Set<Task>> calendar = new TreeMap<>();
        Iterable<Task> inc = incoming(tasks, start, end);
        for (Task task : inc) {
            Date tmp = task.nextTimeAfter(start);
            while(tmp != null && tmp.compareTo(end) <= 0) {
                if (calendar.containsKey(tmp)) {
                    calendar.get(tmp).add(task);
                } else {
                    Set<Task> setOfTasks = new HashSet<>();
                    setOfTasks.add(task);
                    calendar.put(tmp, setOfTasks);
                }
                tmp = task.nextTimeAfter(tmp);
            }
        }
        return calendar;
    }
}
