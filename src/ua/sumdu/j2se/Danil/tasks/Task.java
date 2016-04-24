package ua.sumdu.j2se.Danil.tasks;


import java.util.Date;

public class Task implements Cloneable {
    /*
    public static void main(String[] args) throws Exception {
        Task a = new Task("пробежка",26);
        Task b = new Task("пробежка1",40);
        Task c = new Task("пробежка2",64);
        Task d = new Task("пробежка3",78);
        Task d2;
        Task d3;
        System.out.println(a.getTitle());
        a.setTitle("Передумал бегать, хочу прыгать!!!!");
        System.out.println(a.getTitle());
        System.out.println(a.getTime());
        a.setTime(36);
        System.out.println(a.getTime());
        System.out.println(a.isActive());
        a.setActive(true);
        System.out.println(a.isActive());
        System.out.println(a.isRepeated());
        a.setTime(10, 64, 24);
        System.out.println(a.isRepeated());
        LinkedTaskList list = new LinkedTaskList();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        LinkedTaskList list2 = list.incoming(20, 41);
        d2 = list2.getTask(0);
        System.out.println(d2.getTitle());
        list2.remove(d2);
        d3 = list2.getTask(0);
        System.out.println(d3.getTitle());
        Task[] listTask = new Task[15];
        for(int i = 0; i < 15; i++){
            listTask[i] = new Task("Задание номер" + i + " занесено", 16);
            list2.add(listTask[i]);
        }
        d3 = list2.getTask(16);

        System.out.println(d3.getTitle());
        System.out.println("sdasd");
        System.out.println(5/2);
        d3.setTime(45);
        d3.setTitle("Буду качать пресс");
        Task d4 = d3.clone();
        System.out.println(d4.toString());
        System.out.println(d4.hashCode()/11);
        LinkedTaskList list4 = list2.clone();
        System.out.println(list4.toString());
        ArrayTaskList listArr1 = new ArrayTaskList();
        for(int i = 0; i < 15; i++){
            listTask[i] = new Task("Задание номер" + i + " занесено", 16);
            listArr1.add(listTask[i]);
        }
        Task d5 = listArr1.getTask(13);

        System.out.println(d3.getTitle());
        System.out.println("sdasd");
        System.out.println(5/2);
        d3.setTime(45);
        d3.setTitle("Буду качать Отжиматься");
        Task d6 = d5.clone();
        System.out.println(d4.toString());
        System.out.println(d4.hashCode()/11);
        ArrayTaskList listArr2 = listArr1.clone();
        System.out.println(list4.toString());



    }*/
    private final int milliseconds = 1000;
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;
    private boolean repeated;


//    Конструктор, що конструює неактивну задачу, яка виконується у заданий час без повторення із заданою назвою.
    public Task(String title, Date time){
        if(title.isEmpty())
            throw new NullPointerException("Пустой стринг");
        this.title = title;
        this.time = time;
        active = false;
        repeated = false;

    }

//    Конструктор , що конструює
//    неактивну задачу, яка виконується у заданому проміжку часу (і початок і кінець включно) із
//    заданим інтервалом і має задану назву.
    public Task(String title, Date start, Date end, int interval){
        if(title.isEmpty())
            throw new NullPointerException("Пустой стринг");
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        active = false;
        repeated = true;

    }
//    Методи для зчитування та встановлення назви задачі: String getTitle(),
//    void setTitle(String title).
    public String getTitle(){
        return title;
    }
    public void setTitle(String title)
    {
        if(title.isEmpty())
            throw new NullPointerException("Пустой стринг");
        this.title = title;
    }

//   Методи для зчитування та встановлення стану задачі: boolean isActive(),
//    void setActive(boolean active).
    public boolean isActive(){
        return active;
    }
    void setActive(boolean active){
        this.active = active;
    }

//    Методи для зчитування та зміни часу виконання для задач, що не повторюються:
//    o int getTime(), у разі, якщо задача повторюється метод має повертати час початку
//    повторення;
    public Date getTime(){
        if(repeated == true){
            return start;
        }
        return time;
    }
//    o void setTime(int time), у разі, якщо задача повторювалась, вона має стати такою,
//    що не повторюється.
    public void setTime(Date time){
        if(repeated == true){
            repeated = false;
        }
        this.time = time;

    }
//    Методи для зчитування та зміни часу виконання для задач, що повторюються:
//    o int getStartTime(), у разі, якщо задача не повторюється метод має повертати час
//    виконання задачі;
    public Date getStartTime(){
        if(repeated == true){
            return start;
        }
        return time;

    }
//    o int getEndTime(), у разі, якщо задача не повторюється метод має повертати час
//    виконання задачі;
    public Date getEndTime(){
        if(repeated == true){
            return end;
        }
        return time;

    }
//    o int getRepeatInterval(), у разі, якщо задача не повторюється метод має
//    повертати 0;
    public int getRepeatInterval(){
        if(repeated == true){
            return interval;
        }
        return 0;

    }
//    o void setTime(int start, int end, int interval), у разі, якщо задача не
//    повторювалася метод має стати такою, що повторюється.
    public void setTime(Date start, Date end, int interval){
        if(repeated == false){
            repeated = true;
        }
        this.start = start;
        this.interval = interval;
        this.end = end;

    }
//    Метод для перевірки повторюваності задачі boolean isRepeated().
    public boolean isRepeated(){

        return repeated;
    }

    public Date nextTimeAfter(Date current) {
        Date result = null;
        if (isActive()) {
            if (isRepeated()) {
                if (current.compareTo(start) < 0) {
                    result = start;
                } else {
                    if (current.before(end)) {
                        Date point = start;
                        long repeatNumber = ((end.getTime() - start.getTime())) / interval * milliseconds;
                        for (long i = 0; i <= repeatNumber; i++) {
                            Date next = new Date(point.getTime()  + interval * milliseconds);
                            if (current.compareTo(point) >= 0
                                    && current.compareTo(next) < 0
                                    && next.compareTo(end) <= 0) {
                                result = next;
                                break;
                            }
                            point = next;
                        }
                    }
                }
            } else {
                if (current.compareTo(time) < 0) {
                    result = time;
                }
            }
        }
        return result;
    }

    public boolean equals(Task obj) {
        if(obj != null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        return ( this.title.equals(obj.title)
                && this.time == obj.time
                && this.start == obj.start
                && this.end == obj.end
                && this.interval == obj.interval
                && this.active == obj.active
                && this.repeated == obj.repeated
        );
    }
    @Override
    public int hashCode() {
        return this.getTitle().hashCode()*11;
    }

    @Override
    public String toString() {
        if(!this.repeated) return "Задание : " + this.getTitle() + "\n Время : " + getTime() + "\n";
        return "Задание : " + this.getTitle() + "\n Время начала : "
                + getStartTime() + "\n время конца : " + getEndTime() + "\n"
                + "интервал повторения "+ getRepeatInterval() + "\n";
    }
    @Override
    public Task clone() throws CloneNotSupportedException {
//        Task a = new Task(this.title, this.time);
        Task a = (Task)super.clone();
        a.title = this.title;
        a.time = this.time;
        a.start = this.start;
        a.end = this.end;
        a.interval = this.interval;
        a.active = this.active;
        a.repeated = this.repeated;
        return a;
    }
}
