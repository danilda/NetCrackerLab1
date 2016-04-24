package ua.sumdu.j2se.Danil.tasks;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by User on 07.02.2016.
 */
public class ArrayTaskList extends TaskList {
    private class ArrayIterator implements Iterator<Task>{
        private int cursor;       // следующий элемент
        private int last = -1; // последний элемент

        public boolean hasNext() {
            return cursor < size();
        }

        public Task next(){
            if (!hasNext()) {
                throw new NoSuchElementException("Нет следующего елемента");
            } else {
                last = cursor;
                cursor++;
                return getTask(last);
            }
        }

        public void remove() {
            if (last < 0)
                throw new IllegalStateException();
            try {
                ArrayTaskList.this.remove(getTask(last));
                cursor = last;
                last = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private Task[] array = new Task[10];
    private int lastIndex = 0;

    public void add(Task task){
        if(task == null)
            throw new NullPointerException();
        if(lastIndex == array.length){
            Task[] arrayTmp = new Task[array.length+10];
            for(int i = 0; i < array.length; i++)
                arrayTmp[i] = array[i];
            arrayTmp[lastIndex] = task;
            lastIndex++;
            array = arrayTmp;
        }
        array[lastIndex] = task;
        lastIndex++;
    }
    public boolean remove(Task task){
        if(task == null)
            throw new NullPointerException("Удаление таска со значением null");
        boolean point = false;
        for(int i = 0; i < lastIndex; i++){
            if(task.equals(array[i]) == true && point == false){
                point = true;
                array[i] = null;
            } else if(point == true && array[i]!= null){
                array[i - 1] = array[i];
            } else if( point == true && array[i]== null ) {
                array[i-1] = null;
            }
        }
        if(point == true){
            lastIndex--;
            return true;
        }
        if(point == false){
            return false;
        }
        return false;
    }
    public int size(){
        return lastIndex;
    }

    public Task getTask(int index){
        if(index > lastIndex-1)
            throw new IndexOutOfBoundsException();
        return array[index];
    }

    public ArrayTaskList incoming(int from, int to){
        /*
        if(from < 0 )
            throw new IllegalArgumentException("Отрецательное время начала промежутка");
        if(to < 0 )
            throw new IllegalArgumentException("Отрецательное время начала конца промежутка");
        ArrayTaskList returnList = new ArrayTaskList();
        Task[] arrayTMP = new Task[lastIndex];
        int j = 0;
        for(int i = 0; i < lastIndex; i++){
            if(array[i].nextTimeAfter(from) != -1
            && array[i].nextTimeAfter(from) < to - from){
                arrayTMP[j] = array[i];
                j++;
            }

        }
        if(arrayTMP.length != 0){
            for(int i = 0 ; i <j; i++)
            {
                returnList.add(arrayTMP[i]);
            }
            return returnList;
        }

        return returnList;
        */
        return  null;
    }

    @Override
    public Iterator<Task> iterator() {
        return new ArrayIterator();
    }

    @Override
    public boolean equals(TaskList a) {
        Iterator thisIter = this.iterator();
        Iterator Iter = a.iterator();
        while (thisIter.hasNext() && Iter.hasNext()){
            if(!thisIter.next().equals(Iter.next()))
                return false;
        }
        if(thisIter.hasNext()||Iter.hasNext())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Iterator iter = this.iterator();
        while(iter.hasNext()){
            result *= iter.next().hashCode()/7;
        }
        return result;
    }

    @Override
    public String toString() {
        if (this.size()<=1){
            return "";
        }
        String string = new String();
        Iterator iter = this.iterator();
        while (iter.hasNext()){
            string+= iter.next().toString();
        }
        return string;
    }
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList a = (ArrayTaskList) super.clone();
        a.array = this.array;
        a.lastIndex = this.lastIndex;
        return a;
    }

}
